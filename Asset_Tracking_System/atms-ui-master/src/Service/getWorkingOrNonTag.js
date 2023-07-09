// import Axios from "../utils/axiosInstance"

// export const getWorkingOrNonTag = async () => {
//     let token = await localStorage.getItem("token")
//     let fkUserId = await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/Tag/get-Tag_list_with_working_NonWorking_Status?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
//     body: JSON.stringify({
//         fkUserId:fkUserId,
//         role: role,
//       }) })
// }

import Axios from "../utils/axiosInstance"

export const getWorkingOrNonTag = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"]
    return Axios.get(`/Tag/getNonWorking-Tag_list?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}