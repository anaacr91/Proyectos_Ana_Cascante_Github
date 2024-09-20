
<?php 
session_start();
session_destroy();
 include_once "CabeceraHtml.php" ?>
    <main class="form-signin w-100 m-auto">
    <form id="formlogin" action="../../CulturalCompassBackEnd/Controladores/ControladorLogin.php" method="POST" style="background-color: white;">
      <br><br><br><br>
      <img src="../logo/logo.jpg" alt="Logo de Cultural Compass" height="100" style='border-radius: 10px; margin-left: 200px;' >
      <br><br><br>
      <h1 class="h3 mb-3 fw-normal " style="margin-left: 200px;">Iniciar sesion</h1>
    <div class="form-floating" style="max-width: 400px; margin-left: 100px;">
    <input type="email" class="form-control" id="email" name='email' placeholder="name@example.com">
    <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating" style="max-width: 400px; margin-left: 100px;">
    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
    <label for="floatingPassword">Password</label>
    </div>
    <div class="form-check text-start my-3" style="max-width: 400px; margin-left: 100px;">
    <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
    <label class="form-check-label" for="flexCheckDefault">
    Remember me
    </label>
    </div>
    <div style="max-width: 100px; margin-left: 100px;"></div>
    <input type="submit" name="submit" value="Login" class="btn btn-custom text-center text-white col-lg-3 col-sm-5" style="margin-left:125px; max-width:300px;">
    <!--<input type="hidden" name="action" value="login">-->
    </div>
    </form>
    </main>
    <div class="toggle-container" style="max-height: 512px; margin-top: 325px;">
            <div class="toggle" >
                <div class="toggle-panel toggle-right" >
                    <h1>Bienvenido a CulturalCompass</h1>
                    <p>Ingrese sus datos personales para utilizar todas las funciones del sitio y apuntarse a nuestros eventos</p>
                    <a href="RegistroUsuario.php" class="btn btn-custom text-center text-white" id="register">Sign Up</a>
                </div>
        
        </div>
    </div>

<script src="./../js/bootstrap.bundle.min.js"></script>
<script src="./../js/Search.js"></script>
</body>
</html>