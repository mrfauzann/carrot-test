package com.mitraisCarrot.backend.controller.user;

import com.mitraisCarrot.backend.common.response.CommonResponse;
import com.mitraisCarrot.backend.dto.user.StaffCarrotResponse;
import com.mitraisCarrot.backend.dto.user.UserDto;
import com.mitraisCarrot.backend.entity.user.UserEntity;
import com.mitraisCarrot.backend.repository.user.UserRepository;
import com.mitraisCarrot.backend.service.user.UserBasketQueryService;
import com.mitraisCarrot.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserBasketQueryService userBasketQueryService;


    public UserController(UserService userService, UserRepository userRepository, UserBasketQueryService userBasketQueryService) {
        this.userService = userService;
        this.userBasketQueryService = userBasketQueryService;
    }

    // create all user
    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    // get staff
    @PostMapping("/staff")
    public CommonResponse getAllStaff(@RequestBody UserDto.GetAllUsersRequest input) {
        return userService.getAllUsers(input.role, input.fields, input.page_number, input.page_size, input.sort_by, input.sort_dir);
    }

    //get staff by most carrot
    @GetMapping("/staffCarrot")
    public ResponseEntity<List<UserEntity>> getStaffCarrot() {
        return new ResponseEntity<List<UserEntity>>(userBasketQueryService.getDeptEmployeesInnerJoin(), HttpStatus.OK);
    }
//    @PostMapping("/staffCarrot")
//    public List<StaffCarrotResponse> getStaffByCarrot(){
//        return userRepository.getJoinInformation();
//    }
}
