<?php
date_default_timezone_set("Europe/Madrid");
// Iniciamos la clase de la carta
include 'La-carta.php';
$cart = new Cart;

// include database configuration file
include 'ControladorBD.php';
if(isset($_REQUEST['action']) && !empty($_REQUEST['action'])){
    if($_REQUEST['action'] == 'addToCart' && !empty($_REQUEST['id'])){
        $productID = $_REQUEST['id'];
        // get product details
        $query = $db->query("SELECT * FROM evento WHERE id = ".$productID);
        $row = $query->fetch_assoc();
        $itemData = array(
            'id' => $row['id'],
            'name' => $row['name_event'],
            'price' => $row['precio_evento'],            
            'qty' => 1
        );
        
        $insertItem = $cart->insert($itemData);
        $redirectLoc = $insertItem?'../../CulturalCompassFrontEnd/html/VerCarta.php':'../../CulturalCompassFrontEnd/html/VerCarta.php';
        header("Location: ".$redirectLoc);
    }elseif($_REQUEST['action'] == 'updateCartItem' && !empty($_REQUEST['id'])){
        $itemData = array(
            'rowid' => $_REQUEST['id'],
            'qty' => $_REQUEST['qty']
        );
        $updateItem = $cart->update($itemData);
        echo $updateItem?'ok':'err';die;
    }elseif($_REQUEST['action'] == 'removeCartItem' && !empty($_REQUEST['id'])){
        $deleteItem = $cart->remove($_REQUEST['id']);
        header("Location: ../../CulturalCompassFrontEnd/html/VerCarta.php");
    }elseif($_REQUEST['action'] == 'placeOrder' && $cart->total_items() > 0){
        // insert order details into database
        $insertOrder = $db->query("INSERT INTO orden (
            total_price, 
            created, 
            mes, 
            dia, 
            year, 
            hora, 
            status, 
            idprecio) VALUES ( 
            '".$cart->total()."', 
            '".date("Y-m-d H:i:s")."', 
            '".date("m")."', 
            '".date("d")."', 
            '".date("Y")."', 
            '".date("H:i:s")."', 
            '1', 
            '')");
    

        if($insertOrder){
            $orderID = $db->insert_id;
            $sql = '';
            // get cart items
            $cartItems = $cart->contents();
            foreach($cartItems as $item){
                $_SESSION['orderID'] = $orderID;
                $sql .= "INSERT INTO orden_articulos (order_id, product_id, quantity) VALUES ('".$orderID."', '".$item['id']."', '".$item['qty']."');";

       
                
            }
            // insert order items into database
            $insertOrderItems = $db->multi_query($sql);
            
            if($insertOrderItems){
               
                header("Location: ../../CulturalCompassFrontEnd/html/pagos2.php?id=$orderID");
            }else{
                header("Location:../../CulturalCompassFrontEnd/html/ Pagos.php");
            }
        }else{
            header("Location: ../../CulturalCompassFrontEnd/html/Pagos.php");
        }
    }else{
        header("Location: ../../CulturalCompassFrontEnd/html/EventoDetalle.php");
    }
}else{
    header("Location: ../../CulturalCompassFrontEnd/html/EventoDetalle.php");
}