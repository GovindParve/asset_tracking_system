import Axios from "../utils/axiosInstance"

export const getHighChartData = async (amrid, dur) => {
    console.log("amr---->", amrid, "-->", dur)
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/iotmeter/payload/get-payload_amrid_and_duration_for_dashboard?amrid=${amrid}&duration=${dur}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}
