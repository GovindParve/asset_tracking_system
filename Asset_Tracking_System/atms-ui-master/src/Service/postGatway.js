import Axios from "../utils/axiosInstance"

export const postGatway = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/gateway/asset_gateway', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
