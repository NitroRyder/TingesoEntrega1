import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import usuarioServices from '../services/usuario.services';
{/*---------------------------------------------------------------------------------------------------*/}  
function RegisterUsuario() {
  const [usuario, setUsuario] = useState({
    rut: '',
    name: '',
    age: '',
    workage: '',
    houses: '',
    valorpropiedad: '',
    ingresos: '',
    sumadeuda: '',
    objective: '',
    independiente: '',
    ahorros: Array(12).fill({ transaccion: '', tipo: '' }) // Inicializar con 12 transacciones vacías
  });
{/*---------------------------------------------------------------------------------------------------*/}  
  const [creditos, setCreditos] = useState([
    {
      montop: '',
      plazo: '',
      intanu: '',
      intmen: '',
      segudesg: '',
      seguince: '',
      comiad: '',
      state: 'APROBADO' // Valor predeterminado
    }
  ]);
{/*---------------------------------------------------------------------------------------------------*/}  
  const [showModal, setShowModal] = useState(false);
  const [showCreditoModal, setShowCreditoModal] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUsuario({ ...usuario, [name]: value });
  };

  const handleAhorroChange = (index, e) => {
    const { name, value } = e.target;
    const newAhorros = [...usuario.ahorros];
    newAhorros[index] = { ...newAhorros[index], [name]: value };
    setUsuario({ ...usuario, ahorros: newAhorros });
  };

  const handleCreditoChange = (index, e) => {
    const { name, value } = e.target;
    const newCreditos = [...creditos];
    newCreditos[index] = { ...newCreditos[index], [name]: value };
    setCreditos(newCreditos);
  };

  const addAhorro = () => {
    setUsuario({ ...usuario, ahorros: [...usuario.ahorros, { transaccion: '', tipo: '' }] });
  };

  const addCredito = () => {
    setCreditos([...creditos, {
      montop: '',
      plazo: '',
      intanu: '',
      intmen: '',
      segudesg: '',
      seguince: '',
      comiad: '',
      state: ''
    }]);
  };

  const handleSaveAhorros = () => {
    setShowModal(false);
  };

  const handleSaveCreditos = () => {
    setShowCreditoModal(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
{/*---------------------------------------------------------------------------------------------------*/}  
    const rut = usuario.rut;
    if (rut.length < 7 || rut.length > 12) {
      alert('ERROR: EL RUT INGRESADO NO ES VÁLIDO. POR FAVOR INGRESAR UN RUT VÁLIDO.');
      return;
    }
    if (rut[rut.length - 2] !== '-') {
      alert('ERROR: EL RUT DEBE CONTENER UN "-" EN EL PENÚLTIMO CARÁCTER.');
      return;
    }
    const { name, age, workage, houses, valorpropiedad, ingresos, sumadeuda, objective, independiente, ahorros } = usuario;
    if(age < 18){
      alert('ERROR: DEBE SER MAYOR DE EDAD PARA REGISTRARSE.');
      return;
    }
    if(workage < 0 || houses < 0 || valorpropiedad < 0 || ingresos < 0 || sumadeuda < 0){
      alert('ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGATIVOS O IGUAL A 0.');
      return;
    }
    if(objective === '' || independiente === ''){ 
      alert('ERROR: DEBE LLENAR LOS CAMPOS DE Objetivo y Tipo de trabajador.');
      return;
    }
    if(objective !== 'REMODELACION' && objective !== 'PROPIEDAD COMERCIAL' && objective !== 'PRIMERA VIVIENDA' && objective !== 'SEGUNDA VIVIENDA'){
      alert('ERROR: EL OBJETIVO DEBE SER REMODELACION, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.');
      return;
    }
    if(independiente !== 'INDEPENDIENTE' && independiente !== 'ASALARIADO'){
      alert('ERROR: EL TIPO DE TRABAJADOR DEBE SER INDEPENDIENTE O ASALARIADO.');
      return;
    }
    if(ahorros.length < 12){
      alert('ERROR: DEBE LLENAR LOS 12 CAMPOS DE AHORROS.');
      return;
    }
{/*---------------------------------------------------------------------------------------------------*/}      
    for (let i = 0; i < ahorros.length; i++) {
      if(ahorros[i].transaccion === 0){
        alert('ERROR: LAS TRANSACCIONES INGRESADAS NO PUEDEN SER IGUAL A 0 EN TRANSICIÓN.' + i);
        return;
      }
      if(ahorros[i].tipo === ''){
        alert('ERROR: DEBE LLENAR LOS CAMPOS DE TIPO DE AHORRO EN TRANSICIÓN.' + i);
        return;
      }
      if(ahorros[i].tipo !== 'DEPOSITO' && ahorros[i].tipo !== 'PAGO' && ahorros[i].tipo !== 'TRANSFERENCIA' && ahorros[i].tipo !== 'RETIRO'){
        alert('ERROR: EL TIPO DE AHORRO DEBE SER DEPOSITO O PAGO O TRANSFERENCIA O RETIRO EN TRANSICIÓN.' + i);
        return;
      }
      if(ahorros[i].tipo === 'DEPOSITO' && ahorros[i].transaccion <= 0){
        alert('ERROR: LA TRANSACCIÓN DE UN DEPOSITO DEBE SER MAYOR A 0 EN TRANSICIÓN.' + i);
        return;
      }
      if((ahorros[i].tipo === 'PAGO' || ahorros[i].tipo === 'TRANSFERENCIA' || ahorros[i].tipo === 'RETIRO') && ahorros[i].transaccion >= 0){
        alert('ERROR: LA TRANSACCIÓN DE UN PAGO, TRANSFERENCIA O RETIRO DEBE SER MENOR A 0 EN TRANSICIÓN.' + i);
        return;
      }
    }
    for(let i = 0; i < creditos.length; i++){
      if(creditos[i].state !== 'APROBADO'){
        alert('ERROR: NO PUEDE EXISTIR EN EL HISTORIAL DE CREDITOS UNO QUE NO SEA APROBADO.');
        return;
      }
    }
{/*---------------------------------------------------------------------------------------------------*/}      
    try {
      const response = await usuarioServices.register({ ...usuario, creditos });
      if(response.data === -2){
        alert('ERROR: EL RUT INGRESADO YA SE ENCUENTRA REGISTRADO EN EL SISTEMA.');
      }else{
        alert('Usuario registrado con éxito');
        alert('ATENCIÓN, el ID del usuario es: ' + response.data.id);
        navigate('/home/Client');
      }

    } catch (error) {
      alert('ERROR AL REGISTRAR UN USUARIO DEL TIPO: ' + error.response.data);
    }
  };
{/*---------------------------------------------------------------------------------------------------*/}  
      {/*---------------------------------------------------------------------------------------------*/}
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const handleShowCreditoModal = () => setShowCreditoModal(true);
  const handleCloseCreditoModal = () => setShowCreditoModal(false);

  return (
    <div className="container mt-5">
      <h2>Registrar Usuario</h2>
      <form onSubmit={handleSubmit}>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">RUT:</label>
          <input type="text" className="form-control" name="rut" value={usuario.rut} onChange={handleChange} placeholder="Ejemplo: 12345678-9" required />
          <small className="form-text text-muted">
            Formato: 12345678-9
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Nombre:</label>
          <input type="text" className="form-control" name="name" value={usuario.name} onChange={handleChange} placeholder="Ejemplo: Elsa Payo" required />
          <small className="form-text text-muted">
            Ingrese el nombre y apellido, separados por un espacio. Ej: Elsa Payo.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Edad:</label>
          <input type="number" className="form-control" name="age" value={usuario.age} onChange={handleChange} placeholder="Ejemplo: 20" required />
          <small className="form-text text-muted">
            Ingrese su edad actual, para poder registrarse, tiene que ser mayor de 18 años.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Años de Trabajo:</label>
          <input type="number" className="form-control" name="workage" value={usuario.workage} onChange={handleChange} placeholder="Ejemplo: 30" required />
          <small className="form-text text-muted">
            Ingrese los años que lleva empleado en su trabajo mas reciente. Ej: 3 años.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Número de Casas:</label>
          <input type="number" className="form-control" name="houses" value={usuario.houses} onChange={handleChange} placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese la cantidad de propiedades que posea en este momento. Ej: 1 casa.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Valor de Propiedad:</label>
          <input type="number" className="form-control" name="valorpropiedad" value={usuario.valorpropiedad} onChange={handleChange} placeholder="Ejemplo: 1000000" required/>
          <small className="form-text text-muted">
            Ingrese el valor de su propiedad. Ej: 1000000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Ingresos Mensuales:</label>
          <input type="number" className="form-control" name="ingresos" value={usuario.ingresos} onChange={handleChange} placeholder="Ejemplo: 500000" required />
          <small className="form-text text-muted">
            Ingrese el valor de sus ingresos mensuales. Ej: 500000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Deuda Total:</label>
          <input type="number" className="form-control" name="sumadeuda" value={usuario.sumadeuda} onChange={handleChange} placeholder="Ejemplo: 240000" required/>
          <small className="form-text text-muted">
            Ingrese el valor de la suma de todas sus deudas actuales. Ej: 240000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Objetivo:</label>
          <input type="text" className="form-control" name="objective" value={usuario.objective} onChange={handleChange} placeholder="ESCOGER ENTRE: REMODELACION | PROPIEDAD COMERCIAL | PRIMERA VIVIENDA | SEGUNDA VIVIENDA" required/>
          <small className="form-text text-muted">
            Ingrese el motivo por el cual va a querer registrarse. Ej; REMODELACION, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Tipo de trabajador:</label>
          <input type="text" className="form-control" name="independiente" value={usuario.independiente} onChange={handleChange} placeholder="ESCOGER ENTRE: INDEPENDIENTE | ASALARIADO" required />
          <small className="form-text text-muted">
            Ingrese si es un trabajador independiente o asalariado. Ej: INDEPENDIENTE, ASALARIADO.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        {/* BOTÓN PARA ABRIR EL MODÁL DE AHORROS */}
        <button type="button" className="btn btn-secondary w-100" onClick={handleShowModal}> Agregar historial de depósitos de los últimos 12 meses o más</button>

        {/* BOTÓN PARA ABRIR EL MODÁL DE CRÉDITO */}
        <button type="button" className="btn btn-secondary w-100 mt-3" onClick={handleShowCreditoModal}> Agregar información de crédito (opcional)</button>
        <div className="d-grid gap-2">
        <button type="submit" className="btn btn-primary mt-3 btn-lg">Registrar</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      {/* MODAL PARA AGREGAR AHORROS*/}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Agregar Ahorro</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {usuario.ahorros.map((ahorro, index) => (
            <div key={index} className="mb-3">
              <label className="form-label">Transacción {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="transaccion"
                value={ahorro.transaccion}
                onChange={(e) => handleAhorroChange(index, e)}
                placeholder="Ejemplo: 1000"
                required
              />
              <label className="form-label">Tipo {index + 1}</label>
              <input
                type="text"
                className="form-control"
                name="tipo"
                value={ahorro.tipo}
                onChange={(e) => handleAhorroChange(index, e)}
                placeholder="ESCOGER: DEPOSITO | PAGO | TRANSFERENCIA | RETIRO"
                required
              />
            </div>
          ))}
          <Button variant="secondary" onClick={addAhorro}>Agregar Ahorro</Button>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleSaveAhorros}>Guardar Ahorros</Button>
          <Button variant="secondary" onClick={handleCloseModal}>Cerrar</Button>
        </Modal.Footer>
      </Modal>
      {/*---------------------------------------------------------------------------------------------*/}
      {/* MODAL PARA AGREGAR CRÉDITO */}
      <Modal show={showCreditoModal} onHide={handleCloseCreditoModal}>
        <Modal.Header closeButton>
          <Modal.Title>Agregar Crédito</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {creditos.map((credito, index) => (
            <div key={index} className="mb-3">
              <label className="form-label">Monto del Préstamo {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="montop"
                value={credito.montop}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 1000000"
              />
              <label className="form-label">Plazo (años) {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="plazo"
                value={credito.plazo}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 5"
              />
              <label className="form-label">Interés Anual {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="intanu"
                value={credito.intanu}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 5.5"
              />
              <label className="form-label">Interés Mensual {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="intmen"
                value={credito.intmen}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 0.5"
              />
              <label className="form-label">Seguro de Desgravamen {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="segudesg"
                value={credito.segudesg}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 10000"
              />
              <label className="form-label">Seguro de Incendio {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="seguince"
                value={credito.seguince}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 5000"
              />
              <label className="form-label">Comisión Administrativa {index + 1}</label>
              <input
                type="number"
                className="form-control"
                name="comiad"
                value={credito.comiad}
                onChange={(e) => handleCreditoChange(index, e)}
                placeholder="Ejemplo: 2000"
              />
            
            </div>
          ))}
          <Button variant="secondary" onClick={addCredito}>Agregar Crédito</Button>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleSaveCreditos}>Guardar Créditos</Button>
          <Button variant="secondary" onClick={handleCloseCreditoModal}>Cerrar</Button>
        </Modal.Footer>
      </Modal>

      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-dark btn-lg" 
          onClick={() => navigate('/home/Client')}
        >
          Regresar a Operaciones del Cliente
        </button>
      </div>
    </div>
  );
}

export default RegisterUsuario;