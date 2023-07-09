import Axios from "../utils/axiosInstance"
export const getDeleteProductHistory = async (assetTagName) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/product/get-productallocation-deleteHistory-list?fkUserId=${fkUserId}&role=${role}&assetTagName=${assetTagName}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}