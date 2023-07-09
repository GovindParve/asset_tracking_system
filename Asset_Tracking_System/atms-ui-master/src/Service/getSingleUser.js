
import Axios from "../utils/axiosInstance"

export const getSingleUser = async (id) => {
    let token = await localStorage.getItem("token")
    return Axios.get(`/users/get-user-for-edit/${id}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
