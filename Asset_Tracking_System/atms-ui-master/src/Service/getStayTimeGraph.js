import Axios from "../utils/axiosInstance"

export const getStayTimeGraph = async (duration,tagName) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    //let category = ["BLE"];
    return Axios.get(`/asset/tracking/get_tracking_details_tagName_duration_stayTime_for_graph?duration=${duration}&tagName=${tagName}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}