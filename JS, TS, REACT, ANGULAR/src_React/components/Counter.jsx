import { useState } from 'react'

function Counter() {
  const [count, setCount] = useState(0)
  
  const increment = () => setCount(count + 1)
  const decrement = () => setCount(count - 1)
  const reset = () => setCount(0)
  
  return (
    <div>
      <h2>Contador</h2>
      <p>Valor actual: {count}</p>
      <button onClick={increment}>Incrementar</button>
      <button onClick={decrement}>Decrementar</button>
      <button onClick={reset}>Resetear</button>
    </div>
  )
}

export default Counter