import Axios from "../utils/axiosInstance"

export const getGpsColumnList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["GPS"];
    return Axios.get(`/asset/tracking/get-all-list-mapping?category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}