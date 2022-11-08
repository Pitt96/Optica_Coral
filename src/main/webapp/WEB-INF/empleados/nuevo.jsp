
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.TipoEmpleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo</title>

    </head>
    <%
        List<TipoEmpleado> listTipoEmpleado = null;
        if (request.getAttribute("tipoempleado") != null) {
            listTipoEmpleado = (List<TipoEmpleado>) request.getAttribute("tipoempleado");
        }
    %>
    <body>
        <div class="container">
            <div class="row-col-8">
                <div class="col">
                    <h2>Información</h2>
                    <form action="empleados" method="post" enctype="multipart/form-data" class="form">

                        <input type="hidden" name="accion" value="nuevo">

                        <!-- Nombre del empleado -->
                        <div class="form-group row">
                            <label for="nombreC" class="col-sm-2 col-form-label">Nombre: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="nombre" id="nombreC" placeholder="Nombre..." value="" required/>
                            </div>
                        </div>

                        <!-- Apellido paterno del empleado -->
                        <div class="form-group row">
                            <label for="apellidoP" class="col-sm-2 col-form-label">Apellido paterno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidop" id="apellidoP" placeholder="apellido paterno..." value="" required/>
                            </div>
                        </div>

                        <!-- Apellido materno del empleado -->
                        <div class="form-group row">
                            <label for="apellidoM" class="col-sm-2 col-form-label">Apellido materno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidom" id="apellidoM" placeholder="apellido materno..." value="" required/>
                            </div>
                        </div>

                        <!-- M F -->
                        <fieldset class="form-group">
                            <div class="row">
                                <legend class="col-form-label col-sm-2 pt-0">Sexo</legend>
                                <div class="col-sm-10">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios1" value="M" checked/>
                                        <label class="form-check-label" for="gridRadios1">
                                            M
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios2" value="F"/>
                                        <label class="form-check-label" for="gridRadios2">
                                            F
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <!-- telefono del empleado -->
                        <div class="form-group row">
                            <label for="telefonoC" class="col-sm-2 col-form-label">Telefono: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="telefono" id="telefonoC" placeholder="telefono..." value="" required/>
                            </div>
                        </div>

                        <!-- foto del empleado -->
                        <div class="form-group row">
                            <label for="imagenC" class="col-sm-2 col-form-label">Seleccionar imagen...</label>
                            <div class="col-sm-10">  
                                <input type="file" id="imagenC"  name="foto" value=""/>
                            </div>
                        </div>

                        <!-- Tipo Empleado -->
                        <div class="form-group row">
                            <label for="privilE" class="col-sm-2 col-form-label">Privilegio:</label>
                            <div class="col-sm-3">
                                <select class="custom-select" name="idTipoEmpleado">

                                    <%  for (TipoEmpleado tp : listTipoEmpleado) {%>
                                    <option value="<%= tp.getIdTipoEmpleado()%>"><%= tp.getDescripcion()%></option>
                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <!-- Email -->
                        <div class="form-group row">
                            <label for="emailC" class="col-sm-2 col-form-label">Email: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="email" id="emailC" placeholder="email..." value="" required/>
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
                                <input type="text"  name="clave2" id="passb2" placeholder="Confirmar Password" value=""required/>
                            </div>
                        </div>

                        <div class="clearfix">
                            <button class="btn-info" type="submit">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
