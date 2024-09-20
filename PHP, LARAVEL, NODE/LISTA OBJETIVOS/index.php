<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Objetivos</title>
    <script>
        function validarFormulario() {
            var name = document.getElementById('name').value;
            var date = document.getElementById('date').value;

            var regexName = /^[a-zA-Z]+$/;
            if (!regexName.test(name)) {
                alert('El nombre solo debe contener letras. No se ha guardado el elemento');
                return false;
            }

            var regexDate = /^\d+$/;
            if (!regexDate.test(date)) {
                alert('La fecha solo debe contener números. No se ha guardado el elemento');
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
<form action="añadirobj.php" method="post" onsubmit="return validarFormulario();">
  Nombre: <input type="text" name="name" id="name" required><br>
  Fecha: <input type="date" name="date" id="date" required><br>
  Logrado: <select name="checked" id="checked" required>
  <option value="yes">Yes</option>
            <option value="no">No</option>
            </select><br>
  <input type="submit" value="Añadir Objetivo">
</form>
</body>
</html> 
