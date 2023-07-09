// import Axios from "../utils/axiosInstance"

// export const allGatewayWiseGraphData = async () => {
//     let token = await localStorage.getItem("token")
//     let fkUserId = await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/tracking/get-aged-and-new-tag-count-for-graph?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
// }

import Axios from "../utils/axiosInstance"

export const allGatewayWiseGraphData = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/tracking/get-aged-and-new-tag-count-for-graphnew?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
