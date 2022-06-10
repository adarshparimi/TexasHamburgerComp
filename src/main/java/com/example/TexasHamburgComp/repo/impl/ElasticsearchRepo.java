package com.example.TexasHamburgComp.repo.impl;

import com.example.TexasHamburgComp.model.OpenHours;
import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import com.example.TexasHamburgComp.repo.ThcElasticsearchRepository;
import com.example.TexasHamburgComp.repo.ThcLocationRepository;
import com.example.TexasHamburgComp.repo.ThcMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;


@Repository
@Slf4j
public class ElasticsearchRepo implements ThcElasticsearchRepository {
    private final String LOCATION_INDEX = "location_v1";
    private final String MENUITEM_INDEX = "menuitem_v1";
    private final String RESERVATION_INDEX = "reservation_v1";
    private final String OPENHOUR_INDEX = "openhour_v1";
    @Autowired
    private ThcLocationRepository thcLocationRepository;
    @Autowired
    private ThcMenuRepository thcMenuRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public ThcMenuItem createMenuItem(ThcMenuItem thcMenuItem){
        return elasticsearchOperations.save(thcMenuItem, IndexCoordinates.of(MENUITEM_INDEX));
    }
    @Override
    public Page<ThcMenuItem> findMenuItemPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields){
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        SortBuilder sortRequest = SortBuilders.fieldSort(sortField);
        if(sortOrder.equalsIgnoreCase("asc")) {
            sortRequest.order(SortOrder.ASC);
        }else {
            sortRequest.order(SortOrder.DESC);
        }

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withSorts(sortRequest);

        if(fields != null && fields.length() > 0){
            searchQuery.withFields(fields.split(","));
        }

        SearchHits<ThcMenuItem> searchHits = elasticsearchOperations.search(searchQuery.build(), ThcMenuItem.class, IndexCoordinates.of(MENUITEM_INDEX));
        List<ThcMenuItem> thcMenuItemList = searchHits.stream().map(hit-> hit.getContent()).collect(Collectors.toList());
        log.info("Retrieved {} locations from Elasticsearch", thcMenuItemList.size());
        System.out.println("Location retrieved"+ thcMenuItemList);
        return new PageImpl<>(thcMenuItemList, pageRequest, searchHits.getTotalHits());
    }

    @Override
    public String removeMenuItem(String itemName){
        return elasticsearchOperations.delete(itemName);
    }

    @Override
    public ThcReservation createReservation(ThcReservation thcReservation){
        return elasticsearchOperations.save(thcReservation, IndexCoordinates.of(RESERVATION_INDEX));
    }
    @Override
    public Page<ThcReservation> findReservationsPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields){
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        SortBuilder sortRequest = SortBuilders.fieldSort(sortField);
        if(sortOrder.equalsIgnoreCase("asc")) {
            sortRequest.order(SortOrder.ASC);
        }else {
            sortRequest.order(SortOrder.DESC);
        }

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withSorts(sortRequest);

        if(fields != null && fields.length() > 0){
            searchQuery.withFields(fields.split(","));
        }

        SearchHits<ThcReservation> searchHits = elasticsearchOperations.search(searchQuery.build(), ThcReservation.class, IndexCoordinates.of(LOCATION_INDEX));
        List<ThcReservation> thcReservationList = searchHits.stream().map(hit-> hit.getContent()).collect(Collectors.toList());
        log.info("Retrieved {} locations from Elasticsearch", thcReservationList.size());
        System.out.println("Location retrieved"+ thcReservationList);
        return new PageImpl<>(thcReservationList, pageRequest, searchHits.getTotalHits());
    }


    @Override
    public OpenHours createOpenHours(OpenHours openHours){
        return elasticsearchOperations.save(openHours, IndexCoordinates.of(OPENHOUR_INDEX));
    }
    @Override
    public Page<OpenHours> findOpenHoursPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields){
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        SortBuilder sortRequest = SortBuilders.fieldSort(sortField);
        if(sortOrder.equalsIgnoreCase("asc")) {
            sortRequest.order(SortOrder.ASC);
        }else {
            sortRequest.order(SortOrder.DESC);
        }

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withSorts(sortRequest);

        if(fields != null && fields.length() > 0){
            searchQuery.withFields(fields.split(","));
        }

        SearchHits<OpenHours> searchHits = elasticsearchOperations.search(searchQuery.build(), OpenHours.class, IndexCoordinates.of(RESERVATION_INDEX));
        List<OpenHours> openHoursList = searchHits.stream().map(hit-> hit.getContent()).collect(Collectors.toList());
        log.info("Retrieved {} locations from Elasticsearch", openHoursList.size());
        System.out.println("Location retrieved"+ openHoursList);
        return new PageImpl<>(openHoursList, pageRequest, searchHits.getTotalHits());
    }


    @Override
    public ThcLocation createLocation(ThcLocation thcLocation) {
        return elasticsearchOperations.save(thcLocation, IndexCoordinates.of(LOCATION_INDEX));
    }

    public ThcLocation updLocation(ThcLocation thcLocation){
        String locationId = thcLocation.getLocationId();
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("locationId", locationId).minimumShouldMatch("75%"));

        System.out.println("Query-"+searchQuery);
        SearchHits<ThcLocation> locations = elasticsearchOperations.search(searchQuery.build(),ThcLocation.class, IndexCoordinates.of(LOCATION_INDEX));
//        System.out.println(locations);
        ThcLocation location = locations.getSearchHit(0).getContent();
        System.out.println("search loc - "+location);
//        location.setLocationName(thcLocation.getLocationName());
        location = thcLocation;
        return elasticsearchOperations.save(location,IndexCoordinates.of(LOCATION_INDEX));
    }

    @Override
    public ThcLocation findLocationById(String locationId) {
        return elasticsearchOperations.get(locationId, ThcLocation.class, IndexCoordinates.of(LOCATION_INDEX));
    }

    @Override
    public Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields) {
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        SortBuilder sortRequest = SortBuilders.fieldSort(sortField);
        if(sortOrder.equalsIgnoreCase("asc")) {
            sortRequest.order(SortOrder.ASC);
        }else {
            sortRequest.order(SortOrder.DESC);
        }

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withSorts(sortRequest);

        if(fields != null && fields.length() > 0){
            searchQuery.withFields(fields.split(","));
        }

        SearchHits<ThcLocation> searchHits = elasticsearchOperations.search(searchQuery.build(), ThcLocation.class, IndexCoordinates.of(LOCATION_INDEX));
        List<ThcLocation> thcLocationList = searchHits.stream().map(hit-> hit.getContent()).collect(Collectors.toList());
        log.info("Retrieved {} locations from Elasticsearch", thcLocationList.size());
        System.out.println("Location retrieved"+ thcLocationList);
        return new PageImpl<>(thcLocationList, pageRequest, searchHits.getTotalHits());
    }

    @Override
    public void batchUpsert(List<ThcLocation> thcLocationList) {
        List<UpdateQuery> updateQueries = thcLocationList.stream().
                map(entity -> {
                    Document docToUpsert = elasticsearchOperations.getElasticsearchConverter().mapObject(entity);
                    docToUpsert.setIndex(LOCATION_INDEX);

                    UpdateQuery.Builder request = UpdateQuery
                            .builder(entity.getLocationId())
                            .withDocument(docToUpsert)
                            .withDocAsUpsert(true);

                    return request.build();
                }).collect(Collectors.toList());

    }
}
