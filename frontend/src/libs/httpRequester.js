import axios from "axios";
import {useAccountStore} from "@/stores/account"; // ① 계정 스토어 객체 생성시 사용하는 함수 임포트

const instance = axios.create();

// 인터셉터(응답 시)
instance.interceptors.response.use((res) => {
    return res;
}, async (err) => {
    switch (err.response.status) {
        case 400:
            window.alert("잘못된 요청입니다.");
            break;

        case 401: // ② HTTP 응답코드가 401 이라면 액세스 토큰이 만료된 것일 수 있으므로 쿠키에 있는 리프레시 토큰으로 액세스 토큰을 다시 요청한다.
            //   쿠키는 HTTP요청시 자동으로 포함되므로, 액세스 토큰을 다시 받았다면 토큰을 교체하여 HTTP 요청을 다시 수행한다.
            //    해당 요청의 HTTP 응답 상태 코드가 이전과 동일한 401 일 수도 있으므로 방지를 위해 요청 설정(config)에 config.retried = true 설정한다.
            const config = err.config;

            if (config.retried) { // 재요청 여부
                window.alert("권한이 없습니다.");
                window.location.replace("/");
                return;
            }

            // (쿠키에 있는) 리프레시 토큰으로 액세스 토큰 요청
            const res = await axios.get("/v1/api/account/token");

            // 액세스 토큰
            const accessToken = res.data;

            // 계정 스토어
            const accountStore = useAccountStore();

            // 계정 스토어의 액세스 토큰 변경
            accountStore.setAccessToken(accessToken);

            // 요청 액세스 토큰 교체
            config.headers.authorization = `Bearer ${accountStore.accessToken}`;

            // 중복 재요청 방지를 위한 프로퍼티 추가
            config.retried = true;

            // 재요청
            return instance(config);

        case 500:
            window.alert("오류가 있습니다. 관리자에게 문의해주세요.");
            break;
    }

    return Promise.reject(err);
});

// HTTP 요청 설정 생성 :액세스 토큰이 있는 경우 인증 프로퍼티가 포함된 헤더가 있는 객체를 리턴하고, 없는 경우 빈객체를 리턴한다.
const generateConfig = () => { // ③
    // 계정 스토어
    const accountStore = useAccountStore();

    if (accountStore.accessToken) {
        return {
            headers: {authorization: `Bearer ${accountStore.accessToken}`}
        };
    }

    return {};
};

export default {
    get(url, params) { // ④ Axios 객체의 메서드를 호출하여 HTTP GET 요청을 한다. 호출시 generateConfig메서드를 호출해서 인수로 입력한다.
        const config = generateConfig();
        config.params = params;
        return instance.get(url, config);
    },
    post(url, params) { // ⑤  Axios 객체의 메서드를 호출하여 HTTP post 요청을 한다. 호출시 generateConfig메서드를 호출해서 인수로 입력한다.
        return instance.post(url, params, generateConfig());
    },
    put(url, params) { // ⑥  Axios 객체의 메서드를 호출하여 HTTP put 요청을 한다. 호출시 generateConfig메서드를 호출해서 인수로 입력한다.
        return instance.put(url, params, generateConfig());
    },
    delete(url) { // ⑦  Axios 객체의 메서드를 호출하여 HTTP delete 요청을 한다. 호출시 generateConfig메서드를 호출해서 인수로 입력한다.
        return instance.delete(url, generateConfig());
    },
};