package com.mitraisCarrot.backend.repository.user;

import com.mitraisCarrot.backend.dto.user.StaffCarrotResponse;
import com.mitraisCarrot.backend.entity.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findByRoleId(int roleId, Pageable pageable);
    List<UserEntity> findById(int id);
    Page<UserEntity> findByCarrot(int basketId, Pageable pageable);

    @Query("SELECT u.name, b.earned_amount) "
            + "FROM users u INNER JOIN basket b ON u.basketId = b.id")
    List<UserEntity> fetchStaffByCarrot();

    @Query("Select u.name, b.earned_amount FROM users c JOIN u.userId b")
    public List<StaffCarrotResponse> getJoinInformation();
}