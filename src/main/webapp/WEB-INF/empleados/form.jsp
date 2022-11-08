
<%@page import="pe.com.ecosalud.beans.Pais"%>
<%@page import="pe.com.ecosalud.beans.TipoEmpleado"%>
<%@page import="pe.com.ecosalud.beans.Empleado"%>
<%@page import="pe.com.ecosalud.beans.Estado"%>
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.Direccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="tienda/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <%
        String tipoForm = (String) request.getAttribute("tipoForm");
        int idPais = 0;

        Empleado emp = null;
        if (tipoForm.equals("Actualizar")) {
            emp = (Empleado) request.getAttribute("empleado");
        }

        Direccion dir = null;
        String calle = "";
        int numero = 0;
        String distrito = "";
        String provincia = "";
        int codigopostal = 0;
        try {
            if (request.getAttribute("direccion") != null) {
                dir = (Direccion) request.getAttribute("direccion");
                calle = dir.getCalle();
                numero = dir.getNumero();
                distrito = dir.getDistrito();
                provincia = dir.getProvincia();
                codigopostal = dir.getCodigopostal();
            }
        } catch (Exception e) {
        }

        List<Estado> listEstados = null;
        if (request.getAttribute("estados") != null) {
            listEstados = (List<Estado>) request.getAttribute("estados");
        }

        List<TipoEmpleado> listTipoEmpleado = null;
        if (request.getAttribute("tipoempleado") != null) {
            listTipoEmpleado = (List<TipoEmpleado>) request.getAttribute("tipoempleado");
        }
        List<Pais> listPaises = null;
        if (request.getAttribute("paises") != null) {
            listPaises = (List<Pais>) request.getAttribute("paises");
        }
    %>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <h2>Información</h2>
                    <form action="empleados" method="post" enctype="multipart/form-data" class="form mt-5">

                        <input type="hidden" name="accion" value="ActualizarDatos">
                        <input type="hidden" name="idEmpleado" value="<%= emp.getIdPersona().getIdPersona()%>">

                        <!-- Nombre del empleado -->
                        <div class="form-group row">
                            <label for="nombreC" class="col-sm-2 col-form-label">Nombre: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="nombre" id="nombreC" placeholder="Nombre..." value="<%= emp.getIdPersona().getNombres()%>" required/>
                            </div>
                        </div>

                        <!-- Apellido paterno del empleado -->
                        <div class="form-group row">
                            <label for="apellidoP" class="col-sm-2 col-form-label">Apellido paterno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidop" id="apellidoP" placeholder="apellido paterno..." value="<%= emp.getIdPersona().getApellidop()%>" required/>
                            </div>
                        </div>

                        <!-- Apellido materno del empleado -->
                        <div class="form-group row">
                            <label for="apellidoM" class="col-sm-2 col-form-label">Apellido materno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidom" id="apellidoM" placeholder="apellido materno..." value="<%= emp.getIdPersona().getApellidom()%>" required/>
                            </div>
                        </div>

                        <!-- M F -->
                        <fieldset class="form-group">
                            <div class="row">
                                <legend class="col-form-label col-sm-2 pt-0">Sexo</legend>
                                <div class="col-sm-10">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios1" value="M" <% if (emp.getIdPersona().getSexo().equals("M")) {
                                                out.print("checked");
                                            } %>>
                                        <label class="form-check-label" for="gridRadios1">
                                            M
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios2" value="F" <% if (emp.getIdPersona().getSexo().equals("F"))
                                                out.print("checked");%>>
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
                                <input type="text"  name="telefono" id="telefonoC" placeholder="telefono..." value="<%= emp.getIdPersona().getTelefono()%>" required/>
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
                            <div class="col-sm-5">
                                <select class="custom-select" name="idTipoEmpleado">
                                    <%
                                        if (!tipoForm.equals("Actualizar")) {
                                    %>
                                    <option value="" selected="selected" disabled="true">Selecccione Privilegio</option>
                                    <%}%>

                                    <%  for (TipoEmpleado tp : listTipoEmpleado) {%>
                                    <option value="<%= tp.getIdTipoEmpleado()%>" <% if (tipoForm.equals("Actualizar") && tp.getIdTipoEmpleado() == emp.getIdTipoEmpleado().getIdTipoEmpleado()) {
                                            out.print("selected");
                                        }%> ><%= tp.getDescripcion()%></option>
                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <div class="clearfix">
                            <button class="btn-info" type="submit">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <h2>Cambiar Password: </h2>
                    <form class="form" action="empleados" method="post" enctype="multipart/form-data">

                        <input type="hidden" name="accion" value="ActualizarPassword">
                        <input type="hidden" name="idEmpleado" value="<%= emp.getIdPersona().getIdPersona()%>">

                        <div class="form-group row">
                            <label for="passb1" class="col-sm-2 col-form-label">Password: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="clave1" id="passb1" placeholder="Password..." value=""/>
                            </div>
                        </div>

                        <!-- Confirmar Password del empleado -->
                        <div class="form-group row">
                            <label for="passb2" class="col-sm-2 col-form-label">Confirmar Password: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="clave2" id="passb2" placeholder="Confirmar Password" value=""/>
                            </div>
                        </div>

                        <div class="clearfix">
                            <button class="btn-info" type="submit">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <h2>Direccion: </h2>
                    <form class="form" action="empleados" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="accion" value="ActualizarDireccion">
                        <input type="hidden" name="idEmpleado" value="<%= emp.getIdPersona().getIdPersona()%>">

                        <!-- calle del empleado -->
                        <div class="form-group row">
                            <label for="calleC" class="col-sm-2 col-form-label">Calle: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="calle" id="calleC" placeholder="calle..." value="<%= calle%>"/>
                            </div>
                        </div>
                        <!-- numero del empleado -->
                        <div class="form-group row">
                            <label for="numC" class="col-sm-2 col-form-label">Numero: </label>
                            <div class="col-sm-10">         
                                <input type="number"  name="numero" id="numC" placeholder="numero..." value="<%= numero%>"/>
                            </div>
                        </div>

                        <!-- distrito del empleado -->
                        <div class="form-group row">
                            <label for="disC" class="col-sm-2 col-form-label">Distrito: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="distrito" id="disC" placeholder="distrito..." value="<%= distrito%>"/>
                            </div>
                        </div>

                        <!-- provincia del empleado -->
                        <div class="form-group row">
                            <label for="provC" class="col-sm-2 col-form-label">Provincia: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="provincia" id="provC" placeholder="provincia..." value="<%= provincia%>"/>
                            </div>
                        </div>

                        <!-- codigopostal del empleado -->
                        <div class="form-group row">
                            <label for="postalC" class="col-sm-2 col-form-label">Codigo Postal: </label>
                            <div class="col-sm-10">         
                                <input type="number"  name="codigopostal" id="postalC" placeholder="codigo postal..." value="<%= codigopostal%>"/>
                            </div>
                        </div>

                        <!-- Pais -->
                        <div class="form-group row">
                            <label for="paisP" class="col-sm-2 col-form-label">Pais</label>
                            <div class="col-sm-5">
                                <select class="custom-select" name="idPais" id="capa" name="capa">
                                    <%
                                        if (!tipoForm.equals("Actualizar")) {
                                    %>
                                    <option value="" selected="selected" disabled="true">Selecccione Pais</option>
                                    <%}%>

                                    <%if (dir != null) {
                                            for (Pais pai : listPaises) {%>
                                    <option value="<%= pai.getIdPais()%>" <% if (tipoForm.equals("Actualizar") && pai.getIdPais() == dir.getIdEstado().getIdPais().getIdPais()) {
                                            out.print("selected");
                                            idPais = dir.getIdEstado().getIdPais().getIdPais();
                                        }%> ><%= pai.getNombre()%></option>
                                    <%}
                                    } else {%>


                                    <%for (Pais pai : listPaises) {%>
                                    <option value="<%= pai.getIdPais()%>"><%= pai.getNombre()%></option>
                                    <%}%>

                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <!-- Estados -->
                        <div class="form-group row">
                            <label for="categP" class="col-sm-2 col-form-label">Estado:</label>
                            <div class="col-sm-5">
                                <div id="mostrar">
                                    <%if (dir != null) {%>
                                    <select class="custom-select" name="idEstado">
                                        <%if (dir != null) {
                                            for (Estado est : listEstados) {
                                                if (est.getIdPais().getIdPais() == idPais) {%>
                                        <option value="<%= est.getIdEstado()%>" <% if (tipoForm.equals("Actualizar") && est.getIdEstado() == dir.getIdEstado().getIdEstado()) {
                                                out.print("selected");
                                            }%> ><%= est.getNombre()%></option>
                                        <%}
                                            }
                                        }%>
                                    </select> 
                                    <%} else {%>

                                    <%}%>
                                </div>
                            </div>
                        </div>

                        <div class="clearfix">
                            <button class="btn-info" type="submit">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
            <hr>
            <div class="row">
                <a href="empleados" class="btn btn-info" role="button">Volver</a>
            </div>
            <hr>
        </div>
    </body>
    <script src="tienda/js/jquery.js"></script>
    <script src="bootstrap/js/popper.min.js"></script>
    <script src="tienda/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).on('change', '#capa', function (event) {
            var idPais = $('#capa').val();
            $("#mostrar").load('empleados?accion=estado&idPais=' + idPais);
        });
    </script>
</html>
