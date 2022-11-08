<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VALORACION</title>
    </head>
    <%
        int idCliente = 0;
        String valoracion = "false";
        try {
            if (session.getAttribute("idCliente").toString() != null) {
                idCliente = Integer.parseInt(session.getAttribute("idCliente").toString());
            }
        } catch (Exception e) {
            System.out.println("NULL idCliente " + e.getMessage());
        }
        try {
            if (request.getAttribute("valoracion") != null) {
                valoracion = request.getAttribute("valoracion").toString();
            }
        } catch (Exception e) {
        }
        System.out.println("BOOLEAN VALORACION " + valoracion);
    %>
    <body>

        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="pr-2 pt-1">                         
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span>
                        </button>                    
                    </div>
                    <div class="modal-header text-center">   
                        <ul class="nav nav-pills">
                            <li class="active"><a data-toggle="pill" href="#home">Iniciar Sesión</a></li>
                                <% if (idCliente == 0) {%>
                            <li><a data-toggle="pill" href="#menu1">Registrarse</a></li>
                                <%}%>
                        </ul>
                    </div>

                    <div class="modal-body"> 
                        <div class="tab-content">
                            <div id="home" class="tab-pane fade in active">
                                <form action="logins" method="post" class="form">
                                    <!-- email del cliente -->
                                    <div class="form-group row">
                                        <label for="emaC" class="col-sm-2 col-form-label">Nombre: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="email" id="emaC" placeholder="email..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- Password del cliente -->
                                    <div class="form-group row">
                                        <label for="passb1C" class="col-sm-2 col-form-label">Password: </label>
                                        <div class="col-sm-10">         
                                            <input type="password"  name="password" id="passb1C" placeholder="Password..." value=""/>
                                        </div>
                                    </div>

                                    <% if (valoracion.equals("true")) { %>
                                    <input type="hidden" name="valoracion" value="true">
                                    <%}%>
                                    <input type="hidden" name="idProducto" value="<% out.print(request.getParameter("idProducto"));%>">
                                    <input type="hidden" name="accion" value="validarUsuario">

                                    <!-- comment -->
                                    <div>
                                        <button class="btn-default" type="submit">LoginBB</button>
                                    </div>
                                </form>
                            </div>
                            <div id="menu1" class="tab-pane fade">
                                <form action="personas" method="post" enctype="multipart/form-data" class="form">

                                    <input type="hidden" name="accion" value="crearCliente">

                                    <!-- Nombre del cliente -->
                                    <div class="form-group row">
                                        <label for="nombreC" class="col-sm-2 col-form-label">Nombre: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="nombre" id="nombreC" placeholder="Nombre..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- Apellido paterno del cliente -->
                                    <div class="form-group row">
                                        <label for="apellidoP" class="col-sm-2 col-form-label">Apellido paterno: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="apellidop" id="apellidoP" placeholder="apellido paterno..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- Apellido materno del cliente -->
                                    <div class="form-group row">
                                        <label for="apellidoM" class="col-sm-2 col-form-label">Apellido materno: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="apellidom" id="apellidoM" placeholder="apellido materno..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- M F -->
                                    <fieldset class="form-group">
                                        <div class="row">
                                            <legend class="col-form-label col-sm-2 pt-0">Radios</legend>
                                            <div class="col-sm-10">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="sexo" id="gridRadios1" value="M" checked>
                                                    <label class="form-check-label" for="gridRadios1">
                                                        M
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="sexo" id="gridRadios2" value="F">
                                                    <label class="form-check-label" for="gridRadios2">
                                                        F
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <!-- telefono del cliente -->
                                    <div class="form-group row">
                                        <label for="telefonoC" class="col-sm-2 col-form-label">Telefono: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="telefono" id="telefonoC" placeholder="telefono..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- Email -->
                                    <div class="form-group row">
                                        <label for="emailC" class="col-sm-2 col-form-label">Email: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="email" id="emailC" placeholder="email..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- foto del cliente -->
                                    <div class="form-group row">
                                        <label for="imagenC" class="col-sm-2 col-form-label">Seleccionar imagen...</label>
                                        <div class="col-sm-10">  
                                            <input type="file" id="imagenC"  name="foto" value=""/>
                                        </div>
                                    </div>

                                    <!-- Password del cliente -->
                                    <div class="form-group row">
                                        <label for="passb1" class="col-sm-2 col-form-label">Password: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="clave1" id="passb1" placeholder="Password..." value="" required/>
                                        </div>
                                    </div>

                                    <!-- Confirmar Password del cliente -->
                                    <div class="form-group row">
                                        <label for="passb2" class="col-sm-2 col-form-label">Confirmar Password: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="clave2" id="passb2" placeholder="Confirmar Password" value="" required/>
                                        </div>
                                    </div>

                                    <!-- Perfil del cliente -->
                                    <div class="form-group row">
                                        <label for="perfilC" class="col-sm-2 col-form-label">Perfil: </label>
                                        <div class="col-sm-10">         
                                            <input type="text"  name="perfil" id="perfilC" placeholder="Perfil..." value=""/>
                                        </div>
                                    </div>

                                    <div class="clearfix">
                                        <button class="btn-default" type="submit">Registrar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
