package com.example.TexasHamburgComp.repo;

import com.example.TexasHamburgComp.model.ExecTime;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;

@Repository
public interface ApiExecTimeRepository extends JpaRepository<ExecTime, String> {
}
