package crypto;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String[] args) {
		try {
			int x = 0;
			if (x == 0) {
				File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("large.bmp").getPath());
				BufferedImage img = ImageIO.read(file);

				String pwd = "aaaaaaaaaa";
				ChaosImageEncryptModified lme = new ChaosImageEncryptModified();

				lme.encryptImage(pwd, img, 0, "enc.bmp");

				// ImageIO.write(img, ".bmp", new File("my"));
				// JFrame frame = new JFrame();
				// frame.getContentPane().setLayout(new FlowLayout());
				// frame.getContentPane().add(new JLabel(new ImageIcon(img)));
				// frame.pack();
				// frame.setVisible(true);
				
				
				
			} else {
				{
					File file = new File(ChaosImageEncrypt.class.getClassLoader().getResource("enc.bmp").getPath());
					BufferedImage img = ImageIO.read(file);

					String pwd = "aaaaaaaaaa";
					ChaosImageEncryptModified lme = new ChaosImageEncryptModified();

					lme.encryptImage(pwd, img, 1, "dec.bmp");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
