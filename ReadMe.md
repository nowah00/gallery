# 프로젝트 구조도

```
com.ssg.gallery
├─ account
│  ├─ controller
│  │  └─ AccountController
│  │
│  ├─ dto
│  │  ├─ AccountJoinRequests
│  │  └─ AccountLoginRequests
│  │
│  ├─ etc
│  │  └─ AccountConstants
│  │
│  └─ helper
│     ├─ AccountHelper (interface)
│     └─ SessionAccountHelper
│
├─ cart
│  ├─ controller
│  │  └─ CartController
│  │
│  ├─ dto
│  │  ├─ CartRead
│  │  └─ CartRequest
│  │
│  ├─ entity
│  │  └─ Cart
│  │
│  ├─ repository
│  │  └─ CartRepository (interface)
│  │
│  └─ service
│     ├─ BaseCartService
│     └─ CartService (interface)
│
├─ common
│  ├─ util
│  │  └─ HttpUtils
│  │
│  └─ MainController
│
├─ item
│  ├─ controller
│  │  └─ ItemController
│  │
│  ├─ dto
│  │  └─ ItemRead
│  │
│  ├─ entity
│  │  └─ Item
│  │
│  ├─ repository
│  │  └─ ItemRepository (interface)
│  │
│  └─ service
│     ├─ BaseItemService
│     └─ ItemService (interface)
│
├─ member
│  ├─ entity
│  │  └─ Member
│  │
│  ├─ repository
│  │  └─ MemberRepository (interface)
│  │
│  └─ service
│     ├─ BaseMemberService
│     └─ MemberService (interface)
│
└─ order
   ├─ controller
   │  └─ OrderController
   │
   ├─ dto
   │  ├─ OrderRead
   │  └─ OrderRequest
   │
   ├─ entity
   │  ├─ Order
   │  └─ OrderItem
   │
   ├─ repository
   │  ├─ OrderItemRepository (interface)
   │  └─ OrderRepository (interface)
   │
   └─ service
      ├─ BaseOrderItemService
      ├─ BaseOrderService
      ├─ OrderItemService (interface)
      └─ OrderService (interface)
```

### package: common.util

---

**HttpUtils**

```java
package com.ssg.gallery.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
    public static void setSession (HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    public static Object getSession (HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static void removeSession (HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }
}
```

<aside>

**세션 기능 클래스**

→ 세션 관련 기능을 한 곳에서 관리하기 위한 클래스

---

1. setSession : 세션 key에 value 저장 (로그인)
2. getSession : 세션 key에 저장된 값 반환 (인증)
3. removeSession : 세션 key 삭제 (로그아웃)
</aside>

### Member package와 Account package는 같은 멤버 관리 pakage인데, 왜 따로 관리할까?

→ 둘 다 회원을 다루지만 **Member**는 데이터(DB 객체), **Account**는 계정 기능(로그인, 로그아웃 등 비즈니스 로직)을  다루기 떄문에 분리한다.

<aside>

**Member Package**

- 회원 자체의 정보를 정의하고 저장/조회하는 기능을 담당
- 회원의 데이터를 다루는 도메인의 영역

---

**Account Package**

- 로그인, 로그아웃 등 계정인증, 인가 관련 기능을 담당
- 계정인증/세션 처리 같은 기능을 담은 서비스 영역
</aside>

**⇒ 데이터(member)와 기능(account)을 분리해서 유지보수가 쉬워질 수 있도록 한다.**

# 개발 순서

<aside>

ex) items

DB → Entity → Repository → DTO → Service → Controller

</aside>

## 1. DB 생성

```sql
Create TABLE items
(
    id           INT AUTO_INCREMENT COMMENT '아이디' PRIMARY KEY,
    name         VARCHAR(50)  NOT NULL COMMENT '상품 이름',
    img_path     VARCHAR(100) NOT NULL COMMENT '상품 사진 경로',
    price        INT          NOT NULL COMMENT '상품 가격',
    discount_per INT          NOT NULL COMMENT '상품 할인율',
    created      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '생성 일시'
) COMMENT '상품'
```

## 2. Entity

```java
@Getter
@Entity 
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer discountPer;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

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

```

<aside>

### 어노테이션

---

**@Entity** : JPA에 의해 관리되는 엔티티임을 나타내는 어노테이션

**@Table** : 매핑된 DB 테이블을 지정하는 어노테이션

**@Id** : 해당 필드가 기본키이며, 테이블의 기본키 컬럼과 매핑됨을 나타내는 어노테이션

**@GeneratedValue** :

엔티티의 기본키(PK)를 어떻게 생성할 것인지 나타내는 어노테이션

**@Column** : 해당 컬럼의 제약 조건을 나타내는 어노테이션

**@CreationTimestamp :**

데이터 삽입값이 없다면 현재 시각을 입력하도록 하는 어노테이션

</aside>

<aside>

**@GeneratedValue**

---

- **GenerationType.IDENTITY**

  → DB의 AUTO_INCREMENT 기능을 그대로 사용

- **GenerationType.SEQUENCE**

  → DB의 시퀀스 객체로 PK를 생성

- **GenerationType.TABLE**

  → PK값을 별도 테이블에서 관리 (잘 안씀)

- **GenerationType.AUTO**

  → JPA가 DB에 맞춰 자동 선택

</aside>

## 3. Repository

```java
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByIdIn(List<Integer> ids);
}
```

<aside>

**Spring Data JPA**

---

- 메서드 이름으로 쿼리 자동 생성
- Repository 인터페이스만 만들면 구현체 자동 생성
- JPA + Hibernate 기반 ORM
- CRUD 기능 자동 제공
</aside>

## 4. DTO

```java
@Getter
@Builder
public class ItemRead {
    private Integer id;
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;
}

```

## 5. Service

```java
public interface ItemService {

    List<ItemRead> findAll();

    List<ItemRead> findAll(List<Integer> ids);
}
```

```java
@Service
@RequiredArgsConstructor
public class BaseItemService implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<ItemRead> findAll() {
        return itemRepository.findAll().stream().map(Item::toRead).toList();
    }

    @Override
    public List<ItemRead> findAll(List<Integer> ids) {
        return itemRepository.findAllById(ids).stream().map(Item::toRead).toList();
    }
}
```

## 6. Controller

```java
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/api/items")
    public ResponseEntity<?> readAll(){
        List<ItemRead> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
```