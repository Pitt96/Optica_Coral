
<%@page import="pe.com.ecosalud.beans.Pais"%>
<%@page import="pe.com.ecosalud.beans.Estado"%>
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.Direccion"%>
<%@page import="pe.com.ecosalud.beans.Cliente"%>
<%@page import="pe.com.ecosalud.beans.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CRUD Cliente</title>
        <%@include file="../estilos.jsp" %>
    </head>
    <%
        String tipoForm = (String) request.getAttribute("tipoForm");
        int idPais = 0;

        Persona per = null;
        if (tipoForm.equals("Actualizar")) {
            per = (Persona) request.getAttribute("persona");
        }

        Cliente cli = null;
        if (tipoForm.equals("Actualizar")) {
            cli = (Cliente) request.getAttribute("cliente");
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
                System.out.println("ESTADO.. " + dir.getIdEstado().getIdEstado());
            }
        } catch (Exception e) {
        }

        List<Estado> listEstados = null;
        if (request.getAttribute("estados") != null) {
            listEstados = (List<Estado>) request.getAttribute("estados");
        }
        List<Pais> listPaises = null;
        if (request.getAttribute("paises") != null) {
            listPaises = (List<Pais>) request.getAttribute("paises");
        }

    %>

    <body>
        <div class="container">
            <hr>
            <div class="row">
                <label><% if (request.getAttribute("mensaje") != null)
                        out.print(request.getAttribute("mensaje").toString());%></label>
            </div>
            <hr>
            <div class="row">
                <dic class="col">
                    <a href="home" class="btn btn-info" role="button">Inicio</a>
                    <a href="ventas?accion=verCompras" class="btn btn-info" role="button">Mis compras</a>
                    <a href="logins?accion=cerrarSesion" class="btn btn-info" role="button">Cerrar Sesion</a>
                </dic>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <h2>Información</h2>
                    <form action="personas" method="post" enctype="multipart/form-data" class="form">

                        <input type="hidden" name="accion" value="ActualizarDatos">

                        <!-- Nombre del cliente -->
                        <div class="form-group row">
                            <label for="nombreC" class="col-sm-2 col-form-label">Nombre: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="nombre" id="nombreC" placeholder="Nombre..." value="<%= per.getNombres()%>" required/>
                            </div>
                        </div>

                        <!-- Apellido paterno del cliente -->
                        <div class="form-group row">
                            <label for="apellidoP" class="col-sm-2 col-form-label">Apellido paterno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidop" id="apellidoP" placeholder="apellido paterno..." value="<%= per.getApellidop()%>" required/>
                            </div>
                        </div>

                        <!-- Apellido materno del cliente -->
                        <div class="form-group row">
                            <label for="apellidoM" class="col-sm-2 col-form-label">Apellido materno: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="apellidom" id="apellidoM" placeholder="apellido materno..." value="<%= per.getApellidom()%>" required/>
                            </div>
                        </div>

                        <!-- M F -->
                        <fieldset class="form-group">
                            <div class="row">
                                <legend class="col-form-label col-sm-2 pt-0">Sexo</legend>
                                <div class="col-sm-10">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios1" value="M" <% if (per.getSexo().equals("M")) {
                                                out.print("checked");
                                            } %>>
                                        <label class="form-check-label" for="gridRadios1">
                                            M
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="sexo" id="gridRadios2" value="F" <% if (per.getSexo().equals("F"))
                                                out.print("checked");%>>
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
                                <input type="text"  name="telefono" id="telefonoC" placeholder="telefono..." value="<%= per.getTelefono()%>" required/>
                            </div>
                        </div>

                        <!-- foto del cliente -->
                        <div class="form-group row">
                            <label for="imagenC" class="col-sm-2 col-form-label">Seleccionar imagen...</label>
                            <div class="col-sm-10">  
                                <input type="file" id="imagenC"  name="foto" value=""/>
                            </div>
                        </div>

                        <!-- Perfil del cliente -->
                        <div class="form-group row">
                            <label for="perfilC" class="col-sm-2 col-form-label">Perfil: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="perfil" id="perfilC" placeholder="Perfil..." value="<%= cli.getPerfil()%>" required/>
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
                    <form class="form" action="personas" method="post" enctype="multipart/form-data">

                        <input type="hidden" name="accion" value="ActualizarPassword">
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
                    <form class="form" action="personas" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="accion" value="ActualizarDireccion">

                        <!-- calle del cliente -->
                        <div class="form-group row">
                            <label for="calleC" class="col-sm-2 col-form-label">Calle: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="calle" id="calleC" placeholder="calle..." value="<%= calle%>"/>
                            </div>
                        </div>
                        <!-- numero del cliente -->
                        <div class="form-group row">
                            <label for="numC" class="col-sm-2 col-form-label">Numero: </label>
                            <div class="col-sm-10">         
                                <input type="number"  name="numero" id="numC" placeholder="numero..." value="<%= numero%>" required/>
                            </div>
                        </div>

                        <!-- distrito del cliente -->
                        <div class="form-group row">
                            <label for="disC" class="col-sm-2 col-form-label">Distrito: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="distrito" id="disC" placeholder="distrito..." value="<%= distrito%>"/>
                            </div>
                        </div>

                        <!-- provincia del cliente -->
                        <div class="form-group row">
                            <label for="provC" class="col-sm-2 col-form-label">Provincia: </label>
                            <div class="col-sm-10">         
                                <input type="text"  name="provincia" id="provC" placeholder="provincia..." value="<%= provincia%>"/>
                            </div>
                        </div>

                        <!-- codigopostal del cliente -->
                        <div class="form-group row">
                            <label for="postalC" class="col-sm-2 col-form-label">Codigo Postal: </label>
                            <div class="col-sm-10">         
                                <input type="number"  name="codigopostal" id="postalC" placeholder="codigo postal..." value="<%= codigopostal%>" required/>
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
        </div>

    </body>

    <%@include file="../script.jsp" %>


</div>

<script type="text/javascript">

    $(document).on('change', '#capa', function (event) {
        var idPais = $('#capa').val();
        $("#mostrar").load('personas?accion=estado&idPais=' + idPais);
    });
</script>

</html>
