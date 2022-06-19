package com.example.TexasHamburgComp.service;


import com.example.TexasHamburgComp.consumer.KafkaConsumer;
import com.example.TexasHamburgComp.exceptions.ResourceNotFoundException;
import com.example.TexasHamburgComp.model.*;
import com.example.TexasHamburgComp.producer.JsonKafkaProducer;
import com.example.TexasHamburgComp.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.Test;
import springfox.documentation.spring.web.json.Json;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
@Slf4j
public class DefaultThcService implements ThcService{
    ThcMenuRepository thcMenuRepository;
    ThcReserveRepository thcReserveRepository;
    ThcOpenHoursRepository thcOpenHoursRepository;
    ThcLocationRepository thcLocationRepository;
    ThcElasticsearchRepository thcElasticsearchRepository;
    SecurityRepository securityRepository;

    @Autowired
    public DefaultThcService(ThcMenuRepository thcMenuRepository, ThcReserveRepository thcReserveRepository,
                             ThcOpenHoursRepository thcOpenHoursRepository, ThcLocationRepository thcLocationRepository,
                             ThcElasticsearchRepository thcElasticsearchRepository,SecurityRepository securityRepository){
        this.thcMenuRepository = thcMenuRepository;
        this.thcLocationRepository = thcLocationRepository;
        this.thcOpenHoursRepository = thcOpenHoursRepository;
        this.thcReserveRepository = thcReserveRepository;
        this.thcElasticsearchRepository = thcElasticsearchRepository;
        this.securityRepository = securityRepository;
    }

    @Override
    public boolean kafkaProducer(DailyOrders dailyOrders){
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonKafkaProducer jsonKafkaProducer = new JsonKafkaProducer();
            JsonNode jsonNode = objectMapper.valueToTree(dailyOrders);
        jsonKafkaProducer.runProducer(jsonNode);
        return true;
    }

    @Override
    public boolean register(User user){
        try {
            securityRepository.save(user);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User details invalid");
        }
        return true;
    }

    @Override
    public boolean login(User login){
        List<User> users = securityRepository.selectUser(login.getUsername());
        if(users.isEmpty()){
            throw new ResourceNotFoundException("No Menu items available");
        }
        else{
            if(Objects.equals(users.get(0).getPassword(), login.getPassword())){
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public boolean addThcMenu(ThcMenuItem thcMenuItem) {
        try{
//            thcMenuRepository.save(thcMenuItem);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Menu Item details invalid");
        }
        thcElasticsearchRepository.createMenuItem(thcMenuItem);
        log.info("added Menu item = {}",thcMenuItem);
        return true;
    }

    @Override
    public List<ThcMenuItem> getMenu() {
        List<ThcMenuItem> result = (List<ThcMenuItem>)thcMenuRepository.findAll();
        if(result.isEmpty()){
            throw new ResourceNotFoundException("No Menu items available");
        }else{
            log.info("Get Menu: {}",result);
            return result;
        }

    }

    @Override
    @Test
    @Transactional
    public int deleteMenuItem(String name) {
        String removedItem = thcElasticsearchRepository.removeMenuItem(name);
        log.info("Removed item from index {}", removedItem);
        int deletedRecords = 0;
        try {
            deletedRecords = thcMenuRepository.deleteItemName(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "MenuItem details invalid");
        }
        return deletedRecords;
    }

    @Override
    @Transactional
    public int updateMenuItem(String item_name,ThcMenuItem update_menu_item){
        List<ThcMenuItem> thcMenuItemList = thcMenuRepository.selectLocation(item_name);
        if(thcMenuItemList.isEmpty()){
            throw new ResourceNotFoundException("No Menu items available");
        }
        else{
            log.info("Retreived menuitem deatils: {}",thcMenuItemList);

            long update_id = thcMenuItemList.get(0).getItemId();
            System.out.println("update_id"+ update_id);
            update_menu_item.setItemId(update_id);
            System.out.println("to be updated - "+ update_menu_item);
            ThcMenuItem updated;
            try{
                updated = thcMenuRepository.save(update_menu_item);
            }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Menu item details are invalid");
            }
            log.info("The updated MenuItem: {}",updated);
            return 1;
        }
    }
    @Override
    public Page<ThcMenuItem> findMenuItemPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<ThcMenuItem> foundMenuItems;
        try{
            foundMenuItems = thcElasticsearchRepository.findMenuItemPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        }catch(Exception e){
            throw new ResourceNotFoundException("No Menu item available");
        }
        log.info("The retrieved MenuItems: {}",foundMenuItems);
        return foundMenuItems;
    }


    @Override
    public boolean addReservation(ThcReservation thcReservation) {
        thcElasticsearchRepository.createReservation(thcReservation);
        ThcReservation savedReserve;
        try{
            savedReserve = thcReserveRepository.save(thcReservation);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reservation details not valid");
        }
        log.info("Saved Reservation: {}",savedReserve);
//        System.out.println(thcReservation);
        return true;
    }

    @Override
    public List<ThcReservation> getAllReservations() {
        List<ThcReservation> result = (List<ThcReservation>) thcReserveRepository.findAll();
        if(result.isEmpty()){
            throw new ResourceNotFoundException("No Reservation found");
        }
        else{
            log.info("Get Reservations: {}",result);
            return result;
        }
    }

    @Override
    @Test
    @Transactional
    public int deleteReservation(int reserve_id){
        int deletedRecords;
        try{
            deletedRecords = thcReserveRepository.deleteReservation(reserve_id);
        }catch(Exception e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reservation details provided are invalid");
        }
        log.info("Number of deleted reservations: {}",deletedRecords);
        if(deletedRecords > 0) return 1;
        return 0;
    }

    @Override
    @Transactional
    public int updateReservation(int reserve_id,ThcReservation update_reserve){
        List<ThcReservation> reservation = thcReserveRepository.selectReservation(reserve_id);
        if(reservation.isEmpty()){
            throw new ResourceNotFoundException("Reservation with id= "+ reserve_id +" is unavailable");
        }
        else {

            long update_id = reservation.get(0).getReserveId();
            System.out.println("update_id" + update_id);
            update_reserve.setReserveId(update_id);
            System.out.println("to be updated - " + update_reserve);

            ThcReservation updated;
            try {
                updated = thcReserveRepository.save(update_reserve);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation details are invalid");
            }
            log.info("Updated Reservation: {}", updated);
            return 1;
        }
    }

    @Override
    public Page<ThcReservation> findReservationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<ThcReservation>result = thcElasticsearchRepository.findReservationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        if(result.isEmpty()){
            throw new ResourceNotFoundException("No reservations available");
        }
        else{
            log.info("Reservations paginated and Sorted: {}",result);
            return result;
        }
    }


    @Override
    public boolean addOpenHours(OpenHours openHours){
        try{
            thcOpenHoursRepository.save(openHours);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Open Hours data not valid");
        }
        thcElasticsearchRepository.createOpenHours(openHours);
        log.info("The added OpenHour: {}",openHours);
        return true;
    }

    @Override
    public List<OpenHours> getOpenHours(){
        List<OpenHours> result = (List<OpenHours>) thcOpenHoursRepository.findAll();
        if(result.isEmpty()){
            throw new ResourceNotFoundException("No OpenHours available");
        }
        else{
            log.info("Get OpenHours: {}",result);
            return result;
        }
    }

    @Override
    @Test
    @Transactional
    public int deleteOpenHours(String day){
        int deletedRecords;
        try{
            deletedRecords = thcOpenHoursRepository.deleteOpenHours(day);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Open Hours details not valid");
        }
        log.info("Number of deleted OpenHours: {}", deletedRecords);
        if(deletedRecords > 0) return 1;
        return 0;
    }

    @Override
    @Transactional
    public int updateOpenHours(String day, OpenHours openHours){
        OpenHours result;
        int res;
        try{
            res = thcOpenHoursRepository.deleteOpenHours(day);
        }catch(Exception e){
            throw new ResourceNotFoundException("No OpenHours with day ="+ day +"available");
        }
        try{
            result = thcOpenHoursRepository.save(openHours);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"OpenHour details invalid");
        }
        log.info("The updated OpenHours: {}",result);
        return 1;

    }

    public Page<OpenHours> findOpenHoursPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<OpenHours> result;
        try{
            result = thcElasticsearchRepository.findOpenHoursPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        }catch(Exception e){
            throw new ResourceNotFoundException("No OpenHour details available");
        }
        log.info("found OpenHours paginated and sorted: {}",result);
        return result;
    }

    @Override
    public boolean addLocation(ThcLocation location){
        try{
            thcLocationRepository.save(location);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location details invalid",e);
        }
        thcElasticsearchRepository.createLocation(location);
        log.info("Added location: {}",location);
        return true;
    }

    @Override
    public List<ThcLocation> getLocation(){
        List<ThcLocation> results = (List<ThcLocation>) thcLocationRepository.findAll();
        if(results.isEmpty()){
            throw new ResourceNotFoundException("No Location details available");
        }
        else{
            return results;
        }

    }

    @Override
    @Test
    @Transactional
    public int deleteLocation(String location_name){
        int deletedRecords;
        try{
            deletedRecords = thcLocationRepository.deleteLocation(location_name);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location details invalid");
        }
        log.info("No. of deleted records: {}",deletedRecords);
        if(deletedRecords > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateLocations(String location_name, ThcLocation update_location){
        List<ThcLocation> location;
        try{
            location = thcLocationRepository.selectLocation(location_name);
        }catch(Exception e){
            throw new ResourceNotFoundException("No Location with name = "+ location_name +" available");
        }
        System.out.println("sl = "+ location);

        String update_id = String.valueOf(location.get(0).getLocationId());
        System.out.println("update_id"+ update_id);
        update_location.setLocationId(Integer.parseInt(update_id));
        System.out.println("to be updated - "+ update_location);
        thcElasticsearchRepository.updLocation(update_location);
        try{
            thcLocationRepository.save(update_location);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location details invalid");
        }
        System.out.println("l - "+location);
        return 1;

    }

    @Override
    public Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<ThcLocation> result;
        try{
            result = thcElasticsearchRepository.findLocationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        }catch(Exception e){
            throw new ResourceNotFoundException("No OpenHour details available");
        }
        log.info("found locations paginated and sorted: {}",result);
        return result;
    }

    @Override
    public ThcLocation findLocationById(String id){
        return thcElasticsearchRepository.findLocationById(id);
    }
    @Override
    public void batchUpsert(List<ThcLocation> thcLocationList) {
        thcElasticsearchRepository.batchUpsert(thcLocationList);
    }


}
