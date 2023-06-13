import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInfo extends JFrame {

	private JPanel contentPane;
	private JTextField UsrIDTrf;
	private JTextField Money;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInfo frame = new UserInfo(10);
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
	public UserInfo(int id) {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UsrID_lb = new JLabel("User ID:");
		UsrID_lb.setBounds(22, 60, 57, 19);
		contentPane.add(UsrID_lb);
		
		JLabel UsrID = new JLabel("\"\"");
		UsrID.setBounds(159, 56, 138, 27);
		contentPane.add(UsrID);
		
		JLabel UsrName_lb = new JLabel("User Name: ");
		UsrName_lb.setBounds(22, 92, 106, 24);
		contentPane.add(UsrName_lb);
		
		JLabel Name = new JLabel("New label");
		Name.setBounds(159, 91, 138, 27);
		contentPane.add(Name);
		
		JLabel Email_lb = new JLabel("Email: ");
		Email_lb.setBounds(22, 127, 97, 24);
		contentPane.add(Email_lb);
		
		JLabel Email = new JLabel("New label");
		Email.setBounds(159, 127, 159, 24);
		contentPane.add(Email);
		
		JLabel Bal_lb = new JLabel("Account Balance:");
		Bal_lb.setBounds(22, 162, 138, 27);
		contentPane.add(Bal_lb);
		
		JLabel Balance = new JLabel("New label");
		Balance.setBounds(159, 163, 138, 24);
		contentPane.add(Balance);
		
		JButton FAR = new JButton("Free Amount Request");
		FAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					
					Statement st = Con.createStatement();
					String str= "select * from usrInf where ID="+id+" and FamReq=0;";
					ResultSet rs=st.executeQuery(str);
					
					if(!rs.next()) {
						JOptionPane.showMessageDialog(null, "Already claimed free amount!");
					}
					else {
						str="update usrInf set FamReq=1 , AccBal=100 where ID="+id+";";
						Balance.setText("$ 100");
						st.executeUpdate(str);
					}
					
				}catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		FAR.setBounds(10, 254, 195, 35);
		contentPane.add(FAR);
		
		JButton BondInf = new JButton("Bond Information");
		BondInf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					
					Statement st = Con.createStatement();
					String str="select * from usrInf where id="+id+";";
					ResultSet rs=st.executeQuery(str);
					if(rs.next()) {
						if(rs.getInt("BondSt")==1) {
							Bonding bd=new Bonding(id);
							bd.show();
						}
						else {
							int input=JOptionPane.showConfirmDialog(null, "Do you want create a bond?", "Create New Bond", JOptionPane.YES_NO_OPTION);
							if(input == JOptionPane.YES_OPTION) {
								NewBond nb=new NewBond(id);
								nb.show();
							}
							
						}
					}
				}
				catch(Exception e1){
					System.out.println(e1.getMessage());
				}
			}
		});
		BondInf.setBounds(400, 294, 138, 35);
		contentPane.add(BondInf);
		
		JLabel Trf = new JLabel("Transfer Money");
		Trf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		Trf.setBounds(323, 44, 106, 35);
		contentPane.add(Trf);
		
		JLabel Lb = new JLabel("Enter User ID :");
		Lb.setBounds(323, 91, 122, 27);
		contentPane.add(Lb);
		
		UsrIDTrf = new JTextField();
		UsrIDTrf.setBounds(442, 93, 111, 22);
		contentPane.add(UsrIDTrf);
		UsrIDTrf.setColumns(10);
		
		JLabel lblEnterMoney = new JLabel("Enter Money :");
		lblEnterMoney.setBounds(323, 132, 122, 27);
		contentPane.add(lblEnterMoney);
		
		Money = new JTextField();
		Money.setColumns(10);
		Money.setBounds(442, 135, 111, 22);
		contentPane.add(Money);
		
		
		
		JLabel Label = new JLabel("");
		Label.setBounds(290, 170, 155, 101);
		contentPane.add(Label);
		
		
		JButton TrfBtn = new JButton("Transfer");
		TrfBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					
					Statement st = Con.createStatement();
					
					int id_1=Integer.parseInt(UsrIDTrf.getText());
					
					String str="select * from usrInf where id="+id_1+";";
					ResultSet rs=st.executeQuery(str);
					
					if(rs.next()) {
						float bal=rs.getFloat("AccBal");
						float f=Float.parseFloat(Balance.getText().substring(1));
						float f1=Float.parseFloat(Money.getText());
						rs.close();
						if(f>f1) {
							st.executeUpdate("update usrInf set AccBal="+(f-f1)+" where id="+id+";");
							Balance.setText("$ "+(f-f1));
							st.executeUpdate("update usrInf set AccBal="+(bal+f1)+" where id="+id_1+";");
							Label.setText("Transferred Succesfully!");
						}
						else {
							Label.setText("Not enough Money to tranfer!");
						}
					}
					else {
						Label.setText("User Doesnot exist!");
					}
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		TrfBtn.setBounds(464, 190, 126, 41);
		contentPane.add(TrfBtn);
		
		JLabel BToL = new JLabel("< LOG OUT");
		BToL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginPage lp=new LoginPage();
				lp.show();
			}
		});
		BToL.setForeground(Color.BLUE);
		BToL.setBounds(22, 320, 113, 31);
		contentPane.add(BToL);
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
			
			Statement st = Con.createStatement();
			
			String str="select * from usrInf where id="+id+";";
			ResultSet rs=st.executeQuery(str);
			if (rs.next()) {
				UsrID.setText(""+rs.getInt("ID"));
				Balance.setText("$ "+rs.getString("AccBal"));
			}
			str="select * from LOGININFO where id="+id+";";
			rs=st.executeQuery(str);
			if(rs.next()) {
				Name.setText(rs.getString("Name"));
				Email.setText(rs.getString("Email"));
			}
			
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
}
