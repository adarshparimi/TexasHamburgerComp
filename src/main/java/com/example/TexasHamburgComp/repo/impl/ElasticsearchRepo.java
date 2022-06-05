package com.example.TexasHamburgComp.repo.impl;

import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.repo.ThcElasticsearchRepository;
import com.example.TexasHamburgComp.repo.ThcLocationRepository;
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
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class ElasticsearchRepo implements ThcElasticsearchRepository {
    private final String LOCATION_INDEX = "location_v1";
    @Autowired
    private ThcLocationRepository thcLocationRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public ThcLocation create(ThcLocation thcLocation) {
        return elasticsearchOperations.save(thcLocation, IndexCoordinates.of(LOCATION_INDEX));
    }

    @Override
    public ThcLocation findOneById(String locationId) {
        return null;
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
