<head>
<script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
  <script src="./../js/notify.js"></script>
</head>
<?php include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';  
$db = new DB(); 
?>
<nav class="navbar navbar-expand-lg navbar-primary bg-body-tertiary  mb-5" >
        <div class="container-fluid">
          <a class="navbar-brand" href="PanelOrg.php">Inicio</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
              <a href="PerfilPersonal.php" class="nav-link"  aria-current="page">Gestionar Mi Perfil</a>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Crear
                </a>
                <ul class="dropdown-menu">
                  <li><a class= "dropdown-item" href="CrearEvento.php">Eventos</a></li>
                  <li><a class= "dropdown-item" href="CrearUbicacion.php">Ubicaciones</a></li>
                  <li><a class= "dropdown-item" href="CrearCategoria.php">Categorias</a></li> 
                </ul>
              </li>
              
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Administrar
                </a>
                <ul class="dropdown-menu">
                  <li><a href="AdminForo.php" class="dropdown-item" >Foro</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Buscar por Ubicacion
                </a>
                <ul class="dropdown-menu">
                <?php try{
            $pdo =$db->connect();
            $query_ubicacion="SELECT * FROM location";
            $statement_ubicacion = $pdo->query($query_ubicacion);
            $ubicaciones_global = $statement_ubicacion->fetchAll(PDO::FETCH_ASSOC);

            foreach($ubicaciones_global as $location){ ?>
                   <li><a class="dropdown-item" href="ubicacion.php?id_location=<?php echo $location['id']?>"><?php echo $location['name_location']?></a></li>
            <?php } 
          }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?> 
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Buscar por Categoria
                </a>
                <ul class="dropdown-menu">
                <?php try{
            $pdo =$db->connect();
            $query_categoria="SELECT * FROM category";
            $statement_categoria = $pdo->query($query_categoria);
            $categorias_global = $statement_categoria->fetchAll(PDO::FETCH_ASSOC);

            foreach($categorias_global as $category){ ?>
                   <li><a class="dropdown-item" href="categoria.php?id_categoria=<?php echo $category['id']?>"><?php echo $category['name_category']?></a></li>
            <?php } 
          }catch(PDOException $e){ echo "error:". $e->getMessage(); } ?> 
                </ul>
              </li>
              <li class="nav-item">
              <a href="Contacto.php" class="nav-link"  aria-current="page">Contacto</a>
              </li>
              <li class="nav-item">
              <a href="BuzonMensajes.php" class="nav-link"  aria-current="page">Buzon</a>
              </li>
            </ul>
            
            <a class="btn btn-danger" href="salir.php">Salir</a>
          </div>
        </div>
      </nav> 