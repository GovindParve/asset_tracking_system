import Axios from "../utils/axiosInstance"

export const getAmrListTwoDate = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`iotmeter/payload/get-device_amr_id_list_for_between_two_date_export?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
