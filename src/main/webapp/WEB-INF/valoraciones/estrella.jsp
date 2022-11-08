<%@page import="pe.com.ecosalud.beans.Comentario"%>
<%@page import="java.util.List"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <link href="valoracion/css/font-awesome.min.css" rel="stylesheet">
        <script src="valoracion/js/valoraciones.min.js"></script>
    </head>

    <%
        List<Comentario> listComentario = null;
        if (request.getAttribute("comentarios") != null) {
            listComentario = (List<Comentario>) request.getAttribute("comentarios");
        }
    %>

    <body>
        <div class="container">

            <div class="row">
                <div class="col-sm-6">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <center><h4 class="modal-title" id="myModalLabel">Editar Valoracion</h4></center>
                    </div>

                    <div class="panel-heading">
                        <h3 class="panel-title">Comentarios:</h3>
                    </div>
                    <div class="panel-body" style="width: 560px; height: 200px; overflow-y: scroll;">

                        <div class="post"  >
                            <% for (Comentario comen : listComentario) {%>
                            <a href="#" class="nombre"><%= comen.getIdPersona().getPerfil()%> <%= comen.getFecha()%></a>
                            <p class="texto"><%= comen.getDescripcion()%></p>
                            <%}%>
                        </div>

                    </div>

                    <form class="form" action="estrellas" method="POST">

                        <div class="form-group">
                            <div id="puntos_1" >
                                <i class="fa fa-star-o estrella" style="margin-left: 2px; cursor: pointer;" star="0"></i>
                                <i class="fa fa-star-o estrella" style="margin-left: 2px; cursor: pointer;" star="1"></i>
                                <i class="fa fa-star-o estrella" style="margin-left: 2px; cursor: pointer;" star="2"></i>
                                <i class="fa fa-star-o estrella" style="margin-left: 2px; cursor: pointer;" star="3"></i>
                                <i class="fa fa-star-o estrella" style="margin-left: 2px; cursor: pointer;" star="4"></i>
                            </div>
                        </div>

                        <div class="form-group">
                            <input type="hidden" name="accion" value="estrella">
                            <input type="hidden" name="califi" class="valor5" value=0>
                            <input type="hidden" name="idProducto"  value="<c:out value = "${idProducto}"/>">
                            <input type="hidden" name="idCliente"  value="<c:out value = "${idCliente}"/>">
                            <button type="submit" class="btn-default" >Calificar</button>
                        </div>

                    </form>

                    <form class="form" action="estrellas" method="POST">

                        <div class="form-group">
                            <label for="comen">Comentar</label>
                            <textarea class="form-control" id="comen" name="comentario" rows="2" style="min-width: 1%" required></textarea>
                        </div>

                        <div class="form-group">
                            <input type="hidden" name="accion" value="comentario">
                            <input type="hidden" name="idProducto"  value="<c:out value = "${idProducto}"/>">
                            <input type="hidden" name="idCliente"  value="<c:out value = "${idCliente}"/>">
                            <button type="submit" class="btn-default" >Enviar</button>
                        </div>

                    </form>
                </div>
            </div>

        </div>

        <script>
            $(function () {

                muestra_valor1 = function (datos) {
                    $("input.valor5").val(datos.valor);
                }

                $('#puntos_1').valoraciones({star_ini: <c:out value = "${valor}"/>, callback: muestra_valor1});

            });
        </script>
    </body>

</html>