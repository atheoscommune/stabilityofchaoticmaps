<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="w3css.css">
<title>Image Cryptography</title>
</head>
<body>

	<%@ include file="/sidenav.jspf"%>
	<div style="margin-left: 15%" class="w3-row">
		<div class="w3-col m6 w3-border">
			<form action="WebReqHandler" id="form" class="w3-container "
				method="POST" enctype="multipart/form-data">
				<div class="w3-container w3-blue">
					<h1>Image cryptography using logistic map</h1>
				</div>
				<div class="w3-padding-large">
					<input class="w3-input w3-border" type="hidden" name="reqType"
						value="img_crypto" /> <label>Choose File:</label><input
						class="w3-input w3-border" type="file" name="image" required /> <label>Key</label>
					<input class="w3-input w3-border" type="pwd"
						placeholder="Must be exactly 10 characters long" name="pwd" required />
					<label>Encrypted/decrypted image name</label> <input
						class="w3-input w3-border" type="text" placeholder="Image Name"
						name="imgname" required /> <input class="w3-button"
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
		</div>
	</div>

</body>
</html>