package com.example.TexasHamburgComp.service;

import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.List;

@Service
public interface ThcService {
    boolean addThcMenu(ThcMenuItem thcMenuItem);
    List<ThcMenuItem> getMenu();
    int deleteMenuItem(String name);
    int updateMenuItem(String item_name, ThcMenuItem update_menu_item);

    boolean addReservation(ThcReservation thcReservation);
    List<ThcReservation> getAllReservations();
    int deleteReservation(int reserve_id);
    int updateReservation(int reserve_id,ThcReservation update_reserve);

    boolean addOpenHours(OpenHours openHours);
    List<OpenHours> getOpenHours();
    int deleteOpenHours(String day);
    int updateOpenHours(String day, OpenHours open_hours);

    boolean addLocation(ThcLocation location);
    List<ThcLocation> getLocation();
    int deleteLocation(String location_name);
    int updateLocations(String location_name, ThcLocation update_location);

}
