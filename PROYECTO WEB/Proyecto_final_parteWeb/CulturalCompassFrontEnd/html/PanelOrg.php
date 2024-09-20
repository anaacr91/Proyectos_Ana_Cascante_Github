<?php include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;

if (isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id'] == '2' && $_SESSION['is_active'] == '1') {

?>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="../css/style2.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Panel Organizador </title>
    <script src="./../js/bootstrap.bundle.min.js"></script>
    <script src="./../js/PanelAdmin.js" defer></script>
  </head>

  <body>
    <?php include_once 'NavOrg.php'; ?>
    <div class="container">
      <a href="VerCarta.php" class="btn-flotante"><i class="fa fa-shopping-cart my-float"></i>
        <?php if ($cart->total_items() >0) { ?>
          <font style="vertical-align: inherit;">
            <font style="vertical-align: inherit;">
              <?php echo '+ ' . $cart->total_items(); ?></font>
          </font> <?php } ?>
      </a>
      <div class="row">
        <div class="col-sm-6">
          <h1>Panel de Organizador de: <?= $_SESSION['username'] ?></h1>

        </div>

      </div>
      <br><br><br><br><br>
      <div class="row">
        <div class="col-sm-6">
          <!--crear unos recuadros de select-->
          <h2>Listado Eventos generales</h2>
          <br><br>

          <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
            <?php try {
              $pdo = $db->connect();
              $query = "SELECT * FROM evento";
              $statement = $pdo->query($query);
              $eventos = $statement->fetchAll(PDO::FETCH_ASSOC);
              $eventosOrganizador = [];
              foreach ($eventos as $evento) {
                if ($evento['organizer_id'] == $_SESSION['organizer_id']) {
                  array_push($eventosOrganizador, $evento);
                }
            ?>
                <div class="col">
                  <div class="card shadow-sm">
                    <div style="height: 200px; width:200px;">
                      <img src="../../CulturalCompassBackEnd/Files/Img_event/<?php echo $evento['imagen'] ?>" class=" img-fluid img-thumbnail">
                    </div>
                    <div class="card-body">
                      <p class="card-text"><?php echo $evento['name_event'] ?></p>
                      <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                          <a href="EventoDetalle.php?id_evento=<?php echo $evento['id']  ?>" class="btn btn-sm btn-outline-secondary">Ver Evento</a>
                          <?php if (isset($_SESSION) && $_SESSION['role_id'] ==  '3' || isset($_SESSION) && $_SESSION['role_id'] ==  '2' && $_SESSION['organizer_id'] ==  $evento['organizer_id']) { ?>
                            <a href="GestionEventos.php?id_evento=<?php echo $evento['id']  ?> " class="btn btn-sm btn-outline-secondary">Administrar Evento</a>
                          <?php } ?>
                        </div>
                        <small class="text-body-secondary"><?php echo "Fecha: " . date('d-m-Y', strtotime($evento['start_at'])) ?></small>
                      </div>
                    </div>
                  </div>
                </div>

            <?php }
            } catch (PDOException $e) {
              echo "error:" . $e->getMessage();
            }
            ?>
          </div>
        </div>

        <div class="col-sm-4" style="margin-left:100px;">
          <h2>Eventos Creados</h2>
          <br><br>
          <div style="height: 200px; width:200px;">
            <?php if (isset($_SESSION) && $_SESSION['role_id'] ==  '3' || isset($_SESSION) && $_SESSION['role_id'] ==  '2') {
            ?>
              <?php if (!empty($eventosOrganizador)) { ?>
                <?php foreach ($eventosOrganizador as $evento) { ?>
                  <div class="col">
                    <div class="card shadow-sm">
                      <div style="height: 200px; width:200px;">
                        <img src="../../CulturalCompassBackEnd/Files/Img_event/<?php echo $evento['imagen'] ?>" class=" img-fluid img-thumbnail">
                      </div>
                      <div class="card-body">
                        <p class="card-text"><?php echo $evento['name_event'] ?></p>
                        <div class="d-flex justify-content-between align-items-center">
                          <div class="btn-group">
                            <a href="EventoDetalle.php?id_evento=<?php echo $evento['id']  ?>" class="btn btn-sm btn-outline-secondary">Ver Evento</a>
                            <a href="GestionEventos.php?id_evento=<?php echo $evento['id']  ?> " class="btn btn-sm btn-outline-secondary">Administrar Evento</a>
                          </div>
                          <small class="text-body-secondary"><?php echo "Fecha: " . date('d-m-Y', strtotime($evento['start_at'])) ?></small>
                        </div>
                      </div>
                    </div>
                  </div>


                <?php }
              } else { ?>
                No has creado ningun evento
              <?php } ?>
            <?php } ?>

          </div>


  </body>

  </html>

<?php } else {
  header('location: PanelOrg.php');
} ?>