<?php 
if(isset($_POST['id'])):

    include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
    

 $peticion = $base_de_datos->query("SELECT * FROM user  WHERE role_id='".$_POST['id']."'" ); 
    $tipos= $peticion->fetchAll(PDO::FETCH_OBJ); 
    $html="";

    foreach($tipos as $tipo){
        if(property_exists($tipo, 'username')){
    	$html.="<option value='".$tipo->id."'>".$tipo->username."</option>";
    	

        }
    }
echo $html ;
endif;



 ?>