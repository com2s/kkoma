package com.ssafy.kkoma.api.area.dto;

import com.ssafy.kkoma.domain.area.entity.Area;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubArea {
	private long id;
	private String name;

	// public static SubArea fromEntity(Area area, int level) {
	// 	String[] splitName = area.getName().split(" ");
	//
	// 	return SubArea.builder()
	// 		.id(area.getId())
	// 		.name(splitName[level])
	// 		.build();
	// }
}
