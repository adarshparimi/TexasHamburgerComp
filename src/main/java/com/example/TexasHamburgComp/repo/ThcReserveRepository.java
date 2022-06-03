package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ThcMenuItem;
import com.example.TexasHamburgComp.model.ThcReservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThcReserveRepository extends CrudRepository<ThcReservation, Integer> {

    @Modifying
    @Query(value = "delete from thc_reservation where reserve_id=:reserve_id", nativeQuery = true)
    int deleteReservation(@Param("reserve_id") int reserve_id);

    @Modifying
    @Query(value = "select * from thc_reservation where reserve_id=:reserve_id", nativeQuery = true)
    List<ThcReservation> selectReservation(@Param("reserve_id") int reserve_id);
}
