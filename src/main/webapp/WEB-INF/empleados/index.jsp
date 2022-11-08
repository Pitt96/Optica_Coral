
<%@page import="pe.com.ecosalud.beans.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="../estilos.jsp" %>
    </head>
    <body>
        <%
            List<Empleado> listarEmpleados = null;
            if (request.getAttribute("empleados") != null) {
                listarEmpleados = (List<Empleado>) request.getAttribute("empleados");
            } else {
                System.out.println("EMPLEADO NULL");
            }

        %>
        <div class="container">
            <div class="row">
                <div class="col">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="row">ID Empleado</th>
                                <th scope="col">Nombres</th>
                                <th scope="col">Apellido P</th>
                                <th scope="col">Apellido M</th>
                                <th scope="col">Foto</th>
                                <th scope="col">Tipo Empleado</th>
                                <th scope="col">Editar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Empleado emp : listarEmpleados) {%>
                            <tr>
                                <td><%= emp.getIdPersona().getIdPersona()%></td>
                                <td><%= emp.getIdPersona().getNombres()%></td>
                                <td><%= emp.getIdPersona().getApellidop()%></td>
                                <td><%= emp.getIdPersona().getApellidom()%></td>
                                <td><img src="imagenes/empleados/<%= emp.getIdPersona().getFoto()%>" width="50" height="50"></td>
                                <td><%= emp.getIdTipoEmpleado().getDescripcion()%></td>
                                <td><a href="empleados?accion=editar&idEmpleado=<%= emp.getIdPersona().getIdPersona()%>">Editar</a></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <a href="#" class="edit2 btn btn-info">Nuevo</a>
                <a href="empleados?accion=inicio" class="btn btn-info">Regresar</a>
            </div>
        </div>

        <!-- Edit Modal para Valoraciones-->
        <div class="modal fade" id="edit2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <center><h4 class="modal-title" id="myModalLabel">Nuevo empleado</h4></center>
                    </div>
                    <div class="modal-body2" >
                        <!-- code -->
                    </div>
                    <div class="modal-header">
                        <hr>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <%@include file="../script.jsp" %>

    <script>
        $(document).ready(function () {
            $(document).on('click', '.edit2', function () {
                $('.modal-body2').load('empleados?accion=nuevo', function () {
                    $('#edit2').modal('show');
                });
            });
        });
    </script>
</html>
