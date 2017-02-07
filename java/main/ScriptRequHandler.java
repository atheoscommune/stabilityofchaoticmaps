package main;

import java.net.URL;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXP;

public class ScriptRequHandler {

	private Rengine re;
	private String plotName, imageName;
	private org.rosuda.JRI.REXP rexp;

	public PlotDetails call_LOG_ITER(float xi, float ri, float xj, float rj, int n, String name) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			float x, r;
			x = (float) Math.sqrt(xi * xi + xj * xj);
			r = (float) Math.sqrt(ri * ri + rj * rj);

			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");
			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/LOG_ITER.R");

			/* Set the input parameters value */
			re.eval("r=" + r);
			re.eval("x=" + x);
			re.eval("n=" + n);

			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			double d[] = SupportOperations.getDoubleArray(100, rexp);
			PlotDetails pd = SupportOperations.analyseVector("logiter", d, 100, r, 0, 0, 0);
			pd.setItername(name);
			/* Now close the device */
			re.eval("dev.off()");
			pd.setFracname(call_PIC_FRAC(ri, rj, n, name));

			return pd;
		} else {
			System.err.println("Error creating RObject");
			return null;
		}
	}

	public String call_PIC_FRAC(float ri, float rj, int n, String name) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			float x, r;
			r = (float) Math.sqrt(ri * ri + rj * rj);

			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + "frac.jpeg')");
			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/PIC_FRAC.R");

			/* Set the input parameters value */
			re.eval("r=" + r);
			re.eval("n=" + n);

			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");

			/* Now close the device */
			re.eval("dev.off()");
			return name;
		} else {
			System.err.println("Error creating RObject");
			return null;
		}
	}

	public PlotDetails call_NOOR_ITER(float A, float B, float G, float xi, float ri, float xj, float rj, int n,
			String name) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			float x, r;
			x = (float) Math.sqrt(xi * xi + xj * xj);
			r = (float) Math.sqrt(ri * ri + rj * rj);
			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");

			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/NOOR_ITER.R");

			/* Set the input parameters value */
			re.eval("r=" + r);
			re.eval("x=" + x);
			re.eval("n=" + n);
			re.eval("A=" + A);
			re.eval("B=" + B);
			re.eval("G=" + G);
			/* Run the script */

			rexp = re.eval("source('" + url.getPath() + "')");

			double d[] = SupportOperations.getDoubleArray(100, rexp);
			PlotDetails pd = SupportOperations.analyseVector("nooriter", d, 100, r, A,B,G);
			pd.setItername(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

	public PlotDetails call_COMP_LOG_FRAC(String name, float B, float rx, float ry, int n, float xmin, float xmax,
			float ymin, float ymax, float gap) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			/* Open a jpeg device */

			float r = (float) Math.sqrt(ry * ry + rx * rx);
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");

			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/COMP_LOG_FRAC.R");

			/* Set the input parameters value */
			re.eval("rx=" + rx);
			re.eval("ry=" + ry);

			re.eval("B=" + B);
			re.eval("n=" + n);
			re.eval("xmin=" + xmin);
			re.eval("xmax=" + xmax);
			re.eval("ymin=" + ymin);
			re.eval("ymax=" + ymax);
			re.eval("gap=" + gap);// here gap is used in by=... in R
			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			PlotDetails pd = new PlotDetails();
			pd.setFracname(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

	public PlotDetails call_NOOR_SER(String name, float rx, float ry, int n, float xmin, float xmax, float ymin,
			float ymax, float gap) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");

			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/NOOR_SER.R");

			/* Set the input parameters value */
			re.eval("rx=" + rx);
			re.eval("ry=" + ry);
			re.eval("n=" + n);
			re.eval("xmin=" + xmin);
			re.eval("xmax=" + xmax);
			re.eval("ymin=" + ymin);
			re.eval("ymax=" + ymax);
			re.eval("gap=" + gap);// here gap is used in by=... in R
			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			PlotDetails pd = new PlotDetails();
			pd.setItername(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

	public PlotDetails call_SUP_ITER(String name, int n, float B, float ri, float rj, float xi, float xj) {

		re = RObjectLoader.getRObject();
		if (re != null) {
			float x, r;
			x = (float) Math.sqrt(xi * xi + xj * xj);
			r = (float) Math.sqrt(ri * ri + rj * rj);
			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");

			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/SUP_ITER.R");

			/* Set the input parameters value */
			re.eval("x=" + x);
			re.eval("r=" + r);
			re.eval("n=" + n);
			re.eval("B=" + B);
			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			double d[] = SupportOperations.getDoubleArray(100, rexp);
			PlotDetails pd = SupportOperations.analyseVector("supiter", d, 100, r, 0, B, 0);
			pd.setItername(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

	public PlotDetails call_ISHI_ITER(int n, float A, float B, float rj, float xi, float xj, float ri, String name) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			float x, r;
			x = (float) Math.sqrt(xi * xi + xj * xj);
			r = (float) Math.sqrt(ri * ri + rj * rj);
			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");

			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/ISHI_ITER.R");

			/* Set the input parameters value */
			re.eval("r=" + r);
			re.eval("n=" + n);
			re.eval("x=" + x);
			re.eval("A=" + A);
			re.eval("B=" + B);

			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			double d[] = SupportOperations.getDoubleArray(100, rexp);
			PlotDetails pd = SupportOperations.analyseVector("ishiiter", d, 100, r, A, B, 0);
			pd.setItername(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

	public PlotDetails call_NOOR_FRAC(String name, int n, float xmin, float xmax, float ymin, float ymax, float A,
			float B, float G, float ci, float cj, float gap) {
		re = RObjectLoader.getRObject();
		if (re != null) {
			/* Get the url of the script to be executed */
			URL url = this.getClass().getClassLoader().getResource("/NOORQUADFRAC.R");
			/* Open a jpeg device */
			re.eval("jpeg('WebContent/plotted/" + name + ".jpeg')");
			/* Set the input parameters value */
			re.eval("c=" + ci + " + " + cj + "i");
			re.eval("n=" + n);
			re.eval("G=" + G);
			re.eval("A=" + A);
			re.eval("B=" + B);
			re.eval("xmin=" + xmin);
			re.eval("xmax=" + xmax);
			re.eval("ymin=" + ymin);
			re.eval("ymax=" + ymax);
			re.eval("gap=" + gap);
			/* Run the script */
			rexp = re.eval("source('" + url.getPath() + "')");
			PlotDetails pd = new PlotDetails();
			pd.setFracname(name);
			/* Now close the device */
			re.eval("dev.off()");
			return pd;
		} else {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			return null;
		}
	}

}
