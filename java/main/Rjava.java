package main;

import java.io.*;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXP;

public class Rjava {
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Rjava() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rjava window = new Rjava();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/* To load the Rengine object */
		Rengine re = RObjectLoader.getRObject();
		if (re == null) {
			System.err.println(
					"Either the library used to connect with R is mismatching or R cannot be loaded due to some reason");
			System.exit(1);
		}

		String filename = "map.jpeg";
		/* Open a jpeg device */
		re.eval("jpeg('" + filename + "')");
		/* Get the url of the script to be executed */
		URL url = Rjava.class.getClass().getResource("/simplelogisticsmap.R");

		// System.out.println(url.getPath()+"jhk");
		re.eval("source('" + url.getPath() + "')");

		/*
		 * REXP rexp;
		 * 
		 * re.eval("r = 2+5"); re.eval("v = readline()");
		 * re.eval("v = readline()");
		 */

		// rexp =
		// re.eval("print r");
		// System.out.println(RInteract.message);
		/* Now close the device */
		re.eval("dev.off()");

		Scanner scan = new Scanner(System.in);
		/* This below code creates a frame to display an image */
		JFrame editorFrame = new JFrame("Script image");
		editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		ImageIcon imageIcon = new ImageIcon(image);
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

		editorFrame.pack();
		editorFrame.setLocationRelativeTo(null);
		editorFrame.setVisible(true);
		scan.next();
		re.end();
	}
}
