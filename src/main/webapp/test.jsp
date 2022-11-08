

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>


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

</body>
</html>
