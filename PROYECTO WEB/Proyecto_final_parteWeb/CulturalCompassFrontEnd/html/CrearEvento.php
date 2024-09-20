
<?php session_start(); 
if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='3' && $_SESSION['is_active']=='1'||isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='2' && $_SESSION['is_active']=='1'){
  include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
   $db = new DB();

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_FILES['imagen']) && isset($_POST['id'])) {
    $nombre_img = $_FILES['imagen']['name'];
    $tmp_name = $_FILES['imagen']['tmp_name'];
    $destino = $_SERVER['DOCUMENT_ROOT'] . '/CulturalCompassBackEnd/Files/Img_event/';

    if (move_uploaded_file($tmp_name, $destino . $nombre_img)) {
        try {
            $pdo = $db->connect(); 
            $stmt = $pdo->prepare("UPDATE foto_categoria SET foto = :nombre_img WHERE id = :id");
            $stmt->bindParam(":nombre_img", $nombre_img);
            $stmt->bindParam(":id", $_POST['id']);
            $stmt->execute();
    
            if ($stmt->rowCount() > 0) {
                header("Location: CrearEvento.php");
                exit;
            } else {
                echo "No se actualizó ningún registro, verifica el ID.";
            }
        } catch (PDOException $e) {
            echo "Error en la base de datos: " . $e->getMessage();
        }
    } else {
        echo "Error al subir el archivo.";
    }
} else {
    echo "";
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />
    <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
    integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
    crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>

    <title>Crear Nuevo Evento</title>


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
          <br><br><br><br><br>
    <h1>Crear nuevo Evento </h1>
    <br>
<form id="formularioEvento" method="post" action="GestionEventos.php" enctype="multipart/form-data" >

    <!--<input type="hidden" id="id" value="0"> -->

    <label for="name_event">Nombre evento:</label>
    <input type="text" id="name_event" name="name_event"><br><br>

    <label for="start_at">Inicio:</label>
    <input type="datetime-local" id="start_at" name="start_at"><br><br>

    <label for="end_at">Fin:</label>
    <input type="datetime-local" id="end_at" name="end_at"><br><br>

    <label for="description">Descripción:</label>
    <textarea id="description" name="description"></textarea><br><br>

    <label for="status">Status:</label>
    <select class="form-control" id="status" name="status">
    <option value=1 >Activo</option>
    <option value=0 >Inactivo</option>
    <option value=2 >Pasado</option>
    <option value=3 >Cancelado</option>
    </select>
    
    <label for="url">Url:</label>
    <textarea id="url" name="url"></textarea><br><br>

    <label for="imagen">Imagen:</label>
  <input type="file" accept=".jpg,.jpeg,.png" id="imagen" name="id">

    <label for="category_id">Category:</label>
    <select class='form-control' id='category_id' name='category_id'>
                 <?php  try{
            $pdo =$db->connect();
            $query_2="SELECT * FROM category";
            $statement_2 = $pdo->query($query_2);
            $categorys = $statement_2->fetchAll(PDO::FETCH_ASSOC);

            foreach($categorys as $category){ ?>
                   <option value="<?php echo $category['id']?>"><?php echo $category['name_category']?></option>
            <?php }?> 
            </select> 
            <?php }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?>
            
    
    <label for="location_id">Location:</label>
    <select class='form-control' id='location_id' name='location_id'>
                 <?php  try{
            $pdo =$db->connect();
            $query_3="SELECT * FROM location";
            $statement_3 = $pdo->query($query_3);
            $locations = $statement_3->fetchAll(PDO::FETCH_ASSOC);

            foreach($locations as $location){ ?>
                   <option value="<?php echo $location['id']?>"><?php echo $location['name_location']?></option>
            <?php }?> 
            </select> 
            <?php }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?>

            <label for="organizer_id">Organizer:</label>
<?php  
try {
    $pdo = $db->connect();
    $query_4 = "SELECT * FROM organizer";
    $statement_4 = $pdo->query($query_4);
    $organizers = $statement_4->fetchAll(PDO::FETCH_ASSOC);
    ?>
    <?php if (isset($_SESSION) && $_SESSION['role_id'] == '3'): ?>
        <select class='form-control' id='organizer_id' name='organizer_id'> 
        <?php foreach($organizers as $organizer): ?>
            <option value="<?php echo $organizer['id']?>"><?php echo $organizer['name_org']?></option>
        <?php endforeach; ?>
        </select> 
    <?php elseif (isset($_SESSION) && $_SESSION['role_id'] == '2'): ?>

            <input type='text' class='form-control' id='organizer_placeholder' name='organizer_placeholder' value="<?php echo $_SESSION['username']?>" readonly>
            <input type='hidden' class='form-control' id='organizer_id' name='organizer_id' value="<?php echo $_SESSION['organizer_id']?>">

    <?php endif; ?> 
<?php 
} catch(PDOException $e) {
    echo "error:" . $e->getMessage();
}
?>
    <label for="precio_evento">precio evento:</label>
    <input type="text" id="precio_evento" name="precio_evento"><br><br>

    <br><br>
    
    <button type="button" id="botonGuardarModificar" onclick="guardarModificarInfoEvento()" class="btn btn-primary" >Guardar Evento</button>
</form>
  </div>
<div class="col-sm-6">
    <i class="fa-solid fa-star"></i>

    <?php 
    if(isset($_SESSION['role_id']) && $_SESSION['role_id']=='3'){ ?>
      <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

    <?php }elseif(isset($_SESSION['role_id']) && $_SESSION['role_id']=='2'){ ?>
      <a href="PanelOrg.php" class="btn btn-primary">Ir Atrás</a>
    <?php }  ?>
    
  </div>
  </div>
  </div>

  <script src="../js/CrearEvento.js" defer></script>
  <script src="./../js/bootstrap.bundle.min.js"></script>
        
</body>
</html>

<?php
}else{
header('location: Login.php');
}
?>