import Axios from "../utils/axiosInstance"

export const getUpdateLimits = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    //let role = await localStorage.getItem('role');
    return Axios.get(`/asset/tracking/get_limits/${fkUserId}?`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
