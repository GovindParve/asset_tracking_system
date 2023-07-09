import Axios from "../utils/axiosInstance"

export const excelUploadForOrg = async (payload) => {
    let token = await localStorage.getItem("token")
    let username = await localStorage.getItem("username")
    return Axios.post(`/gateway/import-excel-file-with-gateway-from-SuperAdmin`, payload, {
        headers: {
            "Content-Type" : "multipart/form-data",
            "Access-Control-Allow-Origin": "*",
            "Authorization": `Bearer ${token}`
            // "maxContentLength": 100000000,
            // "maxBodyLength": 1000000000
        }
    })
}
