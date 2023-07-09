import Axios from "../utils/axiosInstance"

export const getDashboardGPSOrgList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category =["GPS"]
    return Axios.get(`/users/get-all-user-with-organization-role?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}