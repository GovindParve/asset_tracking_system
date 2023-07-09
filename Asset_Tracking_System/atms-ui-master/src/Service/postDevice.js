import Axios from "../utils/axiosInstance"

export const postDevice = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/iotmeter/devicedetail', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
