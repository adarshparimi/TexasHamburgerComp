package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.OpenHours;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThcOpenHoursRepository extends CrudRepository<OpenHours, Integer> {

    @Modifying
    @Query(value = "delete from open_hours where day=:day", nativeQuery = true)
    int deleteOpenHours(@Param("day") String day);
}
