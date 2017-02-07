<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="w3css.css">
<title>Superior Iter</title>
</head>
<body>

	<%@ include file="/sidenav.jspf"%>
	<div style="margin-left: 15%" class="w3-row">
		<div class="w3-col m6">
			<form action="WebReqHandler" id="form" class="w3-container	">
				<div class="w3-container w3-blue">
					<h1>Superior plot</h1>
				</div>
				<div class="w3-padding-large">
					<input class="w3-input w3-border" type="hidden" name="reqType"
						value="supiter" /> <label>B</label><input
						class="w3-input w3-border" type="text"
						placeholder="Here B is the control parameter b/w 0 to 1.0"
						name="B" required /> <label>xi</label><input
						class="w3-input w3-border" type="text"
						placeholder="The real component of initial val b/w 0 & 1"
						name="xi" required /><label>xj</label><input
						class="w3-input w3-border" type="text"
						placeholder="The imaginary component of initial val b/w 0 & 1"
						name="xj" required /> <label>ri</label> <input
						class="w3-input w3-border" type="text"
						placeholder="The real component of param r"
						name="ri" required /><label>rj</label> <input
						class="w3-input w3-border" type="text"
						placeholder="The imaginary component of param r"
						name="rj" required /> <label>n</label> <input
						class="w3-input w3-border" type="text"
						placeholder="No. of iterations to be performed" name="n" required /><label>name</label>
					<input class="w3-input w3-border" type="text"
						placeholder="Image name" name="plotName" required /> <input
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
			<%
				}
			%>
			<h2 class="w3-teal w3-center">Description</h2>
			<p class="w3-pale-green">
				Superior or Mann Iterations are are two step feedback machines. It
				takes x as input and produces output. It also takes two parameters β
				and r.<br> 0
				<= β <=1 and 0 <= r <= 4 
			</p>
			<p class="w3-pale-green">
				It uses the equation: <br> x<sub>n+1</sub>=β*f(x<sub>n</sub>) +
				(1-β)*x<sub>n</sub>
			</p>
			<p class="w3-pale-green">
				n iterations are performed to observe the nature of the map.</p>


		</div>
	</div>

</body>
</html>