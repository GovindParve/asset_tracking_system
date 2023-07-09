import Axios from "../utils/axiosInstance"

// export const getSearchTracking = async (pageno,assetTagName,assetGatewayName,date,entryTime,tagEntryLocation,existTime,dispatchTime,time,battryPercentage) => {
//     let token = await localStorage.getItem("token")
//     let fkUserId =  await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/tag/get-list-between-report-excel/${pageno}?assetTagName=${assetTagName}&assetGatewayName=${assetGatewayName}&date=${date}&entryTime=${entryTime}
//     &tagEntryLocation=${tagEntryLocation}&existTime=${existTime}&dispatchTime=${dispatchTime}
//     &time=${time}&battryPercentage=${battryPercentage}&fkUserId=${fkUserId}&role=${role}`, 
//     { headers: { "Authorization": `Bearer ${token}` },
//     body: JSON.stringify({
//          fkUserId: localStorage.getItem('fkUserId'),
//         role: localStorage.getItem('role'),
//       }) })
// }

export const getSearchTracking = async (pageno,selectedHeading,searchTerm) => {
    let token = await localStorage.getItem("token")
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/asset/tracking/getlist-SearchByParameters/${pageno}?${selectedHeading}=${searchTerm}&fkUserId=${fkUserId}&role=${role}`, 
    { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
         fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}