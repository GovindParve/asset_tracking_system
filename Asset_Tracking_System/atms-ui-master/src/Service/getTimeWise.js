import Axios from "../utils/axiosInstance"

export const getTimeWise = async (pageno,fromdate,todate,fromtime,totime) => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/tag/get-list-between-report-excel/${pageno}?fromdate=${fromdate}&todate=${todate}&fromtime=${fromtime}&totime=${totime}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
         fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}