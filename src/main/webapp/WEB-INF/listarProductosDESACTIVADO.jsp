
<%@page import="pe.com.ecosalud.vistas.VMarca"%>
<%@page import="pe.com.ecosalud.beans.Marca"%>
<%@page import="pe.com.ecosalud.beans.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>Productos</title>
        <link rel="stylesheet" type="text/css" href="css/listarProducto/anylinkmenu.css"/>
        <link rel="stylesheet" type="text/css" href="css/listarProducto/estructura.css"/>
        <script type="text/javascript" src="js/listarProducto/menucontents.js"></script>
        <script type="text/javascript" src="js/listarProducto/anylinkmenu.js"></script>

    </head>
    <%
        VMarca mm = new VMarca();

        List<Producto> listProductos = null;
        if (request.getAttribute("productos") != null) {
            listProductos = (List<Producto>) request.getAttribute("productos");
        }

        List<Categoria> listCategorias = null;
        if (request.getAttribute("categorias") != null) {
            listCategorias = (List<Categoria>) request.getAttribute("categorias");
        }

        List<Marca> listMarcas = null;
        if (request.getAttribute("marcas") != null) {
            listMarcas = (List<Marca>) request.getAttribute("marcas");
        }
    %>
    <body>

        <!<!-- HEADER -->
        <%@include file="header.jsp" %>

        <!-- Contenido -->
        <section>

            <table>
                <tr>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <% for (Producto p : listProductos) {%>
                <tr>
                    <td> <%= p.getNombre()%> 
                        <br>${sessionScope.moneda}($) <%= p.getPrecio()%> 
                        <% if (p.getStock() > 0) {%>
                        <br>Disponible (<%= p.getStock()%>) 
                        <br><a href="cart?idProducto=<%= p.getIdProducto()%>&accion=order">Agregar Carrito</a>
                        <%} else {%>
                        <br>Agotado (<%= p.getStock()%>)
                        <br>-
                        <%}%></td>
                </tr>
                <%}%>
            </table>
        </section>

        <!-- Contenido relacionado-->
        <aside>     
            <span>Categorias</span>
            <ul> 
                <% for (Categoria cate : listCategorias) {%>
                <% if (!cate.isPrincipal()) {%>
                <li><a href="home?category=<%= cate.getIdCategoria()%>"><%= cate.getNombre()%>
                    </a>
                    <ul>
                        <% for (Categoria categ : listCategorias) {
                                try {%>
                        <% if (cate.getIdCategoria() == categ.getCategoriaId().getIdCategoria()) {%>
                        <li><a href="home?category=<%= categ.getIdCategoria()%>"><%= categ.getNombre()%></a></li>
                        <%}%><!<!-- fin del if -->
                        <%} catch (Exception e) {
                                }
                            }%><!<!-- fin del for -->
                    </ul>
                </li>

                <%}%><!<!-- fin del if -->
                <%}%><!<!-- fin del for -->
            </ul> 

            </br>
            <span>Marcas</span>
            <ul> 
                <% for (Marca mar : listMarcas) {
                        int cont = 0;%>
                <li><a href="home?brand=<%= mar.getIdMarca()%>"><%= mar.getNombre()%>
                    </a><span> (<%= mm.contarMarca(mar.getIdMarca())%>)</span>
                </li>

                <%}%><!-- fin del for -->
            </ul> 
        </aside>

        <!-- Pie de pagina -->
        <footer>
            <a href="#"></a>
        </footer>

    </script>

    <script type="text/javascript">

        //anylinkmenu.init("menu_anchors_class") //Pass in the CSS class of anchor links (that contain a sub menu)
        anylinkmenu.init("menuanchorclass")

    </script>
</body>
</html>