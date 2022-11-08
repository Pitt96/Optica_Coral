<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles</title>
        <%@include file="../estilos.jsp" %>
    </head>

    <body>

        <div class="container">
            <hr><!-- comment -->
            <a href="home" class="btn btn-info" role="button">Inicio</a>
            <hr>
            <c:set var="p" value='${requestScope["producto"]}'></c:set>
                <div class="row">              
                    <div class="col-sm-2">Nombre:</div>
                    <div class="col-sm-2">
                    <c:out value="${p.nombre}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-2">Descripcion:</div>
                <div class="col-sm-12">
                    <c:out value="${p.descripcion}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-2">Precio:</div>
                <div class="col-sm-2">
                    <c:out value="${p.precio}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-2">Descuento:</div>
                <div class="col-sm-2">
                    <c:out value="${p.precionuevo}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-6">
                    <div class="figure">
                        <img src="imagenes/productos/imagen/<c:out value="${p.imagen}"/>"
                             alt=""
                             ">
                    </div>
                </div>
                <div class="col-sm-6">
                    <video controls width="320" height="240">
                        <source src="imagenes/productos/video/<c:out value="${p.video}"/>" type="video/mp4">
                        Tu navegador no implementa el elemento <code>video</code>.
                    </video>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <div class="col-sm-2">Envio Gratis:</div>
                    <div class="col-sm-2">
                        <c:out value="${p.recomendado}"/>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <div class="col-sm-2">IGV:</div>
                    <div class="col-sm-2">
                        <c:out value="${p.igv}"/>
                    </div>
                </div>
            </div>
            <hr><!-- comment -->
            <div class="row">
                <div class="col">
                   <c:if test="${sessionScope.login != null}">
                    <a href="cart?idProducto=<c:out value="${p.idProducto}"/>&accion=order&idCliente=<c:out value="${sessionScope.login.idPersona}" />" class="btn btn-default add-to-cart <c:if test="p.stock == 0"><c:out value="disabled"/></c:if>"><i class="fa fa-shopping-cart"></i>Agregar al carrito
                    </a>
                    </c:if>
                </div>
            </div>
        </div>

    </body>
</html>
