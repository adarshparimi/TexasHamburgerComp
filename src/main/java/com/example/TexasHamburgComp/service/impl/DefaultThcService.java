package com.example.TexasHamburgComp.service.impl;

import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import com.example.TexasHamburgComp.repo.*;
import com.example.TexasHamburgComp.service.ThcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class DefaultThcService implements ThcService{
    @Autowired
    ThcMenuRepository thcMenuRepository;
    @Autowired
    ThcReserveRepository thcReserveRepository;
    @Autowired
    ThcOpenHoursRepository thcOpenHoursRepository;
    @Autowired
    ThcLocationRepository thcLocationRepository;
    @Autowired
    ThcElasticsearchRepository thcElasticsearchRepository;


    @Override
    public boolean addThcMenu(ThcMenuItem thcMenuItem) {
        thcMenuRepository.save(thcMenuItem);
        thcElasticsearchRepository.createMenuItem(thcMenuItem);
        System.out.println(thcMenuItem);
        return true;
    }

    @Override
    public List<ThcMenuItem> getMenu() {
        return (List<ThcMenuItem>)thcMenuRepository.findAll();
    }

    @Override
    @Test
    @Transactional
    public int deleteMenuItem(String name){
        String removedItem = thcElasticsearchRepository.removeMenuItem(name);
        log.info("Removed item from index {}",removedItem);
        return thcMenuRepository.deleteItemName(name);
    }

    @Override
    @Transactional
    public int updateMenuItem(String item_name,ThcMenuItem update_menu_item){
        List<ThcMenuItem> thcMenuItemList = thcMenuRepository.selectLocation(item_name);
        System.out.println("sl = "+ thcMenuItemList);

        String update_id = thcMenuItemList.get(0).getItemId();
        System.out.println("update_id"+ update_id);
        update_menu_item.setItemId(update_id);
        System.out.println("to be updated - "+ update_menu_item);

        ThcMenuItem updated = thcMenuRepository.save(update_menu_item);
        log.info("The updated MenuItem: {}",updated);
        if(updated != null)
            return 1;
        return 0;
    }
    @Override
    public Page<ThcMenuItem> findMenuItemPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<ThcMenuItem> foundMenuItems = thcElasticsearchRepository.findMenuItemPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        log.info("The retrieved MenuItems: {}",foundMenuItems);
        return foundMenuItems;
    }


    @Override
    public boolean addReservation(ThcReservation thcReservation) {
        thcElasticsearchRepository.createReservation(thcReservation);
        ThcReservation savedReserve = thcReserveRepository.save(thcReservation);
        log.info("Saved Reservation: {}",savedReserve);
//        System.out.println(thcReservation);
        return true;
    }

    @Override
    public List<ThcReservation> getAllReservations() {
        return (List<ThcReservation>)thcReserveRepository.findAll();
    }

    @Override
    @Test
    @Transactional
    public int deleteReservation(int reserve_id){
        int deletedRecords = thcReserveRepository.deleteReservation(reserve_id);
        log.info("Number of deleted reservations: {}",deletedRecords);
        if(deletedRecords > 0) return 1;
        return 0;
    }

    @Override
    @Transactional
    public int updateReservation(int reserve_id,ThcReservation update_reserve){
        List<ThcReservation> reservation = thcReserveRepository.selectReservation(reserve_id);
        System.out.println("sl = "+ reservation);

        int update_id = reservation.get(0).getReserveId();
        System.out.println("update_id"+ update_id);
        update_reserve.setReserveId(update_id);
        System.out.println("to be updated - "+ update_reserve);

        ThcReservation updated = thcReserveRepository.save(update_reserve);
        log.info("Updated Reservation: {}",updated);
        if(updated != null)
            return 1;
        return 0;
    }

    @Override
    public Page<ThcReservation> findReservationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        return thcElasticsearchRepository.findReservationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }


    @Override
    public boolean addOpenHours(OpenHours openHours){
        thcOpenHoursRepository.save(openHours);
        thcElasticsearchRepository.createOpenHours(openHours);
        System.out.println(openHours);
        return true;
    }
    @Override
    public List<OpenHours> getOpenHours(){
        return (List<OpenHours>) thcOpenHoursRepository.findAll();
    }

    @Override
    @Test
    @Transactional
    public int deleteOpenHours(String day){
        int deletedRecords = thcOpenHoursRepository.deleteOpenHours(day);
        log.info("Number of deleted OpenHours: {}", deletedRecords);
        if(deletedRecords > 0) return 1;
        return 0;
    }

    @Override
    @Transactional
    public int updateOpenHours(String day, OpenHours openHours){
        thcOpenHoursRepository.deleteOpenHours(day);
        thcOpenHoursRepository.save(openHours);
        return 1;

    }

    public Page<OpenHours> findOpenHoursPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        return thcElasticsearchRepository.findOpenHoursPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }

    @Override
    public boolean addLocation(ThcLocation location){
        thcLocationRepository.save(location);
        thcElasticsearchRepository.createLocation(location);
        System.out.println(location);
        return true;
    }

    @Override
    public List<ThcLocation> getLocation(){
        return (List<ThcLocation>) thcLocationRepository.findAll();
    }

    @Override
    @Test
    @Transactional
    public int deleteLocation(String location_name){
        int deletedRecords = thcLocationRepository.deleteLocation(location_name);
        if(deletedRecords > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateLocations(String location_name, ThcLocation update_location){
        List<ThcLocation> location = thcLocationRepository.selectLocation(location_name);
        System.out.println("sl = "+ location);

        String update_id = location.get(0).getLocationId();
        System.out.println("update_id"+ update_id);
        update_location.setLocationId(update_id);
        System.out.println("to be updated - "+ update_location);
        thcElasticsearchRepository.updLocation(update_location);
        thcLocationRepository.save(update_location);
        System.out.println("l - "+location);
        return 1;

    }

    @Override
    public Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        return thcElasticsearchRepository.findLocationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
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
