import Axios from "../utils/axiosInstance"

export const putColumn = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/asset/tracking/updatecoloumnmapping', payload, { headers: { "Authorization": `Bearer ${token}` } })
}