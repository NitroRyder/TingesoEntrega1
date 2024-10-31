import httpClient from "../http-common";

// BASE DE LA DIRECCIÓN = /api/usuario
//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/api/usuario/all');
}
//--------------------------------------------------------------------//
const create = data => {
    return httpClient.post("/api/usuario/save", data);
}
//--------------------------------------------------------------------//
const get = id => {
    return httpClient.get(`/api/usuario/find/${id}`);
}
//--------------------------------------------------------------------//
const getByRut = rut => {
    return httpClient.get(`/api/usuario/find/rut/${rut}`);
}
//--------------------------------------------------------------------//
const getByObjective = objective => {
    return httpClient.get(`/api/usuario/find/objective/${objective}`);
}
//--------------------------------------------------------------------//
const update = async (id, userData) => {
    return httpClient.put('/api/usuario/update', null, {
        params: { id, ...userData }
    });
};
//--------------------------------------------------------------------//
const remove = id => {
    return httpClient.delete(`/api/usuario/delete/${id}`);
}
//--------------------------------------------------------------------//
const deleteAll = () => {
    return httpClient.delete('/api/usuario/delete/all');
}
//--------------------------------------------------------------------//
//---------------------[FUNCIONES ADICIONALES]------------------------//
//--------------------------------------------------------------------//
const calcularMontoMensual = data => {
    return httpClient.get('/api/usuario/calcularMontoMensual', {
        params: data
    });
}
//--------------------------------------------------------------------//
const register = data => {
    return httpClient.post('/api/usuario/register', data);
}
//--------------------------------------------------------------------//
const solicitarCredito = formData => {
    return httpClient.post('/api/usuario/solicitarCredito', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}
//--------------------------------------------------------------------//
const aprobarCredito = data => {
    console.log('Data being sent to aprobarCredito:', data);
    return httpClient.post('/api/usuario/aprobarCredito', data);
}
//--------------------------------------------------------------------//
const seguimiento = data => {
    console.log('Data being sent to seguimiento:', data);
    return httpClient.post('/api/usuario/seguimiento', data);
}
//--------------------------------------------------------------------//
const calcularCostosTotales = data => {
    console.log('Data being sent to calcularCostosTotales:', data);
    return httpClient.post('/api/usuario/calcularCostosTotales', data);
}
//--------------------------------------------------------------------//
const notificaciones = data =>{
    console.log('Data being sent to notificaciones:', data);
    return httpClient.post('/api/usuario/notificaciones',data);
    //return httpClient.get('/api/usuario/notificaciones', data);
}
//--------------------------------------------------------------------//
const updateState = data =>{
    console.log('Data being sent to updateState:', data);
    return httpClient.post('/api/usuario/updateState',data);
}
//--------------------------------------------------------------------//
export default {getAll, create, get, getByRut, getByObjective, update, remove, deleteAll, calcularMontoMensual, register, solicitarCredito, aprobarCredito, seguimiento, calcularCostosTotales, notificaciones, updateState};