import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bonding extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bonding frame = new Bonding(10);
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
	public Bonding(int ID) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 493, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UsrName = new JLabel("User Name :");
		UsrName.setBounds(10, 50, 83, 28);
		contentPane.add(UsrName);
		
		JLabel UsrId = new JLabel("User ID :");
		UsrId.setBounds(10, 25, 83, 28);
		contentPane.add(UsrId);
		
		JLabel UsrID = new JLabel("New label");
		UsrID.setBounds(104, 25, 83, 28);
		contentPane.add(UsrID);
		
		JLabel Name = new JLabel("New label");
		Name.setBounds(103, 50, 83, 28);
		contentPane.add(Name);
		
		JLabel BondedAm = new JLabel("Bonded Amount : ");
		BondedAm.setBounds(10, 101, 108, 28);
		contentPane.add(BondedAm);
		
		JLabel BndAm = new JLabel("New label");
		BndAm.setBounds(128, 101, 120, 28);
		contentPane.add(BndAm);
		
		JLabel BondDur = new JLabel("Bond Duration : ");
		BondDur.setBounds(10, 140, 108, 28);
		contentPane.add(BondDur);
		
		JLabel BndDur = new JLabel("New label");
		BndDur.setBounds(128, 140, 108, 28);
		contentPane.add(BndDur);
		
		JLabel lblNewLabel_4 = new JLabel("Current Rewards : ");
		lblNewLabel_4.setBounds(10, 206, 108, 28);
		contentPane.add(lblNewLabel_4);
		
		JLabel CurRew = new JLabel("New label");
		CurRew.setBounds(128, 206, 113, 28);
		contentPane.add(CurRew);
		
		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					Statement st = Con.createStatement();
					String str="select * from Bond where id="+ID+";";
					ResultSet rs=st.executeQuery(str);
					
					if(rs.next()) {				
						LocalTime currentTime = LocalTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
						String formattedTime = currentTime.format(formatter);
						int CurrTim=Integer.parseInt(formattedTime);
						float rew=(float) (rs.getFloat("BondAm")*(5.0/100.0)*(CurrTim-rs.getInt("CurTm")+9)/5.0); 
						CurRew.setText("$ "+rew);
						st.executeUpdate("update Bond set CurRwds="+rew+" where id="+ID+";");
					}
					
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		RefreshBtn.setBounds(299, 135, 136, 38);
		contentPane.add(RefreshBtn);
		
		JButton UnBndBtn = new JButton("Unbond/Claim");
		UnBndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
					Statement st = Con.createStatement();
					String str="select * from Bond where id="+ID+";";
					ResultSet rs=st.executeQuery(str);
					
					LocalTime currentTime = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
					String formattedTime = currentTime.format(formatter);
					int CurrTim=Integer.parseInt(formattedTime);
					if(rs.next()) {
					if(CurrTim-rs.getInt("CurTm") >4) {
						
						float bndA=rs.getFloat("BondAm");
						float rew=(float) (rs.getFloat("BondAm")*(5.0/100.0)*(CurrTim-rs.getInt("CurTm")+9)/5.0);
						rs=st.executeQuery("select * from usrInf where id="+ID+";");
						if(rs.next()) {
							System.out.println("Completed Time");
							float temp=rs.getFloat("AccBal")+bndA+rew;
							str="update usrInf set AccBal="+temp+",BondSt=false where id="+ID+";";
							st.executeUpdate(str);
							str="delete from bond where id="+ID+";";
							st.executeUpdate(str);
						}
						JOptionPane.showMessageDialog(null, "Successfully claimed!");
						dispose();
						
					}
					else {
						System.out.println("Not Completed Time");
						rs=st.executeQuery("select * from usrInf where id="+ID+";");
						if(rs.next()) {
							str="update usrInf set AccBal="+rs.getFloat("AccBal")+",BondSt=false where id="+ID+";";
							st.executeUpdate(str);
							str="delete from bond where id="+ID+";";
							st.executeUpdate(str);
						}
						JOptionPane.showMessageDialog(null, "Successfully claimed!");
						dispose();
					}
					}
				}
				catch(Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
				
		});
		UnBndBtn.setBounds(299, 73, 136, 38);
		contentPane.add(UnBndBtn);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useSSL=false","root","ram@123");
			Statement st = Con.createStatement();
			String str="select * from Bond where id="+ID+";";
			ResultSet rs=st.executeQuery(str);
			
			if(rs.next()) {
				UsrID.setText(""+rs.getInt("ID"));
				BndAm.setText("$ "+rs.getFloat("BondAm"));
				BndDur.setText(""+rs.getInt("BondDur"));
				
				LocalTime currentTime = LocalTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
				String formattedTime = currentTime.format(formatter);
				int CurrTim=Integer.parseInt(formattedTime);
				float rew=(float) (rs.getFloat("BondAm")*(5.0/100.0)*(CurrTim-rs.getInt("CurTm")+9)/5.0); 
				str="select * from LOGININFO where id="+ID+";";
				rs=st.executeQuery(str);
				if(rs.next()) {
					Name.setText(rs.getString("Name"));
				}
				CurRew.setText("$ "+rew);
				
				st.executeUpdate("update Bond set CurRwds="+rew+" where id="+ID+";");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
