package com.ssafy.kkoma.domain.kid.repository;

import com.ssafy.kkoma.domain.kid.entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<Kid, Long> {

}
