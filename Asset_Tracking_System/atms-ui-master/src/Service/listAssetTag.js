import Axios from "../utils/axiosInstance"

export const listAssetTag = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/Tag/get-gps-tag-list-for-dropdown?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
