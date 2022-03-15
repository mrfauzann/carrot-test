package com.mitraisCarrot.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private int pageNumber;
    private int pageSize;
    private long totalData;
}
