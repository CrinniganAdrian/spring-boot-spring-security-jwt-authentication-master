package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findItemsByUsersId(Long userId);

    @Transactional
    Long deleteItemByName(String itemName);
}
