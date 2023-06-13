import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.JComboBox;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField UsrName;
	private JPasswordField pwdFd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginPage() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(306, 10, 1, 1);
		contentPane.add(layeredPane);
		
		UsrName = new JTextField();
		UsrName.setBounds(217, 119, 175, 29);
		contentPane.add(UsrName);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(117, 120, 90, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(116, 182, 90, 22);
		contentPane.add(lblPassword);
		
		pwdFd = new JPasswordField();
		pwdFd.setBounds(217, 181, 175, 29);
		contentPane.add(pwdFd);
		
		JButton loginButton = new JButton("LOGIN");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Actions describing the Login button 
				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					
					Statement st = Con.createStatement();
					char[] pw= pwdFd.getPassword();
					String pws=new String(pw);
					String str="select * from LOGININFO where Email='"+UsrName.getText()+"' and Password='"+pws+"';";
					ResultSet rs= st.executeQuery(str);
					System.out.print(str);
					// If a user exists with the provided user email and password in database then it goes to next page i.e., User Information page
					if(rs.next()) {
						int id=rs.getInt("ID");
						System.out.println(id);
						dispose();
						UserInfo uf = new UserInfo(id);
						uf.show();
					}
					else {
						// IF the details are Incorrect then it gives out a error saying Incorrect credentials and clearing the text fields.
						JOptionPane.showMessageDialog(null, "Incorrect Credentials!");
						UsrName.setText("");
						pwdFd.setText("");
					}
					Con.close();
					
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		loginButton.setBounds(132, 244, 153, 29);
		contentPane.add(loginButton);
		
		JLabel lblNewLabel_1 = new JLabel("LOGIN PAGE");
		lblNewLabel_1.setFont(new Font("Eras Bold ITC", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(208, 49, 153, 35);
		contentPane.add(lblNewLabel_1);
		
		// If the user want to create an account then he can create it using the register here label.
		JLabel RegLabel = new JLabel("New User? Register Here");
		RegLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				RegistrationForm Rf=new RegistrationForm();
				Rf.show();
			}
		});
		RegLabel.setBounds(205, 302, 156, 14);
		contentPane.add(RegLabel);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsrName.setText("");
				pwdFd.setText("");
			}
		});
		resetButton.setBounds(322, 244, 153, 29);
		contentPane.add(resetButton);
	}
}
