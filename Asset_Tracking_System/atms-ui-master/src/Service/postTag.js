import Axios from "../utils/axiosInstance"

export const postTag = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/asset/tag/add_asset_tag', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
