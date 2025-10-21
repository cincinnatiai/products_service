package com.cai.inventory_system.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaginationResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private boolean last;
    private boolean first;
    private int numberOfElements;
    private boolean empty;

    // Getters y setters

}
