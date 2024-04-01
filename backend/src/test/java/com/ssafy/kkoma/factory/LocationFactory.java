package com.ssafy.kkoma.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.kkoma.domain.location.entity.Location;
import com.ssafy.kkoma.domain.location.repository.LocationRepository;

@Component
public class LocationFactory {

	@Autowired
	LocationRepository locationRepository;

	public Location createLocation() {
		final Long REGIONCODE = 11L;

		return locationRepository.save(Location.builder()
			.regionCode(REGIONCODE)
			.x(111.11111)
			.y(111.11111)
			.placeDetail("지하철역 앞")
			.build());
	}

}
