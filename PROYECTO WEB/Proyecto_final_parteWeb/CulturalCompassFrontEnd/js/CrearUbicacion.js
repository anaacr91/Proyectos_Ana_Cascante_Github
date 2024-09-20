let esEditar = false;

const apiUrl = '/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Controladores/AccionesUbicacion.php';

function guardarModificarLoc() {
    const location = {
    name_location : document.getElementById("name_location").value,
    additional_info : document.getElementById("additional_info").value,
    latitud : document.getElementById("latitud").value,
    longitud : document.getElementById("longitud").value,
    ubicationlink: document.getElementById("ubicationlink").value,
    redirectionlink: document.getElementById("redirectionlink").value,
    action:'create'
    };


        $.post(`${apiUrl}`, {location}).done(function(response){
            console.log(response)

            let object = JSON.parse(response);

            if(object.status == 'success'){
                $.notify(object.message, "success");
               
            }else{
                $.notify(object.message, "error");
    
            }
        });
            
    }

    

