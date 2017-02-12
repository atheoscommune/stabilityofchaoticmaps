package crypto;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String[] args) {
		try {
			String fname;
			File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("2.bmp").getPath());
			BufferedImage img = ImageIO.read(file);

			int count = 0;

			String pwd = "abc985321c";

			int[] b = new int[10];

			ChaosImageEncrypt lme = new ChaosImageEncrypt();

			lme.encryptImage(str, img, 1, "dec.bmp");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
