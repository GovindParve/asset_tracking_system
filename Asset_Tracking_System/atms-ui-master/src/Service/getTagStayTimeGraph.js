import Axios from "../utils/axiosInstance"

export const getTagStayTimeGraph = async (fromdate,todate,tagName) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/asset/tracking/get_tracking_details_tagName_datewise_stayTime_for_graph?fromdate=${fromdate}&todate=${todate}&tagName=${tagName}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
