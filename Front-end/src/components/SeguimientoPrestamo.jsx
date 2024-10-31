import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import usuarioServices from '../services/usuario.services';

const SeguimientoPrestamo = () => {
  const [userId, setUserId] = useState('');
  const [solicitud, setSolicitud] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserId(Number(e.target.value));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log('Submitting userId:', userId);

    try {
      const response = await usuarioServices.seguimiento({ userId });
      //console.log('Response:', response);
      if (response.data === -2) {
        alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
      } else if (response.data == null) {
        alert('ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO RECHAZADA');

      }else if (response.data == "") {
        alert('ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO CREADA');
      } else {
        setSolicitud(response.data); // Almacenar la solicitud en el estado
        //console.log('Solicitud recibida:', response.data); // Verificar los datos recibidos
        //alert('Seguimiento de préstamo realizado correctamente');
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
      <h2>Seguimiento de Préstamo</h2>
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
        {solicitud && (
          <div className="mt-3">
            <h3>Detalles de la Solicitud</h3>
            <div className="card">
              <div className="card-body">
                <p className="card-text"><strong>Estado de la solicitud:</strong> {solicitud.state}</p>
                <p className="card-text"><strong>Monto del Préstamo:</strong> {solicitud.montop}</p>
                <p className="card-text"><strong>Plazo:</strong> {solicitud.plazo} años</p>
                <p className="card-text"><strong>Tasa de Interés Anual:</strong> {solicitud.intanu}%</p>
                <p className="card-text"><strong>Tasa de Interés Mensual:</strong> {solicitud.intmen}%</p>
                <p className="card-text"><strong>Seguro de Desgravamen:</strong> {solicitud.segudesg}</p>
                <p className="card-text"><strong>Seguro de Incendio:</strong> {solicitud.seguince}</p>
                <p className="card-text"><strong>Comisión Administrativa:</strong> {solicitud.comiad}</p>
              </div>
            </div>
          </div>
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

export default SeguimientoPrestamo;