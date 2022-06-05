package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ThcLocation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThcElasticsearchRepository {
    ThcLocation create(ThcLocation thcLocation);
    ThcLocation findOneById(String locationId);
    Page<ThcLocation> findLocationsPaginatedAndSorted(String page, String size, String sortField, String sortOrder, String fields);
    void batchUpsert(List<ThcLocation> thcLocationList);


}
