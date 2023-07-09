import Axios from "../utils/axiosInstance"

export const deleteBatch = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/iotmeter/payload/delete-payload', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
