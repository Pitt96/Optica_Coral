<%@page import="pe.com.ecosalud.beans.Categoria"%>
<%@page import="pe.com.ecosalud.beans.Marca"%>
<%@page import="pe.com.ecosalud.beans.Proveedor"%>
<%@page import="pe.com.ecosalud.beans.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Programa Regalos</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="<%= request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <%
        String tipoForm = (String) request.getAttribute("tipoForm");
        //System.out.println("form... " + tipoForm);

        Producto p = null;
        if (tipoForm.equals("Actualizar")) {
            p = (Producto) request.getAttribute("productos");
        }

        List<Categoria> listCategoria = null;
        if (request.getAttribute("categorias") != null) {
            listCategoria = (List<Categoria>) request.getAttribute("categorias");
        }

        List<Marca> listMarcas = null;
        if (request.getAttribute("marcas") != null) {
            listMarcas = (List<Marca>) request.getAttribute("marcas");
        }

        List<Proveedor> listProveedores = null;
        if (request.getAttribute("proveedores") != null) {
            listProveedores = (List<Proveedor>) request.getAttribute("proveedores");
        }
    %>

    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <form class="form" action="productos" enctype="multipart/form-data" method="POST">

                        <input type="hidden" name="accion" value="<%= tipoForm%>">
                        <input type="hidden" name="idProducto" value="<% if (tipoForm.equals("Actualizar")) {
                                out.print(p.getIdProducto());
                            } %>">

                        <!-- nombre del producto -->
                        <div class="form-group row">
                            <label for="nombreP" class="col-sm-2 col-form-label">Nombre:</label>
                            <div class="col-sm-10">
                                <input type="text" name="nombre" id="nombreP" placeholder="Nombre del producto..." value="<% if (tipoForm.equals("Actualizar")) {
                                        out.print(p.getNombre());
                                    } %>" required/>
                            </div>
                        </div>

                        <!-- descripcion del producto -->
                        <div class="form-group row">
                            <label for="descripP" class="col-sm-2 col-form-label">Descripcion:</label>
                            <div class="col-sm-10">         
                                <input type="text"  name="descripcion" id="descripP" placeholder="Descripción del producto..." value="<% if (tipoForm.equals("Actualizar")) {
                                        out.print(p.getDescripcion());
                                    } %>" required/>
                            </div>
                        </div>

                        <!-- imagen del producto -->
                        <div class="form-group row">
                            <label for="imagenP" class="col-sm-2 col-form-label">Seleccionar imagen...</label>
                            <div class="col-sm-10">  
                                <input type="file" id="imagenP"  name="imagen" value=""/>
                            </div>
                        </div>


                        <!-- video del producto -->
                        <div class="form-group row">
                            <label for="videoP" class="col-sm-2 col-form-label">Seleccionar video...</label>
                            <div class="col-sm-10">  
                                <input type="file" id="videoP"  name="video" value=""/>
                            </div>
                        </div>

                        <!<!-- stock del producto -->
                        <% if (tipoForm.equals("Crear")) { %>
                        <div class="form-group row">
                            <label for="stockP" class="col-sm-2 col-form-label">Stock:</label>
                            <div class="col-sm-10">
                                <input type="number" id="stockP" name="stock" placeholder="Stock del producto..." required/>
                            </div>
                        </div>
                        </br>
                        <%}%>

                        <!-- precio del producto -->
                        <div class="form-group row">
                            <label for="precioP" class="col-sm-2 col-form-label">Precio:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precioP" name="precio" step="0.01" placeholder="Precio unitario..." value="<% if (tipoForm.equals("Actualizar")) {
                                        out.print(p.getPrecio());
                                    } %>" required/>
                            </div>
                        </div>
                            
                            <!-- IGV del producto -->
                        <div class="form-group row">
                            <label for="IGVP" class="col-sm-2 col-form-label">IGV</label>
                            <div class="col-sm-10">
                                <input type="number" id="IGVP" name="igv" step="0.01" placeholder="IGV..." value="<% if (tipoForm.equals("Actualizar")) {
                                        out.print(p.getIgv());
                                    } %>" required/>
                            </div>
                        </div>

                        <!-- precio nuevo del producto -->
                        <div class="form-group row">
                            <label for="precionP" class="col-sm-2 col-form-label">Descuento:</label>
                            <div class="col-sm-10">
                                <input type="number" id="precionP" name="precion" step="0.01" placeholder="Descuento..." value="<% if (tipoForm.equals("Actualizar")) {
                                        out.print(p.getPrecionuevo());
                                    } %>" required/>
                            </div>
                        </div>

                        <!-- visible -->
                        <div class="form-group row">
                            <div class="col-sm-2">Visible:</div>
                            <div class="col-sm-10">
                                <div class="form-check">
                                    <input type="checkbox" name="visible" id="visibleP" value="on" <% if (tipoForm.equals("Crear")) {
                                            out.print("checked");
                                        } %> <% if (tipoForm.equals("Actualizar")) {
                                                if (p.isVisible()) {
                                                    out.print("checked");
                                                }
                                            }  %>/>
                                    <label class="form-check-label" for="visibleP">

                                    </label>
                                </div>
                            </div>
                        </div>

                        <!-- recomendado -->
                        <div class="form-group row">
                            <div class="col-sm-2">Envio Gratis:</div>
                            <div class="col-sm-10">
                                <div class="form-check">
                                    <input type="checkbox" id="recomendadoP" name="recomendado" value="on" <% if (tipoForm.equals("Actualizar")) {
                                            if (p.isRecomendado()) {
                                                out.print("checked");
                                            }
                                        }  %>/>
                                    <label class="form-check-label" for="recomendadoP">

                                    </label>
                                </div>
                            </div>
                        </div>

                        <!-- categoria -->
                        <div class="form-group row">
                            <label for="categP" class="col-sm-2 col-form-label">Categoria:</label>
                            <div class="col-sm-5">
                                <select class="custom-select" name="idCategoria">
                                    <%
                                        if (!tipoForm.equals("Actualizar")) {
                                    %>
                                    <option value="" selected="selected" disabled="true">Selecccione Categoria</option>
                                    <%}%>

                                    <% for (Categoria cat : listCategoria) {%>
                                    <option value="<%= cat.getIdCategoria()%>" <% if (tipoForm.equals("Actualizar") && cat.getIdCategoria() == p.getIdCategoria().getIdCategoria()) {
                                            out.print("selected");
                                        }%> ><%= cat.getNombre()%></option>
                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <!-- marca -->
                        <div class="form-group row">
                            <label for="marcaP" class="col-sm-2 col-form-label">Marca:</label>
                            <div class="col-sm-5">
                                <select name="idMarca" class="custom-select">
                                    <%
                                        if (!tipoForm.equals("Actualizar")) {
                                    %>
                                    <option value="" selected="selected" disabled="true">Selecccione Marca</option>
                                    <%}%>

                                    <% for (Marca mar : listMarcas) {%>
                                    <option value="<%= mar.getIdMarca()%>" <% if (tipoForm.equals("Actualizar") && mar.getIdMarca() == p.getIdMarca().getIdMarca()) {
                                            out.print("selected");
                                        }%> ><%= mar.getNombre()%></option>
                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <!-- proveedor -->
                        <div class="form-group row">
                            <label for="proveedorP" class="col-sm-2 col-form-label">Proveedor:</label>
                            <div class="col-sm-5">
                                <select name="idProveedor" class="custom-select">
                                    <%
                                        if (!tipoForm.equals("Actualizar")) {
                                    %>
                                    <option value="" selected="selected" disabled="true">Selecccione Proveedor</option>
                                    <%}%>

                                    <% for (Proveedor prov : listProveedores) {%>
                                    <option value="<%= prov.getIdProveedor()%>" <% if (tipoForm.equals("Actualizar") && prov.getIdProveedor() == p.getIdProveedor().getIdProveedor()) {
                                            out.print("selected");
                                        }%> ><%= prov.getRazonsocial()%></option>
                                    <%}%>
                                </select> 
                            </div>
                        </div>

                        <!-- submit -->
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <a href="productos" class="btn btn-warning" role="button">Cancelar</a> 
                                <button class="btn btn-primary" type="submit"><% if (tipoForm.equals("Actualizar")) {
                                        out.print("Actualizar");
                                    } else {
                                        out.print("Siguiente");
                                    }%></button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
