package com.example.TexasHamburgComp.service;

import com.example.TexasHamburgComp.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ThcService {
    boolean addThcMenu(ThcMenuItem thcMenuItem);
    List<ThcMenuItem> getMenu();
    int deleteMenuItem(String name);
    int updateMenuItem(String item_name, ThcMenuItem update_menu_item);
    Page<ThcMenuItem> findMenuItemPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);

    boolean addReservation(ThcReservation thcReservation);
    List<ThcReservation> getAllReservations();
    int deleteReservation(int reserve_id);
    int updateReservation(int reserve_id,ThcReservation update_reserve);
    Page<ThcReservation> findReservationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);

    boolean addOpenHours(OpenHours openHours);
    List<OpenHours> getOpenHours();
    int deleteOpenHours(String day);
    int updateOpenHours(String day, OpenHours open_hours);
    Page<OpenHours> findOpenHoursPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);

    boolean addLocation(ThcLocation location);
    List<ThcLocation> getLocation();
    int deleteLocation(String location_name);
    int updateLocations(String location_name, ThcLocation update_location);
    Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);
    ThcLocation findLocationById(String id);
    void batchUpsert(List<ThcLocation> thcLocationList);

    boolean kafkaProducer(DailyOrders dailyOrders);

    boolean register(UserReq user);
    boolean login(UserReq login);


}
