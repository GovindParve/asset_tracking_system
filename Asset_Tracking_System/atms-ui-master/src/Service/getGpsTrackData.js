import Axios from "../utils/axiosInstance"

export const getGpsTrackData = async (pageno,tagName,duration) => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["GPS"];
    return Axios.get(`/asset/tag/get-tracking_list_on_userwise/${pageno}?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
         fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}