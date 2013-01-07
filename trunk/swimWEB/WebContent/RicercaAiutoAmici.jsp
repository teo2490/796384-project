<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ricerca Amici</title>
<style type="text/css">
			@import url(css/main.css);
</style>
<script type="text/javascript">
		/* funzione Controllo Inserimento Dati*/
		function check() {
			if(document.getElementById("helpKey").value == "")
			{
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
	<div align="center"><h3>RICERCA AIUTO TRA GLI AMICI</h3></div>
    </td>
  </tr>
  <tr>
    <td>
		<form action="RicercaAiutoAmici" method="post" onSubmit="return check()">
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
  </tr>
</table>			
</body>
</html>