package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.PlotDetails;
import main.ScriptRequHandler;
import sun.util.calendar.LocalGregorianCalendar.Date;

/**
 * Servlet implementation class WebReqHandler
 */


@WebServlet("/WebReqHandler")
public class WebReqHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_CRYPTO="img_crypto", NOOR_ITER = "nooriter", LOG_ITER = "logiter", COMP_LOG_FRAC = "complogfrac",
			ISHI_ITER = "ishiiter", SUP_ITER = "supiter", NOOR_SER = "noorseries", NOOR_QUAD_FRAC = "noorquadfrac";
	public static ScriptRequHandler srh;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebReqHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String reqType = request.getParameter("reqType");
		srh = new ScriptRequHandler();
		float xi, xj, ri, rj, ci, cj, A, B, G, N, px, py, xmin, xmax, gap, rx, ry, ymin, ymax;
		int n;
		PlotDetails pd = new PlotDetails();
		if (reqType == null)
			response.sendRedirect("HomeMenu.html");
		else {
			String imageName = request.getParameter("plotName");
			if (imageName == null || imageName.trim().length() < 1) {
				imageName = LocalDateTime.now().toString();
			}
			/*FileOutputStream out = new FileOutputStream("/plotted/"+imageName+"22");
			out.write(1);
			out.close();*/
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}*/
			switch (reqType) {
			case LOG_ITER:
				xi = Float.parseFloat(request.getParameter("xi"));
				xj = Float.parseFloat(request.getParameter("xj"));
				ri = Float.parseFloat(request.getParameter("ri"));
				rj = Float.parseFloat(request.getParameter("rj"));
				n = Integer.parseInt(request.getParameter("n"));
				pd = srh.call_LOG_ITER(xi, ri, xj, rj, n, imageName);
				imageName  = pd.getItername();
				break;
				
			case NOOR_ITER:
				xi = Float.parseFloat(request.getParameter("xi"));
				xj = Float.parseFloat(request.getParameter("xj"));
				ri = Float.parseFloat(request.getParameter("ri"));
				rj = Float.parseFloat(request.getParameter("rj"));
				n = Integer.parseInt(request.getParameter("n"));
				A = Float.parseFloat(request.getParameter("A"));
				B = Float.parseFloat(request.getParameter("B"));
				G = Float.parseFloat(request.getParameter("G"));
				pd = srh.call_NOOR_ITER(A, B, G, xi, ri, xj, rj, n, imageName);
				imageName  = pd.getItername();
				break;
			case COMP_LOG_FRAC:
				rx = Float.parseFloat(request.getParameter("rx"));
				ry = Float.parseFloat(request.getParameter("ry"));
				n = Integer.parseInt(request.getParameter("n"));
				xmin = Float.parseFloat(request.getParameter("xmin"));
				xmax = Float.parseFloat(request.getParameter("xmax"));
				ymin = Float.parseFloat(request.getParameter("ymin"));
				ymax = Float.parseFloat(request.getParameter("ymax"));
				gap = Float.parseFloat(request.getParameter("gap"));
				B = Float.parseFloat(request.getParameter("B"));
				pd=srh.call_COMP_LOG_FRAC(imageName, B, rx, ry, n, xmin, xmax, ymin, ymax, gap);
				imageName  = pd.getFracname();
				break;
			case ISHI_ITER:

				xi = Float.parseFloat(request.getParameter("xi"));
				xj = Float.parseFloat(request.getParameter("xj"));
				ri = Float.parseFloat(request.getParameter("ri"));
				rj = Float.parseFloat(request.getParameter("rj"));
				n = Integer.parseInt(request.getParameter("n"));
				A = Float.parseFloat(request.getParameter("B"));
				B = Float.parseFloat(request.getParameter("B"));
				pd = srh.call_ISHI_ITER(n, A, B, rj, xi, xj, ri, imageName);
				imageName  = pd.getItername();
				break;
			case SUP_ITER:
				xi = Float.parseFloat(request.getParameter("xi"));
				xj = Float.parseFloat(request.getParameter("xj"));
				ri = Float.parseFloat(request.getParameter("ri"));
				rj = Float.parseFloat(request.getParameter("rj"));
				n = Integer.parseInt(request.getParameter("n"));
				B = Float.parseFloat(request.getParameter("B"));
				pd = srh.call_SUP_ITER(imageName, n, B, ri, rj, xi, xj);
				imageName  = pd.getItername();
				break;
			case NOOR_QUAD_FRAC:
				A = Float.parseFloat(request.getParameter("A"));
				B = Float.parseFloat(request.getParameter("B"));
				G = Float.parseFloat(request.getParameter("G"));
				xmin = Float.parseFloat(request.getParameter("xmin"));
				xmax = Float.parseFloat(request.getParameter("xmax"));
				ymin = Float.parseFloat(request.getParameter("ymin"));
				ymax = Float.parseFloat(request.getParameter("ymax"));
				gap = Float.parseFloat(request.getParameter("gap"));
				n = Integer.parseInt(request.getParameter("n"));
				ci = Float.parseFloat(request.getParameter("ci"));
				cj = Float.parseFloat(request.getParameter("cj"));
				pd = srh.call_NOOR_FRAC(imageName, n, xmin, xmax, ymin, ymax, A, B, G, ci, cj, gap);
				imageName  = pd.getFracname();
				break;
			case IMAGE_CRYPTO:
				//File f = request.getPart(null);
				break;
			}
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect(reqType + ".jsp?image=" + imageName+"&nature="+pd.getNature());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
