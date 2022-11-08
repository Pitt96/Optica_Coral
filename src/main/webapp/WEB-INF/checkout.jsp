<%@page import="pe.com.ecosalud.beans.Envio"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Pago</title>
    </head>
    <%@include file="estilos.jsp" %>
    <script src="https://www.paypalobjects.com/api/checkout.js"></script>
    <%
        Envio env = null;
        if (request.getAttribute("envio") != null) {
            env = (Envio) request.getAttribute("envio");
        }
    %>
    <body>
        <div class="container ">
            <hr>
            <div class="row ">
                <div class="col">
                    <a class="btn btn-info" href="cart">Seguir comprando</a>
                </div>
                <div class="col-sm-8 justify-content-center">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="row">idProducto</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Precio</th>
                                <th scope="col">IGV</th>
                                <th scope="col">Cantidad</th>
                                <th scope="col">Descuento</th>
                                <th scope="col">Importe</th>
                                <th scope="col">Impuesto</th>
                                <th scope="col">Suma</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${sessionScope.cart}" var="pro">
                                <tr>
                                    <td>${pro.p.idProducto}</td>
                                    <td>${pro.p.nombre}</td>
                                    <td>${pro.p.precio}</td>
                                    <td>${pro.p.igv}</td>
                                    <td>${pro.cantidad}</td>
                                    <td>${pro.p.precionuevo}</td>
                                    <td>${pro.getImporte()}</td>
                                    <td>${pro.getImpuesto()}</td>
                                </tr>
                            </c:forEach>

                            <tr>
                                <td colspan="5"></td>
                                <td><%= request.getAttribute("sumaDescuento")%></td>
                                <td><%= request.getAttribute("sumaImporte")%></td>
                                <td><%= request.getAttribute("sumaImpuesto")%></td>
                                <th> = <%= request.getAttribute("total1")%></th>
                            </tr>
                            <tr>
                                <td colspan="8" align="right">Envio (<%= env.getIdPais().getNombre()%>)</td>
                                <th> = <%= request.getAttribute("sumaEnvio")%></th>
                            </tr>
                            <tr>
                                <td colspan="8" align="right">Total a Pagar</td>
                                <th> = <%= request.getAttribute("total")%> USD</th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="title">Pagar usando PayPal</div>
                    <form class="form" action="${initParam["urlpaypal"]}" method="post" target="_top">
                        <input type="hidden" name="business" value="tdavidc_999-999@hotmail.com" />
                        <input type="hidden" name="return" value="${initParam["urlretorno"]}" />
                        <input type="hidden" name="cmd" value="_cart" />
                        <input type="hidden" name="upload" value="1" />
                        <input type="hidden" name="currency_code" value="USD" />
                        <input type="hidden" name="shipping_1" value="<%= request.getAttribute("sumaEnvio")%>">
                        <c:forEach items="${sessionScope.cart}" var="pro">
                            <c:set var="c" value="${c + 1}"/>
                            <input type="hidden" name="item_name_${c}" value="${pro.p.nombre}" />
                            <input type="hidden" name="item_number_${c}" value="${pro.p.idProducto}" />
                            <input type="hidden" name="amount_${c}" value="${pro.p.precio}" />
                            <input type="hidden" name="quantity_${c}" value="${pro.cantidad}" />
                            <input type="hidden" name="discount_amount_${c}" value="${pro.p.precionuevo}" />
                            <input type="hidden" name="tax_rate_${c}" value="${pro.p.igv}" />

                        </c:forEach>

                        <input type="image" src="https://www.paypal.com/en_US/i/btn/btn_paynowCC_LG.gif" border="0" name="submit" alt="Paypal, la forma rápida Y SEGURA DE PAGAR EN Internet"/>
                        <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

