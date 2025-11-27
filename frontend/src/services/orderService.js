import httpRequester from "@/libs/httpRequester"; // ①

// 주문 삽입
export const addOrder = (args) => { // ②
    return httpRequester.post("/v1/api/orders", args).catch(e => e.response);
};

// 주문 목록 조회
export const getOrders = (args) => { // ③
    return httpRequester.get("/v1/api/orders", args).catch(e => e.response);
}

// 주문 상세 조회
export const getOrder = (id) => { // ④
    return httpRequester.get(`/v1/api/orders/${id}`).catch(e => e.response);
};