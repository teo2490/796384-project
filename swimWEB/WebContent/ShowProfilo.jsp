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
<table width="795px" border="0" align="center">
  <tr>
    <td>
	<div align="center"><h3>PROFILO</h3></div>
    </td>
  </tr>
  <tr>
    <td>
		<%
		Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
		ManagerAiutoRemote man = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
		UtenteRegistrato u = (UtenteRegistrato)request.getSession().getAttribute("ut");
		try{
			out.println("<br /><br />"+u.getEmail()+"<br />");
			out.println(u.getNome()+"&nbsp"+u.getCognome()+"<br />");
			List<Aiuto> fb = man.getElencoRichiesteAiutoRicevuteConfermateConFeedback(u);
			//STAMPARE FEEDBACK
			for (Aiuto f: fb)	{ out.println(f.getFeedback()+"<br />"); }
		}catch(NullPointerException e){
			out.println("Nessun utente corrisponde all'email inserita!");
		}catch(SwimBeanException se){
			out.println("Questo utente non ha feedback!!");
		}
		%>
    </td>
  </tr>
</table>			
</body>
</html>