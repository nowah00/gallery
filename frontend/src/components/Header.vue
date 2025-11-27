<script setup>
import {useAccountStore} from "@/stores/account";
import {logout} from "@/services/accountService";
import {useRouter} from "vue-router";

// 계정 스토어
const accountStore = useAccountStore(); // ①

// 라우터 객체
const router = useRouter(); // ②

// 로그아웃
const logoutAccount = async () => { // ③
  const res = await logout();

  if (res.status === 200) {
    accountStore.setAccessToken(""); // ① 로그 아웃을 위한 메서드 수정, 로그 아웃 성공시 계정 스토어의 액세스 토큰 값을 초기화하여 이후 HTTP 요청에서 토큰이 사용되지 않도록
    accountStore.setLoggedIn(false);
    await router.push("/");
  }
};
</script>

<template>
  <header>
    <div class="navbar navbar-dark bg-dark text-white shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <strong>Gallery</strong>
        </router-link>
        <div class="menus d-flex gap-3">
          <template v-if="!accountStore.loggedIn">  <!-- ④ -->
            <router-link to="/login">로그인</router-link>
            <router-link to="/join">회원가입</router-link>
          </template>
          <template v-else>
            <a @click="logoutAccount()">로그아웃</a>
            <router-link to="/orders">주문 내역</router-link>
            <router-link to="/cart">장바구니</router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<style lang="scss">
header {
  .menus {
    a {
      cursor: pointer;
      color: #fff;
      text-decoration: none;
    }
  }
}
</style>