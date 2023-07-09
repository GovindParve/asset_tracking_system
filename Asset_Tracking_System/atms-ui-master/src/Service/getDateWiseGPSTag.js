import Axios from "../utils/axiosInstance"

export const getDateWiseGPSTag = async (pageno,fromdate,todate,tagName) => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["GPS"];
    return Axios.get(`/tag/get-tag-Wise-View-between-date/${pageno}?fromdate=${fromdate}&todate=${todate}&tagName=${tagName}&fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
         fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}