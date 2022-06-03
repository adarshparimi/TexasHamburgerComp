package com.example.TexasHamburgComp.service.impl;

import com.example.TexasHamburgComp.model.*;
import com.example.TexasHamburgComp.repo.*;
import com.example.TexasHamburgComp.service.ThcService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;

@Service
public class DefaultThcService implements ThcService{

    @Autowired
    ThcMenuRepository thcMenuRepository;
    @Autowired
    ThcReserveRepository thcReserveRepository;
    @Autowired
    ThcOpenHoursRepository thcOpenHoursRepository;
    @Autowired
    ThcLocationRepository thcLocationRepository;

    @Override
    public boolean addThcMenu(ThcMenuItem thcMenuItem) {
        thcMenuRepository.save(thcMenuItem);
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
        if(updated != null)
            return 1;
        return 0;
    }


    @Override
    public boolean addReservation(ThcReservation thcReservation) {
        thcReserveRepository.save(thcReservation);
        System.out.println(thcReservation);
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
        if(updated != null)
            return 1;
        return 0;
    }


    @Override
    public boolean addOpenHours(OpenHours OpenHours){
        thcOpenHoursRepository.save(OpenHours);
        System.out.println(OpenHours);
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
        if(deletedRecords > 0) return 1;
        return 0;
    }


    @Override
    @Transactional
    public int updateOpenHours(String day, OpenHours openHours){
        List<OpenHours> openHoursList = thcOpenHoursRepository.selectOpenHours(day);
        System.out.println("sl = "+ openHoursList);

        thcOpenHoursRepository.save(openHours);
        return 1;

    }

    @Override
    public boolean addLocation(ThcLocation location){
        thcLocationRepository.save(location);
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

        thcLocationRepository.save(update_location);
        System.out.println("l - "+location);
        return 1;

    }

}
