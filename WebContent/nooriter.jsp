<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="w3css.css">
<title>Noor Iter</title>
</head>
<body>

	<%@ include file="/sidenav.jspf"%>
	<div style="margin-left: 15%" class="w3-row">
		<div class="w3-col m6">
			<form action="WebReqHandler" id="form" class="w3-container ">
				<div class="w3-container w3-blue">
					<h1>Noor plot</h1>
				</div>
				<div class="w3-padding-large">
					<input class="w3-input w3-border" placeholder="" type="hidden"
						name="reqType" value="nooriter" /> <label>A</label><input
						class="w3-input w3-border"
						placeholder="Here A is the control parameter b/w 0 to 1.0"
						type="text" name="A" required /><label>B</label><input
						class="w3-input w3-border"
						placeholder="Here B is the control parameter b/w 0 to 1.0"
						type="text" name="B" required /> <label>G</label><input
						class="w3-input w3-border"
						placeholder="Here G is the control parameter b/w 0 to 1.0"
						type="text" name="G" required /><label>xi</label><input
						class="w3-input w3-border"
						placeholder="The real component of initial val b/w 0 & 1"
						type="text" name="xi" required /><label>xj</label><input
						class="w3-input w3-border"
						placeholder="The imaginary component of initial val b/w 0 & 1"
						type="text" name="xj" required /><label>ri</label> <input
						class="w3-input w3-border"
						placeholder="The real component of param r "
						type="text" name="ri" required /><label>rj</label> <input
						class="w3-input w3-border"
						placeholder="The imaginary component of param r "
						type="text" name="rj" required /> <label>n</label> <input
						class="w3-input w3-border" placeholder="Number of iterations"
						type="number" min=100 name="n" required /> <label>name</label> <input
						class="w3-input w3-border" placeholder="Image Name" type="text"
						name="plotName" required /> <input class="w3-button"
						type="submit" />
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
			<%
				}
			%>
			<p class="w3-green w3-padding w3-center">Nature of plot: <%= request.getParameter("nature") %></p>
			<h2 class="w3-teal w3-center">Description</h2>
			<p class="w3-pale-green">Noor iteration is a 4 step feedback
				machine.It uses α,β and Y as control parameters.</p>
			<p class="w3-pale-green">
				It uses the equation:<br> {x<sub>n+1</sub> = (1-α)x<sub>n</sub>
				+ αTy<sub>n</sub>; y<sub>n</sub> = (1-β)x<sub>n</sub> + βTz<sub>n</sub>
				; z<sub>n</sub> = (1-Y)x<sub>n</sub> + YTx<sub>n</sub>;}
			</p>
			<p class="w3-pale-green">
				Here T(x<sub>n</sub>) is logistic map:<br> T(x<sub>n</sub>) =
				r*x<sub>n-1</sub>*(1-x<sub>n-1</sub>)<br> N.B.:Tx can be any
				self mapping function.
			</p>
			<p class="w3-pale-red">The equations are represented in complex
				The value of x ranges b/w 0 and 1.</p>

		</div>
	</div>

</body>
</html>