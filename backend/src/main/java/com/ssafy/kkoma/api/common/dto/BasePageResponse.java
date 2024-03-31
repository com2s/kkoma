package com.ssafy.kkoma.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class BasePageResponse<E, DTO> {
    private List<DTO> content;
    private int size;
    private int page;
    private int numberOfElements;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;

    public BasePageResponse(List<DTO> content, Page<E> page) {
        this.content = content;
        this.size = page.getSize();
        this.page = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }
}
