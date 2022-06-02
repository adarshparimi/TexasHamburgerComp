package com.example.TexasHamburgComp.service;

import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.List;

@Service
public interface ThcService {
    public boolean addThcMenu(ThcMenuItem thcMenuItem);
    public List<ThcMenuItem> getMenu();
    public int deleteMenuItem(String name);
    public boolean addReservation(ThcReservation thcReservation);
    public List<ThcReservation> getAllReservations();
    public int deleteReservation(int reserve_id);
    public boolean addOpenHours(OpenHours openHours);
    public List<OpenHours> getOpenHours();
    public int deleteOpenHours(String day);
    public boolean addLocation(ThcLocation location);
    public List<ThcLocation> getLocation();
    public int deleteLocation(String location_name);
    public int updateLocations(String location_name, ThcLocation update_location);

}
