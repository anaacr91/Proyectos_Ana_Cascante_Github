<?php include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;

if ((isset($_SESSION['role_id']) && isset($_SESSION['is_active'])
    && ($_SESSION['role_id'] == '3' || $_SESSION['role_id'] == '2' || $_SESSION['role_id'] == '1' || $_SESSION['role_id'] == '0'))
    || (!isset($_SESSION['role_id']) && !isset($_SESSION['is_active']))) {

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
        evento.description as description  
        FROM evento INNER JOIN organizer on organizer.id=evento.organizer_id INNER JOIN location on location.id=evento.location_id  INNER JOIN category on category.id=evento.category_id  
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
            'location_id' => $evento['location_id'],
            'organizer_id' => $evento['organizer_id'],
            'name_org' => $evento['name_org'],
            'email' => $evento['email'],
            'company' => $evento['company'],
            'phone' => $evento['phone'],
            'longitud' => $evento['longitud'],
            'latitud' => $evento['latitud'],
            'ubicationlink' => $evento['ubicationlink'],
            'redirectionlink' => $evento['redirectionlink'],

        );?>


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

      <link rel="stylesheet" href="../css/style2.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="./../js/bootstrap.bundle.min.js"></script>
      <title><?php echo $itemData['name_event'] ?></title>
      


    </head>
    <body>

    <?php
if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
            include_once 'NavAdmin.php';?>
    <?php
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
            include_once 'NavOrg.php';?>

      <?php
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
            include_once 'NavUser.php';?>

      <?php
} else{
            include_once 'CabeceraHtml.php'?>
    <?php
}?>
<br><br><br><br><br>
    <div class="container">
    <?php if (isset($_SESSION['role_id'])){?>
<a href="VerCarta.php" class="btn-flotante"><i class="fa fa-shopping-cart my-float"></i>
<?php  if($cart->total_items() > 0){ ?>
  <font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
    <?php echo '+ '.$cart->total_items();?></font></font> <?php } ?>
</a> <?php }?>
        <div class="row">
          <div class="col-sm-6">
             <h1><?php echo $itemData['name_event'] ?></h1>
          </div>
          <div class="col-sm-6">
            <i class="fa-solid fa-star"></i>
<?php
if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {?>
      <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

    <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {?>
      <a href="PanelOrg.php" class="btn btn-primary">Ir Atrás</a>

      <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {?>
      <a href="PanelUsuario.php" class="btn btn-primary">Ir Atrás</a>
<?php }?>

      <?php
if (empty($_SESSION)) {?>
        <a href=../index.php class="btn btn-primary">Ir Atrás</a>
<?php }?>
          </div>
        </div>
    <br><br>

    <div class= row>
    <div class="col-sm-6">
        <h2><?php echo $itemData['description'] ?></h2>

    </div>
    <div class="col-sm-6">
      <h2></h2>
       <img src="../../CulturalCompassBackEnd/Files/Img_event/<?php echo $itemData['imagen'] ?>" class="img-fluid img-thumbnail">
    </div>
    <div class="row">
        <div class="col-sm-6">
        <h2><?php echo $itemData['name_org'] . '<br>' . $itemData['email'] . '<br>' . $itemData['phone'] ?></h2>
        <h3><a href="<?php echo $itemData['url'] ?>" target="_blank" >Visita la web del evento aqui</a></h3>
        <?php if (isset($_SESSION['role_id'])){?>
        <a href="../../CulturalCompassBackEnd/Controladores/AccionCarta.php?action=addToCart&id=<?php echo $itemData["id"]; ?>" class="btn btn-custom">Compra tu tiquet aquí</a>
        <?php }?>
        </div>
        <div class="col-sm-6">
        <a  href="" >
        <a href="<?php echo $itemData['redirectionlink'] ?>"<span class="col-lg-3"><span>Como llegar</span></span> </a>
        <h2><iframe src="<?php echo $itemData['ubicationlink'] ?>" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe> </h2>
        </div>
    </div>
    </div>
    </div>
    </body>
  </html>

<?php

    } catch (PDOException $e) {
        echo "error:" . $e->getMessage();
    }

} else {
    header('location: Login.php');
}
?>