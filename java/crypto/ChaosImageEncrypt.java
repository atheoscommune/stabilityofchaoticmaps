package crypto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import sun.awt.windows.ThemeReader;

/**
 *
 * This class is an implementation of the paper Image encryption using chaotic
 * logistic map (Published 2006) by - N.K. Pareek , Vinod Patidar , K.K. Sud.
 *
 * This class is used to encrypt an image, preferably lossless compressed image,
 * by using Picard Iteration of Logistic map. It encrypts individual pixel's RGB
 * value.
 *
 * @author Prince Rachit Sinha
 * @since February-2017
 * @version 1.0
 */

public class ChaosImageEncrypt {
	/**
	 * A String of 24 zeroes.
	 *
	 */
	public static final String allZeroes = "000000000000000000000000";
	/**
	 * Contains value 2.0<sup>24</sup>
	 */
	public static final float DENO = (float) Math.pow(2, 24);

	/**
	 * Constant value 0 for encryption
	 */
	static final int ENCRYPTION = 0;

	/**
	 * Constant value 1 for Decryption
	 */
	static final int DECRYPTION = 1;
	/**
	 * This constant contains an integer value equivalent to 11111111 which when
	 * XORed with an integer extracts the last 8 bits.
	 */
	static final int EXTRACT_LS8 = 255;

	/**
	 * This method finds the value of ( X01+X02 )mod 1.
	 * 
	 * @param X01
	 *            First real number
	 * @param X02
	 *            Second real number
	 * @return floating point number less than 1.0
	 */
	public static float calculateXY0(float X01, float X02) {
		int t1 = (int) (X01 + X02);
		float t2 = (X01 + X02) - t1;
		return t2;
	}

	/**
	 * It extracts and returns the integer value equivalent of 8 bit Alpha value
	 * of the given pixel.
	 * 
	 * @param rgb
	 *            Contains equivalent integer value of argb value of a pixel.
	 */
	static public int getAlpha(int rgb) {
		return (rgb >>> 24);
	}

	/**
	 * It extracts and returns the integer value equivalent of 8 bit Blue color
	 * value of the given pixel.
	 * 
	 * @param rgb
	 *            Contains equivalent integer value of argb value of a pixel.
	 */
	static public int getBlue(int rgb) {
		return (rgb & 0x000000FF);
	}

	/**
	 * It extracts and returns the integer value equivalent of 8 bit green color
	 * value of the given pixel.
	 * 
	 * @param rgb
	 *            Contains equivalent integer value of argb value of a pixel.
	 */
	static public int getGreen(int rgb) {
		return ((rgb >> 8) & 0x000000FF);
	}

	/**
	 * It extracts and returns the integer value equivalent of 8 bit red color
	 * value of the given pixel.
	 * 
	 * @param rgb
	 *            Contains equivalent integer value of argb value of a pixel.
	 */
	static public int getRed(int rgb) {
		return ((rgb >> 16) & 0x000000FF);
	}

	/**
	 * This method is used to validate the ASCII key by the user. The key should
	 * be of length 10.
	 * 
	 * @param key
	 *            A String given by the user
	 * @return True if the key is valid, false otherwise.
	 */

	static private boolean isValidKey(String key) {
		return (key == null ? false : (key.length() == 10 ? true : false));
	}

	/**
	 * An implementation of Logistic Map Picard Iteration which is x<sub>n</sub>
	 * = x<sub>n-1</sub>*r*(1-x<sub>n-1</sub>). Here r is taken as
	 * 3.999999999999999 for maximum chaotic nature.
	 * 
	 * @param I
	 *            A floating point number as x<sub>n-1</sub> to calculate the
	 *            nth iteration.
	 * @return A float after one iteration.
	 */
	public static float logisticIteration(float I) {
		return 3.999999999999999f * I * (1 - I);
	}

	/**
	 * Merge the Red, Green, Apha and Blue bits and return it as one integer.
	 * 
	 * @param alpha
	 *            The alpha value of the pixel
	 * @param R
	 *            The red value of the pixel
	 * @param G
	 *            The green value of the pixel
	 * @param B
	 *            The blue value of the pixel
	 * @return Returns the equivalent ARGB pixel value of the passed params
	 */
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

	/**
	 * An array of hexadecimal values of the ASCII key.
	 */
	char[] hexaKey = new char[20];

	char[] keyArr, B1 = new char[24], B2 = new char[24];

	/**
	 * Sequence of Random integers.
	 *
	 */
	int[] P = new int[24];

	/**
	 * Floating point number for the seed for iteration over logistic maps.
	 *
	 */
	float X0;
	/**
	 * Floating point number which determines the operation to be performed on
	 * the pixel.
	 */
	float Y0;
	/**
	 * Real number calculated by converting the key into binary and then
	 * converting to base 10. (Stores value of Step 6)
	 */
	float X01;
	/**
	 * A floating number which stores value of Step 7
	 */
	float X02;
	/**
	 * A floating number which stores value from calculation of step 11.
	 */
	float Y01;
	float Y02;

	/**
	 * Performs XOR operation as: R with keyArr[3] , G with keyArr[4] and B with
	 * keyArr[5] if the group number is 2. Performs XOR operation as: R with
	 * keyArr[6] , G with keyArr[7] and B with keyArr[8] if the group number is
	 * 5.
	 * 
	 * @param rgb
	 *            The argb value of the pixel.
	 * @param group
	 *            The group number
	 * @return An argb(int) after XOR operation
	 */
	public int byteXOR(int rgb, int group) {// checked

		int i = 0, j = 0, k = 0;
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

	/**
	 * Calculates the sum of hexaKeys in the give range and then divides it by
	 * 96. It stores the calculated value in the static X02.
	 * 
	 * @param start
	 *            The start index to run the summation loop.
	 * @param end
	 *            The end index.
	 */
	public void calculateX02(int start, int end) {
		X02 = 0.0f;
		for (int i = start; i <= end; i++) {
			X02 += Integer.parseInt(hexaKey[i] + "", 16);
		}
		X02 /= 96;
	}

	/**
	 * Calculates the X01 or Y01 according to the formula
	 * (BX<sub>2</sub>)<sub>10</sub>/2<sup>24</sup>.
	 * 
	 * @param B
	 *            An array of characters (bits)
	 */
	public float calculateXY01(char[] B) {
		StringBuilder sbr = new StringBuilder();
		sbr.append(B);
		int i = Integer.parseInt(sbr.reverse().toString(), 2);
		return (i / DENO);

	}

	/**
	 * Calculates Y02 using the formula: <img src='y02.png'>
	 */
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

	/**
	 * Performs the operation : <img src='complexoper.png'>
	 * 
	 * @param rgb
	 *            The argb value of the pixel
	 * @param type
	 *            Operation type i.e. encryption/decryption
	 * @param groupno
	 *            The group number of operation.
	 * @return An argb value as int.
	 */
	public int complexOper(int rgb, int type, int groupno) {// checked

		/* group 3 and 6 */
		int alpha, red = 0, blue = 0, green = 0;

		/* Extract values as bytes */
		alpha = getAlpha(rgb);
		blue = getBlue(rgb);
		green = getGreen(rgb);
		red = getRed(rgb);

		int i = 0, j = 0, k = 0, c = 0;

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

	/**
	 * Creates B1 or B2. This method converts the 3 session keys into binary
	 * array of characters.
	 * 
	 * @param i
	 *            Value of session key #1
	 * @param j
	 *            Value of session key #2
	 * @param k
	 *            Value of session key #3
	 * @return A character array of bits.
	 */

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

	/**
	 * This function is the sole function the user is expected to call when
	 * using this algorithm to encrypt the image.
	 * 
	 * @param key
	 *            The ASCII key string of length 10
	 * @param img
	 *            An object of BufferedImage to be encrypted/decrypted.
	 * @param enctype
	 *            Type pf operation i.e. encryption(0) or decryption(1)
	 * @param output
	 *            enc/dec image name.
	 * @return A boolean value showing the status of operation. True for success
	 *         and false for failure.
	 */
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

		for (char c : hexaKey) {
			System.out.print(c);
		}
		System.out.println();

		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; j++) {

				rgb = img.getRGB(i, j);
				temp = keyArr[9];
				if (enctype == ENCRYPTION) {
					while (temp-- > 0) {
						Y0 = logisticIteration(Y0);
						rgb = operation(enctype, rgb, Y0);
					}
					img.setRGB(i, j, rgb);
					count++;
				} else {
					Stack<Float> stack = new Stack<>();
					while (temp-- > 0) {
						Y0 = logisticIteration(Y0);
						stack.push(Y0);
					}
					while (!stack.isEmpty()) {
						rgb = operation(enctype, rgb, stack.pop());
					}
					img.setRGB(i, j, rgb);
					count++;
				}

				if (count == 16) {
					count = 0;
					modifySessionKey();
					generateRandomReal();
					generateIntegerSequence();

					// System.out.println(Y0);
				}
			}
		ImageIO.write(img, "bmp", new File(output));
		return true;
	}

	/**
	 * Generates a sequence of random integer in [0,24] and stores them into P.
	 */
	public void generateIntegerSequence() {
		for (int i = 0; i < 24; ++i) {
			P[i] = (int) (23 * (f[i] - 0.1f) / 0.8) + 1;
		}
	}

	/**
	 * Generates a sequence of random real nums in between 0 and 0.9 and stores
	 * them into f.
	 */
	public void generateRandomReal() {
		int i = 0;
		while (i < 24) {
			X0 = logisticIteration(X0);
			/* NOTE:If the key is all zero then X0 will lie out of bounds */
			if (X0 >= 0.1 && X0 <= 0.9) {
				f[i] = X0;
				i++;
			}
		}
	}

	/**
	 * Inverts the rgb value of pixel.
	 * 
	 * @param rgb
	 *            The argb value of the pixel
	 * @return An integer equivalent of negative of pixel(rgb only)
	 */
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

	/**
	 * Method used to iterate Y0 (key[10])<sub>10</sub> times. (Indexing assumed
	 * to be from 1.)
	 */
	public void iterateY() {
		int b = keyArr[9];
		while (b-- > 0) {
			Y0 = logisticIteration(Y0);
		}
	}

	/**
	 * This method is called after operation on 16 pixel. It modifies the
	 * Session keys and then calls setHexaKey.
	 */
	public void modifySessionKey() {
		for (int i = 0; i < 9; i++) {
			keyArr[i] = (char) (((int) keyArr[i] + (int) keyArr[9]) % 256);
		}
		setHexaKey();
	}

	/**
	 * <table>
	 * <tr>
	 * <td valign=top>Performs the operation:</td>
	 * <td><img src='group3.png'></td>
	 * </tr>
	 * </table>
	 * 
	 * @param rgb
	 *            The argb of pixel
	 * @param type
	 *            The type of operation viz. encryption(0) or decryption(1)
	 * @param groupno
	 *            The group number of the operation
	 * @return An integer equivalent of pixel after performing the operation
	 */
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

	/**
	 * This function acts as a map to function. It takes a value between 0 and 1
	 * and calls the appropriate operation.
	 * 
	 * @param type
	 *            The type of operation viz. encryption(0) or decryption(1)
	 * @param rgb
	 *            The argb of pixel
	 * @param Y0
	 *            The value which decides the operation
	 * @return Modified argb value in the form of an integer.
	 */
	public int operation(int type, int rgb, float Y0) {

		if ((Y0 >= 0.10 && Y0 < 0.13) || (Y0 >= 0.34 && Y0 < 0.37) || (Y0 >= 0.58 && Y0 < 0.62)) {
			return invertColor(rgb);
		}
		if ((Y0 >= 0.13 && Y0 < 0.16) || (Y0 >= 0.37 && Y0 < 0.40) || (Y0 >= 0.62 && Y0 < 0.66)) {
			return byteXOR(rgb, 2);
		}
		if ((Y0 >= 0.16 && Y0 < 0.19) || (Y0 >= 0.40 && Y0 < 0.43) || (Y0 >= 0.66 && Y0 < 0.70)) {
			return complexOper(rgb, type, 3);
		}
		if ((Y0 >= 0.19 && Y0 < 0.22) || (Y0 >= 0.43 && Y0 < 0.46) || (Y0 >= 0.70 && Y0 < 0.74)) {
			return notByteXOR(rgb, type, 4);
		}
		if ((Y0 >= 0.22 && Y0 < 0.25) || (Y0 >= 0.46 && Y0 < 0.49) || (Y0 >= 0.74 && Y0 < 0.78)) {
			return byteXOR(rgb, 5);
		}
		if ((Y0 >= 0.25 && Y0 < 0.28) || (Y0 >= 0.49 && Y0 < 0.52) || (Y0 >= 0.78 && Y0 < 0.82)) {
			return complexOper(rgb, type, 6);
		}
		if ((Y0 >= 0.28 && Y0 < 0.31) || (Y0 >= 0.52 && Y0 < 0.55) || (Y0 >= 0.82 && Y0 < 0.86)) {
			return notByteXOR(rgb, type, 7);
		}
		if ((Y0 >= 0.31 && Y0 < 0.34) || (Y0 >= 0.55 && Y0 < 0.58) || (Y0 >= 0.86 && Y0 <= 0.90)) {
			return rgb;

		}
		return rgb;
	}

	/**
	 * Converts the ASCII representaion of key into an array character of
	 * hexadecimal values.
	 */
	public void setHexaKey() {
		String str = "", temp;
		for (char c : keyArr) {
			temp = Integer.toHexString((int) c);
			if (temp.length() == 1)
				temp = "0" + temp;
			str += temp;
		}
		hexaKey = str.toCharArray();
	}
}
