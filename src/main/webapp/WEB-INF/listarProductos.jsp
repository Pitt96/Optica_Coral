<%@page import="pe.com.ecosalud.beans.Login"%>
<%@page import="pe.com.ecosalud.vistas.VPromedio"%>
<%@page import="pe.com.ecosalud.vistas.VCategoria"%>
<%@page import="pe.com.ecosalud.beans.Marca"%>
<%@page import="pe.com.ecosalud.beans.Categoria"%>
<%@page import="pe.com.ecosalud.beans.Producto"%>
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.vistas.VMarca"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Inicio |  Crea e-Commerce JAVA EE con pagos Online Paypal y Payu</title>

        <!-- CSS -->
        <%@include file="estilos.jsp" %>
        <!-- Fin CSS -->
    </head><!--/head-->

    <%
        VMarca mm = new VMarca();
        VCategoria vc = new VCategoria();
        VPromedio vp = new VPromedio();

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
        <!--/header-->
        <%@include file="header.jsp" %>
        <!--/Fin header-->

        <!--/slider-->
        <%@include file="slider.jsp" %>
        <!--/Fin slider-->

        <hr/><!--confianza-->
        <p align="center">                                     
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza2.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza3.png">
            <img alt="Elementos de Confianza" src="tienda/images/home/confianza4.png">
        </p><!--/confianza-->
        <hr/>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <h2>Categorías</h2>
                            <!--Listar Categorias-->
                            <div class="panel-group category-products" id="accordian">
                                <% for (Categoria cate : listCategorias) {%>
                                <% if (!cate.isPrincipal()) {%>

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a <% if (vc.isSuperior(cate.getIdCategoria())) { %> data-toggle="collapse" data-parent="#accordian" <%}%> href="#<%= cate.getIdCategoria()%>">
                                                <% if (vc.isSuperior(cate.getIdCategoria())) { %> <span class="badge pull-right"><i class="fa fa-plus"></i></span><%}%>
                                                <a href="home?category=<%= cate.getIdCategoria()%>"><%= cate.getNombre()%></a>
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="<%= cate.getIdCategoria()%>" class="panel-collapse collapse">
                                        <div class="panel-body">
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
                                        </div>
                                    </div>
                                </div>
                                <!-- comment -->
                                <%}%>
                                <%}%>
                            </div>
                            <!--/category-products-->

                            <!--Listar Marcas-->
                            <div class="brands_products">
                                <h2>Marcas</h2>
                                <div class="brands-name">
                                    <ul class="nav nav-pills nav-stacked">
                                        <% for (Marca mar : listMarcas) {%>
                                        <li><a href="home?brand=<%= mar.getIdMarca()%>"> <span class="pull-right">(<%= mm.contarMarca(mar.getIdMarca())%>)</span><%= mar.getNombre()%></a></li>
                                        <%}%><!-- fin del for -->
                                    </ul>
                                </div>
                            </div>
                            <!--/brands_products-->

                            <div class="shipping text-center"><!--shipping-->
                                <img src="tienda/images/home/shipping.jpg" alt="" />
                            </div><!--/shipping-->

                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="features_items">
                            <!--features_items-->
                            <h2 class="title text-center">Productos destacados</h2>

                            <% for (Producto p : listProductos) {%>
                            <!-- Listar productos -->

                            <div class="col-sm-4">
                                <div class="product-image-wrapper">
                                    <div class="single-products">
                                        <div class="productinfo text-center">

                                            <img src="imagenes/productos/imagen/<%= p.getImagen()%>" alt="" />
                                            <h2 <% if (p.getStock() == 0)
                                                    out.print("class=\"gris\"");%>>${sessionScope.moneda} <%= p.getPrecio()%></h2>
                                            <p><%= p.getNombre()%></p>
                                            <span class="btn btn-default add-to-cart <% if (p.getStock() == 0) {
                                                    out.print("disabled");
                                                } %>"><i class="fa fa-shopping-cart"></i>Agregar al carrito</span>
                                        </div>


                                        <div class="product-overlay <% if (p.getStock() == 0)
                                                out.print("grisfondo");%>">
                                            <div class="overlay-content">
                                                <h2>${sessionScope.moneda} <%= p.getPrecio()%></h2>
                                                <p><%= p.getNombre()%></p>

                                                <% if (login != null) {%>
                                                <a href="cart?idProducto=<%= p.getIdProducto()%>&accion=order&idCliente=<%= login.getIdPersona()%>" class="btn btn-default add-to-cart <% if (p.getStock() == 0) {
                                                        out.print("disabled");
                                                    }%>"><i class="fa fa-shopping-cart"></i>Agregar al carrito
                                                </a>

                                                <!-- comment -->
                                                <%} else {%>
                                                <button data-toggle="modal" data-value="<%= p.getIdProducto()%>" onclick="CargarModalLogueo1()" data-target="#myModalLogueo1" class="btn btn-default add-to-cart myModalLogueo1">Agregar al carritoB<i class="fa fa-shopping-cart"></i></button>
                                                    <%}%>

                                            </div>
                                        </div>

                                    </div>

                                    <div class="choose">
                                        <ul class="nav nav-pills nav-justified">

                                            <li>
                                                <!-- estrelllas -->
                                                <a href="#" <% if (p.getStock() == 0) {
                                                    } else {
                                                        out.print("class=\"edit2\"");
                                                    }%>  data-value="<%= p.getIdProducto()%>">
                                                    <span class="stars-active" style="width:50%">
                                                        <% int promedio = vp.promedioEstrellas(p.getIdProducto());
                                                            if (promedio > 0) {%>
                                                        <% for (int i = 0; i < promedio; i++) { %>
                                                        <i class="fa fa-star"></i>
                                                        <%}
                                                        } else {%>
                                                        No hay calificacion
                                                        <%}%>
                                                    </span>
                                                </a>
                                                <!--fin estrellas-->

                                            </li>
                                            <li>
                                                <a href="#" <% if (p.getStock() == 0) { %> class="gris" <%}%>>
                                                    <% if (p.getStock() > 0) {%>
                                                    <i class="fa fa-check-circle"></i>(<%= p.getStock()%>) Disponible
                                                    <%} else {%>
                                                    <i class="fa fa-lock"></i>(<%= p.getStock()%>) Agotado
                                                    <%}%>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="productos?accion=productoDetalles&idProducto=<%= p.getIdProducto()%>" class="" ><strong>+</strong></a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!-- Fin de Listar productos -->
                            <%}%>

                        </div><!--features_items-->

                        <!--/category-tab-->
                        <%@include file="tabcategoria.jsp" %>
                        <!--/Fin category-tab-->

                        <!--/recommended_items-->
                        <%@include file="recomendado.jsp" %>
                        <!--/Fin recommended_items-->

                    </div>
                </div>
            </div>
        </section>

        <!-- Edit Modal para Valoraciones-->
        <div class="modal fade" id="edit2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body2" >
                        <!-- code -->
                    </div>  
                </div>
            </div>
        </div>
        <!-- Fin del Modal Valoraciones -->

        <!-- Modal1 Iniciar Session o Registrarse -->
        <div class="modal fade" id="myModalLogueo1" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content" id="contenido1">  
                    <!--Aqui el Contenido de Login y Registro-->
                </div>
            </div>
        </div>

        <!-- Modal Iniciar Session o Registrarse -->
        <div class="modal fade" id="myModalLogueo" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">

                <div class="modal-content" id="contenido">                   
                    <!--Aqui el Contenido de Login y Registro-->
                </div>
            </div>
        </div>

        <!--/Footer-->
        <%@include file="footer.jsp" %>
        <!--/Fin Footer-->

        <!--/Script-->
        <%@include file="script.jsp" %>
        <!--/Fin Script-->

        <script>
            function enviar_formulario() {
                document.formulario1.submit()
            }
        </script>

        <!-- Cuando se da clic en valoracion -->
        <script>
            $(document).ready(function () {
                $(document).on('click', '.edit2', function () {
                    var idProducto = $(this).data('value');
                    $('.modal-body2').load('estrellas?idCliente=<% if (login != null)
                            out.print(login.getIdPersona());%>&accion=mostrar&idProducto=' + idProducto, function () {
                        $('#edit2').modal('show');
                    });
                });
            });
        </script>
        <!-- Cuando se da clic en Acceder -->
        <script  type="text/javascript">
            function CargarModalLogueo() {
                //document.getElementById("contenido").innerHTML = "";
                $(document).on('click', '.myModalLogueo', function () {
                    $("#myModalLogueo").modal('show');
                    $("#contenido").load("logins?accion=IniciarSession");
                });
            }
        </script>
        <!-- Cuando se da clic en comprar -->
        <script  type="text/javascript">
            function CargarModalLogueo1() {
                $(document).on('click', '.myModalLogueo1', function () {
                    var idProducto = $(this).data('value');
                    $("#myModalLogueo1").modal('show');
                    $("#contenido1").load("logins?accion=IniciarSession&idProducto=" + idProducto);
                });
                //document.getElementById("contenido").innerHTML = "";
            }
        </script>
        <script type="text/javascript">
            function onKeyUp(event) {
                var keycode = event.keyCode;
                if (keycode == '13') {
                    var valor = document.getElementById("texto").value;
                    location.href = 'home?accion=buscar&valor=' + valor;
                }
            }
        </script>
    </body>
</html>
