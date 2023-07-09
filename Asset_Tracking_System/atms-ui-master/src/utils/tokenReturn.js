async function tokenReturn() {
    let yourConfig = {
        headers: {
            Authorization: await localStorage.getItem('token') != null ? 'Bearer ' + await localStorage.getItem('token') : '',
        }
    }
    return yourConfig
}

export default tokenReturn
