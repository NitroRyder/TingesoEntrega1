import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import usuarioServices from '../services/usuario.services';

const EvaluarCredito = () => {
  const [usuariosPendientes, setUsuariosPendientes] = useState([]);
  const [userId, setUserId] = useState('');
  const [files, setFiles] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserId(Number(e.target.value));
  };

  useEffect(() => {
    // Función para obtener usuarios con estado pendiente
    const fetchUsuariosPendientes = async () => {
      try {
        const response = await usuarioServices.getAll(); // Usar el servicio configurado
        const usuariosPendientes = response.data.filter(usuario => usuario.solicitud && usuario.solicitud.state === 'PENDIENTE');
        setUsuariosPendientes(usuariosPendientes);
      } catch (error) {
        console.error('Error al obtener usuarios pendientes:', error);
      }
    };

    fetchUsuariosPendientes();
  }, []);

const handleSubmit = async (e) => {
  e.preventDefault();

  console.log('Submitting userId:', userId);


  try {
    const response = await usuarioServices.aprobarCredito({ userId });
    console.log('Response:', response.data);
    if (response.data === -1) {
      alert('ERROR: ERROR MARCADO EN LAS NOTIFICACIONES');
    } else if (response.data === -2) {
      alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
    } else if (response.data === null || response.data === "") {
      alert('ERROR: ERROR MARCADO EN LAS NOTIFICACIONES');
    } else {
      // Asumiendo que la respuesta contiene un array de archivos en response.data.files
      const files = response.data.files || [];
      setFiles(files);
      console.log('Files:', files);
      alert('EVALUACIÓN DE SOLICITUD DE CRÉDITO EXITOSA');
    }
  } catch (error) {
    console.error('Error response:', error.response);
    if (error.response) {
      console.error('Error data:', error.response.data);
      console.error('Error status:', error.response.status);
      console.error('Error headers:', error.response.headers);
    }
    alert('Error al evaluar el crédito: ' + (error.response?.data || error.message));
  }
};

  return (
    <div className="container">
      <div className="mt-3">
        <h3>Usuarios con Estado Pendiente</h3>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Rut</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {usuariosPendientes.map((usuario) => (
              <tr key={usuario.id}>
                <td>{usuario.id}</td>
                <td>{usuario.name}</td>
                <td>{usuario.rut}</td>
                <td>{usuario.solicitud && usuario.solicitud.state}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {/*---------------------------------------------------------------------------------------------*/}
      <h2>Evaluar Prestamo</h2>
      <form onSubmit={handleSubmit}>
      {/*---------------------------------------------------------------------------------------------*/}
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
      {files && files.length > 0 && (
        <div className="mt-3">
          <h3>Archivos de la Solicitud</h3>
          <div className="card">
            <div className="card-body">
              {files.map((file, index) => (
                <p key={index} className="card-text">
                  <a href={`data:application/octet-stream;base64,${file.data}`} download={file.name}>{file.name}</a>
                </p>
              ))}
            </div>
          </div>
        </div>
      )}
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-danger btn-lg"
          onClick={() => navigate('/update-state')}
        >
          Actualizar estado de la solicitud
        </button>
      </div>
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

export default EvaluarCredito;