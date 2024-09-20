<?php 
session_start();
   if(!empty($_POST)){
    if($_POST['category']['action'] == 'create'){

        $name_category = !empty($_POST['category']['name_category']) ? $_POST['category']['name_category'] : '';

        if(empty($name_category)){
            echo json_encode(array('status' => 'error','message' => 'Tienes que rellenar todos los campos'));
            return;
        }

        try {
            $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            $stmt = $pdo->prepare('INSERT INTO category (name_category) VALUES (:name_category)');
            $stmt->execute(array(
                ':name_category' => $name_category,
            ));

            echo json_encode(array('status' => 'success', 'message' => ' Categoria creada correctamente'));
        } catch(PDOException $e) {
            echo json_encode(array('status' => 'error', 'message' => 'Error al insertar la categoria: ' . $e->getMessage()));
        }
    }

    elseif($_POST['category']['action'] == 'edit'){
        
        $category_id = !empty($_POST['category']['id']) ? $_POST['category']['id'] : '';

            if(empty($category_id)) {
                echo json_encode(array('status' => 'error', 'message' => 'Se requiere un ID de category para la edición'));
                return;
            }
            
            $name_category = !empty($_POST['category']['name_category']) ? $_POST['category']['name_category'] : '';
            

             if(empty($name_category)){
                echo json_encode(array('status' => 'error','message' => 'Tienes que rellenar todos los campos'));
                return;
            }
            try {
                $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                $stmt = $pdo->prepare('UPDATE category SET name_category = :name_category 
                WHERE id = :category_id');
                $stmt->execute(array(
                    ':name_category' => $name_category,
                    ':category_id' => $category_id,
                ));
                echo json_encode(array('status' => 'success', 'message' => 'Categoria actualizado correctamente'));
            } catch(PDOException $e) {
                echo json_encode(array('status' => 'error', 'message' => 'Error al actualizar la categoria: ' . $e->getMessage()));
            }
        }
        elseif ($_POST['category']['action'] == 'delete') {
            $category_id = !empty($_POST['category']['id']) ? $_POST['category']['id'] : '';
    
            if(empty($category_id)) {
                echo json_encode(array('status' => 'error', 'message' => 'Se requiere una ID de categoria para la eliminación'));
                return;
            }
    
            // Eliminar de la base de datos
            try {
                $pdo = new PDO('mysql:host=localhost;dbname=cultural_compass_data', 'root', '');
                $pdo->query('SET foreign_key_checks = 0');
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
                //borrar eventos de esa categoria
                $stmt= $pdo->prepare('DELETE FROM evento WHERE category_id = :category_id');
                $stmt->execute(array(':category_id' => $category_id));

                //borrar preferencias de usuario de esa categoria
                $stmt = $pdo->prepare('DELETE FROM pref_users WHERE id_categoria = :category_id');
                $stmt->execute(array(':category_id' => $category_id));
                
                //borrar categoria tabla categoria
                $stmt = $pdo->prepare('DELETE FROM category WHERE id = :category_id');
                $stmt->execute(array(
                    ':category_id' => $category_id
                ));

                $pdo->query('SET foreign_key_checks = 1');
    
                echo json_encode(array('status' => 'success', 'message' => 'categoria eliminada correctamente'));
            } catch(PDOException $e) {
                echo json_encode(array('status' => 'error', 'message' => 'Error al eliminar la categoria: ' . $e->getMessage()));
            }
        }
         }
       
    ?>
