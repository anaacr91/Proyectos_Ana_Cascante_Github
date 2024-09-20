document.getElementById('save').addEventListener('click', () =>{
    const email = document.getElementById('email').value.trim();
    const errorMessage = document.getElementById('error');
    const result = document.getElementById('result');

    if (email !== ''){ 
        
        errorMessage.textContent = "";  
        
        guardarCookie ('email',email,2);  

    

       result.textContent = "Tu email es: " + leerCookie('email');

      
      

    } else {
  
        errorMessage.textContent = "Por favor ingresa un email correcto";
    }


});

function guardarCookie ( nombre, valor, expiracion){
  
  const fecha = new Date(); 
     
  fecha.setDate(fecha.getDate()+ expiracion);

  document.cookie = `${nombre}=${valor}; expires=${fecha}`; 
}


function leerCookie (nombreCookie){

  const cookies = document.cookie.split(';'); 

  for (const cookie of cookies) { 
    const [name, value] = cookie.split('=');  
    if (name.trim() === nombreCookie ){
     
      return value; 
    }    
  }
  return 'La cookie no existe'; 
}