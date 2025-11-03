import AgregarProducto from "./componentes_ej1_react/AgregarProducto"
import ListaProductos from "./componentes_ej1_react/ListaProductos"
import Header from "./componentes_ej1_react/Header"
import Footer from "./componentes_ej1_react/Footer"
import Carrito from "./componentes_ej1_react/Carrito"

function App_ejercicio1_React_Productos() {  

    return (
    <>
    <Header titulo="Inventario de Productos" />
    <AgregarProducto />
    <ListaProductos />
    <Carrito />
    <Footer />
    </>
  )
}

export default App_ejercicio1_React_Productos