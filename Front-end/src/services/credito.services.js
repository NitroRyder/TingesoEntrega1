import httpClient from "../http-common";
//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/api/credito/all');
}
//--------------------------------------------------------------------//
const create = data => {
    return httpClient.post("/api/credito/save", data);
}
//--------------------------------------------------------------------//
const get = id => {
    return httpClient.get(`/api/credito/find/${id}`);
}
//--------------------------------------------------------------------//
const find = title => {
    return httpClient.get(`/api/credito/find/${title}`);
}
//--------------------------------------------------------------------//
const save = data => {
    return httpClient.post('/api/credito/save', data);
}
//--------------------------------------------------------------------//
const update = data => {
    return httpClient.put('/api/credito/update', data);
}
//--------------------------------------------------------------------//
const remove = id => {
    return httpClient.delete(`/api/credito/delete/${id}`);
}
//--------------------------------------------------------------------//
const deleteAll = () => {
    return httpClient.delete(`/api/credito/delete/all`);
}
//--------------------------------------------------------------------//
const add = (data, rut) => {
    return httpClient.post(`/api/credito/add/${rut}`, data);
}
//--------------------------------------------------------------------//
export default { getAll, create, get, find, save, update, remove, deleteAll, add };