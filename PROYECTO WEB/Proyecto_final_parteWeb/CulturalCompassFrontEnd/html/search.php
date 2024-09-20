<head>
    <link rel="stylesheet" href="./../css/design.css">



<?php
session_start();
session_destroy();
include_once "CabeceraHtml.php";

$text_value = !empty($_GET['search']) ? $_GET['search'] : null;
if (!empty($text_value)) {
    $pdo = $db->connect();
    $query11 = "SELECT * FROM evento WHERE name_event LIKE '%" . $text_value . "%'";
    $eventos = $pdo->query($query11);
    $query12 = $eventos->fetchAll(PDO::FETCH_ASSOC);
}
?>
</head>

<div class="container" style=" margin-top:800px;">
    <div class="row text-center">
        <div class="col-12"><?= empty($query12) ? '<h1>No se encontraron resultados</h1>' : '<h1>' . count($query12) . ' resultados encontrados</h1>' ?></div>
    </div>
</div>


<div class="container">
<div class="album py-5 bg-body-tertiary">
<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 ">
    
        <?php if (!empty($query12)) { ?>
            <?php foreach ($query12 as $event) { ?>
                <div class="col">
                    <div class="card shadow-sm">
                        <div style="max-height: 200px; max-width:200px;">
                            <img src="/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Files/Img_event/<?php echo $event['imagen'] ?>" class=" img-fluid img-thumbnail">
                        </div>
                        <div class="card-body">
                            <p class="card-text"><?php echo $event['name_event'] ?></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="html/EventoDetalle.php?id_evento=<?php echo $event['id']  ?>" class="btn btn-sm btn-outline-secondary">Ver Evento</a>
                                </div>
                                <small class="text-body-secondary"><?php echo "Fecha: " . date('d-m-Y', strtotime($event['start_at'])) ?></small>
                            </div>
                        </div>
                    </div>
                </div>

                <?php } ?>
            <?php } ?>
</div>

</div>
</div>




    

<script  defer src="/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassFrontEnd/js/Search.js" ></script>
<script src="../js/bootstrap.bundle.min.js"></script>
        </body>

    </html>