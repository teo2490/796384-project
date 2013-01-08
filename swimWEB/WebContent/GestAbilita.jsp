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
<table width="795px" border="0" align="center">
  <tr>
    <td>
	<div align="center"><h3>GESTIONE ABILITA'</h3></div>
    </td>
  </tr>
  <tr>
    <td>
    	<div align="center">
					
						<h3><u>Abilita'</u></h3><br>
					<%
					Object obj = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
					ManagerAbilitaRemote man = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obj, ManagerAbilitaRemote.class);
					UtenteRegistrato u1 = (UtenteRegistrato) request.getAttribute("utente");
					List<Abilita> elenco = man.getAbilitaUtente(u1);
					if (elenco.size() >0) 
			        { 
						for (Abilita e: elenco)	{ out.println("<p>"+e.getNome()+"</p>"); }
			        }   
					%>
					<br /><br /><br />
			</div>
    </td>
    <td>
   <form action="AggiungiAbilitaServlet" method="post" onSubmit="return check()">
    		<div align="center">
					<fieldset>
						<h3><u>Aggiungi abilita'</u></h3><br>
						<br /><br />
						
					<select name="abilita">
					<%
					Object obja = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
					ManagerAbilitaRemote mana = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obja, ManagerAbilitaRemote.class);
					UtenteRegistrato u = (UtenteRegistrato) request.getAttribute("utente");
					List<Abilita> elencoa = mana.getElencoAbilitaNonMie(u);
					if (elenco.size() >0) 
			        { 
						for (Abilita e: elenco)	{ out.println("<option value = '"+e.getId()+"' >"+e.getNome()); }
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
</table>			
</body>
</html>