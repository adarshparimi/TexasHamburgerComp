package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ThcLocation;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThcLocationRepository extends CrudRepository<ThcLocation, String> {

    @Modifying
    @Query(value = "delete from thc_location where location_name=:location_name", nativeQuery = true)
    int deleteLocation(@Param("location_name") String location_name);

    @Modifying
    @Query(value = "select * from thc_location where location_name=:location_name", nativeQuery = true)
    List<ThcLocation> selectLocation(@Param("location_name") String location_name);
}

//1c870c67-7ec9-4d89-a7cc-6759f8d64e4f