package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThcElasticsearchRepository {

    ThcMenuItem createMenuItem(ThcMenuItem thcMenuItem);
    Page<ThcMenuItem> findMenuItemPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields);
    String removeMenuItem(String itemName);

    ThcReservation createReservation(ThcReservation thcReservation);
    Page<ThcReservation> findReservationsPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields);

    OpenHours createOpenHours(OpenHours openHours);
    Page<OpenHours> findOpenHoursPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields);

    ThcLocation createLocation(ThcLocation thcLocation);
    ThcLocation findLocationById(String locationId);
    Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields);
    void batchUpsert(List<ThcLocation> thcLocationList);

}
