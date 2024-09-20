document.querySelector('.dropdown-menu').addEventListener('click', function (event) {
    event.preventDefault();
    window.location.href = event.target.href;

});

let org = [];
const apiUrl = '../../CulturalCompassBackEnd/Controladores/AccionesUserOrg.php';


function eliminarOrg(id_user) {


    $.post(`${apiUrl}`, { action: 'delete_user_org', id: id_user }).done(function (response) {

        let object = JSON.parse(response);

        if (object.status == 'success') {
            $.notify(object.message, "success");
            window.location.reload();

        } else {
            $.notify(object.message, "error");

        }
    });


}

function desactivarOrg(id_user, is_active) {
    const user = {
        id_user: id_user,
        is_active: is_active,
        action: 'edit'
    }
    fetch(`${apiUrl}/${user}`, {
        method: 'PUT',

        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user),
    })
        .then(response => {
            if (response.ok) {
                console.log("Status del organizador cambiado exitosamente.");
                return response.json();
            } else {
                throw new Error('Error al cambiar el estatus del organizador.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function guardarModificarOrg(id) {
    const organizer = {
        id: id,
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        role_id: document.getElementById("role_id").value,
        is_active: document.getElementById("is_active").value,
        created_at: document.getElementById("created_at").value,
        email: document.getElementById("email").value,
        action: 'edit_organizer'
    };

    $.post(`${apiUrl}`, { organizer }).done(function (response) {

        let object = JSON.parse(response);

        if (object.status == 'success') {
            $.notify(object.message, "success");

        } else {
            $.notify(object.message, "error");

        }
    });


}