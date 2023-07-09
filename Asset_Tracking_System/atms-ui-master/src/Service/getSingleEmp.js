import Axios from "../utils/axiosInstance"

export const getSingleEmp = async (id) => {
    let token = await localStorage.getItem("token")
    return Axios.get(`/asset/tracking/get-by-id/${id}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}