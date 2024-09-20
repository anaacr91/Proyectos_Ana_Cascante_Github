let esEditar = false;
let Categorias = [];
const apiUrl = '/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassBackEnd/Controladores/AccionesCategoria.php';




   function guardarModificarCat() {
           const category = {     
            name_category: document.getElementById("name_category").value,
            action:'create'
        };

        $.post(`${apiUrl}`, {category}).done(function(response){
            console.log(response)
       
            let object = JSON.parse(response);

            if(object.status == 'success'){
                $.notify(object.message, "success");
               
            }else{
                $.notify(object.message, "error");
    
            }
        });
            
    }
    

