
<?php session_start();

if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='3' && $_SESSION['is_active']=='1'||isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='2' && $_SESSION['is_active']=='1'){
 include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();
  if(isset($_GET['id_category'])) {
  $id_category = $_GET['id_category'];
  try {
    $pdo = $db->connect();
      $query ="SELECT * FROM category WHERE id = :id_category";
      $statement= $pdo -> prepare($query);
      $statement->bindParam(':id_category', $id_category, PDO::PARAM_INT);
      $statement->execute();
      $category=$statement->fetch(PDO::FETCH_ASSOC);
      $categorydata= array(
        'id'=> $category['id'],
        'name_category'=>$category['name_category'],

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
    <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
    <title>Gestionar Categorias</title>
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
           <h1>Administrar Categoria
            <br><br><?php echo $categorydata['name_category'] ?></h1>
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

        <div class="col-sm-6">
        <br><br>
            <h2>Datos Categoria</h2>

            <form >
            <label for="nombre_categoria"> Nombre Categoria: </label>
            <input class="form-control" type="text" id="nombre_categoria" name="nombre_categoria" value="<?php echo $categorydata['name_category'] ?>"> 
     
    </form>
    <button type="button" id="modificarcategoria" onclick="modificarcat(<?=$categorydata['id']?>)" class="btn btn-primary">Guardar cambios Categoria</button>
        <button type="button" onclick="borrarCat(<?=$categorydata['id']?>)" class="btn btn-primary">Borrar Categoria</button>
           
</div>

<script src="./../js/bootstrap.bundle.min.js"></script>
    <script src="./../js/GestionCategorias.js" defer></script>
  </body>
</html>

<?php
 } catch (PDOException $e) {
  echo "error:" . $e->getMessage();
}
}
}else{
header('location: Login.php');
}
?>