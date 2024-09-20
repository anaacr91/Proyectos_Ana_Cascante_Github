<?php include_once 'ApiControladorLogin.php';

$api = new ApiCulturalCompass();



if(isset($_GET['category_id'])&&isset($_GET['when'])){
    $category_id = $_GET['category_id'];
    $when = $_GET['when'];
    $api->getFilteredEvents($category_id, $when);
}
elseif(isset($_POST['email'])&&isset($_POST['password']) || isset($_GET['email'])&&isset($_GET['contrasena'])){

    if(isset($_POST['submit'])){
        
        $email = $_POST['email'];
        $password = $_POST['password'];
        $action = $_POST['submit']; 
    }else{
        $nombre = $_GET['nombre'];
        $email = $_GET['email'];
        $contrasena = $_GET['contrasena'];
        $action = $_GET['submit'];
    }

    switch($action){
        case 'Login':
            $api->userLogin($email, $password);
            break;
        case 'Registro':
            $api->userRegister($nombre,$contrasena,$email);
            break;
        default:
            $api->when_error('No se especifico ninguna accion');
            break;
    }
}

?>