<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profilo</title>
<style type="text/css">
			@import url(css/main.css);
</style>
<script type="text/javascript">
		/* funzione Controllo Inserimento Dati*/
		function check() {
			if(document.getElementById("nome").value == "" ||
			   document.getElementById("cognome").value == ""	)
			{
		  		alert("Uno o più campi sono incompleti.");
		 		return false;
			}
			return true;
		}
		</script>
</head>
<%@ page import="swim.entitybeans.*" %>
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
<table width="795px" border="0" align="center">
  <tr>
    <td>
	<div align="center"><h3>PROFILO</h3></div>
    </td>
  </tr>
  <tr>
    <td>
		<%
		UtenteRegistrato u = (UtenteRegistrato)request.getSession().getAttribute("ut");
		try{
			out.println("<br /><br />"+u.getEmail()+"<br />");
			out.println(u.getNome()+"&nbsp"+u.getCognome());
			//STAMPARE FEEDBACK
		}catch(NullPointerException e){
			out.println("Nessun utente corrisponde all'email inserita!");
		}
		%>
    </td>
  </tr>
</table>			
</body>
</html>