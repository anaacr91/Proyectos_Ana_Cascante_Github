
<?php session_start();
if ((isset($_SESSION['role_id']) && isset($_SESSION['is_active'])
    && ($_SESSION['role_id'] == '3' || $_SESSION['role_id'] == '2' || $_SESSION['role_id'] == '1' || $_SESSION['role_id'] == '0'))
  || (!isset($_SESSION['role_id']) && !isset($_SESSION['is_active']))
) {

  include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();
  $id_categoria = $_GET['id_categoria']; 
  $text_value = !empty($_GET['search']) ? $_GET['search'] : null;
if (!empty($text_value)) {
    $pdo = $db->connect();
    $query11 = "SELECT * FROM evento WHERE name_event LIKE '%" . $text_value . "%'";
    $eventos = $pdo->query($query11);
    $query12 = $eventos->fetchAll(PDO::FETCH_ASSOC);
}
  ?>

  <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />

  <?php
  if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
    include_once 'NavAdmin.php'; ?>
  <?php
  } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
    include_once 'NavOrg.php'; ?>

  <?php
  } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
    include_once 'NavUser.php'; ?>

  <?php
  } else {
    include_once 'CabeceraHtml.php' ?>
  <?php
  } ?>

  <br><br>
  <img src="../logo/logo.jpg" alt="Logo de Cultural Compass" height="100" style="margin-top: 75px; margin-left: 175px;">
  <div class="container mt-5">
    <div class="row">
      <div class="col ">
        <h2>Bienvenido a Cultural Compass</h2>
        <p>En Cultural Compass, exploramos y promovemos una amplia variedad de eventos culturales en diferentes categorías, desde música hasta teatro y gastronomía.</p>
      </div>
    </div>
    <div class="row ">
      <div class="col ">
        <h3>¿Quiénes somos?</h3>
        <p>Somos un equipo apasionado por la cultura y las artes, comprometidos en conectar a las personas con eventos culturales en sus comunidades y más allá.</p>
      </div>
    </div>
    <div class="row">
      <div class="col ">
        <h2>Nuestros Eventos Recomendados</h2>
        <p>Explora nuestros eventos recomendados por tu ciudad </p>

      </div>
    </div>
    <div class="row">
      <div class="col">
        <centro>
          <div class="sidebar">
        </centro>
        <?php if (isset($_SESSION['role_id']) && ($_SESSION['role_id'] == '3' || $_SESSION['role_id'] == '2')) : ?>
          <a href="GestionarCategorias.php?id_category=<?php echo $id_categoria ?>" class="btn btn-primary">Gestionar Categorías</a>
        <?php endif; ?>
      </div>
    </div>
  </div>

  </div>
  <br>
  <div class="container">
  <div class="album py-5 bg-body-tertiary">
    
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <?php try {
          $pdo = $db->connect();
          $query_cat = "SELECT * FROM evento WHERE category_id ='" . $id_categoria . "'";
          $statement_cat = $pdo->query($query_cat);
          $eventos = $statement_cat->fetchAll(PDO::FETCH_ASSOC);
        ?>

          <?php foreach ($eventos as $evento) { ?>
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
                      <?php if (isset($_SESSION['role_id']) && ($_SESSION['role_id'] == '3' || $_SESSION['role_id'] == '2')) : ?>
                        <a href="GestionEventos.php?id_evento=<?php echo $evento['id']  ?> " class="btn btn-sm btn-outline-secondary">Administrar Evento</a>
                      <?php endif; ?>
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
      } else {
        header('location: Login.php');
      }
      ?>

      </div>
    </div>
  </div>

  <script src="../js/bootstrap.bundle.min.js"></script>
  <script  src="./../js/Search.js" ></script>
  </body>

  </html>