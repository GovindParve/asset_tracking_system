import Axios from "../utils/axiosInstance";

export const getPageWiseGatewayList = (pageno) => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  return Axios.get(`/gateway/get-gateway_list-pagination/${pageno}?fkUserId=${fkUserId}&role=${role}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
    role: localStorage.getItem('role'),
  }) 
})
};