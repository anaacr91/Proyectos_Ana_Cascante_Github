import { useState } from 'react'

function AgregarProducto() {

    const [productos, setProductos] = useState([
        { id: 1, nombre: "Teclado Mecánico", precio: 89.99, stock: 12, categoria: "Periféricos", descripcion: "Teclado mecánico retroiluminado con switches azules.", imagen: "assets/teclado.png" },
        { id: 2, nombre: "Ratón Inalámbrico", precio: 35.50, stock: 20, categoria: "Periféricos", descripcion: "Ratón inalámbrico ergonómico con batería de larga duración.", imagen: "assets/raton.png" }
    ])

    const [nuevoProducto, setNuevoProducto] = useState({
        nombre: '', precio: '', stock: '', categoria: '', descripcion: '', imagen: ''
    })
    const handleInputChange = (e) => {
        const { name, value } = e.target
        setNuevoProducto({
            ...nuevoProducto, [name]: value
        })
    }

    const agregarProducto = (e) => {
        e.preventDefault()

        if (!nuevoProducto.nombre || !nuevoProducto.precio || !nuevoProducto.stock || !nuevoProducto.categoria || !nuevoProducto.descripcion || !nuevoProducto.imagen) {
            alert('Por favor, completa todos los campos')
            return
        }

        const producto = {
            id: Date.now(), // ID único basado en timestamp
            nombre: nuevoProducto.nombre,
            precio: parseFloat(nuevoProducto.precio),
            stock: parseInt(nuevoProducto.stock)
        }

        setProductos([...productos, producto])

        setNuevoProducto({
            nombre: '',
            precio: '',
            stock: '',
            categoria: '',
            descripcion: '',
            imagen: ''
        })
    }

    return (
        <div>
            <h2>Agregar un Nuevo Producto</h2>
            <form onSubmit={agregarProducto}>
                <div>
                    <label>Nombre del producto:</label>
                    <input 
                        type="text"
                        name="nombre"
                        value={nuevoProducto.nombre}
                        onChange={handleInputChange}
                        placeholder="Ej: Monitor LED"
                    />
                </div>
                <div>
                    <label>Precio (€):</label>
                    <input 
                        type="number"
                        step="0.01"
                        name="precio"
                        value={nuevoProducto.precio}
                        onChange={handleInputChange}
                        placeholder="0.00"
                    />
                </div>
                <div>
                    <label>Stock:</label>
                    <input 
                        type="number"
                        name="stock"
                        value={nuevoProducto.stock}
                        onChange={handleInputChange}
                        placeholder="0"
                    />
                </div>
                <div>
                    <label>Categoría:</label>
                    <input
                        type="text"
                        name="categoria"
                        value={nuevoProducto.categoria}
                        onChange={handleInputChange}
                        placeholder="Ej: Periféricos"
                    />
                </div>
                <div>
                    <label>Descripción:</label>
                    <input
                        type="text"
                        name="descripcion"
                        value={nuevoProducto.descripcion}
                        onChange={handleInputChange}
                        placeholder="Descripción del producto"
                    />
                </div>
                <div>
                    <label>Imagen:</label>
                    <input
                        type="text"
                        name="imagen"
                        value={nuevoProducto.imagen}
                        onChange={handleInputChange}
                        placeholder="URL de la imagen"
                    />
                </div>
                <button type="submit">Agregar Producto</button>
            </form>
        </div>
    )
}
export default AgregarProducto;