import Axios from "../utils/axiosInstance"

export const AmrIDBillingList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`iotmeter/billing/get_device_amr_id_list_for_billing?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` } })
}
