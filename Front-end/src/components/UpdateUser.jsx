import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import usuarioServices from '../services/usuario.services';

const UpdateUser = () => {
  const [userId, setUserId] = useState('');
  const [userData, setUserData] = useState({
    valorpropiedad: '',
    ingresos: '',
    sumadeuda: '',
    objective: ''
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleUserIdChange = (e) => {
    setUserId(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log('Submitting userId:', userId);
    console.log('Submitting userData:', userData);

    if(userData.valorpropiedad < 0 || userData.ingresos < 0 || userData.sumadeuda < 0){
        console.log('ERROR: LOS VALORES DE LA PROPIEDAD, INGRESOS Y DEUDA NO PUEDEN SER NEGATIVOS.');
    }
    if(userData.objective != "REMODELACIÓN" || userData.objective != "PROPIEDAD COMERCIAL" || userData.objective != "PRIMERA VIVIENDA" || userData.objective != "SEGUNDA VIVIENDA"){
      console.log('ERROR: EL OBJETIVO DEBE SER REMODELACIÓN, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.');
    }
    try {
      const response = await usuarioServices.update(userId, userData);
      if(response === null || response == ""){
        alert("ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.")
      }else{
        console.log('Response:', response);
        alert('Usuario actualizado con éxito');
        navigate('/home/Client');
      }
    } catch (error) {
      alert('Error al actualizar el usuario: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div className="container">
      <h2>Actualizar Usuario</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>User ID:</label>
          <input type="text" value={userId} onChange={handleUserIdChange} className="form-control" placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej: 102
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label>Valor de la Propiedad:</label>
          <input type="number" name="valorpropiedad" value={userData.valorpropiedad} onChange={handleChange} className="form-control" placeholder="Ejemplo: 1000000" required />
          <small className="form-text text-muted">
            Ingrese el valor de su propiedad. Ej: 1000000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label>Ingresos Mensuales:</label>
          <input type="number" name="ingresos" value={userData.ingresos} onChange={handleChange} className="form-control" placeholder="Ejemplo: 500000" required />
          <small className="form-text text-muted">
            Ingrese el valor de sus ingresos mensuales. Ej: 500000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label>Deuda Total:</label>
          <input type="number" name="sumadeuda" value={userData.sumadeuda} onChange={handleChange} className="form-control" placeholder="Ejemplo: 240000" required />
          <small className="form-text text-muted">
            Ingrese el valor de la suma de todas sus deudas actuales. Ej: 240000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label>Objetivo:</label>
          <input type="text" name="objective" value={userData.objective} onChange={handleChange} className="form-control" placeholder="ESCOGER ENTRE: REMODELACIÓN | PROPIEDAD COMERCIAL | PRIMERA VIVIENDA | SEGUNDA VIVIENDA" required />
          <small className="form-text text-muted">
            Ingrese el motivo por el cual va a querer registrarse. Ej; REMODELACIÓN, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="d-grid gap-2">
        <button type="submit" className="btn btn-primary btn-lg">Actualizar Usuario</button>
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
};

export default UpdateUser;