import { useEffect, useState } from 'react';

const UsuarioList = ({ usuarios }) => {
    return (
        <ul>
        {usuarios.map((usuario) => (
            <li key={usuario.id}>{usuario.nombre}</li>
        ))}
        </ul>
    );
}