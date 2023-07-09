import Axios from "../utils/axiosInstance"

export const deleteEmp = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tracking/BulkDeleteEmployee', payload, { headers: { "Authorization": `Bearer ${token}` } })
}