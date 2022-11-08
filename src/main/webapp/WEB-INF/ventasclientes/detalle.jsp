<%@page import="pe.com.ecosalud.beans.DetalleVenta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle Venta</title>
    </head>
    <%
        List<DetalleVenta> listaDetalle = null;
        if (request.getAttribute("detalle") != null) {
            listaDetalle = (List<DetalleVenta>) request.getAttribute("detalle");
        }
    %>
    <body>
        <hr>
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <center><h4 class="modal-title" id="myModalLabel">Detalles</h4></center>
                    </div>
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="row">ID</th>
                                <th scope="col">Producto</th>
                                <th scope="col">Cantidad</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Importe</th>
                                <th scope="col">Descuento</th>
                                <th scope="col">Base</th>
                                <th scope="col">Impuesto</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (DetalleVenta v : listaDetalle) {%>
                            <tr>
                                <td><%= v.getIdDetalleVenta()%></td>
                                <td><%= v.getIdProducto().getNombre()%></td>
                                <td><%= v.getCantidad()%></td>
                                <td><%= v.getValorUnitario()%></td>
                                <td><%= v.getImporte()%></td>
                                <td><%= v.getDescuento()%></td>
                                <td><%= v.getBase()%></td>
                                <td><%= v.getImpuestos()%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
