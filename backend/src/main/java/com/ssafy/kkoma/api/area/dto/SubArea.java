package com.ssafy.kkoma.api.area.dto;

import com.ssafy.kkoma.domain.area.entity.Area;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubArea {

	private long id;
	private String name;

	public static SubArea fromEntity(Area area, int level) {
		return SubArea.builder()
			.id(area.getId())
			.name(getSubName(area, level))
			.build();
	}

	private static String getSubName(Area area, int level) {
        return switch (level + 1) {
            case 2 -> area.getSubName2();
            case 3 -> area.getSubName3();
            case 4 -> area.getSubName4();
            default -> area.getSubName1();
        };
	}
}
