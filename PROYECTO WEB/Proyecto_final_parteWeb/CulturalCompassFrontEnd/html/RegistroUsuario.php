
<?php include_once 'CabeceraHtml.php' ?>
<script src="./../js/RegistroUsuario.js"></script>
<br><br><br><br>
      <center><img src="../logo/logo.jpg" alt="Logo de Cultural Compass" height="100" width="150" style='border-radius: 10px;' ></center>
       <br><br>
      <section>
        <header>
          <center>  <h1 style=" margin-left: 75px;">Registro de Usuario</h1></center>
        </header>
    </section>
    <br><br>
    <center><h2 style=" margin-left: 75px;">Crea tu cuenta</h1></center>
    <br><br>
<center><form id="registroForm" style=" margin-left: 75px;">
        <label for="nombre">Nombre:</label><br>
        <input type="text" id="nombre" name="nombre"><br>
        <span id="nombreError" class="error"></span><br>
        
        <label for="email">Correo electrónico:</label><br>
        <input type="email" id="email" name="email"><br>
        <span id="emailError" class="error"></span><br>
        
        <label for="contrasena">Contraseña:</label><br>
        <input type="password" id="contrasena" name="contrasena"><br>
        <span id="contrasenaError" class="error"></span><br>
       
        <center><input type="submit" value="Registro" id="submit" class="btn btn-custom text-center" style="max-width: 200px"></center>
    </form> </center>
<script src="./../js/bootstrap.bundle.min.js"></script>
<script src="./../js/Search.js"></script>
</body>
</html>