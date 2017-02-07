package crypto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChaosImageEncrypt {
	public static final String allZeroes = "000000000000000000000000";
	static final int ALPHA_MASK = -16777216, NEG_ALPHA_MASK = 16777215;
	static int count[] = new int[9];

	public static final float DENO = (float) Math.pow(2, 24);

	static final int ENCRYPTION = 0, DECRYPTION = 1, EXTRACT_LS8 = 255;

	public static float calculateXY0(float X01, float X02) {
		int t1 = (int) (X01 + X02);
		float t2 = (X01 + X02) - t1;
		return t2;
	}

	static public int getAlpha(int rgb) {
		return (rgb >>> 24);
	}

	static public int getBlue(int rgb) {
		return (rgb & 0x000000FF);
	}

	static public int getGreen(int rgb) {
		return ((rgb >> 8) & 0x000000FF);
	}

	static public int getRed(int rgb) {
		return ((rgb >> 16) & 0x000000FF);
	}

	static private boolean isValidKey(String key) {
		return (key == null ? false : (key.length() == 10 ? true : false));
	}

	public static float logisticMap(float I) {
		return 3.98f * I * (1 - I);
	}

	public static int writeRGB(int alpha, int R, int G, int B) {
		int x = 0;
		R &= EXTRACT_LS8;
		G &= EXTRACT_LS8;
		B &= EXTRACT_LS8;
		x ^= alpha;
		x = x << 8;
		x ^= R;
		x = x << 8;
		x ^= G;
		x = x << 8;
		x ^= B;
		return x;
	}

	float[] f = new float[24];

	char[] hexaKey = new char[20];

	char[] keyArr, B1 = new char[24], B2 = new char[24];

	int[] P = new int[24];

	float X0, Y0, X01, X02, Y01, Y02;

	public int byteXOR(int rgb, int group) {// checked
		/* group 2 and 5 */
		int xbyte = 0, i = 0, j = 0, k = 0;
		if (group == 2) {
			i = 3;
			j = 4;
			k = 5;
		} else {
			i = 6;
			j = 7;
			k = 8;
		}
		int alpha, red = 0, blue = 0, green = 0;

		/* Extract values as bytes i.e. only 8 bits */
		alpha = getAlpha(rgb);
		blue = getBlue(rgb);
		green = getGreen(rgb);
		red = getRed(rgb);

		/* XOR each color with respective keyArr byte */
		red ^= keyArr[i];
		green ^= keyArr[j];
		blue ^= keyArr[k];

		/* Write it into an int */
		rgb = writeRGB(alpha, red, green, blue);

		/* Return the encrypted pixel */
		return rgb;
	}

	public void calculateX02(int start, int end) {
		X02 = 0.0f;
		for (int i = start; i <= end; i++) {
			X02 += Integer.parseInt(hexaKey[i] + "", 16);
		}
		X02 /= 96;
	}

	public float calculateXY01(char[] B) {
		StringBuilder sbr = new StringBuilder();
		sbr.append(B);
		int i = Integer.parseInt(sbr.reverse().toString(), 2);
		return (i / DENO);

	}

	public void calculateY02() {
		float sum = 0.0f;
		char c;
		for (int k = 0; k < 24; k++) {
			c = B2[P[k]];
			if (c == '1')
				sum += Math.pow(2, k);
		}
		Y02 = Y02 / DENO;
	}

	public int complexOper(int rgb, int type, int groupno) {// checked

		/* group 3 and 6 */
		int alpha, red = 0, blue = 0, green = 0;

		/* Extract values as bytes */
		alpha = getAlpha(rgb);
		blue = getBlue(rgb);
		green = getGreen(rgb);
		red = getRed(rgb);

		int newRGB = 0, temp = 0, i = 0, j = 0, k = 0, c = 0;

		if (groupno == 3) {
			i = 3;
			j = 4;
			k = 5;
		} else {
			i = 6;
			j = 7;
			k = 8;
		}
		if (type == ENCRYPTION) {
			red = (red + (keyArr[j]) + (keyArr[i])) % 256;
			green = (green + (keyArr[j]) + (keyArr[k])) % 256;
			blue = (blue + (keyArr[k]) + (keyArr[i])) % 256;

		} else {
			red = red + 256 - keyArr[i] - keyArr[j];
			while (red < 0) {
				red += 256;
			}
			green = green + 256 - keyArr[j] - keyArr[k];
			while (green < 0) {
				green += 256;
			}

			blue = blue + 256 - keyArr[k] - keyArr[i];
			while (blue < 0) {
				blue += 256;
			}
		}
		rgb = writeRGB(alpha, red, green, blue);
		return rgb;
	}

	public char[] createB(char i, char j, char k) {
		StringBuffer sbr = new StringBuffer(allZeroes);
		int l = 0, count = 0;
		char C[] = new char[3];
		C[0] = i;
		C[1] = j;
		C[2] = k;
		String str;
		for (char d : C) {
			count = l;
			str = Integer.toBinaryString((int) d);
			for (int x = 0; x < str.length(); ++x) {
				sbr.setCharAt(count, str.charAt(x));
				count++;
			}
			l += 8;
		}

		return sbr.toString().toCharArray();

	}

	public boolean encryptImage(String key, BufferedImage img, int enctype, String output)
			throws InvalidKeyException, IOException {
		if (!isValidKey(key)) {
			throw new InvalidKeyException("Invalid Key. Either the key is null or the Key length is not 10.");
		}
		keyArr = key.toCharArray();
		setHexaKey();

		B1 = createB(keyArr[3], keyArr[4], keyArr[5]);
		X01 = calculateXY01(B1);
		calculateX02(12, 17);

		int t1 = (int) (X01 + X02);
		X0 = calculateXY0(X01, X02);
		generateRandomReal();
		generateIntegerSequence();

		B2 = createB(keyArr[0], keyArr[1], keyArr[2]);
		Y01 = calculateXY01(B2);
		calculateY02();
		Y0 = calculateXY0(Y01, Y02);

		int h = img.getHeight(), w = img.getWidth(), count = 0;
		int temp = keyArr[9];
		int rgb;
		System.out.println("w:"+w+" h:"+h+" w*h:"+(w*h));
		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; j++) {
				
				rgb = img.getRGB(i, j);
				temp = keyArr[9];

				while (temp-- > 0) {
					rgb = operation(enctype, rgb);
					Y0 = logisticMap(Y0);
				}

				img.setRGB(i, j, rgb);
				count++;
				
				if (count == 16) {
					count = 0;
					modifySessionKey();
					generateRandomReal();
					generateIntegerSequence();

					//System.out.println(Y0);
				}
			}
		ImageIO.write(img, "bmp", new File(output));
		return true;
	}

	public void generateIntegerSequence() {
		for (int i = 0; i < 24; ++i) {
			P[i] = (int) (23 * (f[i] - 0.1f) / 0.8) + 1;
		}
	}

	public void generateRandomReal() {
		int i = 0;
		while (i < 24) {
			X0 = logisticMap(X0);
			if (X0 >= 0.1 && X0 <= 0.9) {
				f[i] = X0;
				i++;
			}
		}
	}

	public int invertColor(int rgb) {// checked
		/* group 1 */
		int alpha, red, green, blue;
		red = 255 - getRed(rgb);
		green = 255 - getGreen(rgb);
		blue = 255 - getBlue(rgb);
		alpha = getAlpha(rgb);

		rgb = writeRGB(alpha, red, green, blue);

		return (rgb);
	}

	public void iterateY() {
		int b = keyArr[9];
		while (b-- > 0) {
			Y0 = logisticMap(Y0);
		}
	}

	public void modifySessionKey() {
		for (int i = 0; i < 9; i++) {
			keyArr[i] = (char) (((int) keyArr[i] + (int) keyArr[9]) % 256);
		}
		setHexaKey();
	}

	public int notByteXOR(int rgb, int type, int groupno) {// checked
		/* group 4 and 7 */
		int xbyte = 0, i, j, k;
		i = j = k = 0;

		if (groupno == 3) {
			i = 3;
			j = 4;
			k = 5;
		} else {
			i = 6;
			j = 7;
			k = 8;
		}
		int alpha, red, blue, green;

		alpha = getAlpha(rgb);
		red = getRed(rgb);
		blue = getBlue(rgb);
		green = getGreen(rgb);

		if (type == ENCRYPTION) {

			red = red ^ keyArr[i];
			green = green ^ keyArr[j];
			blue = blue ^ keyArr[k];

			red = ~red;
			green = ~green;
			blue = ~blue;

		} else {
			red = (~red) & EXTRACT_LS8;
			green = (~green) & EXTRACT_LS8;
			blue = (~blue) & EXTRACT_LS8;

			red = red ^ keyArr[i];
			green = green ^ keyArr[j];
			blue = blue ^ keyArr[k];
		}
		rgb = writeRGB(alpha, red, green, blue);

		return rgb;
	}

	public int operation(int type, int rgb) {

		if ((Y0 >= 0.10 && Y0 < 0.13) || (Y0 >= 0.34 && Y0 < 0.37) || (Y0 >= 0.58 && Y0 < 0.62)) {
			count[0]++;
			return invertColor(rgb);
		}
		if ((Y0 >= 0.13 && Y0 < 0.16) || (Y0 >= 0.37 && Y0 < 0.40) || (Y0 >= 0.62 && Y0 < 0.66)) {
			count[1]++;
			return byteXOR(rgb, 2);
		}
		if ((Y0 >= 0.16 && Y0 < 0.19) || (Y0 >= 0.40 && Y0 < 0.43) || (Y0 >= 0.66 && Y0 < 0.70)) {
			count[2]++;
			return complexOper(rgb, type, 3);
		}
		if ((Y0 >= 0.19 && Y0 < 0.22) || (Y0 >= 0.43 && Y0 < 0.46) || (Y0 >= 0.70 && Y0 < 0.74)) {
			count[3]++;
			return notByteXOR(rgb, type, 4);
		}
		if ((Y0 >= 0.22 && Y0 < 0.25) || (Y0 >= 0.46 && Y0 < 0.49) || (Y0 >= 0.74 && Y0 < 0.78)) {
			count[4]++;
			return byteXOR(rgb, 5);
		}
		if ((Y0 >= 0.25 && Y0 < 0.28) || (Y0 >= 0.49 && Y0 < 0.52) || (Y0 >= 0.78 && Y0 < 0.82)) {
			count[5]++;
			return complexOper(rgb, type, 6);
		}
		if ((Y0 >= 0.28 && Y0 < 0.31) || (Y0 >= 0.52 && Y0 < 0.55) || (Y0 >= 0.82 && Y0 < 0.86)) {
			count[6]++;
			return notByteXOR(rgb, type, 7);
		}
		if ((Y0 >= 0.31 && Y0 < 0.34) || (Y0 >= 0.55 && Y0 < 0.58) || (Y0 >= 0.86 && Y0 <= 0.90)) {
			count[7]++;
			return rgb;

		}
		count[8]++;
		return rgb;
	}

	public void setHexaKey() {
		String str = "";
		for (char c : keyArr) {
			str += Integer.toHexString((int) c);
		}
		hexaKey = str.toCharArray();

	}
}
