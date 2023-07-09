import Axios from "../utils/axiosInstance"

export const getDynamicColumnList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category =["BLE"]
    return Axios.get(`/asset/tracking/get-dbcolumn-with-uicolumnname?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
     })
}

