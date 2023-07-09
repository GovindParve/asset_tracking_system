import Axios from "../utils/axiosInstance"

export const deleteGateway = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/gateway/delete-multiple-gateway', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
