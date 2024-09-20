const apiUrl = '/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Controladores/AccionesUbicacion.php';

/*function borrarloc(id) {

    const location = {
        id: id,
        action:'delete'
    };

    $.post(`${apiUrl}`, {location}).done(function(response){

        let object = JSON.parse(response);
    
        if(object.status == 'success'){
            $.notify(object.message, "success");
           
        }else{
            $.notify(object.message, "error");
    
        }
    });
    }*/
    function borrarloc(id) {
        const data = { location: { id: id, action: 'delete' } };
        fetch(`{apiUrl}?id=${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success =='success') {
                $.notify(object.message, "success");
            } else {
                $.notify(object.message, "error");
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    }

function modificarloc(id) { 
            
            const location = {
            id: id,
            name_location: document.getElementById('nombre_ubicacion').value,
            additional_info: document.getElementById('direccion').value,
            latitud: document.getElementById('latitud').value,
            longitud: document.getElementById('longitud').value,
            ubicationlink: document.getElementById('ubicationlink').value,
            redirectionlink: document.getElementById('redirectionlink').value,
            action:'edit'
};

$.post(`${apiUrl}`, {location}).done(function(response){
console.log(response);
    let object = JSON.parse(response);

    if(object.status == 'success'){
        $.notify(object.message, "success");
       
    }else{
        $.notify(object.message, "error");

    }
});
}
