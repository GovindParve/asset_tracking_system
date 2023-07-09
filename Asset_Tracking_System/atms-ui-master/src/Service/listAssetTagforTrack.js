import Axios from "../utils/axiosInstance"

export const listAssetTagforTrack = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
     let category = ["BLE"];
    return Axios.get(`/asset/tracking/get-tag-list-for-dropdown-in-tracking?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` } })
}
