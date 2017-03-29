package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPVector;

/**
 * This class is responsible to bridge and translate the gaps between values
 * returned by R and that being processed by Java. It carries out translation of
 * R data types into Java data type by using the Rengine API. It provides
 * operations to find properties of a plot.
 * 
 * @author prince
 * @since January-2016
 */
public class SupportOperations {
	/**
	 * String literal for the nature of plot.
	 */
	private final static String STABLE = "STABLE", CYCLIC = "CYCLIC", CHAOTIC = "CHAOTIC", CONVERGENT = "CONVERGENT";
	/**
	 * String literal for the type of plot.
	 */
	public static final String NOOR_ITER = "nooriter", LOG_ITER = "logiter", COMP_LOG_FRAC = "complogfrac",
			ISHI_ITER = "ishiiter", SUP_ITER = "supiter", NOOR_SER = "noorseries", NOOR_QUAD_FRAC = "noorquadfrac";

	/**
	 * 
	 * @param size
	 *            Size of R vector
	 * @param rexp
	 *            Superclass of R objects containing the R vector
	 * @return An array of double values extracted from rexp
	 */
	public static double[] getDoubleArray(int size, REXP rexp) {
		double arr[] = new double[size];
		arr = rexp.asVector().at(0).asDoubleArray();
		return arr;
	}

	/**
	 * Finds the nature of iteration.
	 * 
	 * @param arr
	 *            An array of doubles not more than 100 values.
	 * @param size
	 *            size of the array arr
	 * @return The analysed nature of the vector.
	 */
	public static String stabilityAnalysis(double[] arr, int size) {
		if (size > 5) {
			/*
			 * if size of array is more than five then we see the difference
			 * between the values. If the values have gaps less than 10e-5 then
			 * we say it is convergent at that value.
			 */
			if (arr[size - 1] > 1) {
				return "11Does not exist";
			} else {

				if (Math.abs(arr[size - 1] - arr[size - 2]) < 10e-5
						&& Math.abs(arr[size - 3] - arr[size - 4]) < 10e-5) {
					return "Maybe " + STABLE + " " + CONVERGENT + " @" + arr[size - 1];
				} else {

					/*
					 * Otherwise we attempt to find the of there exist any
					 * cyclic nature. If so then the periodicity.
					 */
					if (size >= 100) {
						int i = size - 100;
						double temp;
						HashMap<Double, Integer> hs = new HashMap();
						while (i < size) {
							temp = Math.floor(arr[i] * 1e5) / 1e5;
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
						if (periodicity > 2) {
							return "Maybe " + CYCLIC + " with periodicity " + periodicity;
						}
					}
				}
			}

			/*
			 * If no stability point or cyclic nature is found then return
			 * chaotic
			 */
			return "Maybe " + CHAOTIC;
		}
		return "";
	}

	/**
	 * This method analyses the nature of plot using the apriori information of
	 * piccard iteration of logistic map. For other iteration it uses the
	 * stabilityAnalysis(arr,size) method.
	 * 
	 * @param type
	 *            The type of map/iteration
	 * @param arr
	 *            An array of double values showing the plots of xn.
	 * @param A
	 *            A parameter b/w 0 and 1
	 * @param B
	 *            A parameter b/w 0 and 1
	 * @param G
	 *            A parameter b/w 0 and 1
	 * @param r
	 *            A parameter whose value is subject to the nature of iteration.
	 *            For Piccard it exists only for interval 0 to 4.0
	 * @param size
	 *            The size of the array arr
	 * @return An object of {@link PlotDetails} containing the information about
	 *         the plot.
	 */
	public static PlotDetails analyseVector(String type, double[] arr, int size, float r, float A, float B, float G) {
		PlotDetails pd = new PlotDetails();
		pd.setType(type);
		/*if(arr[size-1]>1.0 || arr[size-1]<1.0){
			pd.setNature("Does not exist");
			System.out.println(arr[size-1]);
		}else{*/
			switch (type) {
			case LOG_ITER:
				if (r < 3 || arr[size - 1] > 1.0) {
					pd.setNature(stabilityAnalysis(arr, size));
				} else if (r < 3.44949) {
					pd.setNature("Maybe " + STABLE + " and " + CYCLIC + " with periodicity 2");
				} else if (r < 3.56995) {
					pd.setNature("Maybe " + STABLE + " and " + CYCLIC);
				} else if (r < 4.00000000000000000000000) {
					pd.setNature("Maybe " + CHAOTIC);
				} else if (r < 4.000000000000000000000001) {
					pd.setNature("Does Not Exist ");
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
		/*}*/
		return pd;
	}
}
