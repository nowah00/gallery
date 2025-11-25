import axios from "axios";

// 회원가입 : HTTP Post 메서드로 회원가입 메서드를 호출하고 응답값을 반환하는 기능
export const join = (args) => {
    return axios.post("/v1/api/account/join", args).catch(e => e.response);
};

// 로그인 : 로그인을 처리하는 메서드로 HTTP POST 메서드로 로그인 API를 호출하고 응답값을 리턴하는 기능
export const login = (args) => {
    return axios.post("/v1/api/account/login", args).catch(e => e.response);
};

// 로그인 여부 확인 : HTTP Get 메서드로 유효한 ID인지 체크를 하는 API를 호출하고 응답값을 리턴하는 기능
export const check = () => {
    return axios.get("/v1/api/account/check").catch(e => e.response);
};

// 로그아웃 : HTTP Post 메서드를 이용해서 로그아웃 API를 호출하고 응답값을 리턴하는 기능
export const logout = () => {
    return axios.post("/v1/api/account/logout").catch(e => e.response);
};