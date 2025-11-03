import { useState } from 'react'

function TechList() {
  const [items, setItems] = useState(["React", "Vite", "JavaScript"])
  
  const addItem = () => {
    const newItem = `Item ${items.length + 1}`
    setItems([...items, newItem])
  }
  
  const removeLastItem = () => {
    setItems(items.filter((_, i) => i !== items.length - 1))
    //filtramos los items y eliminamos el último
  }
  
  return (
    <div>
      <h2>Lista de tecnologías</h2>
      <ul>
        {items.map((item, index) => (// Usar índice como key es aceptable aquí ya que la lista es estática y no se reordena
        //mapeamos los items y los mostramos en una lista
          <li key={index}>{item}</li>
        ))}
      </ul>
      <button onClick={addItem}>Añadir elemento</button>
      <button onClick={removeLastItem}>
        Eliminar último
      </button>
    </div>
  )
}

export default TechList