package com.mitraisCarrot.backend.service.user;

import com.mitraisCarrot.backend.common.response.BodyResponse;
import com.mitraisCarrot.backend.common.response.CommonResponse;
import com.mitraisCarrot.backend.common.response.HeaderResponse;
import com.mitraisCarrot.backend.common.response.PaginationResponse;
import com.mitraisCarrot.backend.dto.user.UserDto;
import com.mitraisCarrot.backend.entity.user.UserEntity;
import com.mitraisCarrot.backend.repository.user.UserRepository;
import com.mitraisCarrot.backend.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public  class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private String statusMsg;
    private int statusCode;
    private List<Object> response;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        // convert DTO to entity
        UserEntity user = mapToEntity(userDto);
        UserEntity newUser = userRepository.save(user);

        // convert entity to DTO
        return mapToDTO(newUser);
    }

    @Override
    public CommonResponse getAllUsers(String role, String fields, String pageNumber, String pageSize, String sortBy, String sortDir) {
        this.statusCode = HttpStatus.OK.value();
        this.statusMsg = HttpStatus.OK.getReasonPhrase();
        long startTime = System.currentTimeMillis();

        this.response = new ArrayList<>();
        List<String> fieldList = Strings.stringSeparator(fields.toLowerCase());

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), sort);
        Page<UserEntity> users = fetchBasedOnRole(role, pageable);

        getData(response, fieldList, users);

        long endTime = System.currentTimeMillis();
        long processTime = endTime - startTime;

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setPageNumber(users.getNumber());
        paginationResponse.setTotalData(users.getTotalElements());
        paginationResponse.setPageSize(users.getSize());

        return getResponse(response, statusCode, statusMsg, processTime, paginationResponse);
    }

    @Override
    public CommonResponse getUserById(int id, String fields) {
        this.statusCode = HttpStatus.OK.value();
        this.statusMsg = HttpStatus.OK.getReasonPhrase();
        long startTime = System.currentTimeMillis();

        this.response = new ArrayList<>();
        List<String> fieldList = Strings.stringSeparator(fields.toLowerCase());

        List<UserEntity> users = fetchById(id);
        Page<UserEntity> pageUsers = new PageImpl<>(users);
        getData(response, fieldList, pageUsers);

        long endTime = System.currentTimeMillis();
        long processTime = endTime - startTime;

        return getResponse(response, statusCode, statusMsg, processTime, null);
    }

    @Override
    public CommonResponse getStaffByCarrot(int userId, int basketId, String role, String fields, String pageNumber, String pageSize, String sortBy, String sortDir){
        return null;
    }

    private UserDto mapToDTO(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPosition(userEntity.getPosition());
        userDto.setRole(userEntity.getRoleId());
        return userDto;
    }

    private UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPosition(userDto.getPosition());
        userEntity.setRoleId(userDto.getRole());
        return userEntity;
    }

    private Page<UserEntity> fetchBasedOnRole(String role, Pageable pageable) {
        int roleId = Integer.parseInt(role);
        if (roleId == 1) {
            return userRepository.findByRoleId(roleId, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    private List<UserEntity> fetchById(int id) {
        return userRepository.findById(id);
    }

    private Page<UserEntity> fetchStaffByCarrot(int basketId, Pageable pageable){
        return null;
    }

    private void getData(List<Object> response, List<String> fieldList, Page<UserEntity> users) {
        // get users data
        List<UserEntity> listOfUser = users.getContent();
        List<UserDto> usersData = listOfUser.stream().map(this::mapToDTO).collect(Collectors.toList());
        if (fieldList.size() > 0) {
            for (UserDto user : usersData) {
                HashMap userObj = new HashMap<>();
                Class<? extends UserDto> userClass = user.getClass();

                // get attribute from user class for field validation
                List<String> userAttrNames = new ArrayList<>();
                Field[] userAttrFields = userClass.getDeclaredFields();

                for (Field userAttrField : userAttrFields) {
                    userAttrField.setAccessible(true);
                    userAttrNames.add(userAttrField.getName());
                }

                for (String field : fieldList) {
                    // check if requested fields are in user dto attributes
                    if (!userAttrNames.contains(field)) {
                        System.out.println(field + " field is not valid");
                        continue;
                    }

                    // get method from requested field
                    Method m;
                    String methodName = "get" + Strings.stringToTitleCase(field);
                    try {
                        m = userClass.getMethod(methodName);
                    } catch (NoSuchMethodException e) {
                        System.out.println("error: " + e + ", field: " + field);
                        continue;
                    }

                    // get value from requested field method
                    try {
                        userObj.put(field, m.invoke(user).toString());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("error: " + e + ", field: " + field);
                        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
                        this.statusMsg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
                    }
                }

                // don't show data if user object map is empty because an error from get method block above
                if (!userObj.isEmpty()) {
                    response.add(userObj);
                }
            }
        } else {
            this.response = Collections.singletonList(usersData);
        }
    }

    public CommonResponse getResponse(List<Object> response, int statusCode, String statusMsg, long processTime, PaginationResponse paginationResponse ) {
        BodyResponse bodyResponse = new BodyResponse();
        if (!response.isEmpty()) {
            if (paginationResponse != null) {
                bodyResponse.setPagination(paginationResponse);
            }
            bodyResponse.setData(response);
        }

        HeaderResponse headerResponse = new HeaderResponse();
        headerResponse.setStatusCode(statusCode);
        headerResponse.setMessage(statusMsg);
        headerResponse.setProcessTime(processTime + "ms");

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setHeader(headerResponse);
        commonResponse.setBody(bodyResponse);

        return commonResponse;
    }
}
