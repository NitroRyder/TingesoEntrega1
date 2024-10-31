import React from 'react';

const UsuarioList = () => {
  const [usuarios, setUsuarios] = React.useState([]);

  const navigate = useNavigate();

  const init = () => {
    usuarioService
    .getAll()
    .then((response) => {
      console.log("LISTADO DE USUARIOS: ", response.data); 
      setUsuarios(response.data);
    })
    .catch((e) => {
      console.log(
        "ERROR: NO SE HA PODIDO MOSTRAR LISTADO DE USUARIOS.", e);
    });
  };

  React.useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    usuarioService
    .remove(id).then((response) => {
      console.log("USUARIO ELIMINADO: ", response.data);
      init();
    })
    .catch((e) => {
      console.log(
        "ERROR: NO SE HA PODIDO ELIMINAR USUARIO.", e);  
    });
  }

  const handleEdit = (id) => {
    console.log("EDITAR USUARIO CON ID: ", id);
    navigate(`/usuario/edit/${id}`);
  }

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/usuario/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<PersonAddIcon />}
        >
          Añadir Empleado
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>RUT</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Nombre</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Edad</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Edad de Trabajo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Casas</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Valor Propiedad</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Ingresos</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Deuda Total</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Objetivo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Independencia</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Ahorros</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Solicitud</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Creditos</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Notificaciones</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {usuarios.map((usuario) => (
            <TableRow key={usuario.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
              <TableCell align="left">{usuario.rut}</TableCell>
              <TableCell align="left">{usuario.name}</TableCell>
              <TableCell align="left">{usuario.age}</TableCell>
              <TableCell align="left">{usuario.workage}</TableCell>
              <TableCell align="left">{usuario.houses}</TableCell>
              <TableCell align="left">{usuario.valorpropiedad}</TableCell>
              <TableCell align="left">{usuario.ingresos}</TableCell>
              <TableCell align="left">{usuario.sumadeuda}</TableCell>
              <TableCell align="left">{usuario.objective}</TableCell>
              <TableCell align="left">{usuario.independiente}</TableCell>
              <TableCell align="left">{usuario.ahorros.length}</TableCell>
              <TableCell align="left">{usuario.solicitud ? 'Sí' : 'No'}</TableCell>
              <TableCell align="left">{usuario.creditos.length}</TableCell>
              <TableCell align="left">{usuario.notifications.length}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(usuario.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(usuario.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<DeleteIcon />}
                >
                  Eliminar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};
export default UsuarioList;