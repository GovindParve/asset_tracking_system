import Axios from "../utils/axiosInstance"

export const getPayloadFilter = async (first, last) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/iotmeter/payload/get-payload_report_between_two_date_for_filter?fkUserId=${fkUserId}&role=${role}&fromDate=${first}&toDate=${last}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
