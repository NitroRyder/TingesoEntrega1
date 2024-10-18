import httpClient from '../utils/httpClient';

// BASE DE LA DIRECCIÓN = /api/usuario

const getUsuarios = () => {
  return httpClient.get('/api/usuario/all');
}
// RECOMENDACIÓN -> SI SOLO USA UN VALOR PUEDO QUITAR LOS PARENTESIS
const getUsuarioById = id => {
    return httpClient.get(`/api/usuario/find/${id}`);
}

export default {
    getUsuarios,
}

