import Axios from "../utils/axiosInstance"

export const listAmrID = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`iotmeter/device/get-device_amr_id_list?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
