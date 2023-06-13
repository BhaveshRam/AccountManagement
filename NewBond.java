import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewBond extends JFrame {

	private JPanel contentPane;
	private JTextField Amount;
	private JTextField Duration;
	private JButton Cnfm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBond frame = new NewBond(10);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewBond(int id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 421, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Amount = new JTextField();
		Amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String value = Amount.getText();
	            int l = value.length();
	            if ( Character.isDigit(e.getKeyChar())) {
	            	Amount.setEditable(true);
	            	
	            } 
	            if(Character.isAlphabetic(e.getKeyChar())) {
	            	Amount.setEditable(false);
	            }
			}
		});
		Amount.setBounds(221, 82, 111, 20);
		contentPane.add(Amount);
		Amount.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("BOND CREATION");
		lblNewLabel.setFont(new Font("Eras Bold ITC", Font.PLAIN, 15));
		lblNewLabel.setBounds(113, 11, 156, 43);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bonding Amount :");
		lblNewLabel_1.setBounds(66, 84, 111, 17);
		contentPane.add(lblNewLabel_1);
		
		Duration = new JTextField();
		Duration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String value = Duration.getText();
	            int l = value.length();
	            if ( Character.isDigit(e.getKeyChar())) {
	            	Duration.setEditable(true);
	            	
	            } 
	            if(Character.isAlphabetic(e.getKeyChar())) {
	            	Duration.setEditable(false);
	            }
			}
		});
		Duration.setColumns(10);
		Duration.setBounds(221, 132, 111, 20);
		contentPane.add(Duration);
		
		JLabel lblNewLabel_1_1 = new JLabel("Bonding Duration (in yrs): ");
		lblNewLabel_1_1.setBounds(27, 134, 156, 18);
		contentPane.add(lblNewLabel_1_1);
		
		Cnfm = new JButton("Confirm");
		Cnfm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					Statement st = Con.createStatement();
					
					LocalTime currentTime = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
					String formattedTime = currentTime.format(formatter);
					int CurrTim=Integer.parseInt(formattedTime);
					System.out.println(CurrTim);
					String str ="insert into Bond values ("+Float.parseFloat(Amount.getText())+","+Integer.parseInt(Duration.getText())+","+CurrTim+","+0.00+
							","+id+");";
					st.executeUpdate(str);
					str="update usrInf set BondSt=1 where id="+id+";";
					st.executeUpdate(str);
					JOptionPane.showMessageDialog(null, "Successfully Created!");
					dispose();
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		Cnfm.setBounds(129, 196, 111, 29);
		contentPane.add(Cnfm);
	}
}
