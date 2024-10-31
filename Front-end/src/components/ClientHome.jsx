import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function ClientHome() {
  const navigate = useNavigate();

  return (
    <div className="container text-center mt-5">
      <h1>Operaciones del Cliente</h1>
      <p>Seleccione una operación:</p>
      <div className="row">
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-primary btn-lg w-100" 
            onClick={() => navigate('/usuario/register')}
          >
            Registrar Usuario
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-secondary btn-lg w-100" 
            onClick={() => navigate('/credito/simular')}
          >
            Simular Crédito
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-success btn-lg w-100" 
            onClick={() => navigate('/credito/solicitar')}
          >
            Solicitar Crédito
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-warning btn-lg w-100" 
            onClick={() => navigate('/credito/evaluar')}
          >
            Evaluar Crédito Solicitado
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-danger btn-lg w-100" 
            onClick={() => navigate('/credito/seguimiento')}
          >
            Seguimiento al Crédito Creado
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3">
          <button 
            className="btn btn-info btn-lg w-100" 
            onClick={() => navigate('/costo/calcular')}
          >
            Calcular Costos
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3 d-flex justify-content-center">
          <button 
            className="btn btn-info btn-lg w-100" 
            style={{ backgroundColor: 'purple', borderColor: 'purple', color: 'white' }}
            onClick={() => navigate('/usuario/notificaciones')}
          >
            Ver Notificaciones
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3 d-flex justify-content-center">
          <button 
            className="btn btn-info btn-lg w-100" 
            style={{ backgroundColor: 'orange', borderColor: 'orange', color: 'white' }}
            onClick={() => navigate('/update-user')}
          >
            Actualizar Usuario
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="col-md-4 mb-3 d-flex justify-content-center">
          <button 
            className="btn btn-dark btn-lg w-100" 
            onClick={() => navigate('/')}
          >
            Regresar a Home
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}

      </div>
    </div>
  );
}

export default ClientHome;