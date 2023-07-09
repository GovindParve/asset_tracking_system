import Axios from "../utils/axiosInstance"

export const getFeedbackList = async (pageno) => {
  let token = await localStorage.getItem("token")
  let fkUserId = await localStorage.getItem('fkUserId');
  let role = await localStorage.getItem('role');
  let category = ["BLE"];
  return Axios.get(`/issue/getallList/${pageno}?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
      fkUserId: localStorage.getItem('fkUserId'),
      role: localStorage.getItem('role'),
    }) })
}