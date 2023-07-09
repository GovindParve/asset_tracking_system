import Axios from "../utils/axiosInstance"

export const getPayloadData = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role =await localStorage.getItem('role');
    return Axios.get(`/iotmeter/payload/get-payload_list?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}