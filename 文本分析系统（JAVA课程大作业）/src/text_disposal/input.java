package text_disposal;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class input extends JFrame implements ActionListener{
    public static boolean tap=false; //用来记录是否已经输入完毕
	public static  String[] name= new String[10];
	private JPanel jp = new JPanel();
	private JLabel jl=new JLabel("请依次输入十个人的名字及其别名");
	private JLabel jlb=new JLabel("(用逗号隔开)");
	private JLabel jl1=new JLabel("一：");
	private JLabel jl2=new JLabel("二：");
	private JLabel jl3=new JLabel("三：");
	private JLabel jl4=new JLabel("四：");
	private JLabel jl5=new JLabel("五：");
	private JLabel jl6=new JLabel("六：");
	private JLabel jl7=new JLabel("七：");
	private JLabel jl8=new JLabel("八：");
	private JLabel jl9=new JLabel("九：");
	private JLabel jl10=new JLabel("十：");
	private JTextField jf1=new JTextField(20);
	private JTextField jf2=new JTextField(20);
	private JTextField jf3=new JTextField(20);
	private JTextField jf4=new JTextField(20);
	private JTextField jf5=new JTextField(20);
	private JTextField jf6=new JTextField(20);
	private JTextField jf7=new JTextField(20);
	private JTextField jf8=new JTextField(20);
	private JTextField jf9=new JTextField(20);
	private JTextField jf10=new JTextField(20);
	private JButton jb = new JButton("确定");
	public  input(){
		
		for(int i=0;i<=9;i++)
		input.name[i]=null;
		this.setVisible(true);
		this.add(jp);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300,400);
		jp.add(jl);
		jp.add(jlb);
	    jp.add(jl1);
		jp.add(jf1);
		
		jp.add(jl2);
		jp.add(jf2);
		
		jp.add(jl3);
		jp.add(jf3);
		
		jp.add(jl4);
		jp.add(jf4);
		
		jp.add(jl5);
		jp.add(jf5);
		
		jp.add(jl6);
		jp.add(jf6);
		
		jp.add(jl7);
		jp.add(jf7);
		
		jp.add(jl8);
		jp.add(jf8);
		
		jp.add(jl9);
		jp.add(jf9);
		
		jp.add(jl10);
		jp.add(jf10);
		jp.add(jb);
		jb.addActionListener(this);
		int windowWidth = this.getWidth(); //获得窗口宽
		int windowHeight = this.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb)
		{
			name[0]=jf1.getText();
			name[1]=jf2.getText();
			name[2]=jf3.getText();
			name[3]=jf4.getText();
			name[4]=jf5.getText();
			name[5]=jf6.getText();
			name[6]=jf7.getText();
			name[7]=jf8.getText();
			name[8]=jf9.getText();
			name[9]=jf10.getText();
			input.tap=true;
			StringStat.cal();
			this.dispose();
			// TODO Auto-generated method stub
		}
		else {
			
		}
		
	}
	
	

}
