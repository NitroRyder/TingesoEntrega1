import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import usuarioServices from '../services/usuario.services';

const VerNotificaciones = () => {
  const [userId, setUserId] = useState('');
  const [notifications, setNotifications] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserId(Number(e.target.value));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log('Submitting userId:', userId);

    try {
        const response = await usuarioServices.notificaciones({ userId });
        console.log('Response:', response.data);

        if (response.data === -2) {
            alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
        } else if (response.data.length === 0) {
            alert('No hay notificaciones para el usuario');
        } else if (response.data === null || response.data === "") {
            alert('ERROR: EL USUARIO NO EXISTE');
        } else {
            setNotifications(response.data);
        }
    } catch (error) {
        console.error('Error response:', error.response);
        if (error.response) {
          console.error('Error data:', error.response.data);
          console.error('Error status:', error.response.status);
          console.error('Error headers:', error.response.headers);
        }
        alert('Error al realizar el seguimiento del préstamo: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div className="container">
      <h2>Notificaciones recibidas por la solicitud ingresada</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>User ID:</label>
          <input type="number" id="userId" value={userId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Realizar Seguimiento</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      {notifications && notifications.length > 0 ? (
        <div className="mt-3">
          <h3>Notificaciones:</h3>
          <div className="card">
            <div className="card-body">
              {notifications.map((notification, index) => (
                <div className="notification-item mb-3" key={index}>
                  <h5 className="card-title">Notificación {index + 1}</h5>
                  <p className="card-text"><strong>Mensaje:</strong> {notification}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      ) : (
        <p>No hay notificaciones para mostrar</p>
      )}
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
};

export default VerNotificaciones;