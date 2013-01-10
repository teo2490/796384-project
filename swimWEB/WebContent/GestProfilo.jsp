<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestione Profilo</title>
<style type="text/css">
			@import url(css/main.css);
</style>
</head>
<%@ page import="swim.sessionbeans.*" %>
<%@ page import="swim.entitybeans.*" %>
<%@ page import="java.util.*" %>
<%@ page import="swim.util.ContextUtil" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<body>
<div id="content">
			<div id="subcontent">
				<div id="header">
					<div id="logo">
						&nbsp;
					</div>			
					<br>		
					<div id="menu">
				        <ul>
				            <li><a href="Home.jsp">Home</a></li>
				            <li><a href="HomeUtente.jsp">Profilo</a></li>		
				            <li><a href="LogoutServlet">Logout</a></li>					            
				        </ul>
				    </div>
				</div>
				<br/><br/><br/><br/>
				<%
					String messaggio = (String) request.getAttribute("messaggio");
					if(messaggio != null) {
						out.println("<p>" + messaggio + "</p>");
					}
				%>
				<br /><br />
<table width="795px" border="0" align="center">
  <tr>
    <td>
	<div align="center"><h3>GESTIONE PROFILO</h3></div>
    </td>
  </tr>
  <tr>
  	 <td>
  	 <div align="center">
  	 				<%
					Object obj = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
  	 				ManagerUtenteRegistratoRemote man = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obj, ManagerUtenteRegistratoRemote.class);
					UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
					String url = "";
					try{
						url = u.getUrl();
					}catch(NullPointerException e){
						url = "";
					}
					out.println(u.getNome()+"&nbsp"+u.getCognome()+"<br /><br />");
					out.println(u.getEmail());
					out.println("<form action=\"ModificaProfiloServlet\" method=\"post\">");
					out.println("<p>Vecchia Password: </p><input type=\"text\" name=\"oldpsw\" id=\"oldpsw\" value=\""+u.getPsw()+"\" />");
					out.println("<p>Nuova Password: </p><input type=\"text\" name=\"newpsw\" id=\"newpsw\" value=\""+""+"\" />");
					out.println("<p>URL Immagine: </p><input type=\"text\" name=\"url\" id=\"url\" value=\""+url+"\" /><br /><br />");
					out.println("<input type=\"submit\" name=\"submit\" value=\"Conferma\" id=\"ok\"/></form><br />");
			     
					%>
		<br /><br /><br />
	</div>
	 </td>
  </tr>
</table>			
</body>
</html>