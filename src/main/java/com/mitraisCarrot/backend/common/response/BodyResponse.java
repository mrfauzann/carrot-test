package com.mitraisCarrot.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyResponse {
    private Object data;
    private PaginationResponse pagination;
}