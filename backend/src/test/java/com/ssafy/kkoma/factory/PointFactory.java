package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointFactory {

    @Autowired
    private PointRepository pointRepository;

    public Point createPoint(Member member, int balance) {
        Point point = new Point(balance);
        member.setPoint(point);
        return pointRepository.save(point);
    }
}
