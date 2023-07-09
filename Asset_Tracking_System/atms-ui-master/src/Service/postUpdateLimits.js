import Axios from "../utils/axiosInstance"

export const postUpdateLimits = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tracking/add_limits', payload, { headers: { "Authorization": `Bearer ${token}` } })
}