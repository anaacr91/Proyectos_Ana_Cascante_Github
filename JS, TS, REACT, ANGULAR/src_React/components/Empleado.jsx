function Empleado({ name, role, id }) {
  const handleClick = (id) => {
    alert(`Button clicked! (${id})`)
  }

  return (
    <div className="empleado">
      Nombre: {name}
      Rol: {role}
      Contacto: 
      <button onClick={() => handleClick(id)}>{id}</button>
    </div>
  )
}
export default Empleado