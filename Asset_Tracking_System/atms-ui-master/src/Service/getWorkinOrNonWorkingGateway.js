// import Axios from "../utils/axiosInstance"

// export const getWorkinOrNonWorkingGateway = async () => {
//     let token = await localStorage.getItem("token")
//     let fkUserId = await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/gateway/get-gateway_list_for_workingNon_working_gateway?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
//     body: JSON.stringify({
//         fkUserId:fkUserId,
//         role: role,
//       }) })
// }

import Axios from "../utils/axiosInstance"

export const getWorkinOrNonWorkingGateway = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"]
    return Axios.get(`/gateway/get-gateway_list_for_Nonworking_gateway?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}