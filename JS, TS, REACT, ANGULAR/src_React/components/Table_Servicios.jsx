const SERVICIOS = [
    { id: 1, nombre: 'Consultoría', descripcion: 'Asesoramiento experto para tu negocio.', precio: 150 },
    { id: 2, nombre: 'Desarrollo Web', descripcion: 'Creación de sitios web modernos y funcionales.', precio: 300 },
    { id: 3, nombre: 'Marketing Digital', descripcion: 'Estrategias para aumentar tu presencia en línea.', precio: 200 },
];

function Table_Servicios() {
    return (
        <div>
            <h2>Servicios</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    {SERVICIOS.map(servicio => (
                        <tr key={servicio.id}>
                            <td>{servicio.id}</td>
                            <td>{servicio.nombre}</td>
                            <td>{servicio.descripcion}</td>
                            <td>€{servicio.precio}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Table_Servicios;