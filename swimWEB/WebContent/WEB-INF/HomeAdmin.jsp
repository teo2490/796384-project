<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Home Amministratore</title>
		<style type="text/css">
			@import url(css/main.css);
		</style>	
	</head>
	<body>
		<div id="content">
			<div id="subcontent">
				<div id="header">
					<div id="logo">
						&nbsp;
					</div>					
					<div id="menu">
				        <ul>
				            <li><a href="Main">Main</a></li>
				            <li><a href="Logout">Logout</a></li>				            
				        </ul>
				    </div>
				</div>
				<%
					String messaggio = (String) request.getAttribute("messaggio");
					if(messaggio != null) {
						out.println("<p>" + messaggio + "</p>");
					}
				%>
				<p>Ciao ${sessionScope.utente.nome} ${sessionScope.utente.cognome}</p>				
				<ul>
					<li><a href="gestAbilitaAdmin">Gestione Abilita'</a></li>
				</ul>
			</div>
		</div>
	</body>
</html>