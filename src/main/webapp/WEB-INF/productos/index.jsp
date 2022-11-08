<%@page import="pe.com.ecosalud.beans.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="<%= request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <%
        List<Producto> listarProductos = null;

        if (request.getAttribute("productos") != null) {
            listarProductos = (List<Producto>) request.getAttribute("productos");
        }
        String result = "";

        if (request.getAttribute("opArt") != null) {
            result = (String) request.getAttribute("opArt");
        } else if (request.getSession().getAttribute("opArt") != null) {
            result = (String) request.getSession().getAttribute("opArt");
        }
    %>
    <body align="center">
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="row">ID Producto</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Precio</th>
                                <th scope="col">IGV</th>
                                <th scope="col"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                    </svg>
                                </th>
                                <th scope="col">Imagen</th>
                                <th scope="col">Video</th>
                                <th scope="col">Stock</th>
                                <th scope="col"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                    </svg>
                                </th>
                                <th scope="col">Categoria</th>
                                <th scope="col">Marca</th>
                                <th scope="col"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                    </svg>
                                </th>
                                <th>Envio Gratis</th>
                            </tr>
                        </thead>
                        <tbody>
                                <% for (Producto producto : listarProductos) {%>
                                <tr <% if (!producto.isVisible())
                                        out.print("class = \" table-danger \" ");%>>
                                <td><%= producto.getIdProducto()%> </td>
                                <td><%= producto.getNombre()%> </td>
                                <td><%= producto.getPrecio()%> </td>
                                <td><%= producto.getIgv()%> </td>
                                <td>
                                    <a class="edit2" data-value="<%= producto.getIdProducto()%>"> 
                                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-cash-stack" viewBox="0 0 16 16">
                                        <path d="M14 3H1a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1h-1z"/>
                                        <path fill-rule="evenodd" d="M15 5H1v8h14V5zM1 4a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1H1z"/>
                                        <path d="M13 5a2 2 0 0 0 2 2V5h-2zM3 5a2 2 0 0 1-2 2V5h2zm10 8a2 2 0 0 1 2-2v2h-2zM3 13a2 2 0 0 0-2-2v2h2zm7-4a2 2 0 1 1-4 0 2 2 0 0 1 4 0z"/>
                                        </svg>
                                    </a>
                                </td>
                                <td><img src="imagenes/productos/imagen/<%= producto.getImagen()%>" width="50" height="50"></td>
                                <td>

                                <td>
                                    <span id="vstock<%= producto.getIdProducto()%>"><%= producto.getStock()%></span>
                                </td>
                                <td>
                                    <a class="edit" data-value="<%= producto.getIdProducto()%>"> 
                                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-file-earmark-plus-fill" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M2 2a2 2 0 0 1 2-2h5.293A1 1 0 0 1 10 .293L13.707 4a1 1 0 0 1 .293.707V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm7.5 1.5v-2l3 3h-2a1 1 0 0 1-1-1zM8.5 7a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V11a.5.5 0 0 0 1 0V9.5H10a.5.5 0 0 0 0-1H8.5V7z"/>
                                        </svg>
                                    </a>
                                </td>
                                <td><%= producto.getIdCategoria().getNombre()%> </td>
                                <td><%= producto.getIdMarca().getNombre()%> </td>

                                <td>
                                    <a href="productos?accion=editar&idProducto=<%= producto.getIdProducto()%>" class="btn btn-primary" role="button">Editar Producto</a> 
                                </td>
                                <td>
                                    <%= producto.isRecomendado()%>
                                </td>

                            </tr>
                            <% }%>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

        <a href="productos?accion=nuevo" class="btn btn-primary" role="button">Ingresar Nuevo</a> 
        <a href="admin" class="btn btn-primary" role="button">Regresar</a>
        <hr>

        <!-- Edit Modal para Stock-->
        <div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <center><h4 class="modal-title" id="myModalLabel">Editar Stock</h4></center>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form class="form" action="productos" method="POST" enctype="multipart/form-data">
                                <div class="form-group row">
                                    <input type="hidden" name="accion" value="stockUpdateProducto">
                                    <label for="eidP" class="col-sm-4 col-form-label">ID Producto</label>
                                    <div class="col-sm-8">
                                        <input type="number" name="idProducto" class="form-control" id="eidP" readonly="">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="estock" class="col-sm-4 col-form-label">Stock Actual</label>
                                    <div class="col-sm-8">
                                        <input type="number" class="form-control" id="estock" readonly="">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for ="stockN" class="col-sm-4 col-form-label">Agregar Stock</label>
                                    <div class="col-sm-8">
                                        <div id = "contenedor-campos">
                                            <input class="campo-numerico form-control" id="stockN" type="number" name ="stockNuevo" min="1" pattern="^[0-9]+" onpaste="return false;" onDrop="return false;" autocomplete=off required>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
                                    <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-edit"></span> </i> Actualizar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fin del Modal Edit -->

        <!-- Edit Modal para Precio-->
        <div class="modal fade" id="edit2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <center><h4 class="modal-title" id="myModalLabel">Editar Divisas</h4></center>
                    </div>
                    <div class="modal-body2">
                        <!-- code -->
                    </div>
                </div>
            </div>
        </div>
        <!-- Fin del Modal Edit -->


        <script src="<%= request.getContextPath()%>/bootstrap/js/jquery-3.5.1.min.js"></script>
        <script src="<%= request.getContextPath()%>/bootstrap/js/popper.min.js"></script>
        <script src="<%= request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
        <script src="<%= request.getContextPath()%>/js/crudproducto/validarCampoNumerico.js"></script>


        <!-- /.modal -->
        <script>
                                                $(document).ready(function () {
                                                    $(document).on('click', '.edit', function () {
                                                        var id = $(this).data('value');
                                                        var vstock = $('#vstock' + id).text();

                                                        $('#edit').modal('show');
                                                        $('#estock').val(vstock);
                                                        $('#eidP').val(id);
                                                    });
                                                });
        </script>

        <script>
            $(document).ready(function () {
                $(document).on('click', '.edit2', function () {
                    var id = $(this).data('value');
                    $('.modal-body2').load('productos?idProducto=' + id + '&accion=editarDivisas', function () {

                        $('#edit2').modal('show');

                    });
                });
            });
        </script>

    </div>
</body>
</html>
