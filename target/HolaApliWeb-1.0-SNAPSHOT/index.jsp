<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/x-icon" href="/assets/logo-vt.svg" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bootstrap Login Page</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
      crossorigin="anonymous"
    />
  </head>
  <body class="bg-info d-flex justify-content-center align-items-center vh-100">
        <form action="ServletAppDrogueria" method="post">
          <input type="hidden" name="action" value="ingresar">
          <!-- Otros campos del formulario -->
          <div class="bg-white p-5 rounded-5 text-secondary shadow" style="width: 25rem">
            <div class="d-flex justify-content-center" style="font-size: 5rem;">
              🌐
            </div>
            <div class="text-center fs-1 fw-bold">Login</div>
            <div class="input-group mt-4">
              <div class="input-group-text bg-info" style="color: white;">
                🧑‍
              </div>
              <input
                class="form-control bg-light"
                type="text"
                name="user"
                placeholder="Usuario"
              />
            </div>
            <div class="input-group mt-1">
              <div class="input-group-text bg-info">
                🔐
              </div>
              <input
                class="form-control bg-light"
                type="password"
                name="password"
                placeholder="Contraseña"
              />
            </div>
            <button type="submit" class="btn btn-info text-white w-100 mt-4 fw-semibold shadow-sm">
              Iniciar Sesión
            </button>
          </div>
        </form>
  </body>
</html>