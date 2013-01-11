<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestione Abilità</title>
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
	<div align="center"><h3>GESTIONE ABILITA'</h3></div>
    </td>
  </tr>
  <tr>
  	 <td>
  	 <div align="center">
  	 				<%
					Object obj = ContextUtil.getInitialContext().lookup("ManagerAmministratore/remote");
  	 				ManagerAmministratoreRemote man = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(obj, ManagerAmministratoreRemote.class);
					Amministratore a = (Amministratore) request.getSession().getAttribute("utente");
					List<Abilita> elenco = man.getElencoRichieste();
					if (elenco.size() >0)  
			        { 
						//"<p>"+e.getNome()+"</p><img src=\"image/ok.png\" height=\"20px\" width=\"20px\"><img src=\"image/no.png\" height=\"20px\" width=\"20px\" style=\"margin-left: 15px\"><br />"
						for (Abilita e: elenco)	{ out.println("<form action=\"ConfermaAbilitaServlet\" method=\"post\"><input type=\"text\" name=\"id\" id=\"id\" value=\""+e.getId()+"\" style=\"visibility: hidden;\" /><input type=\"text\" name=\"nome\" id=\"nome\" value=\""+e.getNome()+"\" /><input type=\"text\" name=\"desc\" id=\"desc\" value=\""+e.getDescrizione()+"\" /><input type=\"submit\" name=\"submit\" value=\"Conferma\" id=\"ok\"/><br />"); }
			        }   
					%>
		<br /><br /><br />
	</div>
	 </td>
  </tr>
</table>			
</body>
</html>