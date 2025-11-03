
import { useState } from 'react'
import './css/App1.css'
import Hello from '../components/hello.jsx'
import Counter from '../components/Counter.jsx'
import UserInfo from '../components/UserInfo.jsx'
import TechList from '../components/TechList.jsx'
import NameChanger from '../components/NameChanger.jsx'

function App2() {
  // Variables normales (se reinician en cada renderizado)
  const appTitle = "Mi Aplicación React"
  const currentYear = new Date().getFullYear()
  
  // Estado para el nombre (compartido entre Hello y NameChanger)
  const [name, setName] = useState("John Doe")
  const age = 30
  const email = "john@example.com"
  
  // Función para manejar el cambio de nombre desde el componente hijo
  const handleNameChange = (newName) => {
    setName(newName)
  }
  
  return (
    <>
      <div>
        <h1>{appTitle}</h1>
        <p>Año actual: {currentYear}</p>
      </div>

      <Hello name={name} age={age} email={email} />

      <Counter />
      
      <UserInfo />
      
      <TechList />

      <NameChanger onNameChange={handleNameChange} currentName={name} />
    </>
  )
}

export default App2
