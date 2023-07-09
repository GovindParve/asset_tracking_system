import Axios from "../utils/axiosInstance"

export const getGPSDynamicColumnList = async (columnname) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category =["GPS"]
    return Axios.get(`/asset/tracking/get-dbcolumn-with-uicolumnname?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
     })
}