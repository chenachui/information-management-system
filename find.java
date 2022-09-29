import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class find extends JFrame {
	FindPanel panel=new FindPanel();
	JLabel label = new JLabel("请输入要查询成绩的学生学号");
	JTextField textId = new JTextField();
	JButton buttonDelete = new JButton("确定查询");
	JButton buttonReturn = new JButton("返回");

	Vector rowData, columnNames;
	JTable table = null;
	JScrollPane scrollpane = null;
	PreparedStatement prestatement = null;
	Connection conn = null;
	ResultSet result = null;

	public find() {
		setTitle("查询学生成绩");
		label.setBounds(new Rectangle(25, 70, 170, 30));
		textId.setBounds(new Rectangle(25, 120, 150, 30));
		buttonDelete.setBounds(new Rectangle(10, 180, 90, 30));
		buttonReturn.setBounds(new Rectangle(120, 180, 80, 30));

		buttonDelete.addActionListener(new findLisener());
		buttonReturn.addActionListener(new returnLisener());

		columnNames = new Vector();
		columnNames.add("学号");
		columnNames.add("课程");
		columnNames.add("成绩");

		rowData = new Vector(); // rowData可以存放多行,开始从数据库里取
		try {
			// 加载驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 得到连接
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=test", "sa",
					"123456");
			prestatement = conn.prepareStatement("select * from score ");
			result = prestatement.executeQuery();

			while (result.next()) {
				// rowData可以存放多行
				Vector hang = new Vector();
				hang.add(result.getInt(1));
				hang.add(result.getString(2));
				hang.add(result.getString(3));

				// 加入到rowData
				rowData.add(hang);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null) {
					// result.close();
				}
				if (prestatement != null) {
					prestatement.close();
				}
				if (conn != null) {
					// conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 初始化Jtable
		table = new JTable(rowData, columnNames);
		table();
	}

	void table() {
		scrollpane = new JScrollPane(table);
		MyTableColor.makeFace(table);
		scrollpane.setBounds(new Rectangle(250, 50, 300, 150));
		/*Container contpane = getContentPane();
		contpane.setLayout(null);
		contpane.add(label);
		contpane.add(textId);
		contpane.add(buttonDelete);
		contpane.add(buttonReturn);
		contpane.add(scrollpane);*/
		panel.setLayout(null);
		panel.add(label);
		panel.add(textId);
		panel.add(buttonDelete);
		panel.add(buttonReturn);
		panel.add(scrollpane);
		this.add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 330);
		setVisible(true);
	}

	class findLisener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			table = new JTable(null, columnNames);
			Vector newrowData = new Vector();
			String s = textId.getText();
			int id = Integer.parseInt(s);
			try {
				Statement statement = conn.createStatement();
				String sql = "select * from scoreview where id= '" + id + "'";
				ResultSet rs = statement.executeQuery(sql);
				while (rs.next()) {
					String course = rs.getString("course_name");
					int score = rs.getInt("score");
					Vector newhang = new Vector();
					newhang.add(rs.getInt(1));
					newhang.add(rs.getString(2));
					newhang.add(rs.getString(3));
					// 加入到rowData
					newrowData.add(newhang);
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				int a = statement.executeUpdate(sql);
				if (a == 1) {
					JOptionPane.showMessageDialog(null, "更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "更新失败", "失败", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			table = new JTable(newrowData, columnNames);
			table();
		}
	}

	class returnLisener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new mainwin();
		}
	}

	public static void main(String args[]) {
		new find();
	}
}

class FindPanel extends JPanel{  
    public void paintComponent(Graphics g){  
        super.paintComponent(g);  
        //绘制一张背景图片 
        Image image = new ImageIcon("images//back2.jpg").getImage();  
        g.drawImage(image, 0, 0, this);  
    }  
}

