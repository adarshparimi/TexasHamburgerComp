package com.example.TexasHamburgComp.controller;

import com.example.TexasHamburgComp.interceptor.ApiExecTimeInterceptor;
import com.example.TexasHamburgComp.repo.ApiExecTimeRepository;
import com.example.TexasHamburgComp.service.impl.DefaultThcService;
import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import com.example.TexasHamburgComp.model.*;
import com.example.TexasHamburgComp.service.ThcService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ThcController {

    private ThcService thcService;
    private ApiExecTimeInterceptor apiExecTimeInterceptor;

    public ThcController(ThcService thcService){
        this.thcService = thcService;
    }

    @PostMapping("/addMenuItem")
    @ApiOperation(value = "This operation adds a menu item")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public boolean addMenuItem(@RequestBody ThcMenuItem thcMenuItem){
//        System.out.println(thcMenu);
        boolean result = thcService.addThcMenu(thcMenuItem);
        return result;
    }

    @GetMapping("/getMenu")
    @ApiOperation(value = "This operation retrieves all menu items")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public List<ThcMenuItem> getAllMenu(){
        return thcService.getMenu();
    }

    @PostMapping("/deleteMenuItem")
    @ApiOperation(value = "This operation is to delete a menu item")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean deleteMenuItem(@RequestBody String name){
        int deletedRecords = thcService.deleteMenuItem(name);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateMenuItem")
    @ApiOperation(value = "This operation updates a menu item")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean updateMenuItem(@RequestBody ThcMenuItemUpdate menuItemUpdate){
        String item_name = menuItemUpdate.getItemName();
        ThcMenuItem update_menu_item = menuItemUpdate.getThcMenuItem();
        int updatedRecords = thcService.updateMenuItem(item_name, update_menu_item);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @RequestMapping(value = "/findMenuItems",method = RequestMethod.GET)
    @ApiOperation(value = "find all menu items paginated and sorted")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public Page<ThcMenuItem> findMenuItemPaginatedAndSorted(@RequestParam("page") final String page,
                                                             @RequestParam("size") final String size,
                                                             @RequestParam(name = "sortBy",required = false) final String sortBy,
                                                             @RequestParam(name = "sortOrder",required = false, defaultValue = "ASC") final String sortOrder,
                                                             @RequestParam(name = "fields", required = false) final String fields){
        return thcService.findMenuItemPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }

    @PostMapping("/addReservations")
    @ApiOperation(value = "This operation add new reservations")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean addReservations(@RequestBody ThcReservation thcReservation){
        thcService.addReservation(thcReservation);
        return true;
    }

    @GetMapping("/getReservations")
    @ApiOperation(value = " This operation will get all reservation details")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public List<ThcReservation> getAllReservations(){
        return thcService.getAllReservations();
    }

    @PostMapping("/deleteReservations")
    @ApiOperation(value = "This operation deletes a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean deleteReservations(@RequestBody int reserve_id){
        int deletedRecords = thcService.deleteReservation(reserve_id);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateReservation")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean updateReservation(@RequestBody ThcReservationUpdate thcReservationUpdate){
        int reserve_id = thcReservationUpdate.getReserveId();
        ThcReservation update_reserve = thcReservationUpdate.getThcReservation();
        int updatedRecords = thcService.updateReservation(reserve_id, update_reserve);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @RequestMapping(value = "/findReservations",method = RequestMethod.GET)
    @Operation(summary = "find all reservations paginated and sorted")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public Page<ThcReservation> findReservationsPaginatedAndSorted(@RequestParam("page") final String page,
                                                             @RequestParam("size") final String size,
                                                             @RequestParam(name = "sortBy",required = false) final String sortBy,
                                                             @RequestParam(name = "sortOrder",required = false, defaultValue = "ASC") final String sortOrder,
                                                             @RequestParam(name = "fields", required = false) final String fields){
        return thcService.findReservationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }

    @PostMapping("/addOpenHours")
    @ApiOperation(value = "This operation adds open hours")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
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
    @ApiOperation("This operation deletes an Open Hour field")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean deleteOpenHours(@RequestBody String day){
        int deletedRecords = thcService.deleteOpenHours(day);
        if(deletedRecords == 1)
            return true;
        return false;
    }

    @PostMapping("/updateOpenHours")
    @ApiOperation(value = "This operation updates an OpenHours row")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public boolean updateOpenHours(@RequestBody OpenHoursUpdate openHoursUpdate){
        String day = openHoursUpdate.getDay();
        OpenHours open_hours = openHoursUpdate.getOpenHours();
        int updatedRecords = thcService.updateOpenHours(day, open_hours);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @RequestMapping(value = "/findOpenHours",method = RequestMethod.GET)
    @Operation(summary = "find all locations paginated and sorted")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public Page<ThcLocation> findOpenHoursPaginatedAndSorted(@RequestParam("page") final String page,
                                                             @RequestParam("size") final String size,
                                                             @RequestParam(name = "sortBy",required = false) final String sortBy,
                                                             @RequestParam(name = "sortOrder",required = false, defaultValue = "ASC") final String sortOrder,
                                                             @RequestParam(name = "fields", required = false) final String fields){
        return thcService.findLocationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }

    @PostMapping("/addLocation")
    @ApiOperation(value = "This operation will add a location to database")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public boolean addLocation(@RequestBody ThcLocation location){
        thcService.addLocation(location);
        return true;
    }

    @GetMapping("/getLocation")
    @ApiOperation(value = "This operation will get location details")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public ResponseEntity<?> getLocation(){
        List<ThcLocation> location =  thcService.getLocation();
        return new ResponseEntity<>(location,HttpStatus.OK);
    }

    @PostMapping("/deleteLocation")
    @ApiOperation(value = "This operation deletes a location")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public boolean deleteLocation(@RequestBody String location_name){
        int deletedRecords = thcService.deleteLocation(location_name);
        if(deletedRecords > 0)
            return true;
        return false;
    }

    @PostMapping("/updateLocation")
    @ApiOperation(value = "This operation updates a location")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public boolean updateLocations(@RequestBody ThcLocationUpdate thcLocationUpdate){
        String location_name = thcLocationUpdate.getLocation_name();
        ThcLocation update_location = thcLocationUpdate.getLocation();
        int updatedRecords = thcService.updateLocations(location_name, update_location);
        if(updatedRecords > 0)
            return true;
        return false;
    }

    @RequestMapping(value = "/findLocations",method = RequestMethod.GET)
    @Operation(summary = "find all locations paginated and sorted")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public Page<ThcLocation> findLocationsPaginatedAndSorted(@RequestParam("page") final String page,
                                                       @RequestParam("size") final String size,
                                                       @RequestParam(name = "sortBy",required = false) final String sortBy,
                                                       @RequestParam(name = "sortOrder",required = false, defaultValue = "ASC") final String sortOrder,
                                                       @RequestParam(name = "fields", required = false) final String fields){
        return thcService.findLocationsPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }

    @RequestMapping(value = "/findLocationById", method = RequestMethod.GET)
    @Operation(summary = "find a location by it's ID")
//    @ApiResponses(value ={
//            @ApiResponse(code = 200,message = "Ok"),
//            @ApiResponse(code = 201, message = "Added"),
//            @ApiResponse(code = 500,message = "Internal Server Error")
//    })
    public ThcLocation findOneById(@RequestParam("id") final String id){
        return thcService.findLocationById(id);
    }

}
