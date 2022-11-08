
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login ADMIN</title>
        <link href="tienda/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <hr>
            <div class="row ">
                <div class="col-md-12">
                    <form action="admin" method="post" class="form">
                        <!-- email del cliente -->
                        <div class="form-group row">
                            <label for="emaC" class="col-sm-2 col-form-label">Correo: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="email" id="emaC" placeholder="email..." value="" required/>
                            </div>
                        </div>

                        <!-- Password del cliente -->
                        <div class="form-group row">
                            <label for="passb1C" class="col-sm-2 col-form-label">Password: </label>
                            <div class="col-sm-10">         
                                <input type="password"  name="password" id="passb1C" placeholder="Password..." value="" required/>
                            </div>
                        </div>

                        <input type="hidden" name="accion" value="validarUsuario">

                        <!-- comment -->
                        <div>
                            <button class="btn-info" type="submit">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script src="tienda/js/jquery.js"></script>
    <script src="bootstrap/js/popper.min.js"></script>
    <script src="tienda/js/bootstrap.min.js"></script>
</html>
