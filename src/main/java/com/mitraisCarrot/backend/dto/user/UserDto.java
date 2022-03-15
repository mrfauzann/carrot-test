package com.mitraisCarrot.backend.dto.user;

import com.mitraisCarrot.backend.utils.AppConstants;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String name;
    private String email;
    private int position;
    private int role;

    public static class GetAllUsersRequest {
        public String role;
        public String fields;
        public String page_number = AppConstants.DEFAULT_PAGE_NUMBER;
        public String page_size = AppConstants.DEFAULT_PAGE_SIZE;
        public String sort_by = AppConstants.DEFAULT_SORT_BY;
        public String sort_dir = AppConstants.DEFAULT_SORT_DIRECTION;
    }
}
