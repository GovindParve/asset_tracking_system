import Axios from "../utils/axiosInstance"

export const getSingleDevice = async (id) => {
    let token = await localStorage.getItem("token");
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role =await localStorage.getItem('role');
    return Axios.get(` /gateway/get-single-gateway/${id}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
      
}
