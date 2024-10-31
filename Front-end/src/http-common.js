import axios from "axios";

const entrega1BackendServer = import.meta.env.VITE_ENTREGA_BACKEND_SERVER;
const entrega1BackendPort = import.meta.env.VITE_ENTREGA_BACKEND_PORT;

console.log(entrega1BackendServer);
console.log(entrega1BackendPort);

export default axios.create({
    baseURL: `http://${entrega1BackendServer}:${entrega1BackendPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});