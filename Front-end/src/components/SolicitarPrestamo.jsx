import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import usuarioServices from '../services/usuario.services';

const SolicitarPrestamo = () => {
  const [formData, setFormData] = useState({
    userId: '',
    montop: '',
    plazo: '',
    intanu: '',
    intmen: '',
    segudesg: '',
    seguince: '',
    comiad: '',
    comprobanteIngresos: null,
    certificadoAvaluo: null,
    historialCrediticio: null,
    escrituraPrimeraVivienda: null,
    planNegocios: null,
    estadosFinancieros: null,
    presupuestoRemodelacion: null,
    dicom: null,
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (files) {
      setFormData({ ...formData, [name]: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();
    for (const key in formData) {
      data.append(key, formData[key]);
    }
    const {userId, montop, plazo, intanu, intmen, segudesg, seguince, comiad } = formData;
    if (userId <= 0) {
      alert("ERROR: EL USER ID DEBE SER MAYOR A 0");
      return;
    }
    if (montop <= 0) {
      alert("ERROR: EL MONTO DEL PRÉSTAMO DEBE SER MAYOR A 0");
      return;
    }
    if (plazo <= 0) {
      alert("ERROR: EL PLAZO DEL PRÉSTAMO DEBE SER MAYOR A 0");
      return;
    }
    if (intanu <= 0) {
      alert("ERROR: LA TASA DE INTERÉS ANUAL DEBE SER MAYOR A 0");
      return;
    }
    if (intmen <= 0) {
      alert("ERROR: LA TASA DE INTERÉS MENSUAL DEBE SER MAYOR A 0");
      return;
    }
    if (segudesg <= 0) {
      alert("ERROR: EL SEGURO DE DESGRAVAMEN DEBE SER MAYOR A 0");
      return;
    }
    if (seguince <= 0) {
      alert("ERROR: EL SEGURO DE INCENDIO DEBE SER MAYOR A 0");
      return;
    }
    if (comiad <= 0) {
      alert("ERROR: LA COMISIÓN ADMINISTRATIVA DEBE SER MAYOR A 0");
      return;
    }
    const { comprobanteIngresos, certificadoAvaluo, dicom } = formData;
    if (!comprobanteIngresos || !certificadoAvaluo || !dicom) {
      alert("ERROR: LOS ARCHIVOS COMPROBANTE DE INGRESOS, CERTIFICADO DE AVALÚO Y DICOM SON OBLIGATORIOS");
      return;
    }
    try {
      const response = usuarioServices.solicitarCredito(data);
      if(response.data === -2) {
        alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
      }else if(response.data !== -2){
        alert('Solicitud de crédito creada o actualizada correctamente');
      }
    } catch (error) {
      alert('Error al crear la solicitud de crédito: ' + error.response.data);
    }
  };

  return (
    <div className="container">
      <h2>Solicitar Préstamo</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>User ID:</label>
          <input type="text" name="userId" value={formData.userId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 102" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Monto del préstamo:</label>
          <input type="number" name="montop" value={formData.montop} onChange={handleChange} className="form-control" placeholder="Ejemplo: 100000" required />
          <small className="form-text text-muted">
            Ingrese el monto del prestamo que necesita. Ej: 100000 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Plazo (años):</label>
          <input type="number" name="plazo" value={formData.plazo} onChange={handleChange} className="form-control" placeholder="Ejemplo: 20" required />
          <small className="form-text text-muted">
            Ingrese la cantidad de años que desea que dure el pago préstamo. Ej: 20 años.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Tasa de interés anual:</label>
          <input type="number" step="0.01" name="intanu" value={formData.intanu} onChange={handleChange} className="form-control" placeholder="Ejemplo: 0.05" required />
          <small className="form-text text-muted">
            Ingrese la tasa de interes anual. Ej: 0.05 para 5%.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Tasa de interés mensual:</label>
          <input type="number" step="0.01" name="intmen" value={formData.intmen} onChange={handleChange} className="form-control" placeholder="Ejemplo: 0.04" required />
          <small className="form-text text-muted">
            Ingrese la tasa de interes mensual. Ej: 0.04 para 4%.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Seguro de desgravamen:</label>
          <input type="number" step="0.01" name="segudesg" value={formData.segudesg} onChange={handleChange} className="form-control" placeholder="Ejemplo: 200" required />
          <small className="form-text text-muted">
            Ingrese el valor del seguro de desgravamen. Ej: 200 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Seguro de incendio:</label>
          <input type="number" step="0.01" name="seguince" value={formData.seguince} onChange={handleChange} className="form-control" placeholder="Ejemplo: 150" required />
          <small className="form-text text-muted">
            Ingrese el valor del seguro de incendio. Ej: 150 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Comisión administrativa:</label>
          <input type="number" step="0.01" name="comiad" value={formData.comiad} onChange={handleChange} className="form-control" placeholder="Ejemplo: 50" required />
          <small className="form-text text-muted">
            Ingrese el valor de la comisión administrativa. Ej: 50 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Comprobante de ingresos:</label>
          <input type="file" name="comprobanteIngresos" onChange={handleChange} className="form-control" required />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Certificado de avalúo:</label>
          <input type="file" name="certificadoAvaluo" onChange={handleChange} className="form-control" required />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Historial crediticio:</label>
          <input type="file" name="historialCrediticio" onChange={handleChange} className="form-control" />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Escritura primera vivienda:</label>
          <input type="file" name="escrituraPrimeraVivienda" onChange={handleChange} className="form-control" />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Plan de negocios:</label>
          <input type="file" name="planNegocios" onChange={handleChange} className="form-control" />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Estados financieros:</label>
          <input type="file" name="estadosFinancieros" onChange={handleChange} className="form-control" />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Presupuesto de remodelación:</label>
          <input type="file" name="presupuestoRemodelacion" onChange={handleChange} className="form-control" />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>DICOM:</label>
          <input type="file" name="dicom" onChange={handleChange} className="form-control" required />
          <small className="form-text text-muted">
            Formato: ARCHIVO PDF
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
        <button type="submit" className="btn btn-primary btn-lg">Solicitar Préstamo</button>
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
    </div>
  );
};

export default SolicitarPrestamo;