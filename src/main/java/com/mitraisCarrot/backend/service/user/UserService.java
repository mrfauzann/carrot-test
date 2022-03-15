package com.mitraisCarrot.backend.service.user;

import com.mitraisCarrot.backend.common.response.CommonResponse;
import com.mitraisCarrot.backend.dto.user.UserDto;

public interface UserService {
    UserDto addUser(UserDto userDto);

    CommonResponse getAllUsers(String role, String fields, String pageNumber, String pageSize, String sortBy, String sortDir);
    CommonResponse getUserById(int id, String fields);
    CommonResponse getStaffByCarrot(int userId, int basketId, String role, String fields, String pageNumber, String pageSize, String sortBy, String sortDir);
}
