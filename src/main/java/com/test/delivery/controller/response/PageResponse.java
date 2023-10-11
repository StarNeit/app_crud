package com.test.delivery.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PageResponse<E> {
    private long totalCount;
    private List<E> items;
}
