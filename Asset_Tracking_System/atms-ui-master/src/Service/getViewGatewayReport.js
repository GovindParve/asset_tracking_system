import Axios from "../utils/axiosInstance"

//export const getViewGatewayReport = async (pageno,gatewayName, duration) => {
  // console.log("gatewayName---->", gatewayName, "-->", duration)
  //   let token = await localStorage.getItem("token")
  //   let fkUserId = await localStorage.getItem('fkUserId');
  //   let role = await localStorage.getItem('role');
  //   return Axios.get(`/asset/tracking/view-all-tracking-data-with-time-duration-and-gateway-report-view?gatewayName=${gatewayName}&duration=${duration}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
  //   body: JSON.stringify({
  //       fkUserId: localStorage.getItem('fkUserId'),
  //       role: localStorage.getItem('role'),
  //     }) 
  //   })
  export const getViewGatewayReport = async (pageno,gatewayName, duration) => {
    console.log("gatewayName---->", gatewayName, "-->", duration)
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"];
    return Axios.get(`/asset/tracking/view-all-tracking-data-with-time-duration-and-gateway-report-view-with-pagination/${pageno}?gatewayName=${gatewayName}&duration=${duration}&fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) 
    })
}