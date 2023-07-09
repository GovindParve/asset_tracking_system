
import Axios from "../utils/axiosInstance"

export const postBilling = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/iotmeter/billingdetails', payload, { headers: { "Authorization": `Bearer ${token}` } })
}

