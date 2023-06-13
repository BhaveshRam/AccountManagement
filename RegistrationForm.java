import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class RegistrationForm extends JFrame {

	private JPanel contentPane;
	private JTextField Name;
	private JTextField Email;
	private JPasswordField pwdFd_1;
	private JPasswordField pwdFd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationForm frame = new RegistrationForm();
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
	public RegistrationForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTRATION FORM");
		lblNewLabel.setFont(new Font("Eras Bold ITC", Font.PLAIN, 18));
		lblNewLabel.setBounds(92, 28, 210, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(44, 96, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(44, 136, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setBounds(44, 177, 74, 21);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Confirm Password");
		lblNewLabel_4.setBounds(33, 209, 106, 27);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Already a User? Login");
		lblNewLabel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginPage lp=new LoginPage();
				lp.show();
			}
		});
		lblNewLabel_5.setBounds(122, 302, 148, 32);
		contentPane.add(lblNewLabel_5);
		
		Name = new JTextField();
		Name.setBounds(141, 90, 174, 26);
		contentPane.add(Name);
		Name.setColumns(10);
		
		Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(141, 130, 174, 26);
		contentPane.add(Email);
		
		pwdFd_1 = new JPasswordField();
		pwdFd_1.setBounds(141, 208, 174, 28);
		contentPane.add(pwdFd_1);
		
		pwdFd = new JPasswordField();
		pwdFd.setBounds(141, 170, 174, 28);
		contentPane.add(pwdFd);
		
		JButton RegButton = new JButton("REGISTER");
		RegButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					
					Statement st = Con.createStatement();
					char[] pw= pwdFd.getPassword();
					String pws=new String(pw);
					
					char[] pw_1= pwdFd_1.getPassword();
					String pws_1=new String(pw_1);
					
					if(!pws.equals(pws_1)) {
						JOptionPane.showMessageDialog(null, "Mismatched passwords");
					}
					else {
						Random random =new Random();
						int rando=(random.nextInt(100 - 1 + 1) + 1);
						String str ="select * from LOGININFO where ID="+rando+";";
						ResultSet rs= st.executeQuery(str);
						while(rs.next()) {
							rando=(random.nextInt(100 - 1 + 1) + 1);
							str ="select * from LOGININFO where ID="+rando+";";
							rs= st.executeQuery(str);
						}
						str ="select * from LOGININFO where Email='"+Email.getText()+"';";
						rs= st.executeQuery(str);
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "Email Already exists!");
						}
						else {
							str="insert into LOGININFO values("+rando+",'"+Name.getText()+"','"+Email.getText()+"','"+pws+"');";
							st.executeUpdate(str);
							str="insert into usrInf values("+rando+","+0.00+",false,false);";
							st.executeUpdate(str);
							JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
						}
					}
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
						
			}
		});
		RegButton.setBounds(141, 257, 106, 34);
		contentPane.add(RegButton);
	}

}
