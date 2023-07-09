import Axios from "../utils/axiosInstance"

export const postEmpUser = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tracking/addEmployeeUser', payload, { headers: { "Authorization": `Bearer ${token}` } })
}