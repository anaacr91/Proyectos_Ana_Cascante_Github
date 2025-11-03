import Table_Empleados from '../components/Table_Empleados.jsx'
import Navegacion from '../components/Navegacion.jsx'
import Header from '../components/Header.jsx'

import Empleado from '../components/Empleado.jsx'
import Servicio from '../components/Servicio.jsx'



function App() {  

    return (
    <>
    <Header />
    <Navegacion />
    <h1>Equipo</h1>
    <Table_Empleados />
    <Servicio />

    </>
  )
}

export default App