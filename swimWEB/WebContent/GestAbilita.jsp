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
<!-- QUI -->
    <td>
    <form action="RichiestaNuovaAbilitaServlet" method="post" onSubmit="return check()">
    	<div align="center">
					<fieldset>
						<h3><u>Richiesta di una nuova abilità</u></h3><br>
						<label for="nome">Nome:</label>
						<br />
						<input type="text" name="nome" id="nome" />
						<br />
						<label for="desc">Descrizione:</label>
						<br />
						<input type="text" name="desc" id="desc" />
						<br /><br />
						<input type="submit" name="submit" value="OK" />
					</fieldset>
				</div>
		</form>
    </td>
  </tr>
</table>			
</body>
</html>