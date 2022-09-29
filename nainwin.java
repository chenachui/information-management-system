import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainwin extends JFrame {
	MainPanel panel=new MainPanel();
	JButton B1=new JButton("插入学生信息");
	JButton B2=new JButton("删除学生信息");
	JButton B3=new JButton("更新学生信息");
	JButton B4=new JButton("查询学生信息");
	public mainwin(){
		setTitle("操作");

		B1.setBounds(new Rectangle(80,100,170,30));
		B2.setBounds(new Rectangle(80,150,170,30));
		B3.setBounds(new Rectangle(80,200,170,30));
		B4.setBounds(new Rectangle(80,250,170,30));
		B1.addActionListener(new insertListener());
		B2.addActionListener(new deleteListener());
		B3.addActionListener(new updateListener());
		B4.addActionListener(new findListener());
		
		/*Container contpane=getContentPane();
		contpane.setLayout(null);
		contpane.add(B1);
		contpane.add(B2);
		contpane.add(B3);
		contpane.add(B4);*/
		panel.setLayout(null);
		panel.add(B1);
		panel.add(B2);
		panel.add(B3);
		panel.add(B4);
		this.add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 420);
		setVisible(true);
		
	}
	class insertListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new insert();
		}	
	}
	
	class deleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new delete();
		}	
	}
	class updateListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				dispose();
				new update();
			}	
		}
	class findListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				dispose();
				new find();
			}	
		}
	public static void main(String args[]){
		new mainwin();
	}
	}

class MainPanel extends JPanel{  
    public void paintComponent(Graphics g){  
        super.paintComponent(g);  
        //绘制一张背景图片 
        Image image = new ImageIcon("images//back8.png").getImage();  
        g.drawImage(image, 0, 0, this);  
    }  
}
