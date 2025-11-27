// 세션기능을 제공하는 저장고
import {defineStore} from 'pinia'
// 파니아에서 제공하는 defineStore 메서드를 호출하여 스토어를 정의하였다
// 계정 데이터를 다루는 계정 스토어

export const useAccountStore = defineStore("account", { // ① "account"는 스토어의 고유 식별자 아이디
    state: () => ({
        checked: false, // ②
        loggedIn: false, // ③
        accessToken: "", // ①  액세스 토큰 프로퍼티 문자열 타입
    }),
    actions: {
        setChecked(val) { // ④ 사용자의 로그인 체크여부 값을 수정하는 메서드
            this.checked = val;
        },
        setLoggedIn(val) { // ⑤ 사용자의 로그인 여부값을 수정하는 메서드
            this.loggedIn = val;
        },
        setAccessToken(val) { // ② 액세스 토큰의 값 수정 메서드
            this.accessToken = val;
        },
    },
});