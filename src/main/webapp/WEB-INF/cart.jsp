<%@page import="pe.com.ecosalud.beans.Login"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Carrito</title>

        <!-- CSS -->
        <%@include file="estilos.jsp" %>
        <!-- Fin CSS -->

    </head><!--/head-->

    <%
        Login login = null;
        HttpSession misession = (HttpSession) request.getSession();
        try {
            if (session.getAttribute("login").toString() != null) {
                login = (Login) misession.getAttribute("login");
                System.out.println("EXISTE SESION LOGIN" + login.getIdPersona());
            }
        } catch (Exception e) {
            System.out.println("NULL SESION LOGIN " + e.getMessage());
        }
    %>
    <body>

        <!--/header-->
        <%@include file="header.jsp" %>
        <!--/Fin header-->


        <hr/><!--confianza-->
        <p align="center">                                     
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza2.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza3.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza4.png">
        </p><!--/confianza-->
        <hr/>

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Carrito - Precio en USD</a></li>
                        <li class="active">Lista de productos</li>
                    </ol>
                </div>
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Producto</td>
                                <td class="description"></td>
                                <td class="price">Precio</td>
                                <td class="quantity">Cantidad</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>

                        <!-- productos ADD CART-->
                        <c:forEach items="${sessionScope.cart}" var="pro">
                            <c:set var="total" value="${total + pro.p.precio * pro.cantidad}"/>
                            <tr>
                                <td class="cart_product">
                                    <a href="#"><img src="imagenes/productos/imagen/${pro.p.imagen}"  width="110px" alt=""></a>
                                </td>
                                <td class="cart_description">
                                    <h4><a href="#">${pro.p.nombre}</a></h4>
                                    <p>ID Referencia Web: ${pro.p.idProducto}</p>
                                </td>
                                <td class="cart_price">
                                    <p>${pro.p.precio}</p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" href="?accion=mas&idProducto=${pro.p.idProducto}"> + </a>
                                        <input class="cart_quantity_input" type="text" name="quantity" value="${pro.cantidad}" autocomplete="off" size="2">
                                        <a class="cart_quantity_down" href="?accion=menos&idProducto=${pro.p.idProducto}"> - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p id="precio_1" class="cart_total_price">
                                        <fmt:setLocale value="en_US"></fmt:setLocale>
                                        <fmt:formatNumber value="${pro.p.precio * pro.cantidad}" type="currency" ></fmt:formatNumber>

                                        </p>
                                    </td>
                                    <td class="cart_delete">
                                        <a class="cart_quantity_delete" href="?accion=delete&idProducto=${pro.p.idProducto}"><i class="fa fa-times"></i></a>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </div>
            </div>
        </section> <!--/#cart_items-->

        <section id="do_action">
            <div class="container">
                <div class="heading">
                    <h3>¿Qué te gustaría hacer ahora?</h3>
                    <p>Te gustaría pagar o seguir comprando?</p>
                </div>
                <div class="row">

                    <div class="col-sm-10">
                        <div class="total_area">
                            <ul>
                                <li>Sub Total <span>${total}</span></li>
                                <li>Tax/IVA(18%) <span>${total * 0.18}</span></li>
                                <li><h3>Total <span>${total + (total * 0.18)} USD</span></h3></li>
                            </ul>
                            <a class="btn btn-default update" href="home">Seguir comprando</a>
                            <a class="btn btn-default update" href="cart?accion=comprar">Realizar Pago</a>
                            
                        </div>
                    </div>
                </div>
            </div>
        </section><!--/#do_action-->

        <!--/Footer-->
        <%@include file="footer.jsp" %>
        <!--/Fin Footer-->

        <!--/Script-->
        <%@include file="script.jsp" %>
        <!--/Fin Script-->
    </body>
</html>
