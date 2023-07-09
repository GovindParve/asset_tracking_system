import Axios from "../utils/axiosInstance"

export const deleteDevice = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/iotmeter/device/delete-device', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
