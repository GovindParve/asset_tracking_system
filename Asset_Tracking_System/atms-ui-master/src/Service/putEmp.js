import Axios from "../utils/axiosInstance"

export const putEmp = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/asset/tracking/UpdateEmployee', payload, { headers: { "Authorization": `Bearer ${token}` } })
}