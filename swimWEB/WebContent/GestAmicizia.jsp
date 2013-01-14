<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestione Amicizia</title>
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
	<div align="center"><h3>GESTIONE AMICIZIE</h3></div>
    </td>
  </tr>
  <tr>
<!-- QUI -->
    <td>
    	<div align="center">
					
						<h3><u>Richieste di Amicizia</u></h3><br>
					<%
					Object obj = ContextUtil.getInitialContext().lookup("ManagerAmicizia/remote");
  	 				ManagerAmiciziaRemote man = (ManagerAmiciziaRemote) PortableRemoteObject.narrow(obj, ManagerAmiciziaRemote.class);
					UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
					try{
						List<Amicizia> elenco = man.getElencoRichiesteAmiciziaRicevute(u);
						if (!elenco.isEmpty())  
				        { 
							//"<p>"+e.getNome()+"</p><img src=\"image/ok.png\" height=\"20px\" width=\"20px\"><img src=\"image/no.png\" height=\"20px\" width=\"20px\" style=\"margin-left: 15px\"><br />"
							for (Amicizia e: elenco)	{ out.println("<form action=\"ConfermaAmiciziaServlet\" method=\"post\"><input type=\"text\" name=\"id\" id=\"id\" value=\""+e.getId()+"\" style=\"visibility: hidden;\" /><p>"+e.getRichiedente().getEmail()+"</p><input type=\"submit\" name=\"submit\" value=\"Conferma\" id=\"ok\"/></form><br />"); }
				        }
					}catch(SwimBeanException e){
						out.println("Non ci sono richieste di amicizia per te!");
					}
					%> 
					<br /><br /><br />
			</div>
    </td>
    </tr>
    <tr>
    <td>
   <form action="RichiestaAmiciziaServlet" method="post" onSubmit="return check()">
    		<div align="center">
					<fieldset>
						<h3><u>Invio Richiesta di Amicizia</u></h3><br>
						<br /><br />
					<label for="email">Email dell'utente:</label>
						<br />
						<input type="text" name="email" id="email" />
						<br />
					<input type="submit" name="submit" value="OK" />
					<br /><br /><br />
					</fieldset>
			</div>
		</form>
    </td>
    </tr>
<!-- QUI -->
<div align="center">
	<tr>
    <td>
    	<div align="center">
					
						<h3><u>Amici</u></h3><br>
					<%
					Object objf = ContextUtil.getInitialContext().lookup("ManagerAmicizia/remote");
  	 				ManagerAmiciziaRemote manf = (ManagerAmiciziaRemote) PortableRemoteObject.narrow(objf, ManagerAmiciziaRemote.class);
					UtenteRegistrato uf = (UtenteRegistrato) request.getSession().getAttribute("utente");
					try{
					List<UtenteRegistrato> elencof = manf.getElencoAmici(uf);
					if (elencof.size() >0)  
			        { 
						//"<p>"+e.getNome()+"</p><img src=\"image/ok.png\" height=\"20px\" width=\"20px\"><img src=\"image/no.png\" height=\"20px\" width=\"20px\" style=\"margin-left: 15px\"><br />"
						for (UtenteRegistrato e: elencof)	{ out.println(e.getEmail()+"&nbsp"+e.getNome()+"&nbsp"+e.getCognome()+"<br />"); }
			        }
					}catch(SwimBeanException e){
						out.println("Non hai amici!");
					}
					%>
					<br /><br /><br />
			</div>
    </td>
  </tr>
  </div>
</table>			
</body>
</html>