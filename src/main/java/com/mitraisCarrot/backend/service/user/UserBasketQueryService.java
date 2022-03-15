package com.mitraisCarrot.backend.service.user;

import com.mitraisCarrot.backend.entity.user.UserEntity;
import com.mitraisCarrot.backend.repository.basket.BasketRepository;
import com.mitraisCarrot.backend.repository.user.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserBasketQueryService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private BasketRepository basketRepository;

    public List<UserEntity> getDeptEmployeesInnerJoin() {
        List<UserEntity> list = userRepository.fetchStaffByCarrot();
        list.forEach(l -> System.out.println(l));
        return list;
    }
}
