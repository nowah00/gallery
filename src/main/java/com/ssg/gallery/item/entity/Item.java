package com.ssg.gallery.item.entity;

import com.ssg.gallery.item.dto.ItemRead;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity // JPA에 의해 관리되는 엔티티임을 나타내는 어노테이션
@Table(name = "items") // 매핑된 에이터베이스 테이블 (gallery 스키마의 items 테이블)을 지정하는 어노테이션
public class Item {
    @Id // 아이디 필드, 해당 필드가 기본키이며, 테이블의 기본키 컬럼과 매핑된을 나타내는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본키의 값을 데이터베이스가 자동으로 증가시켜 생성하도록 @GeneratedValue 어노테이션과
    // 기본키 생성 전략을 GenerationType.IDENTITY 로 지정함
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer discountPer;

    @Column(updatable = false, nullable = false) // updatable : 최초값 입력 후에는 업데이트 불가
    @CreationTimestamp // 데이터 삽입값이 없다면 현재 시각을 입력하도록 하는 어노테이션
    private LocalDateTime created;

    // 상품 조회 DTO 로 변환하는 메서드
    // 엔티티 객체를 상품 조회 DTO 로 변환하는 메서드이다. 빌더를 활용하여 필드의 값을 간편하게 초기화하고 객체를 생성한다.
    // 상품 서비스에서 데이터를 조회할때 사용함
    public ItemRead toRead() {
        return ItemRead.builder()
                .id(id)
                .name(name)
                .imgPath(imgPath)
                .price(price)
                .discountPer(discountPer)
                .build();
    }
}
