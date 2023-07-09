import Axios from "../utils/axiosInstance"

export const putGateway = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/gateway/update-gateway', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
