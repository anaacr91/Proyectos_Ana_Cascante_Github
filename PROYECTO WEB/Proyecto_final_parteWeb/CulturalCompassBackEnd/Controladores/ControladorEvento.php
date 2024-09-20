<?php
include_once 'ControladorBD.php';

class Evento extends DB{
    function get_all_events(){
        $query = $this->connect()->query('SELECT * FROM evento');
        return $query;
    }

    function get_filtered_events($category_id, $when){
        $current_date = date('Y-m-d H:i:s');
        $interested_date=date('Y-m-d H:i:s', strtotime($current_date . ' + '.$when.' days'));
        echo $interested_date;
        $query = $this->connect()->prepare("SELECT * FROM evento WHERE category_id = :id");/*AND start_at >= ? AND start_at <= ?*/
        $query->execute(['id'=>$category_id]);
        return $query;
    }

}
?>