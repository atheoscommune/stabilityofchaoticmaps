/**
 * 
 */
package crypto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

/**
 * @author prince
 *
 */
public class GUILauncher extends JFrame {

	private JPanel encryptBtn;
	BufferedImage  cryptimg, dispimg;

	JLabel dispLabel = new JLabel("");
	private JTextField textField;
	ButtonGroup bgroup = new ButtonGroup();
	MultipleIterationImageEncryption cie;

	File f;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUILauncher frame = new GUILauncher();
					frame.setVisible(true);
					frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUILauncher() {
		cie = new MultipleIterationImageEncryption();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 510);
		encryptBtn = new JPanel();
		encryptBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(encryptBtn);

		JLabel lblWelcomeToImage = new JLabel("Welcome to Image cryptography module");

		JLabel errorLbl = new JLabel("");
		JButton btnChooseimage = new JButton("ChooseImage");
		btnChooseimage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilter filter = new FileNameExtensionFilter("BMP Files", "bmp");
				filechooser.setFileFilter(filter);
				if (filechooser.showOpenDialog(encryptBtn) == JFileChooser.APPROVE_OPTION) {
					f = filechooser.getSelectedFile();
					errorLbl.setText(f.getPath());
					try {
						dispimg = ImageIO.read(f);
						errorLbl.setIcon(new ImageIcon(dispimg));
					} catch (IOException e) {
						errorLbl.setText("Some Error" + e.getMessage());
						errorLbl.setIcon(null);
						f = null;
					}
				} else {
					errorLbl.setIcon(null);
					errorLbl.setText("No File Selected");
					f = null;
				}
				errorLbl.revalidate();
			}
		});

		JLabel lblKeyCharacters = new JLabel("Key(10 characters)");

		textField = new JTextField();
		textField.setColumns(10);

		JRadioButton rdbtnEncrypt = new JRadioButton("Encrypt");

		JRadioButton decryptBtn = new JRadioButton("Decrypt");
		bgroup.add(rdbtnEncrypt);
		bgroup.add(decryptBtn);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().length() != 10) {
					JOptionPane.showMessageDialog(null, "Key length must be 10");
				} else if (!rdbtnEncrypt.isSelected() && !decryptBtn.isSelected()) {
					JOptionPane.showMessageDialog(null, "Select encryption or decryption");
				} else if (dispimg==null) {
					JOptionPane.showMessageDialog(null, "Choose a BMP file");
				} else {
					int cryptType = (rdbtnEncrypt.isSelected() ? 0 : 1);
					try {
						String name = f.getName();
						String pwd = textField.getText();
						System.out.println(cryptType+" "+pwd);
						cie.encryptImage(pwd, dispimg, cryptType, "crypto"+name);
						JOptionPane.showMessageDialog(null, "Process completed!!!!");
						errorLbl.setIcon(new ImageIcon(dispimg));
						errorLbl.revalidate();
					} catch (InvalidKeyException e) {

						errorLbl.setText("Key length related exception:" + e.getMessage());
						errorLbl.setIcon(null);
						errorLbl.revalidate();
					} catch (IOException e) {
						errorLbl.setText("Some Error" + e.getMessage());
						errorLbl.setIcon(null);
						errorLbl.revalidate();
					}
				}
			}
		});
		GroupLayout gl_encryptBtn = new GroupLayout(encryptBtn);
		gl_encryptBtn.setHorizontalGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING).addGroup(gl_encryptBtn
				.createSequentialGroup()
				.addGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_encryptBtn.createSequentialGroup().addGap(233).addComponent(lblWelcomeToImage))
						.addGroup(gl_encryptBtn.createSequentialGroup().addGap(53)
								.addGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING).addComponent(dispLabel)
										.addComponent(errorLbl)))
						.addGroup(
								gl_encryptBtn.createSequentialGroup().addGap(40)
										.addGroup(gl_encryptBtn.createParallelGroup(
												Alignment.LEADING).addComponent(lblKeyCharacters).addComponent(
														btnChooseimage))
										.addGap(31)
										.addGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_encryptBtn.createSequentialGroup()
														.addComponent(textField, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(122).addComponent(rdbtnEncrypt).addGap(42)
														.addComponent(decryptBtn, GroupLayout.PREFERRED_SIZE, 80,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(btnSubmit))))
				.addContainerGap(77, Short.MAX_VALUE)));
		gl_encryptBtn.setVerticalGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING).addGroup(gl_encryptBtn
				.createSequentialGroup().addGap(32).addComponent(lblWelcomeToImage).addGap(65)
				.addGroup(gl_encryptBtn.createParallelGroup(Alignment.BASELINE).addComponent(lblKeyCharacters)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnEncrypt).addComponent(decryptBtn))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_encryptBtn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_encryptBtn.createSequentialGroup().addComponent(btnChooseimage).addGap(37)
								.addComponent(errorLbl).addGap(66).addComponent(dispLabel))
						.addComponent(btnSubmit))
				.addContainerGap(219, Short.MAX_VALUE)));
		lblWelcomeToImage.setHorizontalAlignment(JLabel.CENTER);
		encryptBtn.setLayout(gl_encryptBtn);
	}
}
