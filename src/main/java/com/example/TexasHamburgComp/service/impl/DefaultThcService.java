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
        List<ThcMenuItem> menuList = (List<ThcMenuItem>)thcMenuRepository.findAll();
        return menuList;
    }

    @Override
    @Test
    @Transactional
    public int deleteMenuItem(String name){
        int deletedRecords = thcMenuRepository.deleteItemName(name);
        return deletedRecords;
    }

    @Override
    public boolean addReservation(ThcReservation thcReservation) {
        thcReserveRepository.save(thcReservation);
        System.out.println(thcReservation);
        return true;
    }

    @Override
    public List<ThcReservation> getAllReservations() {
        List<ThcReservation> reserveList = (List<ThcReservation>)thcReserveRepository.findAll();
        return reserveList;
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
    public boolean addOpenHours(OpenHours OpenHours){
        thcOpenHoursRepository.save(OpenHours);
        System.out.println(OpenHours);
        return true;
    }
    @Override
    public List<OpenHours> getOpenHours(){
        List<OpenHours> openHoursList = (List<OpenHours>) thcOpenHoursRepository.findAll();
        return openHoursList;
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
    public boolean addLocation(ThcLocation location){
        thcLocationRepository.save(location);
        System.out.println(location);
        return true;
    }

    @Override
    public List<ThcLocation> getLocation(){
        List<ThcLocation> locationList = (List<ThcLocation>) thcLocationRepository.findAll();
        return locationList;
    }

    @Override
    @Test
    @Transactional
    public int deleteLocation(String location_name){
//        int deletedRecords = thcLocationRepository.deleteLocation(location_name);
//        if(deletedRecords > 0) return 1;
        int deletedRecords = thcLocationRepository.deleteLocation(location_name);
        System.out.println(deletedRecords);
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
//        if(location != null)
//        return 0;
    }

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//
//        com.example.TexasHamburgComp.model.User user = userRepository.findUserByUsername(userName);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
////        user.getAuthorities()
////                .forEach(role -> {
////                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()
////                            .getName()));
////                });
//
//        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
//    }

}
