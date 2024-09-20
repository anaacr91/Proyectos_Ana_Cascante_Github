<?php include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;

if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='1' && $_SESSION['is_active']=='1'){

  ?>
  <!DOCTYPE html>
  <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css " integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
      <title>Panel Usuario</title>
      <script src="./../js/bootstrap.bundle.min.js"></script>
      <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js '></script>
      
        <script src='https://cdn.jsdelivr.net/npm/rrule@2.6.4/dist/es5/rrule.min.js'></script>
      
        <!-- the rrule-to-fullcalendar connector. must go AFTER the rrule lib -->
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/rrule@6.1.10/index.global.min.js'></script> 
      <script src="./../js/PanelUsuario.js" defer></script>
      <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/style2.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

  
    </head>
    <body>
    <?php include_once 'NavUser.php'; ?>
      <div class="container">
      <a href="VerCarta.php" class="btn-flotante"><i class="fa fa-shopping-cart my-float"></i>
        <?php if ($cart->total_items() > 0) { ?>
          <font style="vertical-align: inherit;">
            <font style="vertical-align: inherit;">
              <?php echo '+ ' . $cart->total_items(); ?></font>
          </font> <?php } ?>
      </a>
        <div class="row">
          <div class="col-sm-6">
             <h1>Hola <?=$_SESSION['username']?></h1>
          </div>
          
        </div>
        <div class="row">
          <div class="col-sm-6">
              <h2>Historial de Compras</h2>
              <h2>Calendario eventos</h2>
              <!--obtener eventos api y recorrerlos-->
            <div id="calendar">
              
            </div>
          </div>
          <div class="col-sm-6">
              <h2>lista de mis eventos</h2>
              <!--obtener eventos api favoritos y recorrerlos-->
            <?php 
        $pdo =$db->connect();
        $query="SELECT * FROM event_users eu INNER JOIN evento e ON eu.id_event=e.id WHERE eu.id_user = ".$_SESSION['id']. " ORDER BY eu.id DESC";
        $statement = $pdo->query($query);
        $misEventos = $statement->fetchAll(PDO::FETCH_ASSOC);
        $misEventosNombre = array();
        ?>

        <?php if(!empty($misEventos)){?>
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Fecha Inicio</th>
                <th>Fecha fin</th>
              </tr>
            </thead>
            <tbody>
              <?php foreach($misEventos as $misEvento){?>
              <tr>
                <td><?php echo $misEvento['name_event']?></td>
                <td><?php echo $misEvento['description']?></td>
                <td><?php echo $misEvento['start_at']?></td>
                <td><?php echo $misEvento['end_at']?></td>
              </tr>
              <?php $misEventosNombre[] = $misEvento['name_event']?>
              <?php }?>
            </
            <tbody>
          </table>
        <?php } else{?>
          <h3>No estás apuntado a ningún evento</h3>
          <?php }?>
          </div>
          <div class="row">
            <div class="col-12">
              <h2>listado de eventos</h2>

        <br><br>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

    <?php try{
        $pdo =$db->connect();
        $query="SELECT * FROM evento";
        $statement = $pdo->query($query);
        $eventos = $statement->fetchAll(PDO::FETCH_ASSOC);

        foreach($eventos as $evento){ ?>
    <div class="col">
    <div class="card shadow-sm">
    <div style="height: 200px; width:200px;">
      <img src="../../CulturalCompassBackEnd/Files/Img_event/<?php echo $evento['imagen'] ?>" class=" img-fluid img-thumbnail">
        </div>
    <div class="card-body">
    <p class="card-text"><?php echo $evento['name_event'] ?></p>
    <div class="d-flex justify-content-between align-items-center">
    <div class="btn-group">
      <a href="EventoDetalle.php?id_evento=<?php echo $evento['id']  ?>"  class="btn btn-sm btn-outline-secondary">Ver Evento</a>
      <?php if(in_array($evento['name_event'], $misEventosNombre)){?>
        <button   class="btn btn-sm btn-outline-secondary" onclick="removeEvento(<?php echo $evento['id']?>,<?=$_SESSION['id']?>)">Desapuntarme</button>
      <?php } else{?>
        <button   class="btn btn-sm btn-outline-secondary" onclick="addEvento(<?php echo $evento['id']?>,<?=$_SESSION['id']?>)">Apuntarme</button>

      <?php }?>
    </div>
    <small class="text-body-secondary"><?php  echo "Fecha: ".date('d-m-Y',strtotime($evento['start_at'])) ?></small>
    </div>
    </div>
    </div>
    </div>

    <?php }    
    
  }catch(PDOException $e){
  echo "error:". $e->getMessage();
  }
  
  ?>       
            </div>
            <div class="col-sm-6">
              <h2>listado de eventos en mapa</h2>
              <!--volcar lat y long en el mapa por api google-->
            </div>
          </div>
        </div>
  
      </div>
    	<input type="hidden" id="id_usuario" value="<?=$_SESSION['id']?>">
    </body>
  </html>
  <?php

        }else{

        header('location: Login.php');

        }

?>
