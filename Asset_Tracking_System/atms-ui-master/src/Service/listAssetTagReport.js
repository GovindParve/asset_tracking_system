import Axios from "../utils/axiosInstance"

export const listAssetTagforTrack = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/asset/tracking/get-tag-list-for-dropdown-in-tracking?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
