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
<script type="text/javascript">
		/* funzione Controllo Inserimento Dati*/
		function check() {
			if(document.getElementById("nome").value == "" ||
				document.getElementById("desc").value == "") {
		  		alert("Uno o più campi sono incompleti.");
		 		return false;
			}
			return true;
		}
</script>
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
	<div align="center"><h3>GESTIONE ABILITA'</h3></div>
    </td>
  </tr>
  <tr>
<!-- QUI -->
    <td>
    	<div align="center">
					
						<h3><u>Abilita'</u></h3><br>
					<%
				 	Object obj = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
					ManagerAbilitaRemote man = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obj, ManagerAbilitaRemote.class);
					UtenteRegistrato u1 = (UtenteRegistrato) request.getSession().getAttribute("utente");
					List<Abilita> elenco = null;
					try{
						elenco = man.getAbilitaUtente(u1);
						if (!elenco.isEmpty()) 
				        { 
							for (Abilita e: elenco)	{ out.println("<p>"+e.getNome()+"</p>"); }
				        }    
					}catch(SwimBeanException e){
						out.println("L'utente non possiede ancora abilita'!");	
					}catch(NullPointerException e){
						out.println("L'utente non possiede ancora abilita'!");
					}
					   
					%>
					<br /><br /><br />
			</div>
    </td>
    </tr>
    <tr>
    <td>
   <form action="AggiungiAbilitaServlet" method="post">
    		<div align="center">
					<fieldset>
						<h3><u>Aggiungi abilita'</u></h3><br>
						<br /><br />
						
					
					<%
					Object obja = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
					ManagerAbilitaRemote mana = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obja, ManagerAbilitaRemote.class);
					UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
					List<Abilita> elencoa = mana.getElencoAbilitaNonMie(u);
					if (!elencoa.isEmpty()) 
			        { 
						out.println("<select name=\"abilita\">");
						for (Abilita e: elencoa)	{ 
							out.println("<option value = \""+Integer.toString(e.getId())+"\" id=\"abil\" >"+e.getNome()+"</option>");
							}
						out.println("</select>");
						out.println("<input type=\"submit\" name=\"submit\" value=\"OK\" />");
			        } 
					else {
						out.println("<p>L'utente possiede tutte le abilità esistenti nel sistema</p>");
					}
					%>
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
  </div>
</table>			
</body>
</html>