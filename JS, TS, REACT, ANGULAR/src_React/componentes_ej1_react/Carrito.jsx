import { useState } from 'react';

function Carrito({
  productosCarrito
}) {
  const [CarritoOpen, setCarritoOpen] = useState(false);


  const cambiarCarrito = () => {
    setCarritoOpen(!CarritoOpen);
  };


  const cerrarCarrito = () => {
    setCarritoOpen(false);
  };


  const totalPrice = productosCarrito.reduce(
    (total, product) => total + product.price,
    0
  );


  const totalItems = productosCarrito.length;

  return (
    <>

      <button 
        onClick={cambiarCarrito}
        aria-label="Abrir carrito de compras"
      >
        🛒
        {totalItems > 0 && (
          <span >{totalItems}</span>
        )}
      </button>


      {CarritoOpen && (
        <div  onClick={cerrarCarrito}>
          <div onClick={(e) => e.stopPropagation()}
          >
              <button
                onClick={cerrarCarrito}
                aria-label="Cerrar carrito"
              >
                ✕
              </button>
            </div>

            <div >
              {totalItems === 0 ? (
                <div >
                  <p>Tu carrito está vacío</p>
                  <span>🛍️</span>
                </div>
              ) : (
                <>
                  
                  <div >
                    <div >
                      <strong>Total: ${totalPrice.toFixed(2)}</strong>
                    </div>
                    <div >
                      <button >
                        Proceder al Pago
                      </button>
                      
                    </div>
                  </div>
                </>
              )}
            </div>
          </div>
   
      )}

      
    </>
  );
}

export default Carrito;