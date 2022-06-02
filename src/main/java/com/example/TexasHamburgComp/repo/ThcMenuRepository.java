package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ThcMenuItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThcMenuRepository extends CrudRepository<ThcMenuItem, Integer> {

    @Modifying
    @Query(value = "delete from thc_menu_item where item_name=:item_name", nativeQuery = true)
    int deleteItemName(@Param("item_name") String item_name);
}
