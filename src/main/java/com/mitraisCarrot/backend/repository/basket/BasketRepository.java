package com.mitraisCarrot.backend.repository.basket;

import com.mitraisCarrot.backend.entity.basket.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketEntity, Integer> {

}
