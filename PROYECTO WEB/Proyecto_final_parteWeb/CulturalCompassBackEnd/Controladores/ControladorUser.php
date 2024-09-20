<?php
include_once 'ControladorBD.php';

class User extends DB{

    function user_register($nombre,$contrasena,$email){
        $current_date = date('Y-m-d H:i:s');
        $role_id='1';
        $is_active='1';
        $query = $this->connect()->prepare('INSERT INTO `user`(`username`, `password`, `role_id`, `is_active`, `created_at`, `email`) VALUES (?, ?, ?, ?, ?, ?)');
        $query->execute([$nombre, $contrasena, $role_id, $is_active, $current_date, $email]);
        
        return $query;
    }

    function check_user($email, $password){
        $query = $this->connect()->prepare('SELECT u.*, o.id as organizer_id FROM user u LEFT JOIN organizer o on u.id = o.id_user  WHERE  u.email = ? AND u.password = ?');
        $query->execute([$email, $password]);
        return $query;
    }
    
    
}
?>