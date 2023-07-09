import Axios from "../utils/axiosInstance"

export const empuserExcelUpload = async (file) => {
    let token = await localStorage.getItem("token")
    return Axios.post("/user/import-excel-file-with-empuser-details-to-database", file, {
        headers: {
            'content-type': 'multipart/form-data',
            'Access-Control-Allow-Origin': '*',
            "Authorization": `Bearer ${token}`
        }
    })
}
