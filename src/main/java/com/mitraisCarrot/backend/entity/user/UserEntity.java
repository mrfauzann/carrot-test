package com.mitraisCarrot.backend.entity.user;

import com.mitraisCarrot.backend.entity.basket.BasketEntity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private int position;
    private int roleId;
    @OneToOne(targetEntity = BasketEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ub_fk", referencedColumnName = "id")
    private BasketEntity basketEntity;
}
