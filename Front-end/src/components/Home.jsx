import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Home() {
  const navigate = useNavigate();

  return (
    <div className="container text-center mt-5">
      <h1>Bienvenido a Presta Banco</h1>
      <p>Seleccione su tipo de usuario:</p>
      <div className="d-grid gap-2 col-6 mx-auto">
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <button 
            className="btn btn-primary btn-lg" 
            onClick={() => navigate('/home/Client')}
          >
            Soy un Cliente
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <button 
            className="btn btn-danger btn-lg" 
            onClick={() => navigate('/home/Client')}
          >
            Soy un Ejecutivo
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
      </div>
    </div>
  );
}

export default Home;