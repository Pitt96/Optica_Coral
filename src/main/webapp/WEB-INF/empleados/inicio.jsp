
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>INICIO</title>
        <link href="tienda/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <hr>
        <div class="container">
            <div class="row">
                <div class="col">
                    <a href="admin?accion=crudempleado" class="btn btn-info" role="button">CRUD Empleados</a>
                    <a href="admin?accion=crudproducto" class="btn btn-info" role="button">CRUD Productos</a>
                    <a href="admin?accion=ventas" class="btn btn-info" role="button">Ventas</a>
                    <a href="admin?accion=home" class="btn btn-info" role="button">Tienda</a>
                    <a href="admin?accion=cerrarSession" class="btn btn-info" role="button">Cerrar Sesion</a>
                </div>
            </div>
        </div>
    </body>
    
    <script src="tienda/js/jquery.js"></script>
    <script src="bootstrap/js/popper.min.js"></script>
    <script src="tienda/js/bootstrap.min.js"></script>
    
</html>
