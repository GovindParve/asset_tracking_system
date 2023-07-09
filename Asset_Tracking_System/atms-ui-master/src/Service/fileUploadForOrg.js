import Axios from "../utils/axiosInstance"

export const fileUploadForOrg = async (payload) => {
    let token = await localStorage.getItem("token");
    return Axios.post(`/tag/import-excel-file-with-tag-details-to-database-From-superAdmin`, payload, {
        headers: {
            "Content-Type" : "multipart/form-data",
            "Access-Control-Allow-Origin": "*",
            "Authorization": `Bearer ${token}`
            // "maxContentLength": 100000000,
            // "maxBodyLength": 1000000000
        }
    })
}
