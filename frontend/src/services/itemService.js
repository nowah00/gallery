import httpRequester from "@/libs/httpRequester.js"; // ①

// 상품 목록 조회
export const getItems = () => { // ②
    return httpRequester.get("/v1/api/items").catch(e => e.response);
};