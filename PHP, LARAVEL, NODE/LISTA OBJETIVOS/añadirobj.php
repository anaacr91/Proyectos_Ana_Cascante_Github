<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Añadir Objetivo</title>
</head>
<body>
    
<?php
$filename = 'objectives.csv';


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $name = $_POST['name'] ?? '';
    $date = $_POST['date'] ?? '';
    $checked = $_POST['checked'] ?? '';
    $content = $name . ',' . $date . ',' . $checked;

    if (!file_exists($filename)) {
        $header = "Name;Date;Checked\n"; 
        file_put_contents($file, $header);
    }

    if (empty($name) || empty($date) || empty($checked)) {
        echo "Error al añadir el objetivo.";
    } else {
        $data = "$name;$date;$checked\n";
    
        if (file_put_contents($filename, $data, FILE_APPEND)) {
            echo "Objetivo añadido con éxito."; 
            ?>

<script>
    function redireccionar(){
    window.location="index.php";
    } 
    setTimeout ("redireccionar()", 5000); 
</script> 
 
 <?php
        } else {
            echo "Error al añadir el objetivo."; 
        }   
    }
    $file = fopen($filename. '.csv', 'a');
    fputcsv($file, explode(',', $content));
    fclose($file);
} else {
    echo "Por favor, envía el formulario para añadir un objetivo.";
}
?> 

</body>
</html>
