
import Axios from "../utils/axiosInstance"

export const getSingleTagTracking = async (tag) => {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/asset/Singletag/get-tracking-list-for-filter-tagnamewise-all-data?tagName=${tag}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
