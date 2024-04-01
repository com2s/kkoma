package com.ssafy.kkoma.domain.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.location.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
