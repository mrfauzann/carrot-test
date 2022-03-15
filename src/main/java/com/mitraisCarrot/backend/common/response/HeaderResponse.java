package com.mitraisCarrot.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderResponse {
    private String processTime;
    private String message;
    private int statusCode;
}
