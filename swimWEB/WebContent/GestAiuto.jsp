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
	<div align="center"><h3>GESTIONE AIUTI</h3></div>
    </td>
  </tr>
  <tr>
<!-- QUI -->
    <td>
    	<div align="center">
					
						<h3><u>Richieste di Aiuto</u></h3><br>
					<%
					Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
  	 				ManagerAiutoRemote man = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
					UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
					try{
						List<Aiuto> elenco = man.getElencoRichiesteAiutoRicevuteNonConfermate(u);
						if (!elenco.isEmpty())  
				        { 
							//"<p>"+e.getNome()+"</p><img src=\"image/ok.png\" height=\"20px\" width=\"20px\"><img src=\"image/no.png\" height=\"20px\" width=\"20px\" style=\"margin-left: 15px\"><br />"
							for (Aiuto e: elenco)	{ out.println("<form action=\"ConfermaAiutoServlet\" method=\"post\"><input type=\"text\" name=\"id\" id=\"id\" value=\""+e.getId()+"\" style=\"visibility: hidden;\" /><p>"+e.getTipo()+"&nbsp"+e.getRichiedente().getEmail()+"</p><input type=\"submit\" name=\"submit\" value=\"Conferma\" id=\"ok\"/><br />"); }
				        }
					}catch(SwimBeanException e){
						out.println("Non ci sono richieste di aiuto per te!");
					}
					%>
					<br /><br /><br />
			</div>
    </td>
    </tr>
    <tr>
    <td>
   <form action="RichiestaAiutoServlet" method="post" onSubmit="return check()">
    		<div align="center">
					<fieldset>
						<h3><u>Invio Richiesta di Aiuto</u></h3><br>
						<br /><br />
					<label for="email">Email dell'aiutante scelto:</label>
						<br />
						<input type="text" name="email" id="email" />
						<br />
					<select name="abilita">
					<%
					Object obja = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
					ManagerAbilitaRemote mana = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obja, ManagerAbilitaRemote.class);
					List<Abilita> elencoa = mana.getElencoAbilita();
					if (elencoa.size() >0) 
			        { 
						for (Abilita e: elencoa)	{ out.println("<option value = \""+Integer.toString(e.getId())+"\" id=\"abil\" >"+e.getNome()+"</option>"); }
			        } 
					%>
					</select>
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
					
						<h3><u>Feedback</u></h3><br>
					<%
					Object objf = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
  	 				ManagerAiutoRemote manf = (ManagerAiutoRemote) PortableRemoteObject.narrow(objf, ManagerAiutoRemote.class);
					UtenteRegistrato uf = (UtenteRegistrato) request.getSession().getAttribute("utente");
					try{
					List<Aiuto> elencof = manf.getElencoRichiesteAiutoFatteConfermateSenzaFeedback(uf);
					if (elencof.size() >0)  
			        { 
						//"<p>"+e.getNome()+"</p><img src=\"image/ok.png\" height=\"20px\" width=\"20px\"><img src=\"image/no.png\" height=\"20px\" width=\"20px\" style=\"margin-left: 15px\"><br />"
						for (Aiuto e: elencof)	{ out.println("<form action=\"FeedbackServlet\" method=\"post\"><input type=\"text\" name=\"id\" id=\"id\" value=\""+e.getId()+"\" style=\"visibility: hidden;\" /><p>"+e.getTipo()+"&nbsp"+e.getRichiesto().getEmail()+"</p><input type=\"text\" name=\"feedback\" id=\"feedback\" /><input type=\"submit\" name=\"submit\" value=\"Conferma\" id=\"ok\"/><br />"); }
			        }
					}catch(SwimBeanException e){
						out.println("Non hai ricevuto aiuto per cui lasciare un feedback!");
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