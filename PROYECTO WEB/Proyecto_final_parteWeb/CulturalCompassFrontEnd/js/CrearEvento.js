 let esEditar = false;

    const apiUrl = '/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Controladores/AccionesEvento.php';


function guardarModificarInfoEvento(){
    let formData = new FormData(); 
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
    formData.append('evento[action]', 'create');

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