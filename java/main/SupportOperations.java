package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPVector;

public class SupportOperations {
	private final static String STABLE = "STABLE", CYCLIC = "CYCLIC", CHAOTIC = "CHAOTIC", CONVERGENT = "CONVERGENT";
	public static final String NOOR_ITER = "nooriter", LOG_ITER = "logiter", COMP_LOG_FRAC = "complogfrac",
			ISHI_ITER = "ishiiter", SUP_ITER = "supiter", NOOR_SER = "noorseries", NOOR_QUAD_FRAC = "noorquadfrac";

	public static double[] getDoubleArray(int size, REXP rexp) {
		double arr[] = new double[size];
		arr = rexp.asVector().at(0).asDoubleArray();
		return arr;
	}

	public static String stabilityAnalysis(double[] arr, int size) {
		if (size > 4) {
			if (Math.abs(arr[size - 1] - arr[size - 2]) < 10e-5 && Math.abs(arr[size - 3] - arr[size - 4]) < 10e-5) {
				return "Maybe " + STABLE + " " + CONVERGENT + " @" + arr[size - 1];
			} else {
				if (size >= 100) {
					int i = size - 100;
					double temp;
					HashMap<Double, Integer> hs = new HashMap();
					while (i < size) {
						temp = Math.floor(arr[i] * 1e5) / 1e5;
						System.out.println(temp);
						if (hs.containsKey(temp)) {
							hs.put(temp, hs.get(temp) + 1);
						} else
							hs.put(temp, 1);
						i++;
					}
					Collection<Integer> c = hs.values();
					Iterator<Integer> iter = c.iterator();
					int periodicity = 0;
					while (iter.hasNext()) {
						if (iter.next() >= 8)
							periodicity++;
					}
					// System.out.println(periodicity);
					if (periodicity > 2) {
						return "Maybe " + CYCLIC + " with periodicity " + periodicity;
					}
				}
			}
		}

		return "Maybe " + CHAOTIC;
	}

	public static PlotDetails analyseVector(String type, double[] arr, int size, float r, float A, float B, float G) {
		PlotDetails pd = new PlotDetails();
		pd.setType(type);
		switch (type) {
		case LOG_ITER:
			if (r < 1) {
				pd.setNature(stabilityAnalysis(arr, size));
			} else if (r < 3) {
				pd.setNature(stabilityAnalysis(arr, size));
			} else if (r < 3.44949) {
				pd.setNature("Maybe " + STABLE + " & " + CYCLIC + " with periodicity 2");
			} else if (r < 3.56995) {
				pd.setNature("Maybe " + STABLE + " & " + CYCLIC);
			} else {
				pd.setNature("Maybe " + CHAOTIC);
			}
			break;
		case SUP_ITER:
			pd.setNature(stabilityAnalysis(arr, size));
			break;
		case ISHI_ITER:
			pd.setNature(stabilityAnalysis(arr, size));
			break;
		case NOOR_ITER:
			pd.setNature(stabilityAnalysis(arr, size));
			break;
		}
		return pd;
	}
}
