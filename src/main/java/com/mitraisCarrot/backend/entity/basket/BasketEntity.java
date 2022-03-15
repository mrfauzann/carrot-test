package com.mitraisCarrot.backend.entity.basket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketEntity {
    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private int earnedAmount;
    private int sharedAmount;
    private int spendAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private Date updatedAt;
}
