import Axios from "../utils/axiosInstance"

export const login = async (payload) => {
    return Axios.get(`/users/signin?username=${payload.emailid}&password=${payload.passWord}`)
}
