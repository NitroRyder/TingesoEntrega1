import httpClient from "../http-common";
//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/api/ahorros/all');
}
//--------------------------------------------------------------------//
const create = data => {
    return httpClient.post("/api/ahorros/save", data);
}
//--------------------------------------------------------------------//
const get = id => {
    return httpClient.get(`/api/ahorros/find/${id}`);
}
//--------------------------------------------------------------------//
const update = data => {
    return httpClient.put('/api/ahorros/update', data);
}
//--------------------------------------------------------------------//
const remove = id => {
    return httpClient.delete(`/api/ahorros/delete/${id}`);
}
//--------------------------------------------------------------------//
export default { getAll, create, get, update, remove };