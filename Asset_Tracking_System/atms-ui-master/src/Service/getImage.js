import Axios from "../utils/axiosInstance"

export const getImage = async () => {
    let token = await localStorage.getItem("token")
    let fkadminId = await localStorage.getItem('fkUserId');
    //let role = await localStorage.getItem('role');
    return Axios.get(`/asset/tracking/get-image/${fkadminId}?`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}