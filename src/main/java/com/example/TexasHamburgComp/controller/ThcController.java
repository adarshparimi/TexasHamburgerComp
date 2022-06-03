package com.example.TexasHamburgComp.controller;


import com.example.TexasHamburgComp.model.*;
import com.example.TexasHamburgComp.service.ThcService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThcController {

    private ThcService thcService;

    public ThcController(ThcService thcService){
        this.thcService = thcService;
    }

    @PostMapping("/addMenuItem")
    @ApiOperation(value = "This operation adds a menu item")
    public boolean addMenuItem(@RequestBody ThcMenuItem thcMenuItem){
//        System.out.println(thcMenu);
        thcService.addThcMenu(thcMenuItem);
        return true;
    }

    @GetMapping("/getMenu")
    @ApiOperation(value = "This operation retrieves all menu items")
    public List<ThcMenuItem> getAllMenu(){
        return thcService.getMenu();
    }

    @PostMapping("/deleteMenuItem")
    public boolean deleteMenuItem(@RequestBody String name){
        int deletedRecords = thcService.deleteMenuItem(name);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateMenuItem")
    public boolean updateMenuItem(@RequestBody ThcMenuItemUpdate menuItemUpdate){
        String item_name = menuItemUpdate.getItemName();
        ThcMenuItem update_menu_item = menuItemUpdate.getThcMenuItem();
        int updatedRecords = thcService.updateMenuItem(item_name, update_menu_item);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @PostMapping("/addReservations")
    @ApiOperation(value = "This operation add new reservations")
    public boolean addReservations(@RequestBody ThcReservation thcReservation){
        thcService.addReservation(thcReservation);
        return true;
    }

    @GetMapping("/getReservations")
    @ApiOperation(value = " This operation will get all reservation details")
    public List<ThcReservation> getAllReservations(){
        return thcService.getAllReservations();
    }

    @PostMapping("/deleteReservations")
    public boolean deleteReservations(@RequestBody int reserve_id){
        int deletedRecords = thcService.deleteReservation(reserve_id);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateReservation")
    public boolean updateReservation(@RequestBody ThcReservationUpdate thcReservationUpdate){
        int reserve_id = thcReservationUpdate.getReserveId();
        ThcReservation update_reserve = thcReservationUpdate.getThcReservation();
        int updatedRecords = thcService.updateReservation(reserve_id, update_reserve);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @PostMapping("/addOpenHours")
    @ApiOperation(value = "This operation adds open hours")
    public boolean addOpenHours(@RequestBody OpenHours openHours){
        thcService.addOpenHours(openHours);
        return true;
    }

    @GetMapping("/getOpenhours")
    @ApiOperation(value = "This operation will retreive all open hours")
    public List<OpenHours> getOpenHours(){

        return thcService.getOpenHours();
    }

    @PostMapping("/deleteOpenHours")
    public boolean deleteOpenHours(@RequestBody String day){
        int deletedRecords = thcService.deleteOpenHours(day);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateOpenHours")
    public boolean updateOpenHours(@RequestBody OpenHoursUpdate openHoursUpdate){
        String day = openHoursUpdate.getDay();
        OpenHours open_hours = openHoursUpdate.getOpenHours();
        int updatedRecords = thcService.updateOpenHours(day, open_hours);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @PostMapping("/addLocation")
    @ApiOperation(value = "This operation will add a location to database")
    public boolean addLocation(@RequestBody ThcLocation location){
        thcService.addLocation(location);
        return true;
    }

    @GetMapping("/getLocation")
    @ApiOperation(value = "This operation will get location details")
    public List<ThcLocation> getLocation(){
        return thcService.getLocation();
    }

    @PostMapping("/deleteLocation")
    public boolean deleteLocation(@RequestBody String location_name){
        int deletedRecords = thcService.deleteLocation(location_name);
        if(deletedRecords > 0)
            return true;
        return false;
    }

    @PostMapping("/updateLocation")
    public boolean updateLocations(@RequestBody ThcLocationUpdate thcLocationUpdate){
        String location_name = thcLocationUpdate.getLocation_name();
        ThcLocation update_location = thcLocationUpdate.getLocation();
        int updatedRecords = thcService.updateLocations(location_name, update_location);
        if(updatedRecords > 0)
            return true;
        return false;
    }

}
