<?php session_start();

if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='3' && $_SESSION['is_active']=='1'||isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='2' && $_SESSION['is_active']=='1'){
 include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();
  $id_evento = $_GET['id_evento'];
  try {
      $pdo = $db->connect();
      $query = "SELECT location.longitud as longitud, location.latitud as latitud, location.ubicationlink as ubicationlink, location.redirectionlink as redirectionlink,
      location.name_location as name_location, location.additional_info as additional_info, category.name_category as name_category,
   organizer.name_org as name_org, organizer.email as email, organizer.company as company, organizer.phone as phone,  evento.url as url,
    evento.imagen as imagen, evento.category_id as category_id, evento.location_id as location_id, evento.organizer_id as organizer_id,
     evento.id as id, evento.name_event as name_event, evento.start_at as start_at, evento.end_at as end_at, evento.status as status,
      evento.description as description, evento.precio_evento as precio  
      FROM evento 
      INNER JOIN organizer on organizer.id=evento.organizer_id 
      INNER JOIN location on location.id=evento.location_id  
      INNER JOIN category on category.id=evento.category_id  
      WHERE evento.id= :id_evento" ;
      $statement = $pdo->prepare($query);
      $statement->bindParam(':id_evento', $id_evento, PDO::PARAM_INT);
      $statement->execute();
      $evento = $statement->fetch(PDO::FETCH_ASSOC);
      $itemData = array(
          'id' => $evento['id'],
          'name_event' => $evento['name_event'],
          'start_at' => $evento['start_at'],
          'end_at' => $evento['end_at'],
          'description' => $evento['description'],
          'status' => $evento['status'],
          'url' => $evento['url'],
          'imagen' => $evento['imagen'],
          'category_id' => $evento['category_id'],
          'name_category' => $evento['name_category'],
          'location_id' => $evento['location_id'],
          'name_location' => $evento['name_location'],
          'addicional_info' => $evento['additional_info'],
          'organizer_id' => $evento['organizer_id'],
          'name_org' => $evento['name_org'],
          'email' => $evento['email'],
          'company' => $evento['company'],
          'phone' => $evento['phone'],
          'longitud' => $evento['longitud'],
          'latitud' => $evento['latitud'],
          'ubicationlink' => $evento['ubicationlink'],
          'redirectionlink' => $evento['redirectionlink'],
          'precio' => $evento['precio'],
      

      );?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
        integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <title>Gestionar Eventos</title>

</head>

<body>
    <?php switch($_SESSION['role_id']){
                case '1':
                  include_once 'NavUser.php';
                    break;

                case '2':
                  include_once 'NavOrg.php';
                    break;

                case '3':
                  include_once 'NavAdmin.php';
                    break;
                default:
                   header ('location:../../CulturalCompassFrontEnd/html/login.php');
                    break;
            } ?>

    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <h1>Administrar Evento<?php echo $itemData['name_event'] ?></h1>
            </div>
            <div class="col-sm-6">
                <i class="fa-solid fa-star"></i>

                <?php 
        if(isset($_SESSION['role_id']) && $_SESSION['role_id']=='3'){ ?>
                <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

                <?php }elseif(isset($_SESSION['role_id']) && $_SESSION['role_id']=='2'){ ?>
                <a href="PanelOrg.php" class="btn btn-primary">Ir Atrás</a>
                <?php } ?>

            </div>
            <br><br><br><br><br>
            <div class="col-sm-6">
                <h2>GuestList Asistentes del Evento</h2>
                <?php
try {
    $pdo =$db->connect();
    
    $query = "SELECT * FROM event_users e INNER JOIN user u ON u.id = e.id_user WHERE e.id_event = $id_evento";
    
    $statement = $pdo->query($query);
    
    $usuarios = $statement->fetchAll(PDO::FETCH_ASSOC);
   

    if (count($usuarios) > 0) {
        ?>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Role ID</th>
                                <th>Is Active</th>
                                <th>Created At</th>
                                <th>Email</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                    foreach ($usuarios as $usuario) {
                        ?>
                            <tr>
                                <td><?php echo $usuario['id']; ?></td>
                                <td><?php echo $usuario['username']; ?></td>
                                <td><?php echo $usuario['password']; ?></td>
                                <td><?php echo $usuario['role_id']; ?></td>
                                <td><?php echo $usuario['is_active']; ?></td>
                                <td><?php echo date('d-m-Y', strtotime($usuario['created_at'])); ?></td>
                                <td><?php echo $usuario['email']; ?></td>
                                <td>
                                    <button type="button" id="eliminarUser"
                                        onclick="eliminarUserEvent(<?=$usuario['id']?>,<?=$id_evento?>)"
                                        class="btn btn-primary">Eliminar User</button>
                                </td>

                            </tr>
                            <?php
                    }
                    ?>
                        </tbody>
                    </table>
                </div>
                <?php
    } else {
        echo "No se encontraron usuarios.";
    }
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}

?>
                <form method="POST" enctype="multipart/form-data" >
                    <div class="col-12 col">

                    </div>
                </form>
            </div>

            <div class="col-sm-6">
                <h2>Datos Evento</h2>
                <form >
                    <div class="col-12 col-sm-10">
                        <label for="name_event"> Nombre evento: </label>
                        <input class="form-control" type="text" id="name_event" name="name_event"
                            value="<?php echo $itemData['name_event'] ?>">
                    </div>
                    <div class="col-12 col-sm-10">
                        <label for="start_at"> fecha comienzo: </label>
                        <input class="form-control" type="datetime-local" id="start_at" name="start_at"
                            value="<?php echo $itemData['start_at'] ? htmlspecialchars(date('Y-m-d h:s',strtotime($itemData['start_at']))) : '' ?>">

                    </div>
                    <div class="col-12 col-sm-10">
                        <label for="end_at"> fecha fin: </label>
                        <input class="form-control" type="datetime-local" id="end_at" name="end_at"
                            value="<?php echo $itemData['end_at'] ? htmlspecialchars(date('Y-m-d h:s',strtotime($itemData['end_at']))) : '' ?>">
                    </div>
                    <div class="col-12 col-sm-10">
                        <label for="description">Descripción</label>
                        <textarea class="form-control" id="description"
                            rows="3"><?php echo $itemData['description'] ?></textarea>
                    </div>
                    <div class="col-12 col-sm-10">
                        <label for="status">Status</label>
                        <select class="form-control" id="status" name="status">

                            <option value=1 <?php if ($itemData['status'] == '1'){echo "selected";}?>>Activo</option>
                            <option value=0 <?php if ($itemData['status'] == '0'){echo "selected";}?>>Inactivo</option>
                            <option value=2 <?php if ($itemData['status'] == '2'){echo "selected";}?>>Pasado</option>
                            <option value=3 <?php if ($itemData['status'] == '3'){echo "selected";}?>>Cancelado</option>
                        </select>
                    </div>
                    <div class='col-12 col-sm-10'>
                        <label for="url">URL</label>
                        <input class="form-control" type="text" id="url" name="url"
                            value="<?php echo  $itemData["url"]?>" />
                    </div>
                    <div class='col-12 col-sm-10'>
                        <label for="imagen">Imagen</label>
                        <?php try{
                            $pdo = $db->connect();
                            $query_01 = "SELECT imagen FROM evento WHERE id = $id_evento";
                            $statement_01 = $pdo->prepare($query_01);
                            $statement_01->execute();
                            $imagenes = $statement_01->fetchAll(PDO::FETCH_ASSOC);
                            if (count($imagenes) > 0) {
                                foreach ($imagenes as $imagen) {
                                   ?>
                                    <img src="../../CulturalCompassBackEnd/Files/Img_event/<?php echo $itemData['imagen']?>" style="max-width: 550px; max-height: 550px">
                                    <?php
                                }
                            } else {
                                echo "No se encontraron imagenes.";
                            }
                        } catch (PDOException $e) {
                            echo "Error: " . $e->getMessage();
                        } ?>
                        <input type='file' accept=".jpg,.jpeg,.png" id="imagen" name="imagen" >
                    </div>

                    <div class='col-12 col-sm-10'>
                        <label for="category_id">Categoria</label>
                        <select class='form-control' id='category_id' name='category_id'>
                            <?php  try{
            $pdo =$db->connect();
            $query_2="SELECT * FROM category";
            $statement_2 = $pdo->query($query_2);
            $categorys = $statement_2->fetchAll(PDO::FETCH_ASSOC);

            foreach($categorys as $category){ ?>
                            <option value="<?php echo $category['id']?>" <?php if ($itemData['category_id'] == $category ['id']) {echo "selected";} ?>> <?php echo $category['name_category']?>
                            </option>
                            <?php }?>
                        </select>
                        <?php }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?>
                    </div>

                    <div class='col-12 col-sm-10'>
                        <label for="location_id">localización</label>
                        <select class='form-control' id='location_id' name='location_id'>
                            <?php  try{
            $pdo =$db->connect();
            $query_3="SELECT * FROM location";
            $statement_3 = $pdo->query($query_3);
            $locations = $statement_3->fetchAll(PDO::FETCH_ASSOC);

            foreach($locations as $location){ ?>
                            <option value="<?php echo $location['id']?>" <?php if ($itemData['location_id'] == $location['id']){echo "selected";}?>><?php echo $location['name_location']?>
                            </option>
                            <?php }?>
                        </select>
                        <?php }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?>
                    </div>

                    <div class='col-12 col-sm-10'>
                        <label for="organizer_id">Organizador</label>
                        <select class='form-control' id='organizer_id' name='organizer_id'>
                            <?php  try{
            $pdo =$db->connect();
            $query_4="SELECT * FROM organizer";
            $statement_4= $pdo->query($query_4);
            $organizers = $statement_4->fetchAll(PDO::FETCH_ASSOC);

            foreach($organizers as $organizer){ ?>
                            <option value="<?php echo $organizer['id']?>"<?php if ($itemData['organizer_id'] == $organizer['id']){echo "selected";}?>><?php echo $organizer['name_org']?></option>
                            <?php }?>
                        </select>
                        <?php }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?>
                    </div>
                    <div class="col-12 col-sm-10">
                        <label for="precio_evento"> Precio evento: </label>
                        <input class="form-control" type="text" id="precio_evento" name="precio_evento"
                            value="<?php echo $itemData['precio'] ?>">
                    </div>
                </form>

                <button type="button" id="botonGuardarModificar"
                    onclick="guardarModificarInfoEvento(<?=$evento['id']?>)" class="btn btn-primary">Guardar Cambios
                    Evento</button>
                <button type="button" onclick="borrarEvento(<?=$evento['id']?>)" class="btn btn-danger">Borrar
                    Evento</button>

            </div>
        </div>

    </div>
    <script src="./../js/bootstrap.bundle.min.js"></script>
    <script src="./../js/GestionEventos.js" defer></script>
</body>

</html>

<?php


} catch (PDOException $e) {
  echo "error:" . $e->getMessage();
}

}else{
header('location: Login.php');
}
?>