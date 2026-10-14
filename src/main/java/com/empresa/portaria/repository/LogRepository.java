package com.empresa.portaria.repository;

import com.empresa.portaria.model.LogPortaria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogPortaria, Long> {
    List<LogPortaria> findAllByOrderByDataHoraDescIdDesc(Pageable pageable);
}
