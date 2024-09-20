<?php 
session_start();
   if(!empty($_POST)){
    if($_POST['location']['action'] == 'create'){

        $name_location = !empty($_POST['location']['name_location']) ? $_POST['location']['name_location'] : '';
        $additional_info = !empty($_POST['location']['additional_info']) ? $_POST['location']['additional_info'] : '';
        $latitud = !empty($_POST['location']['latitud']) ? $_POST['location']['latitud'] : '';
        $longitud = !empty($_POST['location']['longitud']) ? $_POST['location']['longitud'] : '';
        $ubicationlink = !empty($_POST['location']['ubicationlink']) ? $_POST['location']['ubicationlink'] : '';
        $redirectionlink = !empty($_POST['location']['redirectionlink']) ? $_POST['location']['redirectionlink'] : '';

        if(empty($name_location) || empty($additional_info) || empty($latitud) || empty($longitud) || empty($ubicationlink) || empty($redirectionlink)){
            echo json_encode(array('status' => 'error','message' => 'Tienes que rellenar todos los campos'));
            return;
        }

        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('INSERT INTO location (name_location, additional_info, latitud, longitud, ubicationlink, redirectionlink) 
            VALUES (:name_location, :additional_info, :latitud, :longitud, :ubicationlink, :redirectionlink)');
            $stmt->execute(array(
                ':name_location' => $name_location,
                ':additional_info' => $additional_info,
                ':latitud' => $latitud,
                ':longitud' => $longitud,
                ':ubicationlink' => $ubicationlink,
                ':redirectionlink' => $redirectionlink,
            ));

            echo json_encode(array('status' => 'success', 'message' => ' Ubicación creada correctamente'));
        } catch(PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al insertar la Ubicación: ' . $e->getMessage()));
        }
    }

    elseif($_POST['location']['action'] == 'edit'){
        
        $location_id = !empty($_POST['location']['id']) ? $_POST['location']['id'] : '';

            if(empty($location_id)) {
                echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de location para la edición'));
                return;
            }
            
            $name_location = !empty($_POST['location']['name_location']) ? $_POST['location']['name_location'] : '';
            $additional_info = !empty($_POST['location']['additional_info']) ? $_POST['location']['additional_info'] : '';
            $latitud = !empty($_POST['location']['latitud']) ? $_POST['location']['latitud'] : '';
            $longitud = !empty($_POST['location']['longitud']) ? $_POST['location']['longitud'] : '';
            $ubicationlink = !empty($_POST['location']['ubicationlink']) ? $_POST['location']['ubicationlink'] : '';
            $redirectionlink = !empty($_POST['location']['redirectionlink']) ? $_POST['location']['redirectionlink'] : '';
            
            if(empty($name_location) || empty($additional_info) || empty($latitud) || empty($longitud) || empty($ubicationlink) || empty($redirectionlink)){
                echo json_encode(array('status' => 'error', 'message' => 'Tienes que rellenar todos los campos'));
                return;
            }

            try {
                $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                $stmt = $pdo->prepare('UPDATE location SET name_location = :name_location, additional_info = :additional_info, latitud = :latitud, longitud = :longitud, ubicationlink = :ubicationlink, redirectionlink = :redirectionlink 
                WHERE id = :location_id');
                $stmt->execute(array(
                    ':name_location' => $name_location,
                    ':additional_info' => $additional_info,
                    ':latitud' => $latitud,
                    ':longitud' => $longitud,
                    ':ubicationlink' => $ubicationlink,
                    ':redirectionlink' => $redirectionlink,
                    ':location_id' => $location_id,
                ));

                echo json_encode(array('status' => 'success', 'message' => 'Ubicación actualizado correctamente'));
            } catch(PDOException $e) {
                echo json_encode(array('status' => 'error', 'message' => 'Ubicación al actualizar el evento: ' . $e->getMessage()));
            }
        }
  
        if ($_POST['location']['action'] == 'delete') {
            $location_id = !empty($_POST['location']['id']) ? $_POST['location']['id'] : '';
 
    
            if(empty($location_id)) {
                echo json_encode(array('status' => 'error', 'message' => 'Se requiere una ID de Ubicación para la eliminación'));
                return;
            }
    
            // Eliminar de la base de datos
            try {
                $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
                $pdo->query('SET foreign_key_checks = 0');
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                
                //borrar eventos de esa ubicacion
                $stmt= $pdo->prepare('DELETE FROM evento WHERE location_id = :location_id');
                $stmt->bindParam(':location_id', $location_id, PDO::PARAM_INT);
                $stmt->execute(array(':location_id' => $location_id));

                //borrar ubicacion tabla location
                $stmt = $pdo->prepare('DELETE FROM location WHERE id = :location_id');
                $stmt->bindParam(':location_id', $location_id, PDO::PARAM_INT);
                $stmt->execute(array(
                    ':location_id' => $location_id
                ));

                $pdo->query('SET foreign_key_checks = 1');
    
                echo json_encode(array('status' => 'success', 'message' => 'Ubicación eliminada correctamente'));
            } catch(PDOException $e) {
                echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar la Ubicación: ' . $e->getMessage()));
            }
        }
         }
       
    ?>
