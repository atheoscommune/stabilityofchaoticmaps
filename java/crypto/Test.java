package crypto;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String[] args) {
		try {
			String fname;
			File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("raw.bmp").getPath());
			BufferedImage img = ImageIO.read(file);

			int count = 0;
			String pwd = "";
			String pwdHexa = "00000000000000000000";

			for (int i = 0; i < 20; i += 2) {
				pwd += (char) (Integer.parseInt(pwdHexa.substring(i, i + 2), 16));
			}
			int[] b = new int[10];
			System.out.println("done");
			ChaosImageEncrypt lme = new ChaosImageEncrypt();

			lme.encryptImage(pwd, img, 0, "enc.bmp");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
