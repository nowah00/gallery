package com.ssg.gallery.item.repository;

import com.ssg.gallery.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    // 여러 상품 아이디로 상품 데이터를 조회하는 메서드
    List<Item> findAllByIdIn(List<Integer> ids);
}
