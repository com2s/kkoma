package com.ssafy.kkoma.domain.area.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.area.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>, AreaRepositoryCustom {

}
