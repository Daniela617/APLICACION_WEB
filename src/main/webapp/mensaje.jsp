<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <title>Error</title>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .error-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #ffffff;
            border: 1px solid #ced4da;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .error-title {
            font-size: 24px;
            color: #dc3545;
        }
        .error-emoji {
            font-size: 48px;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 16px;
            color: #dc3545;
        }
        .btn-accept {
            margin-top: 40px;
            background-color: #dc3545;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .btn-accept:hover {
            background-color: #c82333;
        }
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
            height: 11vh;/* Cambia la altura segÃºn tus preferencias */
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px;
            border-bottom: 5px solid #2e76a4;
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
            <h1 class="titulo">Mensaje Error</h1>
        </div>
    </div>
    <div class="error-container p-3">
        <div class="error-title">Algo ha salido mal</div>
        <div class="error-emoji">❌</div>
        <div class="error-message">Se tienen que completar todos los campos.</div>
         <a href="<%=request.getContextPath()%>/new">
            <button class="btn-accept" >Aceptar</button>
         </a>
        
    </div>
</body>
</html>