import Axios from "../utils/axiosInstance";

export const getPageWiseOrgGatewayList = (pageno) => {
    let token = localStorage.getItem("token");
    let fkUserId = localStorage.getItem("fkUserId");
    let role = localStorage.getItem("role");
    let category = ["BLE"];
    return Axios.get(`/tag/getgateway-list-to-view-from-SuperAdmin/${pageno}?fkUserId=${fkUserId}&role=${role}&category=${category}`, {
        headers: { "Authorization": `Bearer ${token}` },
        body: JSON.stringify({
            fkUserId: localStorage.getItem('fkUserId'),
            role: localStorage.getItem('role'),
        })
    })
};