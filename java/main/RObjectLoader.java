package main;

import org.rosuda.JRI.Rengine;

public class RObjectLoader {
	static Rengine re = null;

	public static Rengine getRObject() {
		if (!Rengine.versionCheck()) {
			return null;
		}
		if (re == null)
			re = new Rengine(new String[] { "--no-save" }, false, null);
		if (!re.waitForR()) {
			System.out.println("Cannot load R");
			return null;
		} else
			return re;
	}
}