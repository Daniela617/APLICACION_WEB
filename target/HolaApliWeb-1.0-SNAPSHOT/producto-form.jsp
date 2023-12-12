<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Crud mysql</title>
    <style>
        .btn-eliminar{
            background-color: #c72f2faa; /* Color rojo */
            color: white;
        }
        .btn-editar {
            background-color: #369cdd; /* Color verde */
            color: white;
        }
        .cabecera {
            background-color: #369cdd;
            width: 100%;
            height: 11vh;/* Cambia la altura según tus preferencias */
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px;
            border-bottom: 5px solid #2e76a4;
        }
        .icono-izquierda {
            width: auto; /* Ancho de la imagen */
            height: 100%; /* Altura de la imagen */
        }
        .custom-btn {
            width: 100%; /* Ocupa el 100% del ancho de su contenedor */
        }
        .boton-derecha{ 
            background-color: #2e76a4;
            color: #F8F8F8;
            margin-right: 3%;
            font-size:large;
            width: 90px;
        }
        .boton-derecha:hover{
            transform: translate(-5px,-5px);
            transition: all .3s;
        }
        .btn-editar {
            background-color: #369cdd; /* Color verde */
            color: white;
        }
        .btn-editar:hover{
            transform: scale(0.9);
            transition: all .3s;
        }

        .btn-eliminar{
            background-color: #c72f2faa; /* Color rojo */
            color: white;
        }
        .btn-eliminar:hover {
            transform: scale(0.9);
            transition: all .3s;
        }
        .separador {
        height: 50px; 
        width: 2px;
        background-color: #F8F8F8; 
        margin: 0 10px;
        }
        .titulo-pagina {
        flex: 1;
        padding-left: 10px;
        }
        .titulo-pagina h1{
            color: #F8F8F8;
        }
        /*controlar responsive*/
        @media (max-width: 768px) {
            .titulo {
                font-size: 24px; 
            }
        }


    </style>

</head>
<body>
    <div class="cabecera">
       
        <div class="separador"></div>
        <div class="titulo-pagina">
            <h1 class="titulo">Crear producto</h1>
        </div>
       
    </div>
    <div class="container p-3">
                
            <div class="card card-body" style="border: 2px solid #369cdd;">
                <form >
                    <div class="form-group mb-3">
                        <label for="Precio compra">Nombre del producto</label>
                        <input type="text" name="nombre" class="form-control" placeholder="Nombre producto" autofocus>
                    </div>
                    <div class="form-group mb-3">
                        <label for="Precio compra">Precio de venta</label>
                        <input type="text" name="precioPublico" row = '2' class="form-control" placeholder="Precio público" autofocus>
                    </div>
                    <div class="form-group mb-3">
                        <label for="Precio compra">Precio de compra</label>
                        <input type="text" name="precioCompra" row = '3' class="form-control" placeholder="Precio compra" autofocus>
                    </div>
                    <div class="form-group mb-3">
                        <label for="fecha">Fecha de vencimiento</label>
                        <input type="date" id="fecha" row = '4' name="fecha" class="form-control">
                    </div>
                    <div class="form-group mb-3">
                        <label for="Precio compra">Cantidad de existencias</label>
                        <input type="text" name="cantidad" row = '5' class="form-control" placeholder="Cantidad" autofocus>
                    </div>
                    <div class="form-group mb-3">
                        <label for="Precio compra">Laboratorio</label>
                        <input type="text" name="laboratorio" row = '6' class="form-control" placeholder="Laboratorio" autofocus>
                    </div>
                    <div class="btn-group">
                        <input type="submit" class="btn btn-success custom-btn" name="save_product" value="Guardar">
                        <a href="list_products.php" class="btn btn-secondary ml-2">Cancelar</a>
                    </div>
                </form>
        </div>

    </div>
   </body>
</html>
