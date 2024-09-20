<?php session_start();

if(isset($_SESSION['role_id']) && isset($_SESSION['is_active'])){
 include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();
  ?>
  <!DOCTYPE html>
  <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <link
        rel="stylesheet"
        media="screen"
        href="./../css/bootstrap.min.css"
        type="text/css"
      />
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
        integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
        crossorigin="anonymous"
        referrerpolicy="no-referrer"
      />
      <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
      <title>Gestionar Perfil</title>
      <script src="./../js/bootstrap.bundle.min.js"></script>
      

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
             <h1>Gestion de Perfil</h1>
          </div>
          <div class="col-sm-6">
            <i class="fa-solid fa-star"></i>

            <?php
if(isset($_SESSION['role_id']) && $_SESSION['role_id']=='3'){ ?>
      <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

    <?php }elseif(isset($_SESSION['role_id']) && $_SESSION['role_id']=='2'){ ?>
      <a href="PanelOrg.php" class="btn btn-primary">Ir Atrás</a>

      <?php }elseif(isset($_SESSION['role_id']) && $_SESSION['role_id']=='1'){ ?>
      <a href="PanelUsuario.php" class="btn btn-primary">Ir Atrás</a>


    <?php } ?>
            
          </div>
        <br><br><br><br><br>
        <div class="row">
          <div class="col-sm-6">
          <?php if (empty($_SESSION['id'])){}else{?>

  <?php try {
    $pdo =$db->connect();
    
    $query_2 = "SELECT * FROM user WHERE id = ".$_SESSION['id'];
    
    $statement_2 = $pdo->query($query_2);
    
    $usuarios_2 = $statement_2->fetchAll(PDO::FETCH_ASSOC);
    foreach ($usuarios_2 as $usuario_2){?>

        <h2>Información Personal de: <?=$usuario_2['username']?></h2>
<form method="POST" action="../../CulturalCompassBackEnd/Controladores/AccionesUserOrg.php">
  <input type="hidden" name="id" value="<?=$usuario_2['id']?>">
  <input type="hidden" name="action" value="edit_profile">
                
  <label for="username">Username:</label>
    <input class="form-control" type="text" id="username" name="username" value="<?php echo $usuario_2['username'] ?>">
    <label for="password">Password:</label>
    <input class="form-control" type="password" id="password" name="password" value="<?php echo $usuario_2['password'] ?>">
    <label for="role_id">Role ID: Solicita un cambio de Rol en Contacto</label>
    <?php if (isset($_SESSION) && $_SESSION['role_id'] == '3'): ?>
    <select class="form-control" id="role_id" name="role_id">
      <!--solo el admin puede tocar esta parte-->
        <option value="3"<?php if ($usuario_2['role_id']== '3') { echo "selected";}?>>Administrador</option>
        <option value="2"<?php if ($usuario_2['role_id']== '2') { echo "selected";}?>>Organizador</option>
        <option value="1"<?php if ($usuario_2['role_id']== '1') {echo "selected";}?>>User</option>
    </select>
    <?php  else : ?>

<input type='text' class='form-control' id='user_placeholder' name='user_placeholder' value="<?php echo $_SESSION['role_id']?>" readonly>
<input type='hidden' class='form-control' id='user_id' name='user_id' value="<?php echo $_SESSION['role_id']?>">
    <?php endif; ?> 

    <label for="is_active">Is Active: (aquí puedes desactivar tu cuenta)</label>
    <select class="form-control" id="is_active" name="is_active">
      <option value="1"<?php if ($usuario_2['is_active']== '1') { echo "selected";}?>>Activo</option>
      <option value="0"<?php if ($usuario_2['is_active']== '0') { echo "selected";}?>>Inactivo</option>
    </select>
    <label for="created_at">Created At:</label>
    <input class="form-control" type="text" id="created_at" name="created_at" value="<?php echo $usuario_2['created_at'] ?>">
    <label for="email">Email:</label>
    <input class="form-control" type="email" id="email" name="email" value="<?php echo $usuario_2['email'] ?>">
    <button type="submit" class="btn btn-primary">Guardar Cambios </button> 
  </form>
  <?php }
     } catch (PDOException $e) {echo "Error: " . $e->getMessage();} } ?>
          </div>

          <div class="col-sm-6">
              <h2>Preferencias e Intereses</h2>
<?php if (empty($_SESSION['id'])){}else{?>
  <form method="POST" action="../../CulturalCompassBackEnd/Controladores/AccionesUserOrg.php">
    <input type="hidden" name="action" value="preferencias">
    <input type="hidden" name="id" value="<?= $usuario_2['id'] ?>">

    <?php
    try {
        $pdo = $db->connect();

        $query_categories = "SELECT * FROM category";
        $statement_categories = $pdo->query($query_categories);
        $categories = $statement_categories->fetchAll(PDO::FETCH_ASSOC);

        $query_preferences = "SELECT * FROM pref_users WHERE id_usuario = :id_usuario";
        $statement_preferences = $pdo->prepare($query_preferences);
        $statement_preferences->bindParam(':id_usuario', $_SESSION['id']);
        $statement_preferences->execute();
        $user_preferences = $statement_preferences->fetchAll(PDO::FETCH_ASSOC);

        $selected_categories = array();
        foreach ($user_preferences as $preference) {
            $selected_categories[] = $preference['id_categoria'];
        }

        foreach ($categories as $category) {
            ?>
            <label for="category_<?php echo $category['id']; ?>">
                <input type="checkbox" id="category_<?php echo $category['id']; ?>" name="categories[]" value="<?php echo $category['id']; ?>" <?php if (in_array($category['id'], $selected_categories)) echo 'checked'; ?>>
                <?php echo $category['name_category']; ?>
            </label><br>
            <?php
        }
        ?>
        <button type="submit" id="botonGuardar" class="btn btn-primary">Guardar Preferencias</button>
        <?php
    } catch (PDOException $e) {
        echo "Error: " . $e->getMessage();
    } }
    ?>
</form>

          </div>
          
          <div class="col-12">
            <br><br>
            <center><h4>Historial de Compras</h4></center>
            <br>
          <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                	 <th><center>N# orden</center></th>
					         <th><center>Evento</center></th>
                   <th><center>Total</center></th>
					         <th><center>Metodo de pago</center></th>
                   <th><center>Fecha compra</center></th>
					         
					
                </tr>
                </thead>
                <tbody>
              <?php 
              $mas = $base_de_datos->query("SELECT orden.id as orden, evento.name_event as name_event, orden.total_price as total_price, clientes.pago as metodo, orden.created as fecha 
              FROM orden_articulos, evento, orden, clientes 
              WHERE orden_articulos.product_id=evento.id 
              AND orden_articulos.order_id=orden.id 
              AND clientes.orden=orden.id 
              and clientes.id_user=".$_SESSION['id'].""); 
              $vendidos = $mas->fetchAll(PDO::FETCH_OBJ); 
              foreach($vendidos as $vendido){ ?>
              <tr>
					
					<td><center><?php echo $vendido->orden?></center></td>
					<td><center><?php echo $vendido->name_event ?></center></td>
          <td><center><?php echo $vendido->total_price ?></center></td>
					<td><center><?php echo $vendido->metodo ?></center></td>
          <td><center><?php echo date('d-m-Y',strtotime($vendido->fecha)) ?></center></td>
					
					
				</tr>
				<?php } ?>
                </tbody> 
            </table>
        
        </div>
        </div>
            
          </div>
      </div>

    </body>
  </html>

  <?php

}else{

header('location: Login.php');

}

?>