
<%@page import="pe.com.ecosalud.beans.Venta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras</title>
        <%@include file="../estilos.jsp" %>
    </head>
    <%
        List<Venta> listaVentas = null;
        if (request.getAttribute("ventas") != null) {
            listaVentas = (List<Venta>) request.getAttribute("ventas");
        }

    %>
    <body>
        <hr>
        <div class="container">
            <div class="row">
                <a href="personas?accion=editar" class="btn btn-info" role="button">Regresar</a>
            </div>
            <div class="row">
                <div class="col">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="row">ID</th>
                                <th scope="col">Fecha</th>
                                <th scope="col">Total Descuento</th>
                                <th scope="col">Total Impuesto</th>
                                <th scope="col">Total Envio</th>
                                <th scope="col">Total Importe</th>
                                <th scope="col">Total</th>
                                <th scope="col">Estado</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Venta v : listaVentas) {%>
                            <tr>

                                <td><%= v.getIdVenta()%></td>
                                <td><%= v.getFecha()%></td>
                                <td><%= v.getTotalDescuento()%></td>
                                <td><%= v.getIgv()%></td>
                                <td><%= v.getCostoEnvio()%></td>
                                <td><%= v.getTotalImporte()%></td>
                                <td><%= v.getTotalNeto()%></td>
                                <td><%= v.getEstado()%></td>
                                <td><a href="#" data-value="<%= v.getIdVenta()%>" class="btn btn-info edit2" role="button">Detalle</a></td>
                                <td><a href="reporte?accion=imprimirPDF&idVenta=<%= v.getIdVenta() %>" class="btn btn-info" role="button">Imprimir</a></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="modal fade" id="edit2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body2" >
                        <!-- code -->
                    </div>  
                </div>
            </div>
        </div>
        <%@include file="../script.jsp" %>

        <script>
            $(document).ready(function () {
                $(document).on('click', '.edit2', function () {
                    var idVenta = $(this).data('value');
                    $('.modal-body2').load('ventas?accion=detalleVentaCliente&idVenta=' + idVenta, function () {
                        $('#edit2').modal('show');
                    });
                });
            });
        </script>
    </body>
</html>
