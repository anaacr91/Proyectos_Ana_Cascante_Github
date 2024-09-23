<?php include_once '../CulturalCompassBackEnd/Controladores/ControladorBD.php';  
$db = new DB(); 

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" media="screen" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/principal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="css/design.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script type="module" src="js/Search.js" ></script>

    <script src="https://cdn.rawgit.com/googlemaps/v3-utility-library/master/markerwithlabel/src/markerwithlabel.js"></script>

  <script async defer src="https://maps.googleapis.com/maps/api/js?key=...&libraries=places,geometry&callback=initMap"></script>

  <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    

    <script src="https://unpkg.com/@googlemaps/markerclustererplus"></script>
    
    <script>(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||
      (b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));
        e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);
        a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||
        "";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})
        ({key: "AIzaSyCvPUzlTWH-_xKiYcI7NW3R6U71Uhx94Wc", v: "beta"});</script>   

<script src="/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassFrontEnd/js/notify.js"></script>

    <script defer src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

    <title>CULTURAL COMPASS</title>

</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-primary bg-body-tertiary fixed-top mb-5" >
        <div class="container-fluid">
          <a class="navbar-brand" href="index.php">Inicio</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link " aria-current="page" href="html/Login.php">Login</a>
              </li>
              <li class="nav-item">
                <a class="nav-link " aria-current="page" href="html/RegistroUsuario.php">Registro Usuario</a>
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
                   <li><a class="dropdown-item" href="html/ubicacion.php?id_location=<?php echo $location['id']?>"><?php echo $location['name_location']?></a></li>
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
                   <li><a class="dropdown-item" href="html/categoria.php?id_categoria=<?php echo $category['id']?>"><?php echo $category['name_category']?></a></li>
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
     

    
