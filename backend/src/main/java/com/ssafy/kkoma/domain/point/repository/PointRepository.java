package com.ssafy.kkoma.domain.point.repository;

import com.ssafy.kkoma.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

}
