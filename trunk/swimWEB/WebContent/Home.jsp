<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<style type="text/css">
			@import url(css/main.css);
</style>
<script type="text/javascript">
		/* funzione Controllo Inserimento Dati*/
		function check() {
			if(document.getElementById("id").value == "" ||
				document.getElementById("password").value == "") {
		  		alert("Uno o più campi sono incompleti.");
		 		return false;
			}
			return true;
		}
		</script>
</head>
<body>
<div id="content">
			<div id="subcontent">
				<div id="header">
					<div id="logo">
						&nbsp;
					</div>					

				</div>
				<h1 align="center">BENVENUTI IN SWIMv2!</h1>
					<div id="menu">
				        <ul>
				            <li><a href="Home.jsp">Home</a></li>				            
				        </ul>
				    </div>
				<%
					String messaggio = (String) request.getAttribute("messaggio");
					if(messaggio != null) {
						out.println("<p>" + messaggio + "</p>");
					}
				%>
                <table width="795px" border="0" align="center">
  <tr>
    <td>
    	<form action="RicercaAiuto" method="post" onSubmit="return check()">
    	<div align="center">
					<fieldset>
						<h3><u>CERCA UN AIUTO</u></h3><br>
						<label for="id">Tipo di aiuto:</label>
						<br />
					<input type="text" name="helpKey" id="helpKey" width="746" />
						<br /><br />
						<input type="submit" name="submit" value="OK" />
						<br /><br /><br />
					</fieldset>
				</div>
		</form>
    </td>
    <td>
    	<form action="/swimWEB/src/servlet/LoginServlet.java" method="post">
    	<div align="center">
					<fieldset>
						<h3><u>LOGIN</u></h3><br>
						<!-- input type Desktop/TODO -->
						<label for="id">Codice utente:</label>
						<br />
						<input type="text" name="id" id="id" />
						<br />
						<label for="password">Password:</label>
						<br />
						<input type="password" name="password" id="password" />
						<br /><br />
						<input type="submit" name="submit" value="OK" />
					</fieldset>
				</div>
		</form>
    </td>
  </tr>
  <tr>
    <td>
		<div align="center"><img src="image/logo.jpeg"  width="300" height="300"/></div>
	</td>
    <td>
    	<div align="center">
    	<form action="Registrazione" method="post" onSubmit="return check()">
					<fieldset>
					<h3><u>REGISTRATI</u></h3><br>
						<label for="id">						Email:</label>
						<br />
						<input type="text" name="mail" id="mail" />
						<br />
						<label for="password">Password:</label>
						<br />
						<input type="password" name="password" id="password" />
                        <br />
                        <label for="label">Nome:</label>
                        <br />
                        <input type="text" name="nome" id="nome" />
                        <br />
                        <label for="label">Cognome:</label>
                        <br />
                        <input type="text" name="cognome" id="cognome" />
                        <br /><br />
					    <input type="submit" name="submit2" value="OK" />
					</fieldset>
		</form>
		</div>
    </td>
  </tr>
</table>
  </div>
</div>	
</body>
</html>