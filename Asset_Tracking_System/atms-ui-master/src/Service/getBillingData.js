import Axios from "../utils/axiosInstance"

export const getBillingData = async (id,first,last) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/iotmeter/billing/get_total_volume_against_amr_id?amrid=${id}&fromDate=${first}&toDate=${last}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}