<script setup>
import {getItems} from "@/services/itemService";
import {reactive} from "vue";
import Card from "@/components/Card.vue";

// 반응형 상태
const state = reactive({ // ① 반응형상태, 내부에 상품 목록을 저장할 items 배열, 해당 배열에 데이터가 추가되거나, 삭제되면 자동으로 UI에 반영된다.
  items: []
});

// 커스텀 생성 훅
(async function onCreated() { // ② 상품서비스의 상품목록을 조회하는 getItems() 호출하고 리턴 데이터를 state.items에 입력한다.
  const res = await getItems();

  if (res.status === 200) {
    state.items = res.data;
  }
})();
</script>

<template>
  <div class="home">
    <div class="album py-5 bg-light"> <!-- ③ 상품 목록 출력 요소 상품개수만큼 반복하여 카드 컴포넌트에 출력하고, 카드 컴포넌트에 상품 데이터 전달-->
      <div class="container"> <!-- ④  템플릿의 핵심요소를 감싸는 컨테이너 max-width 설정-->
        <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3 g-3"> <!-- ⑤ 열을 나타내는 요소 row-cols-* 클래스로 화면크기에 따라 하나의 열에 몇개의 컬럼을 출력할지 지정, g-3는 열간의 수직,수평 간격 적용-->
          <div class="col" v-for="item in state.items"> <!-- ⑥ 상품 목록 출력-->
            <Card :item="item"/> <!-- ⑦ item 속성에 각 상품 데이터(item)을 입력해 전달-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>