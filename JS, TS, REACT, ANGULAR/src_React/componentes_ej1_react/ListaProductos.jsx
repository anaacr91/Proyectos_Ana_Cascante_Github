import { useState } from 'react'

function ListaProductos() {

    const [productos, setProductos] = useState([
        { id: 1, nombre: "Teclado Mecánico", precio: 89.99, stock: 12, categoria: "Periféricos", descripcion: "Teclado mecánico retroiluminado con switches azules.", imagen: "assets/teclado.png" },
        { id: 2, nombre: "Ratón Inalámbrico", precio: 35.50, stock: 20, categoria: "Periféricos", descripcion: "Ratón inalámbrico ergonómico con batería de larga duración.", imagen: "assets/raton.png" }
    ])
    
    const eliminarProducto = (id) => {
        setProductos(productos.filter(producto => producto.id !== id))
    }

    return (
        <div>
            <div>
                <h2>Lista de Productos ({productos.length})</h2>
                {productos.map((producto) => (
                    <ul key={producto.id}>
                        <li>Nombre: {producto.nombre}</li>
                        <li>Categoría: {producto.categoria}</li>
                        <li>Precio (€): {producto.precio}</li>
                        <li>Stock: {producto.stock}</li>
                        <li>Descripción: {producto.descripcion}</li>
                        <li>Imagen: <img src={producto.imagen} alt={producto.nombre} width="100"/></li>
                        <li>
                            Acción: 
                            <button onClick={() => eliminarProducto(producto.id)}>
                                Borrar
                            </button>
                        </li>
                    </ul>
                ))}
            </div>
        </div>
    )
}
export default ListaProductos;