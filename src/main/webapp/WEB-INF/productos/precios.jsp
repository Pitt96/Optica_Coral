
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.ProductoMoneda"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%= request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <title>Precios</title>
    </head>
    <%
        String tipoForm = (String) request.getAttribute("tipoForm");
        //System.out.println("form... " + tipoForm);

        List<ProductoMoneda> listaProductoMoneda = null;
        if (request.getAttribute("monedas") != null) {
            listaProductoMoneda = (List<ProductoMoneda>) request.getAttribute("monedas");
        }
    %>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <form class="form" action="productos" enctype="multipart/form-data" method="POST">

                        <% if (tipoForm.equals("Actualizar")) {
                                int cont = 0;%>
                        <input type="hidden" name="accion" value="divisaUdateProducto">
                        <% for (ProductoMoneda pm : listaProductoMoneda) {%>
                        <input type="hidden" name="idProductoMoneda<% out.print(++cont);%>" value="<%= pm.getIdProductoMoneda()%>">

                        <!-- precio -->
                        <div class="form-group row">
                            <label class="col-sm-6 col-form-label">Moneda <%= pm.getMoneda()%> precio:</label>
                            <div class="col-sm-6">
                                <input type="number" step="0.01" name="precio<%= pm.getMoneda()%>" placeholder="Precio unitario..." value="<%= pm.getPrecio()%>" required/>
                            </div>
                        </div>

                        <!-- precio nuevo -->
                        <div class="form-group row">
                            <label  class="col-sm-6 col-form-label">Moneda <%= pm.getMoneda()%> descuento:</label>
                            <div class="col-sm-6">
                                <input type="number" step="0.01" name="precionuevo<%= pm.getMoneda()%>" placeholder="Precio unitario..." value="<%= pm.getPrecionuevo()%>" required/>
                            </div>
                        </div>



                        <%}%>
                        <%} else if (tipoForm.equals("CrearconPrecio")) {%>

                        <input type="hidden" name="accion" value="<%= tipoForm%>">

                        <!-- precio USD -->
                        <div class="form-group row">
                            <label for="precioUSD" class="col-sm-2 col-form-label">Precio USD:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precioUSD" name="preciousd" step="0.01" placeholder="Precio unitario..." value="" required/>
                            </div>
                        </div>

                        <!-- precio nuevo USD -->
                        <div class="form-group row">
                            <label for="precionUSD" class="col-sm-2 col-form-label">Descuento USD:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precionUSD" name="precionusd" step="0.01" placeholder="Precio unitario nuevo..." value="" required/>
                            </div>
                        </div>

                        <!-- precio EUR -->
                        <div class="form-group row">
                            <label for="precioEUR" class="col-sm-2 col-form-label">Precio EUR:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precioEUR" name="precioeur" step="0.01" placeholder="Precio unitario..." value="" required/>
                            </div>
                        </div>

                        <!-- precio nuevo EUR -->
                        <div class="form-group row">
                            <label for="precionEUR" class="col-sm-2 col-form-label">Descuento EUR:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precionEUR" name="precioneur" step="0.01" placeholder="Precio unitario nuevo..." value="" required/>
                            </div>
                        </div>

                        <!-- precio CNY -->
                        <div class="form-group row">
                            <label for="precioCNY" class="col-sm-2 col-form-label">Precio CNY:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precioCNY" name="preciocny" step="0.01" placeholder="Precio unitario..." value="" required/>
                            </div>
                        </div>

                        <!-- precio nuevo CNY-->
                        <div class="form-group row">
                            <label for="precionCNY" class="col-sm-2 col-form-label">Email</label>
                            <div class="col-sm-10">
                                <input type="number" id="precionCNY" name="precioncny" step="0.01" placeholder="Precio unitario nuevo..." value="" required/>
                            </div>
                        </div>

                        <%}%>

                        <!-- submit -->
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <a href="productos" class="btn btn-warning" role="button">Cancelar</a> 
                                <button class="btn btn-success" type="submit"><% if (tipoForm.equals("Actualizar")) {
                                        out.print("Actualizar");
                                    } else {
                                        out.print("Crear Producto");
                                    }%></button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
