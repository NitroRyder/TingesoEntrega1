import React, { useState } from 'react';
import usuarioServices from '../services/usuario.services';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function UpdateState() {
    const [userId, setUserId] = useState('');
    const [state, setState] = useState('');
    const navigate = useNavigate();

    const handleUserIdChange = (e) => {
        setUserId(e.target.value);
    };

    const handleStateChange = (e) => {
        setState(e.target.value);
    };

    const stateDescriptions = [
        "Estado: EN REVISIÓN INICIAL",
        "Estado: PENDIENTE DE DOCUMENTACIÓN",
        "Estado: EN EVALUACIÓN",
        "Estado: PRE-APROBADA",
        "Estado: EN APROBACIÓN FINAL",
        "Estado: APROBADA",
        "Estado: RECHAZADA",
        "Estado: CANCELADA POR EL CLIENTE",
        "Estado: EN DESEMBOLSO",
        "Estado: PENDIENTE"
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = { userId: parseInt(userId), state: parseInt(state) };
            const response = await usuarioServices.updateState(data);
            if(response.data === -2){
                alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
            }else if(response.data === -1){
                alert('ERROR: ERROR MARCADO EN LAS NOTIFICACIONES');
            }else if(response.data === null || response.data === ""){
                alert('ERROR: ERROR MARCADO EN LAS NOTIFICACIONES');
            }else{
                alert('ESTADO ACTUALIZADO EXITOSAMENTE');
                navigate('/home/Client');
            }
        } catch (error) {
            alert('Error al actualizar el estado: ' + (error.response?.data || error.message));
        }
    };

    return (
        <div className="container">
            <h2>Actualizar Estado del Usuario</h2>
            <form onSubmit={handleSubmit}>
            {/*---------------------------------------------------------------------------------------------*/}                
                <div className="mb-3">
                    <label>User ID:</label>
                    <input type="number" id="userId" value={userId} onChange={handleUserIdChange} className="form-control" placeholder="Ejemplo: 1" required />
                    <small className="form-text text-muted">
                        Ingrese el ID de su cuenta. Ej 102
                    </small>
                </div>
            {/*---------------------------------------------------------------------------------------------*/}                
                <div className="mb-3">
                    <label>Nuevo Estado:</label>
                    <select value={state} onChange={handleStateChange} className="form-control" required>
                        <option value="" disabled>SELECCIONE UN ESTADO PARA LA SOLICITUD</option>
                        {stateDescriptions.map((description, index) => (
                            <option key={index + 1} value={index + 1} title={description}>
                                {index + 1} - {description}
                            </option>
                        ))}
                    </select>
                </div>
            {/*---------------------------------------------------------------------------------------------*/}
                <div className="d-grid gap-2">
                <button type="submit" className="btn btn-primary btn-lg">Actualizar Estado</button>
                <button type="button" onClick={() => navigate('/home/Client')} className="btn btn-danger btn-lg">Regresar a Operaciones del Cliente</button>
                </div>
            </form>
            {/*---------------------------------------------------------------------------------------------*/}
            <div className="mt-3 d-grid gap-2">
                <button 
                className="btn btn-dark btn-lg"
                onClick={() => navigate('/home/Client')}
                >
                Regresar a Operaciones del Cliente
                </button>
            </div>
            {/*---------------------------------------------------------------------------------------------*/}            
        </div>
    );
}

export default UpdateState;