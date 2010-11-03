<%-- Affichage des messages--%>

<jsp:useBean id="bean" scope="application" class="Gestion.GestionMessages"/>

<%
    for(int i = 0; i < bean.size(); i++){
        out.print(bean.getMessage(i).getUtilisateur() + " : <br/>");
        out.print(bean.getMessage(i).getContenu() + "<br/><br/>");
    }
%>

<a name="BasDePage"/>