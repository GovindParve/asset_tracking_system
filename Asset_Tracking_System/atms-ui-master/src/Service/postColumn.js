import Axios from "../utils/axiosInstance"

export const postColumn = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tracking/add-lablel-with-column', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
