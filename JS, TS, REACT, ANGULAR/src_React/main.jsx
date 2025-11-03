import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './css/index.css'
import App from './App_inventario.jsx'
import App_Inventario from './App_inventario.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App_Inventario />
  </StrictMode>,
)
