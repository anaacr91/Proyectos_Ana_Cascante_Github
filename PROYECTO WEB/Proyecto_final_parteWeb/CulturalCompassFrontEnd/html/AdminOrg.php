<?php session_start();

if (isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id'] == '3' && $_SESSION['is_active'] == '1') {
  include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();


?>

  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css
    " integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

    <title>Administrar Organizadores</title>

  </head>

  <body>

    <?php include_once 'NavAdmin.php'; ?>

    <div class="container">
      <div class="row">
        <div class="col-sm-6">
          <br><br><br>
          <h1>Administrar Organizadores</h1>
        </div>
        <div class="col-sm-6">
          <i class="fa-solid fa-star"></i>
          <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

        </div>
      </div>
      <br><br><br><br>
      <div class="row">
        <div class="col-sm-6">
          <h2>Listado Organizadores</h2>
          <?php
          try {

            $pdo = $db->connect();

            $query = "SELECT * FROM user WHERE role_id = 2";

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
                          <button type="button" id="eliminarOrg" onclick="eliminarOrg(<?= $usuario['id'] ?>)" class="btn btn-primary">Eliminar Organizador</button>
                        </td>
                        <td>
                         <!-- <button type="button" id="desactivarOrg" onclick="desactivarOrg(<?= $usuario['id'] ?>,<?= $usuario['is_active'] ?>)" class="btn btn-primary" class="btn btn-primary">Banear Org</button>-->
                        </td>
                        <td><a class="btn btn-primary" href="AdminOrg.php?user_id=<?= $usuario['id'] ?>">
                            Seleccionar</a></td>
                      </tr>
                    <?php
                    }
                    ?>
                  </tbody>
                </table>
              </div>
          <?php
            } else {
              echo "No se encontraron organizadores";
            }
          } catch (PDOException $e) {
            echo "Error: " . $e->getMessage();
          }
          ?>
        </div>
        <div class="col-sm-6">
          <h2>Datos Organizador seleccionado</h2>

          <?php if (empty($_GET['user_id'])) {
          } else { ?>
            <?php try {
              $pdo = $db->connect();

              $query_2 = "SELECT * FROM user WHERE role_id = 2 and id = " . $_GET['user_id'];

              $statement_2 = $pdo->query($query_2);

              $usuarios_2 = $statement_2->fetchAll(PDO::FETCH_ASSOC);
              foreach ($usuarios_2 as $usuario_2) { ?>
                <form action="../../CulturalCompassBackEnd/Controladores/AccionesUserOrg.php" method="POST">
                  <input type="hidden" name="id" value="<?= $usuario_2['id'] ?>">
                  <input type="hidden" name="action" value="edit_organizer">
                  <label for="username">Username:</label>
                  <input class="form-control" type="text" id="username" name="username" value="<?php echo $usuario_2['username'] ?>">
                  <label for="password">Password:</label>
                  <input class="form-control" type="password" id="password" name="password" value="<?php echo $usuario_2['password'] ?>">
                  <label for="role_id">Role ID:</label>
                  <select class="form-control" id="role_id" name="role_id">
                    <option value="3" <?php if ($usuario_2['role_id'] == '3') {
                                        echo "selected";
                                      } ?>>Administrador
                    </option>
                    <option value="2" <?php if ($usuario_2['role_id'] == '2') {
                                        echo "selected";
                                      } ?>>Organizador
                    </option>
                    <option value="1" <?php if ($usuario_2['role_id'] == '1') {
                                        echo "selected";
                                      } ?>>User</option>
                  </select>
                  <label for="is_active">Is Active:</label>
                  <select class="form-control" id="is_active" name="is_active">
                    <option value="1" <?php if ($usuario_2['is_active'] == '1') {
                                        echo "selected";
                                      } ?>>Activo</option>
                    <option value="0" <?php if ($usuario_2['is_active'] == '0') {
                                        echo "selected";
                                      } ?>>Inactivo
                    </option>
                  </select>
                  <label for="created_at">Created At:</label>
                  <input class="form-control" type="text" id="created_at" name="created_at" value="<?php echo $usuario_2['created_at'] ?>">
                  <label for="email">Email:</label>
                  <input class="form-control" type="email" id="email" name="email" value="<?php echo $usuario_2['email'] ?>">
                  <button type="submit" class="btn btn-primary">Guardar Cambios Organizador</button>
                </form>
          <?php }
            } catch (PDOException $e) {
              echo "Error: " . $e->getMessage();
            }
          } ?>
        </div>
        <br><br><br><br>
        <div class="row">
          <div class="col-sm-6">
            <h2>Historial de compras de tickets</h2>
            
            <br>
            <?php if (!empty($_GET['user_id'])) { ?>
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
              FROM orden_articulos, evento, orden, clientes, user 
              WHERE orden_articulos.product_id=evento.id 
              AND orden_articulos.order_id=orden.id 
              AND clientes.orden=orden.id 
              AND user.id = clientes.id_user
              AND user.id = '". $_GET['user_id']."'"); 

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
            <?php }?>
          </div>
          <div class="col-sm-6">
            <h2>Historial de creacion eventos</h2>
            <!--crear eventos bbdd por users test y volcarlos en query-->
            <?php if (!empty($_GET['user_id'])) { ?>
              <?php
              $queryEventos = "SELECT * FROM evento e  INNER JOIN organizer o ON e.organizer_id = o.id INNER JOIN user u ON o.id_user = u.id
          WHERE
              u.id = " . $_GET['user_id'];

              $statement_3 = $pdo->query($queryEventos);

              $events = $statement_3->fetchAll(PDO::FETCH_ASSOC);
              ?>
              <?php if (count($events) > 0) { ?>
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th>Evento</th>
                        <th>Fecha comienzo</th>
                        <th>Fecha fin</th>
                      </tr>
                    </thead>
                    <tbody>
                      <?php
                      foreach ($events as $event) {
                      ?>
                        <tr>
                          <td><?php echo $event['name_event']; ?></td>
                          <td><?php echo $event['start_at']; ?></td>
                          <td><?php echo $event['end_at']; ?></td>
                        </tr>
                      <?php } ?>
                    </tbody>
                  </table>

                <?php } ?>
                </div>
              <?php } else { ?>
                <p>Sin información</p>
              <?php } ?>
          </div>
        </div>
      </div>

    </div>
    <script src="./../js/AdminOrg.js" defer></script>
  </body>

  </html>

<?php

} else {

  header('location: Login.php');
}

?>