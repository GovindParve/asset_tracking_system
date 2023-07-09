import Axios from "../utils/axiosInstance"
export const uploadOnlyAdminFile = async (file) => {
    let token = await localStorage.getItem("token")
    return Axios.post("/user/import-excel-file-with-user-details-to-database-for-direct-Admin", file, {
        headers: {
            'content-type': 'multipart/form-data',
            'Access-Control-Allow-Origin': '*',
            "Authorization": `Bearer ${token}`
            // "maxContentLength": 100000000,
            // "maxBodyLength": 1000000000
        }
    })
}