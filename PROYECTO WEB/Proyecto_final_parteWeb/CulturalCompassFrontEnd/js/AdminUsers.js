/*document.querySelector('.dropdown-menu').addEventListener('click', function(event) {
    event.preventDefault();
    window.location.href = event.target.href;
   
  });*/
let users = [];
const apiUrl = '../../CulturalCompassBackEnd/Controladores/AccionesUserOrg.php';


function eliminarUser(id_user) {

    $.post(`${apiUrl}`, { action: 'delete_user', id: id_user }).done(function (response) {

        let object = JSON.parse(response);

        if (object.status == 'success') {
            $.notify(object.message, "success");
            window.location.reload();

        } else {
            $.notify(object.message, "error");

        }
    });


}



function guardarModificarUser(id) {

    const user = {
        id: id,
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        role_id: document.getElementById("role_id").value,
        is_active: document.getElementById("created_at").value,
        created_at: created_at,
        email: document.getElementById("email").value,
        action: 'edit_user'
    };
    console.log(user);
    console.log(apiUrl);



    $.post(`${apiUrl}`, { user }).done(function (response) {

        let object = JSON.parse(response);

        if (object.status == 'success') {
            $.notify(object.message, "success");

        } else {
            $.notify(object.message, "error");

        }
    });


}

