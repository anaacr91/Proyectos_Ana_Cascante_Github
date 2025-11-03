import { useState } from 'react'

function NameChanger({ onNameChange, currentName }) {// Recibimos la función y el nombre actual como props
  const [localName, setLocalName] = useState(currentName || "John Doe")// Estado local para el nombre
  
  const handleNameChange = (e) => {// Manejador del cambio de nombre
    const newName = e.target.value// Nuevo nombre desde el input
    setLocalName(newName)// Actualizamos el estado local
    
    if (onNameChange) {
      onNameChange(newName)
    }// Si se pasa una función onNameChange, la ejecutamos para comunicar el cambio al padre
  }
  
  return (
    <div>
      <h2>Cambiar nombre</h2>
      <input 
        type="text" 
        value={localName} 
        onChange={handleNameChange}
        placeholder="Escribe un nombre"
      />
      <p>Nombre actual: {localName}</p>
    </div>
  )
}

export default NameChanger