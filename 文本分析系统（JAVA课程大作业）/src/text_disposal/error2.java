package text_disposal;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class error2 extends JFrame {
private JPanel pn=new JPanel();
private JLabel jb=new JLabel("请先录入人物！");
public error2()
{
	this.setVisible(true);
	this.add(pn);
	pn.add(jb);
	jb.setFont(new Font("Dialog",1,20));
	this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	this.setSize(200,100);
	int windowWidth = this.getWidth(); //获得窗口宽
	int windowHeight = this.getHeight(); //获得窗口高
	Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
	Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
	int screenWidth = screenSize.width; //获取屏幕的宽
	int screenHeight = screenSize.height; //获取屏幕的高
	this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);
}
}