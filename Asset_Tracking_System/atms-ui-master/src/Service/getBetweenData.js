import Axios from "../utils/axiosInstance"

export const getBetweenData = async (amrId, first, last) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/iotmeter/payload/get-payload_amrid_and_customer_date_for_dashboard?amrid=${amrId}&fromDate=${first}&toDate=${last}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
