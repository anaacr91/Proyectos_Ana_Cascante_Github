import { useState } from 'react'

function UserInfo() {
  const [isVisible, setIsVisible] = useState(true)
  const [user, setUser] = useState({
    name: "Ana", 
    age: 25,
    email: "ana@example.com"
  })
  
  const toggleVisibility = () => setIsVisible(!isVisible)// Alterna la visibilidad de la información del usuario
  // si esta visible, mostrar 'Ocultar', si no, 'Mostrar'
  const celebrateBirthday = () => setUser({...user, age: user.age + 1})
  
  return (
    <div>
      <h2>Información del Usuario</h2>
      {isVisible && (
        <div>
          <p>Nombre: {user.name}</p>
          <p>Edad: {user.age}</p>
          <p>Email: {user.email}</p>
          <button onClick={celebrateBirthday}>
            Cumplir años
          </button>
        </div>
      )}
      <button onClick={toggleVisibility}>
        {isVisible ? 'Ocultar' : 'Mostrar'} información
      </button>
    </div>
  )
}

export default UserInfo