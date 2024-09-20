const apiUrl = '../../CulturalCompassBackEnd/Controladores/AccionesEvento.php';

function borrarEvento(id_evento) {
    console.log("eliminando");
    let formData = new FormData();
    formData.append('evento[id]', id_evento);
    formData.append('evento[action]', 'delete');

    fetch(apiUrl, {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.error(data);
        if (data.status == 'success') {
            console.error(data.roleId);
            alert(data.message);
            if(data.roleId == 3){
                window.location.href = '../../CulturalCompassFrontEnd/html/PanelAdmin.php';
            }else if (data.roleId == 2){
                console.error(data.roleId);

                window.location.href = '../../CulturalCompassFrontEnd/html/PanelOrg.php';
            }
        } else {
            alert(data.message);
        }
    })
    .catch(error => console.error('Error:', error));
}

function guardarModificarInfoEvento(id) {

    let formData = new FormData(); 
    formData.append('evento[id]', id);
    formData.append('evento[name_event]', document.getElementById("name_event").value);
    formData.append('evento[start_at]', document.getElementById("start_at").value);
    formData.append('evento[end_at]', document.getElementById("end_at").value);
    formData.append('evento[description]', document.getElementById("description").value);
    formData.append('evento[status]', document.getElementById("status").value);
    formData.append('evento[url]', document.getElementById("url").value);
    formData.append('evento[imagen]', document.getElementById("imagen").files[0]);
    formData.append('evento[category_id]', document.getElementById("category_id").value);
    formData.append('evento[location_id]', document.getElementById("location_id").value);
    formData.append('evento[organizer_id]', document.getElementById("organizer_id").value);
    formData.append('evento[precio_evento]', document.getElementById("precio_evento").value);
    formData.append('evento[action]', 'edit');

    fetch('../../CulturalCompassBackEnd/Controladores/AccionesEvento.php', {
        method: 'POST',
        body: formData
    }).then(response => response.json())
      .then(data => {
        if (data.status === "error") {
            alert(data.message);
        } else {
            alert("Evento creado con éxito!");

        }
    }).catch(error => {
        console.error('Error:', error);
    });



}

function eliminarUserEvent(id_user, id_evento) {
    const evento = {
        id_evento: id_evento,
        idUsuario: id_user,
        action: 'remove_user_event'
    };

    $.post(`${apiUrl}`, { evento }).done(function (response) {

        let object = JSON.parse(response);

        if (object.status == 'success') {

            $.notify(object.message, "success");
            window.location.reload();
        } else {
            $.notify(object.message, "error");

        }


    });

}

