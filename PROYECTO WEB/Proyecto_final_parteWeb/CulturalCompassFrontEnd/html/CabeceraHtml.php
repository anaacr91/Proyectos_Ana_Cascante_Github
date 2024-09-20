<?php include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';  
$db = new DB(); 
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" >
    <link rel="stylesheet" href="./../css/principal.css">
    <link rel="stylesheet" href="./../css/design.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <script type="module" defer src="/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassFrontEnd/js/Search.js" ></script>

    <script src="/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassFrontEnd/js/notify.js"></script>
    <script defer src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

    <title>CULTURAL COMPASS</title>

</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-primary bg-body-tertiary fixed-top mb-5" >
        <div class="container-fluid">
          <a class="navbar-brand" href="../index.php">Inicio</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link " aria-current="page" href="Login.php">Login</a>
              </li>
              <li class="nav-item">
                <a class="nav-link " aria-current="page" href="RegistroUsuario.php">Registro Usuario</a>
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
              </ul>
              <div class="d-flex" role="search">
              <input class="form-control me-2 text-value" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success " onclick="searchFunction()" >Busqueda por palabra clave</button>
            </div>
          </div>
        </div>
      </nav> 
   