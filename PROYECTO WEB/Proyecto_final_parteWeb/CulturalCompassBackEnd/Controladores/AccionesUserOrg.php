<?php
session_start();
switch ($_POST['action']) {
    case 'create':
        $username = !empty($_POST['username']) ? $_POST['username'] : '';
        $password = !empty($_POST['password']) ? $_POST['password'] : '';
        $role_id = !empty($_POST['role_id']) ? $_POST['role_id'] : '';
        $is_active = !empty($_POST['is_active']) ? $_POST['is_active'] : '';
        $created_at = !empty($_POST['created_at']) ? $_POST['created_at'] : '';
        $email = !empty($_POST['email']) ? $_POST['email'] : '';

        if (
            empty($username) || empty($password) || empty($role_id)
            || empty($is_active) || empty($created_at) || empty($email)
        ) {
            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }

        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('INSERT INTO user (username, password, role_id, is_active, created_at, email) 
            VALUES (:username, :password, :role_id, :is_active, :created_at, :email)');
            $stmt->execute(array(
                ':username' => $username,
                ':password' => $password,
                ':role_id' => $role_id,
                ':is_active' => $is_active,
                ':created_at' => $created_at,
                ':email' => $email,
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario creado correctamente'));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al insertar el usuario: ' . $e->getMessage()));
        }
        break;
    case 'edit_user':
        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';

        if (empty($user_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de usuario para la edición'));
            return;
        }
        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';
        $username = !empty($_POST['username']) ? $_POST['username'] : '';

        $password = !empty($_POST['password']) ? $_POST['password'] : '';

        $role_id = !empty($_POST['role_id']) ? $_POST['role_id'] : '';

        $is_active = !empty($_POST['is_active']) ? $_POST['is_active'] : '';

        $created_at = !empty($_POST['created_at']) ? $_POST['created_at'] : '';

        $email = !empty($_POST['email']) ? $_POST['email'] : '';

        if (
            empty($username) || empty($password) || empty($role_id)
            || empty($is_active) || empty($created_at) || empty($email)
        ) {

            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');

            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('UPDATE user SET username = :username, password = :password, role_id = :role_id, is_active = :is_active, created_at = :created_at, email = :email WHERE id = :user_id');
            $stmt->execute(array(
                ':username' => $username,
                ':password' => $password,
                ':role_id' => $role_id,
                ':is_active' => $is_active,
                ':created_at' => $created_at,
                ':email' => $email,
                ':user_id' => $user_id
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario editado correctamente'));

            header('location: ../../CulturalCompassFrontEnd/html/AdminUsers.php');
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al editar el usuario: ' . $e->getMessage()));
        }
        break;


    case 'preferencias':
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            // Primero, obtén las categorías seleccionadas del formulario
            $selected_categories = isset($_POST['categories']) ? $_POST['categories'] : array();

            // Luego, obtén las preferencias actuales del usuario
            $query_preferences = "SELECT * FROM pref_users WHERE id_usuario = :id_usuario";
            $statement_preferences = $pdo->prepare($query_preferences);
            $statement_preferences->bindParam(':id_usuario', $_POST['id']);
            $statement_preferences->execute();
            $user_preferences = $statement_preferences->fetchAll(PDO::FETCH_ASSOC);

            // Ahora, determina las categorías que se deben eliminar: las no seleccionadas
            $categories_to_delete = array();
            foreach ($user_preferences as $preference) {
                if (!in_array($preference['id_categoria'], $selected_categories)) {
                    $categories_to_delete[] = $preference['id_categoria'];
                }
            }

            // Realiza las operaciones de inserción y eliminación en la base de datos
            $pdo->beginTransaction();

            // Elimina las categorías no seleccionadas
            foreach ($categories_to_delete as $category_id) {
                $query_delete = "DELETE FROM pref_users WHERE id_usuario = :id_usuario AND id_categoria = :id_categoria";
                $statement_delete = $pdo->prepare($query_delete);
                $statement_delete->bindParam(':id_usuario', $_POST['id']);
                $statement_delete->bindParam(':id_categoria', $category_id);
                $statement_delete->execute();
            }

            // Inserta las nuevas categorías seleccionadas
            foreach ($selected_categories as $category_id) {
                $query_insert = "INSERT INTO pref_users (id_usuario, id_categoria) VALUES (:id_usuario, :id_categoria)";
                $statement_insert = $pdo->prepare($query_insert);
                $statement_insert->bindParam(':id_usuario', $_POST['id']);
                $statement_insert->bindParam(':id_categoria', $category_id);
                $statement_insert->execute();
            }

            $pdo->commit();

            // Si todo ha ido bien, redirecciona o devuelve alguna respuesta apropiada
            header("Location: ../../CulturalCompassFrontEnd/html/PerfilPersonal.php");
            // exit();
        } catch (PDOException $e) {
            // Si hay algún error, realiza un rollback y maneja la excepción
            $pdo->rollback();
            echo "Error: " . $e->getMessage();
        }
        break;



    case 'delete_user':

        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';
        if (empty($user_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de usuario para la eliminación'));
            return;
        }
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            //antes de elimnar el usuario, elimino tambien su info en event_users
            $stmt = $pdo->prepare('DELETE FROM event_users WHERE id_user = :id');

            $stmt->execute(array(
                ':id' => $user_id
            ));

            //elimino info de pref_users
            $stmt = $pdo->prepare('DELETE FROM pref_users WHERE id_usuario = :id');
            $stmt->execute(array(
                ':id' => $user_id
            ));

            $stmt = $pdo->prepare('DELETE FROM user WHERE id = :id');
            $stmt->execute(array(
                ':id' => $user_id
            ));
            echo json_encode(array('status' => 'success', 'message' => 'Usuario eliminado correctamente'));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar el usuario: ' . $e->getMessage()));
        }
        break;
    case 'edit_profile':

        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';

        $username = !empty($_POST['username']) ? $_POST['username'] : '';

        $password = !empty($_POST['password']) ? $_POST['password'] : '';

        $role_id = !empty($_POST['role_id']) ? $_POST['role_id'] : '';

        $is_active = !empty($_POST['is_active']) ? $_POST['is_active'] : '';

        $created_at = !empty($_POST['created_at']) ? $_POST['created_at'] : '';

        $email = !empty($_POST['email']) ? $_POST['email'] : '';

        if (
            empty($username) || empty($password) || empty($role_id)
            || empty($is_active) || empty($created_at) || empty($email)
        ) {

            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');

            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('UPDATE user SET username = :username, password = :password, role_id = :role_id, is_active = :is_active, created_at = :created_at, email = :email WHERE id = :user_id');
            $stmt->execute(array(
                ':username' => $username,
                ':password' => $password,
                ':role_id' => $role_id,
                ':is_active' => $is_active,
                ':created_at' => $created_at,
                ':email' => $email,
                ':user_id' => $user_id
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario editado correctamente'));
            unset($_SESSION['username']);
            $_SESSION['username'] = $username;
            header('location: ../../CulturalCompassFrontEnd/html/PerfilPersonal.php');
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al editar el usuario: ' . $e->getMessage()));
        }
        break;
    case 'delete_user_org':
        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';
        if (empty($user_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de usuario para la eliminación'));
            return;
        }
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->query('SET foreign_key_checks = 0');

            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            //recibo el id de usuario pero tengo que obtener el id de organizador
            $stmt = $pdo->prepare('SELECT * FROM organizer WHERE id_user = :id');
            $stmt->execute(array(
                ':id' => $user_id
            ));
            $id_organizador = $stmt->fetch(PDO::FETCH_ASSOC);
            $id_organizador = $id_organizador['id'];

            if (empty($id_organizador)) {
                echo json_encode(array('status' => 'error', 'message' => 'No se ha encontrado el organizador'));
                return;
            }

            //comprobar si hay un evento creado por ese organizador
            $stmt = $pdo->prepare('SELECT * FROM evento WHERE organizer_id = :id');
            $stmt->execute(array(
                ':id' => $id_organizador
            ));

            $eventos = $stmt->fetchAll(PDO::FETCH_ASSOC);


            if (!empty($eventos)) {
                foreach ($eventos as $evento) {
                    $id_evento = $evento['id'];


                    //elimino de la tabla event_users
                    $stmt = $pdo->prepare('DELETE FROM event_users WHERE id_event = :id');
                    $stmt->execute(array(
                        ':id' => $id_evento
                    ));

                    //elimino el evento
                    $stmt = $pdo->prepare('DELETE FROM evento WHERE id = :id');
                    $stmt->execute(array(
                        ':id' => $id_evento
                    ));
                }
            }

            //elimino el organizer
            $stmt = $pdo->prepare('DELETE FROM organizer WHERE id_user = :id');
            $stmt->execute(array(
                ':id' => $id_organizador
            ));
            
            //antes de eliminar el usuario, elimino tambien su info en event_users
            $stmt = $pdo->prepare('DELETE FROM event_users WHERE id_user = :id');

            $stmt->execute(array(
                ':id' => $user_id
            ));

            //elimino info de pref_users
            $stmt = $pdo->prepare('DELETE FROM pref_users WHERE id_usuario = :id');
            $stmt->execute(array(
                ':id' => $user_id
            ));

            //elimino el user
            $stmt = $pdo->prepare('DELETE FROM user WHERE id = :id');
            $stmt->execute(array(
                ':id' => $user_id
            ));

            $pdo->query('SET foreign_key_checks = 1');




            echo json_encode(array('status' => 'success', 'message' => 'Usuario eliminado correctamente'));
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar el usuario: ' . $e->getMessage()));
        }
        break;

    case 'edit_organizer':
        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';

        if (empty($user_id)) {
            echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de usuario para la edición'));
            return;
        }
        $user_id = !empty($_POST['id']) ? $_POST['id'] : '';

        $username = !empty($_POST['username']) ? $_POST['username'] : '';

        $password = !empty($_POST['password']) ? $_POST['password'] : '';

        $role_id = !empty($_POST['role_id']) ? $_POST['role_id'] : '';

        $is_active = !empty($_POST['is_active']) ? $_POST['is_active'] : '';

        $created_at = !empty($_POST['created_at']) ? $_POST['created_at'] : '';

        $email = !empty($_POST['email']) ? $_POST['email'] : '';

        if (
            empty($username) || empty($password) || empty($role_id)
            || empty($is_active) || empty($created_at) || empty($email)
        ) {

            echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
            return;
        }
        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');

            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('UPDATE user SET username = :username, password = :password, role_id = :role_id, is_active = :is_active, created_at = :created_at, email = :email WHERE id = :user_id');
            $stmt->execute(array(
                ':username' => $username,
                ':password' => $password,
                ':role_id' => $role_id,
                ':is_active' => $is_active,
                ':created_at' => $created_at,
                ':email' => $email,
                ':user_id' => $user_id
            ));

            echo json_encode(array('status' => 'success', 'message' => 'Usuario editado correctamente'));

            header('location: ../../CulturalCompassFrontEnd/html/AdminOrg.php');
        } catch (PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al editar el usuario: ' . $e->getMessage()));
        }



        break;
    default:
        echo json_encode(array('status' => 'error', 'message' => 'Acción no válida'));
        break;
}
