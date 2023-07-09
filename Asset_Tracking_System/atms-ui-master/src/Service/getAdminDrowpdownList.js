import Axios from "../utils/axiosInstance"

export const getAdminDrowpdownList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"]
    //return Axios.get(`/asset/tracking/get-drop-down-list?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
    return Axios.get(`/asset/tracking/get-drop-down-listNew?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` } })
}