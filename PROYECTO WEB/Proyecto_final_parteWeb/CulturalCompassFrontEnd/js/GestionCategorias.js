const apiUrl = '/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Controladores/AccionesCategoria.php';

function borrarCat(id) {

    const category = {
        id: id,
        action:'delete'
    };

    $.post(`${apiUrl}`, {category}).done(function(response){
        let object = JSON.parse(response);

        if(object.status == 'success'){
            $.notify(object.message, "success");
        }else{
            $.notify(object.message, "error");
        }
    });
}

function modificarcat(id) { 
    const category = {
        id: id,
        name_category: document.getElementById('nombre_categoria').value,
        action:'edit'
    }

    $.post(`${apiUrl}`, {category}).done(function(response){
        let object = JSON.parse(response);

        if(object.status == 'success'){
            $.notify(object.message, "success");
        }else{
            $.notify(object.message, "error");
        }
    });
}