package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ThcLocation;
import com.example.TexasHamburgComp.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRepository extends CrudRepository<User, String> {
    @Modifying
    @Query(value = "select * from user where username=:username", nativeQuery = true)
    List<User> selectUser(@Param("username") String username);
}
