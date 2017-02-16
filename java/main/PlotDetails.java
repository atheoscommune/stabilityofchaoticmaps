package main;

/**
 * This class stores the details about the plot generated.
 * 
 * @author prince
 * @since February-2017
 */
public class PlotDetails {
	/**
	 * Defines the nature of plot i.e. Convergent , stable, cyclic, chaotic or
	 * where the plot is convergent or cyclic.
	 */
	private String nature;
	/**
	 * It stores the name of the jpeg iteration image plotted.
	 */
	private String itername;
	/**
	 * It stores the name of the jpeg julia image plotted.
	 */
	private String fracname;
	/**
	 * It stores the type of plot viz. logistim map, noor iteration, ishikawa
	 * iteration etc.
	 */
	private String type;

	/**
	 * @return Type of the plot
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter for type of plot
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return nature of the plot
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * Setter for namture of plot
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * @return name of the iteration plot
	 */
	public String getItername() {
		return itername;
	}

	/**
	 * Setter for iteration name
	 */
	public void setItername(String itername) {
		this.itername = itername;
	}

	/**
	 * @return name of the fractal plot
	 */
	public String getFracname() {
		return fracname;
	}

	/**
	 * Setter for fractal name
	 */
	public void setFracname(String fracname) {
		this.fracname = fracname;
	}

}
