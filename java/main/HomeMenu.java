package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.rosuda.JRI.Rengine;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;

public class HomeMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeMenu window = new HomeMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(UIManager.getColor("DesktopIcon.foreground"));
		frame.getContentPane().setBackground(SystemColor.controlLtHighlight);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rengine re = RObjectLoader.getRObject();
		re.end();
	}

}
