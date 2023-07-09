import Axios from "../utils/axiosInstance"
export const getProductHistory = async (assetTagName) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/product/get-product-allocation-History?assetTagName=${assetTagName}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}