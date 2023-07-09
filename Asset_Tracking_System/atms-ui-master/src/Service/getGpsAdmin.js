import Axios from "../utils/axiosInstance"

export const getGpsAdmin = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["GPS"];
    return Axios.get(`/user/get-admin_list?fkUserId=${fkUserId}&role=${role}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  // return Axios.get(`/user/get-admin_list?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
