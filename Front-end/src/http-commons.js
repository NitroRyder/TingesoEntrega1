import axios from "axios";

// EL PRIMERO ES EL id DEL PC
const BackendServer = "http://localhost";
const BackendPort = "5432";

export default axios.create({
    baseURL: `${BackendServer}:${BackendPort}`,
    headers: {
        "Content-type": "application/json"
    }
});