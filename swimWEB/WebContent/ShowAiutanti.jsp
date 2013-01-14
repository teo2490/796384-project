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
	<%@ page import="java.util.*" %>
	<%@ page import="swim.entitybeans.*" %>
	<body>
		<div id="content">
			<div id="subcontent">
				<div id="header">
					<div id="logo">
						&nbsp;
					</div>					
					<div id="menu">
				        <ul>
				            
				            <%
				            UtenteRegistrato ut = (UtenteRegistrato) request.getSession().getAttribute("utente");
				            if(ut==null){
				            	out.println("<li><a href=\"Home.jsp\">Home</a></li>");
				            }
				            else{
				            	out.println("<li><a href=\"HomeUtente.jsp\">Profilo</a></li>");
				            	out.println("<li><a href=\"LogoutServlet\">Logout</a></li>");
				            }
				            %>		            
				        </ul>
				    </div>
				</div>
				<br/><br/><br/><br/>
			<% 
			List<String> ris;
			ris = (List<String>)request.getSession().getAttribute("possAiutanti");
			//Devo stampare i possibili aiutanti
			for (String u: ris)	{ out.println(u+"<br />"); }
			//out.println("<p>"+u.getNome()+"&nbsp"+u.getCognome()+"&nbsp"+u.getEmail()+"&nbsp"+"</p>");
			%>
			</div>
		</div>
	</body>
</html>