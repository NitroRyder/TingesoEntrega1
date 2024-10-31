import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import usuarioServices from '../services/usuario.services';

const CalcularCosto = () => {
  const [userId, setUserId] = useState('');
  const [costResult, setCostResult] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserId(Number(e.target.value));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log('Submitting userId:', userId);

    try {
      const response = await usuarioServices.calcularCostosTotales({ userId });
      if (response.data === -2) {
        alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
      }else if (response.data === "") {
        alert('ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO CREADA');
      } else if (response.data === null) {
        alert('TASA DE INTERÉS MENSUAL INCORRECTA');
      } else {
        setCostResult(response.data); // Almacenar la solicitud en el estado
        //alert('Cálculo de costos totales realizado correctamente');
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
      <h2>Calcular Costos</h2>
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
          <button type="submit" className="btn btn-primary btn-lg">Calcular Costos</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      {costResult.length > 0 && (
        <div className="mt-3">
          <h3>Detalles del Costo</h3>
          <div className="card">
            <div className="card-body">
              <p className="card-text"><strong>CUOTA MENSUAL DEL PRÉSTAMO:</strong> {costResult[0].toFixed(4)}</p>
              <p className="card-text"><strong>SEGURO DE DESGRAVAMEN:</strong> {costResult[1].toFixed(4)} <strong>MENSUALES</strong></p>
              <p className="card-text"><strong>SEGURO DE INCENDIO:</strong> {costResult[2].toFixed(4)} <strong>MENSUALES</strong></p>
              <p className="card-text"><strong>COMISIÓN ADMINISTRATIVA:</strong> {costResult[3].toFixed(4)}</p>
              <p className="card-text"><strong>COSTO MENSUAL DEL PRÉSTAMO:</strong> {costResult[4].toFixed(4)}</p>
              <p className="card-text"><strong>COSTO TOTAL DEL PRÉSTAMO:</strong> {costResult[5].toFixed(4)}</p>
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

export default CalcularCosto;