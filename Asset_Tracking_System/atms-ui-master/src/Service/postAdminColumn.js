import Axios from "../utils/axiosInstance"

export const postAdminColumn = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tracking/add-admincolumns', payload, { headers: { "Authorization": `Bearer ${token}` } })
}