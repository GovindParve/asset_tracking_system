import Axios from "../utils/axiosInstance"

export const getDateWiseGateway = async (pageno,fromdate,todate,gatewayName) => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"];
    return Axios.get(`/gateway/get-gateway-Wise-View-between-date/${pageno}?fromdate=${fromdate}&todate=${todate}&gatewayName=${gatewayName}&fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
         fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}