package crypto;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String[] args) {
		try {
			int x = 1;
			if (x == 0) {
				File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("raw.bmp").getPath());
				BufferedImage img = ImageIO.read(file);

				String pwd = "PRINCE123g";
				ChaosImageEncrypt lme = new ChaosImageEncrypt();

				lme.encryptImage(pwd, img, 0, "enc.bmp");
			}else{
				{
					File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("enc.bmp").getPath());
					BufferedImage img = ImageIO.read(file);

					String pwd = "PRINCE123g";
					ChaosImageEncrypt lme = new ChaosImageEncrypt();

					lme.encryptImage(pwd, img, 1, "dec.bmp");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
