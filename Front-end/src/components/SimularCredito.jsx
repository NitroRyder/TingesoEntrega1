import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import usuarioServices from '../services/usuario.services';

function SimularCredito() {
  const [credito, setCredito] = useState({
    rut: '',
    P: '',
    r: '',
    n: '',
    V: ''
  });

  const [resultado, setResultado] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredito({ ...credito, [name]: value });
    {/*IMPRECIÓN PASO A PASO*/}
    console.log(credito);
  };
{/*---------------------------------------------------------------------------------------------------*/} 
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null); // Reset error state
    {/*---------------------------------------------------------------------------------------------*/}  
    const rut = credito.rut;
    if (rut.length < 7 || rut.length > 12) {
      setError('ERROR: EL RUT INGRESADO NO ES VÁLIDO. POR FAVOR INGRESAR UN RUT VÁLIDO.');
      return;
    }
    // Verificar que el penúltimo carácter sea '-'
    if (rut[rut.length - 2] !== '-') {
      setError('ERROR: EL RUT DEBE CONTENER UN "-" EN EL PENÚLTIMO CARÁCTER.');
      return;
    }
    const { P, r, n, V } = credito;
    if (P < 0 || r < 0 || n < 0 || V < 0) {
      setError('ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGARIVOS O IGUAL A 0.');
      return;
    }

    if(n <= 30 && 0.035<= r && r <=0.05 && P <= V*0.8){
      alert("PRESTAMO SIMULADO: Primera Vivienda");
      alert("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:\n 1.- Comprobante de ingresos\n 2.- Certificado de avalúo\n 3.- Historial crediticio")
      alert("SIEMPRE RECORDAR: INGRESAR EL INFORME DE DICOM.")
    }else if(n <= 20 && 0.04<= r && r <=0.06 && P <= V*0.7){
      alert("PRESTAMO SIMULADO: Segunda Vivienda");
      alert("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:\n 1.- Comprobante de ingresos\n 2.- Certificado de avalúo\n 3.- Escritura de la primera vivienda\n 4.- Historial crediticio");
      alert("SIEMPRE RECORDAR: INGRESAR EL INFORME DE DICOM.")
    }else if(n <= 25 && 0.05<= r && r <=0.07 && P <= V*0.6){
      alert("PRESTAMO SIMULADO: Propiedades Comerciales");
      alert("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:\n 1.- Estado financiero del negocio\n 2.- Comprobante de ingresos\n 3.- Certificado de avalúo\n 4.- Plan de negocios");
      alert("SIEMPRE RECORDAR: INGRESAR EL INFORME DE DICOM.")
    }else if(n <=15 && 0.045<= r && r <=0.06 && P<= V*0.5){
      alert("PRESTAMO SIMULADO: Remodelación");
      alert("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:\n 1.- Comprobante de ingresos\n 2.- Presupuesto de la remodelación\n 3.- Certificado de avalúo actualizado");
      alert("SIEMPRE RECORDAR: INGRESAR EL INFORME DE DICOM.")
    }else{
      setError('ERROR: LOS VALORES INGRESADOS NO CUMPLEN CON LOS REQUISITOS MÍNIMOS PARA SIMULAR EL CRÉDITO.');
      return;
    }
    {/*---------------------------------------------------------------------------------------------*/}  
    try {      
      const response = await usuarioServices.calcularMontoMensual(credito);
      if(response.data === -2) {
        setError('ERROR: EL RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA.');
      }else if(response.data === -1) {
        setError('ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGARIVOS O IGUAL A 0.');
      }else{
        setResultado(response.data);
      }

    } catch (error) {
      setError('ERROR AL SIMULAR EL CRÉDITO. POR FAVOR, INTENTAR DE NUEVO.');
      console.error('Error:', error);
    }
  };
  {/*---------------------------------------------------------------------------------------------*/}  
  return (
    <div className="container mt-5">
      <h2>Simular Crédito</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">RUT</label>
          <input type="text" className="form-control" name="rut" value={credito.rut} onChange={handleChange} placeholder="Ejemplo: 12345678-9" required />
          <small className="form-text text-muted">
            Formato: 12345678-9
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label className="form-label">Monto del Préstamo (P)</label>
          <input type="number" className="form-control" name="P" value={credito.P} onChange={handleChange} placeholder="Ejemplo: 50000" required />
          <small className="form-text text-muted">
            Ingrese el monto del préstamo en pesos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label className="form-label">Tasa de Interés Anual (r)</label>
          <input type="number" step="0.01" className="form-control" name="r" value={credito.r} onChange={handleChange} placeholder="Ejemplo: 0.05" required />
          <small className="form-text text-muted">
            Ingrese la tasa de interés anual en formato decimal. Ej: 0.05 para 5%
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label className="form-label">Plazo del Préstamo en Años (n)</label>
          <input type="number" className="form-control" name="n" value={credito.n} onChange={handleChange} placeholder="Ejemplo: 30" required />
          <small className="form-text text-muted">
            Ingrese el plazo del préstamo en años.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label className="form-label">Valor de la Propiedad (V)</label>
          <input type="number" className="form-control" name="V" value={credito.V} onChange={handleChange} placeholder="Ejemplo: 100000" required />
          <small className="form-text text-muted">
            Ingrese el valor de la propiedad en pesos.
          </small>
          {/*---------------------------------------------------------------------------------------------*/}  
        </div>
        <div className="d-grid gap-2">
        <button type="submit" className="btn btn-primary btn-lg">Simular</button>
        </div>
      </form>
      {resultado && (
        <div className="mt-3">
          <h4>Resultado de la Simulación</h4>
          <p>Cuota Mensual: {resultado}</p>
        </div>
      )}
      {error && (
        <div className="mt-3 alert alert-danger">
          {error}
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
    </div>
  );
}

export default SimularCredito;