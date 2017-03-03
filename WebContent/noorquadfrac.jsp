<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="w3css.css">
<title>Noor Fractal</title>
</head>
<body>

	<%@ include file="/sidenav.jspf"%>
	<div style="margin-left: 15%" class="w3-row">
		<div class="w3-col m6">
			<form action="WebReqHandler" id="form" class="w3-border">
				<div class="w3-container w3-blue">
					<h1>Noor quadratic fractal/julia set</h1>
				</div>
				<div class="w3-padding-large">

					<input class="w3-input w3-border" type="hidden" name="reqType"
						value="noorquadfrac" /> <span>ci</span><input
						class="w3-input w3-border" type="text" name="ci"
						placeholder="The real component of c" required /><span>cj</span><input
						class="w3-input w3-border" type="text" name="cj"
						placeholder="The imaginary component of c" required /><span>n</span>
					<input class="w3-input w3-border" type="text" name="n"
						placeholder="Number of iteration to be made on each point of plane"
						required /> <span>name</span> <input class="w3-input w3-border"
						type="text" name="plotName"
						placeholder="Name of the image to be generated" required /> <span>A</span><input
						class="w3-input w3-border" type="text" name="A"
						placeholder="Here A is the control parameter b/w 0 to 1.0"
						required /><span>B</span><input class="w3-input w3-border"
						type="text" name="B"
						placeholder="Here B is the control parameter b/w 0 to 1.0"
						required /> <span>G</span><input class="w3-input w3-border"
						type="text" name="G"
						placeholder="Here G is the control parameter b/w 0 to 1.0"
						required /><span>xmin</span><input class="w3-input w3-border"
						type="text" name="xmin"
						placeholder="Left boundary on x axis. To zoom default value=-2"
						required /><span>xmax</span><input class="w3-input w3-border"
						type="text" name="xmax"
						placeholder="Right boundary on x axis. To zoom default value=2"
						required /> <span>ymin</span><input class="w3-input w3-border"
						type="text" name="ymin"
						placeholder="Left boundary on y axis. To zoom default value=-2"
						required /><span>ymax</span><input class="w3-input w3-border"
						type="text" name="ymax"
						placeholder="Right boundary on y axis. To zoom default value=2"
						required /> <span>gap</span><input class="w3-input w3-border"
						type="text" name="gap"
						placeholder="Defines resolution. 0.1<= values <=0.001" required />
					<input class="w3-button" type="submit" />


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

			<p class="w3-pale-green">Noor iteration is a 4 step feedback
				machine.It uses α,β and Y as control parameters.</p>
			<p class="w3-pale-green">
				It uses the equation:<br> {x<sub>n+1</sub> = (1-α)x<sub>n</sub>
				+ αTy<sub>n</sub>; y<sub>n</sub> = (1-β)x<sub>n</sub> + βTz<sub>n</sub>
				; z<sub>n</sub> = (1-Y)x<sub>n</sub> + YTx<sub>n</sub>;}
			</p>
			<p class="w3-pale-green">
				Noor fractals are plotted here using the equation Txn = z<sup>2</sup>
				+ c in the complex plane. Where c is input by user and remains same
				throughout the calculation. The value of each point on plane is calculated n times and
				assigned the color according to its nature.
			</p>
			<p class="w3-pale-red">The equations are represented in complex
				numbers, which is used to plot the fractal in a complex plane.</p>
			<p class="w3-pale-red">Each point(finite points are chosen b/w
				xmin and xmax with a gap of 'gap') on the plane is iterated n times
				and its characteristics is noted.</p>
			<p class="w3-pale-green">The</p>
			<p class="w3-pale-red">
			<ul class="w3-pale-red">
				<li>Black suggests that the point lies inside the julia set
					after n iterations.</li>
				<li>Red and orange suggests that the points escape before n
					iterations.</li>
				<li>white means the point escapes in initial iterations.</li>

			</ul>
			</p>

		</div>
	</div>

</body>
</html>