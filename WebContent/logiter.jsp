<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="w3css.css">
<title>Logisitic Map Iterator</title>
</head>
<body>

	<%@ include file="/sidenav.jspf"%>
	<div style="margin-left: 15%" class="w3-row">
		<div class="w3-col m6 w3-border">
			<form action="WebReqHandler" id="form" class="w3-container ">
				<div class="w3-container w3-blue">
					<h1>Logistic map plot</h1>
				</div>
				<div class="w3-padding-large">
					<input class="w3-input w3-border" type="hidden" name="reqType"
						value="logiter" /> <label>xi</label><input
						class="w3-input w3-border" type="text"
						placeholder="The real component of initial val b/w 0 & 1"
						name="xi" required /><label>xj</label><input
						class="w3-input w3-border" type="text"
						placeholder="The imaginary component of initial val b/w 0 & 1"
						name="xj" required /> <label>ri</label> <input
						class="w3-input w3-border" type="text"
						placeholder="The real component of param r between 0 to 4.0"
						name="ri" required /><label>rj</label> <input
						class="w3-input w3-border" type="text" placeholder=The imaginary
						component of param r between 0 to 4.0"" name="rj" required /> <label>n</label>
					<input class="w3-input w3-border" type="number"
						placeholder="Number of iterations" name="n" min=100 required /> <label>name</label>
					<input class="w3-input w3-border" type="text"
						placeholder="Image Name" name="plotName" required /> <input
						class="w3-button" type="submit" />
				</div>
			</form>
		</div>
		<div class="w3-col m6 w3-padding">


			<%
				if (request.getParameter("image") != null && request.getParameter("image").trim().length() > 0) {
			%>
			<h2 class="w3-teal w3-center">Image</h2>
			<a
				href='http://localhost:8080/fyp/plotted/<%=request.getParameter("image").toString() + ".jpeg"%>''>
				<img alt="Result" id="resultimage" class="w3-image"
				src='http://localhost:8080/fyp/plotted/<%=request.getParameter("image").toString() + ".jpeg"%>'>
			</a>
			<p class="w3-green w3-padding w3-center">Nature of plot: <%= request.getParameter("nature") %></p>
			<br>
			<a
				href='http://localhost:8080/fyp/plotted/<%=request.getParameter("image").toString() + "frac.jpeg"%>''>
				<img alt="Result" id="resultimagefrac" class="w3-image"
				src='http://localhost:8080/fyp/plotted/<%=request.getParameter("image").toString() + "frac.jpeg"%>'>
			</a>
			<%
				}
			%>
			<h2 class="w3-teal w3-center">Description</h2>
			<p class="w3-pale-green">Logistic maps are the functions which
				shows chaotic nature at certain range.</p>
			<p class="w3-pale-green">
				Here f(x<sub>n</sub>) is logistic map:<br> f(x<sub>n</sub>) =
				r*x<sub>n-1</sub>*(1-x<sub>n-1</sub>)
			</p>
			<p class="w3-pale-red">The equations are represented in complex
				The value of x ranges b/w 0 and 1. That of r between 0 and 4. The
				map shows chaotic nature at r>3.6</p>

		</div>
	</div>

</body>
</html>