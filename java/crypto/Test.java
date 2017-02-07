package crypto;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String[] args) {
		try {
			File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("enc.bmp").getPath());
			BufferedImage img = ImageIO.read(file);

			int count = 0;

			String str = "ghulamprin";

			int[] b = new int[10];

			/*for (int i = 0; i < 256 ; i++) {
				System.out.println("ascii: " + i + " char " + (char) i);

			}*/
			ChaosImageEncrypt lme = new ChaosImageEncrypt();

			lme.encryptImage(str, img, 1, "dec.bmp");
			/*
			 * System.out.println(lme.getRed(-14798917) + " " +
			 * lme.getGreen(-14798917) + " " + lme.getBlue(-14798917));
			 * System.out.println(Integer.parseInt("00011110", 2));
			 * System.out.println(Integer.parseInt("00101111", 2));
			 * System.out.println(Integer.parseInt("10111011", 2));
			 */

			/*
			 * for (int cb : ChaosImageEncrypt.count) { System.out.println(cb);
			 * }
			 */
			// System.out.println(lme.keyArr[6] + " " + lme.keyArr[7] + " " +
			// lme.keyArr[8]);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
