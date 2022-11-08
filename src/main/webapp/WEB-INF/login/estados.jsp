
<%@page import="java.util.List"%>
<%@page import="pe.com.ecosalud.beans.Estado"%>
<%
    List<Estado> estado = null;
    if (request.getAttribute("estados") != null) {
        estado = (List<Estado>) request.getAttribute("estados");
    }
    int idPais = Integer.parseInt(request.getAttribute("idPais").toString());
%>

<!-- Estados -->

        <select class="custom-select" name="idEstado">
            <% for (Estado est : estado) {%>
            <option value="<%= est.getIdEstado()%>" <% if (est.getIdPais().getIdPais() == idPais) {
                    out.print("selected");
                }%>><%= est.getNombre()%></option>
            <%}%>
        </select> 


