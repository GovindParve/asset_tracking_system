import Axios from "../utils/axiosInstance"

export const putDevice = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/iotmeter/device/update-device', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
