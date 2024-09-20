<?php
session_start();
$errors = [];

$username = $email = $password = $confirm_password = $birthday = $card_number = $expiry_date = $security_code = $accept_terms = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST["username"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $confirm_password = $_POST["confirm_password"];
    $birthday = $_POST["birthday"];
    $card_number = $_POST["card_number"];
    $expiry_date = $_POST["expiry_date"];
    $security_code = $_POST["security_code"];
    $accept_terms = isset($_POST['accept_terms']) ? $_POST['accept_terms'] : '0';


    if (empty($username) || strlen($username) < 3) {
        $errors["username"] = "El nombre de usuario es obligatorio y debe tener al menos 3 caracteres.";
    } elseif (!preg_match('/^[A-Z][a-zA-Z]*$/', $username)) {
        $errors["username"] = "El nombre de usuario debe contener solo letras y la primera letra debe estar en mayúscula.";
    }
    if (empty($email) || !filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errors["email"] = "el email es obligatorio y ha de cumplir el formato correcto";
    }
    if (
        empty($password) || strlen($password) < 5 || !preg_match('/[A-Z]/', $password)
        || !preg_match('/[a-z]/', $password) || !preg_match('/[0-9]/', $password)
    ) {
        $errors["password"] = "el password es obligatorio y debe contener mínimo una letra mayúscula, una minúscula, un 
        número y debe tener al menos 5 caracteres ";
    }
    if (empty($confirm_password) || $confirm_password !== $password) {
        $errors["confirm_password"] = " es obligatorio y debe ser igual que la contraseña";
    }
    if (empty($birthday)) {
        $errors["birthday"] = "la fecha de nacimiento es obligatoria";
    }
    if (strlen($card_number) != 16) {
        $errors["card_number"] = "la targeta debe de tener 16 carácteres";
    }

    if (!empty($card_number)) {

        if (empty($expiry_date)) {
            $errors["expiry_date"] = "La fecha de caducidad es obligatoria.";
        } elseif (strtotime($expiry_date) <= time()) {
            $errors["expiry_date"] = "La fecha de caducidad debe ser una fecha futura.";
        }
    }

    if (!empty($card_number)) {

        if (empty($security_code)) {
            $errors["security_code"] = "El código de seguridad es obligatorio.";
        } elseif (strlen($security_code) != 3) {
            $errors["security_code"] = "El código de seguridad deben de tener 3 numeros.";
        }
    }  
    if (isset($_POST['accept_terms']) && $_POST['accept_terms'] == '1') {  
    } else {
    $errors["accept_terms"] = "Debes aceptar las condiciones de uso.";
    }

    if (empty($errors)) {
        $register_form = [
            "username" => $username,
            "email" => $email,
            "password" => $password,
            "confirm_password" => $confirm_password,
            "birthday" => $birthday,
            "card_number" => $card_number,
            "expiry_date" => $expiry_date,
            "security_code" => $security_code,
            "accept_terms" => $accept_terms
        ];

        $_SESSION['registro'] = $register_form;

        echo "<h1>Se ha registrado correctamente.</h1>";
?>
        <br>
        <form method="POST">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" value="<?php echo $username; ?>">
            <span style="color:red;"><?php echo $errors["username"] ?? ""; ?></span>
            <br>
            <label for="email">Email</label>
            <input type="text" name="email" id="email" value="<?php echo $email; ?>">
            <span style="color:red;"><?php echo $errors["email"] ?? ""; ?></span>
            <br>
            <label for="password">Password</label>
            <input type="text" name="password" id="password" value="<?php echo $password; ?>">
            <span style="color:red;"><?php echo $errors["password"] ?? ""; ?></span>
            <br>
            <label for="confirm_password"> Confirm Password</label>
            <input type="text" name="confirm_password" id="confirm_password" value="<?php echo $confirm_password; ?>">
            <span style="color:red;"><?php echo $errors["confirm_password"] ?? ""; ?></span>
            <br>
            <label for="birthday">Birthday</label>
            <input type="date" name="birthday" id="birthday" value="<?php echo $birthday; ?>">
            <span style="color:red;"><?php echo $errors["birthday"] ?? ""; ?></span>
            <br>
            <label for="card_number">Card number</label>
            <input type="text" name="card_number" id="card_number" value="<?php echo $card_number ?>">
            <span style="color:red;"><?php echo $errors["card_number"] ?? ""; ?></span>
            <br>
            <label for="expiry_date">Expiration Date</label>
            <input type="date" name="expiry_date" id="expiry_date" value="<?php echo $expiry_date; ?>">
            <span style="color:red;"><?php echo $errors["expiry_date"] ?? ""; ?></span>
            <br>
            <label for="security_code">Security Code</label>
            <input type="text" name="security_code" id="security_code" value="<?php echo $security_code; ?>">
            <span style="color:red;"><?php echo $errors["security_code"] ?? ""; ?></span>
            <br>
            <label for="accept_terms">Accept Terms</label>
            <input type="checkbox" name="accept_terms" id="accept_terms" value="1">
            <span style="color:red;"><?php echo $errors["accept_terms"] ?? ""; ?></span>
            <br>

    <?php
        exit();
    }
}
    ?>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>
        <form method="POST">
             <label for="username">Username</label>
            <input type="text" name="username" id="username" value="<?php echo $username; ?>">
            <span style="color:red;"><?php echo $errors["username"] ?? ""; ?></span>
            <br>
            <label for="email">Email</label>
            <input type="text" name="email" id="email" value="<?php echo $email; ?>">
            <span style="color:red;"><?php echo $errors["email"] ?? ""; ?></span>
            <br>
            <label for="password">Password</label>
            <input type="text" name="password" id="password" value="<?php echo $password; ?>">
            <span style="color:red;"><?php echo $errors["password"] ?? ""; ?></span>
            <br>
            <label for="confirm_password"> Confirm Password</label>
            <input type="text" name="confirm_password" id="confirm_password" value="<?php echo $confirm_password; ?>">
            <span style="color:red;"><?php echo $errors["confirm_password"] ?? ""; ?></span>
            <br>
            <label for="birthday">Birthday</label>
            <input type="date" name="birthday" id="birthday" value="<?php echo $birthday; ?>">
            <span style="color:red;"><?php echo $errors["birthday"] ?? ""; ?></span>
            <br>
            <label for="card_number">Card number</label>
            <input type="text" name="card_number" id="card_number" value="<?php echo $card_number ?>">
            <span style="color:red;"><?php echo $errors["card_number"] ?? ""; ?></span>
            <br>
            <label for="expiry_date">Expiration Date</label>
            <input type="date" name="expiry_date" id="expiry_date" value="<?php echo $expiry_date; ?>">
            <span style="color:red;"><?php echo $errors["expiry_date"] ?? ""; ?></span>
            <br>
            <label for="security_code">Security Code</label>
            <input type="text" name="security_code" id="security_code" value="<?php echo $security_code; ?>">
            <span style="color:red;"><?php echo $errors["security_code"] ?? ""; ?></span>
            <br>
            <label for="accept_terms">Accept Terms</label>
            <input type="checkbox" name="accept_terms" id="accept_terms" value="1">
            <span style="color:red;"><?php echo $errors["accept_terms"] ?? ""; ?></span>
            <br>
            <input type="submit" value="registrarse">
        </form>
    </body>

    </html>