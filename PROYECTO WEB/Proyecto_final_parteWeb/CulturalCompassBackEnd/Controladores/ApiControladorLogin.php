<?php
session_start();

include_once 'ControladorEvento.php';
include_once 'ControladorUser.php';

class ApiCulturalCompass{

    function getAllEvents(){
        $evento=new Evento();
        $res=$evento->get_all_events();
        
        if($res->rowCount()){
            $eventos=array();
            $eventos['eventos']=array();

            while($row = $res->fetch(PDO::FETCH_ASSOC)){
                $item=array(
                    "id"=>$row['id'],
                    "name"=>$row['name_event'],
                    "description"=>$row['description'],
                    "start_at"=>$row['start_at'],
                    "end_at"=>$row['end_at'],
                    "status"=>$row['status'],
                    "url"=>$row['url'],
                    "category_id"=>$row['category_id'],
                    "location_id"=>$row['location_id'],
                    "organizer_id"=>$row['organizer_id']
                );
                array_push($eventos['eventos'], $item);
            }

            $this->printJSON($eventos);
        }else{
            $this->when_error('No existen eventos');
        }  
    }

    function getFilteredEvents($category_id, $when){
        $evento=new Evento();
        $res=$evento->get_filtered_events($category_id, $when);
        
        if($res->rowCount()){
            $eventos=array();
            $eventos['eventos']=array();

            while($row = $res->fetch(PDO::FETCH_ASSOC)){
                $item=array(
                    "id"=>$row['id'],
                    "name"=>$row['name_event'],
                    "description"=>$row['description'],
                    "start_at"=>$row['start_at'],
                    "end_at"=>$row['end_at'],
                    "status"=>$row['status'],
                    "url"=>$row['url'],
                    "category_id"=>$row['category_id'],
                    "location_id"=>$row['location_id'],
                    "organizer_id"=>$row['organizer_id']
                );
                array_push($eventos['eventos'], $item);
            }

            $this->printJSON($eventos);
        }else{
            $this->when_error('No existen eventos');
        }  
    }

    function userRegister($nombre,$contrasena,$email){
        $user=new User();
        $res=$user->user_register($nombre,$contrasena,$email);
        header('location: ../../CulturalCompassFrontEnd/html/login.php');
        exit();
        
    }


    function userLogin($email, $password){
        $user=new User();
        $res=$user->check_user($email, $password);
        if($res->rowCount()){
            $row = $res->fetch();
            $privilegio = $row['role_id'];
            $_SESSION['username'] = $row['username'];
            $_SESSION['password'] = $row['password'];
            $_SESSION['role_id'] = $row['role_id'];
            $_SESSION['is_active'] = $row['is_active'];
            $_SESSION['created_at'] = $row['created_at'];
            $_SESSION['email'] = $row['email'];
            $_SESSION['id'] = $row['id'];
            $_SESSION['organizer_id'] = is_null($row['organizer_id'])?"":$row['organizer_id'];

            switch($privilegio){
                case '1':
                   header('location: ../../CulturalCompassFrontEnd/html/PanelUsuario.php');
                    break;

                case '2':
                    header('location: ../../CulturalCompassFrontEnd/html/PanelOrg.php');
                    break;

                case '3':
                    header('location: ../../CulturalCompassFrontEnd/html/PanelAdmin.php');
                    break;
                default:
                   header ('location:../../CulturalCompassFrontEnd/html/login.php');
                    break;
            }

        }else{
            $this->when_error('usuario o contraseña incorrectos');
        }
    }

    function when_error($message){
        echo json_encode(array('message' => $message));
    }
    function printJSON($array){
        echo json_encode($array);
    }
    function exito($message){
        echo json_encode(array('message' => $message));
    }
}

?>