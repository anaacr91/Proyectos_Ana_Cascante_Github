<?php
session_start();
if (!empty($_POST)) {
    if ($_POST['evento']['action'] == 'create') {

        $name_event = !empty($_POST['evento']['name_event']) ? $_POST['evento']['name_event'] : '';
        $start_at = !empty($_POST['evento']['start_at']) ? $_POST['evento']['start_at'] : '';
        $end_at = !empty($_POST['evento']['end_at']) ? $_POST['evento']['end_at'] : '';
        $description = !empty($_POST['evento']['description']) ? $_POST['evento']['description'] : '';
        $status = !empty($_POST['evento']['status']) ? $_POST['evento']['status'] : '';
        $url = !empty($_POST['evento']['url']) ? $_POST['evento']['url'] : '';
        $category_id = !empty($_POST['evento']['category_id']) ? $_POST['evento']['category_id'] : '';
        $location_id = !empty($_POST['evento']['location_id']) ? $_POST['evento']['location_id'] : '';
        $organizer_id = !empty($_POST['evento']['organizer_id']) ? $_POST['evento']['organizer_id'] : '';
        $precio_evento= !empty($_POST['evento']['precio_evento']) ? $_POST['evento']['precio_evento'] : '';
        
        if (isset($_FILES['evento']['name']['imagen']) && $_FILES['evento']['error']['imagen'] == UPLOAD_ERR_OK) {
            $imagen = $_FILES['evento']['name']['imagen'];
            $target_dir = "../Files/Img_event/";
            $target_file = $target_dir . basename($imagen);
    
            if (!move_uploaded_file($_FILES['evento']['tmp_name']['imagen'], $target_file)) {
                echo json_encode(['status' => 'error', 'message' => 'Error al mover la imagen']);
                exit;
            }
        } else {
            $errorCode = isset($_FILES['evento']['error']['imagen']) ? $_FILES['evento']['error']['imagen'] : 'imagen no subida';
            echo json_encode(['status' => 'error', 'message' => 'Upload error code: ' . $errorCode]);
            exit;
        }
    
        if (
            empty($name_event) || empty($start_at) || empty($end_at) || empty($description)
            || empty($status) || empty($url) || empty($imagen) || empty($category_id)
            || empty($location_id) || empty($organizer_id) || empty($precio_evento)
        ) {
            echo json_encode(['status' => 'error', 'message' => 'Tienes que rellenar todos los campos']);
            
        }
        
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('INSERT INTO evento (name_event, start_at, end_at, description, status, url, imagen, category_id, location_id, organizer_id, precio_evento) 
            VALUES (:name_event, :start_at, :end_at, :description, :status, :url, :imagen, :category_id, :location_id, :organizer_id, :precio_evento)');
            $stmt->execute(array(
                ':name_event' => $name_event,
                ':start_at' => $start_at,
                ':end_at' => $end_at,
                ':description' => $description,
                ':status' => $status,
                ':url' => $url,
                ':imagen' => $imagen,
                ':category_id' => $category_id,
                ':location_id' => $location_id,
                ':organizer_id' => $organizer_id,
                ':precio_evento' => $precio_evento,
            ));

            echo json_encode(array('status' => 'error', 'message' => 'Evento creado correctamente'));

        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al insertar el evento: ')) . $e->getMessage();
        }

    } elseif ($_POST['evento']['action'] == 'edit') {

        $evento_id = !empty($_POST['evento']['id']) ? $_POST['evento']['id'] : '';

        if (empty($evento_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de evento para la edición'));
            return;
        }

        $name_event = !empty($_POST['evento']['name_event']) ? $_POST['evento']['name_event'] : '';
        $start_at = !empty($_POST['evento']['start_at']) ? $_POST['evento']['start_at'] : '';
        $end_at = !empty($_POST['evento']['end_at']) ? $_POST['evento']['end_at'] : '';
        $description = !empty($_POST['evento']['description']) ? $_POST['evento']['description'] : '';
        $status = !empty($_POST['evento']['status']) ? $_POST['evento']['status'] : '';
        $url = !empty($_POST['evento']['url']) ? $_POST['evento']['url'] : '';
        $imagen= !empty($_FILES['evento']['name']['imagen']) ? $_FILES['evento']['name']['imagen'] : '';
        $category_id = !empty($_POST['evento']['category_id']) ? $_POST['evento']['category_id'] : '';
        $location_id = !empty($_POST['evento']['location_id']) ? $_POST['evento']['location_id'] : '';
        $organizer_id = !empty($_POST['evento']['organizer_id']) ? $_POST['evento']['organizer_id'] : '';
        $precio_evento= !empty($_POST['evento']['precio_evento']) ? $_POST['evento']['precio_evento'] : '';
        
        if (
            empty($name_event) || empty($start_at) || empty($end_at) || empty($description) || empty($status) ||
            empty($url) || empty($imagen) || empty($category_id) || empty($location_id) || empty($organizer_id) || empty($precio_evento)
        ) {
            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }
        if (isset($_FILES['evento']['name']['imagen']) && $_FILES['evento']['error']['imagen'] == UPLOAD_ERR_OK) {
            $imagen = $_FILES['evento']['name']['imagen'];
            $target_dir = "../Files/Img_event/";
            $target_file = $target_dir . basename($imagen);
    
            if (!move_uploaded_file($_FILES['evento']['tmp_name']['imagen'], $target_file)) {
                echo json_encode(['status' => 'error', 'message' => 'Error al mover la imagen']);
                exit;
            }
        } else {
            $errorCode = isset($_FILES['evento']['error']['imagen']) ? $_FILES['evento']['error']['imagen'] : 'imagen no subida';
            echo json_encode(['status' => 'error', 'message' => 'Upload error code: ' . $errorCode]);
            exit;
        }


        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('UPDATE evento SET name_event = :name_event, start_at = :start_at, end_at = :end_at, description = :description, status = :status, url = :url, imagen = :imagen, category_id = :category_id, location_id = :location_id, organizer_id = :organizer_id, precio_evento = :precio_evento
                WHERE id = :evento_id');
            $stmt->execute(array(
                ':name_event' => $name_event,
                ':start_at' => $start_at,
                ':end_at' => $end_at,
                ':description' => $description,
                ':status' => $status,
                ':url' => $url,
                ':imagen' => $imagen,
                ':category_id' => $category_id,
                ':location_id' => $location_id,
                ':organizer_id' => $organizer_id,
                ':precio_evento' => $precio_evento,
                ':evento_id' => $evento_id
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Evento actualizado correctamente'));
            return;
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al actualizar el evento: ' . $e->getMessage()));
            return;
        }
    } elseif ($_POST['evento']['action'] == 'add_user_event') {
        $id_user = !empty($_POST['evento']['idUsuario']) ? $_POST['evento']['idUsuario'] : null;
        $id_evento = !empty($_POST['evento']['id_evento']) ? $_POST['evento']['id_evento'] : null;

        if (empty($id_user) || empty($id_evento)) {
            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }

        // comprobaria que ese usuario no esté ya en ese evento
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('SELECT * FROM event_users WHERE id_event = :id_evento AND id_user = :id_user');
            $stmt->execute(array(
                ':id_evento' => $id_evento,
                ':id_user' => $id_user,

            ));

            if ($stmt->rowCount() > 0) {
                echo json_encode(array('status' => 'error', 'message' => 'El usuario ya está asociado a este evento'));
                return;
            }

            $stmt = $pdo->prepare('INSERT INTO event_users (id_event, id_user,date) VALUES (:id_evento, :id_user, :date_value)');
            $stmt->execute(array(
                ':id_evento' => $id_evento,
                ':id_user' => $id_user,
                ':date_value' => date('Y-m-d H:i:s')
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario apuntado correctamente'));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al agregar el usuario: ' . $e->getMessage()));
        }
    } elseif ($_POST['evento']['action'] == 'remove_user_event') {

        $id_user = !empty($_POST['evento']['idUsuario']) ? $_POST['evento']['idUsuario'] : null;
        $id_evento = !empty($_POST['evento']['id_evento']) ? $_POST['evento']['id_evento'] : null;

        if (empty($id_user) || empty($id_evento)) {
            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }

        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('DELETE FROM event_users WHERE id_event = :id_evento AND id_user = :id_user');
            $stmt->execute(array(
                ':id_evento' => $id_evento,
                ':id_user' => $id_user,

            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario eliminado correctamente'));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar el usuario: ' . $e->getMessage()));
        }
    } elseif ($_POST['evento']['action'] == 'delete') {


        $evento_id = !empty($_POST['evento']['id']) ? $_POST['evento']['id'] : '';

        if (empty($evento_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de evento para la eliminación'));
            return;
        }

        // Eliminar de la base de datos
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            //elimino la relacion de usuarios en el evento
            $stmt = $pdo->prepare('DELETE FROM event_users WHERE id_event = :evento_id');
            $stmt->execute(array(
                ':evento_id' => $evento_id
            ));

            //elimino el evento de la tabla eventos
            $stmt = $pdo->prepare('DELETE FROM evento WHERE id = :evento_id');
            $stmt->execute(array(
                ':evento_id' => $evento_id
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Evento eliminado correctamente', "roleId" => $_SESSION['role_id']));
        } catch (PDOException $e) {
           
           echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar el evento: ' . $e->getMessage()));
        }
    } elseif ($_POST['evento']['action'] == 'list_user_event') {
        $id_user = !empty($_POST['evento']['idUsuario']) ? $_POST['evento']['idUsuario'] : '';
        $eventos = [];

        if (empty($id_user)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de usuario para listar los eventos del usuario'));
            return;
        }

        // Listar eventos del usuario
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('SELECT * FROM evento e INNER JOIN event_users eu ON e.id = eu.id_event WHERE eu.id_user = :id_user');
            $stmt->execute(array(
                ':id_user' => $id_user,

            ));
            $results = $stmt->fetchAll(PDO::FETCH_ASSOC);

            if (!empty($results)) {
                foreach ($results as $row) {
                    $eventos[] = [
                        "eventid" => $row['id'],
                        "title" => $row['name_event'],
                        "description" => $row['description'],
                        "start" => $row['start_at'],
                        "end" => $row['end_at'],
                    ];
                }
            }

            echo json_encode(array('status' => 'success', 'data' => $eventos));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al listar los eventos del usuario: ' . $e->getMessage()));
        }
    }
}

if (!empty($_GET)) {
    if ($_GET['evento']['action'] == 'list_user_event') {
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('SELECT * FROM evento');
            $stmt->execute();
            $eventos = $stmt->fetchAll(PDO::FETCH_ASSOC);

            echo json_encode($eventos, true);
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al obtener los eventos: ' . $e->getMessage()));
        }
    }
}
