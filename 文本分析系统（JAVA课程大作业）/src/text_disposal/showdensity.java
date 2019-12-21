package text_disposal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class showdensity extends JFrame implements ActionListener {
	//public static String []st =new String[2];
	JComboBox comboBox=new JComboBox(input.name); 
	private mypanel jpp=new mypanel();
public showdensity()
{
	this.setTitle("下拉列表框使用");  
	this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
    this.setBounds(100,100,250,100);
    JPanel contentPane=new JPanel();  
    contentPane.setBorder(new EmptyBorder(5,5,5,5));  
    contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
    JLabel label=new JLabel("人物姓名:");  
    contentPane.add(label);  
    contentPane.add(comboBox);  
    this.add(contentPane,new BorderLayout().NORTH);
    this.add(jpp, BorderLayout.CENTER);
   
    this.setSize(1800,600);
    this.setVisible(true); 
    comboBox.addActionListener(this);
}
class mypanel extends JPanel
{
	
	public  void  paint(Graphics g) {
	for(int i=0;i<10;i++)
		if(input.name[i].equals(comboBox.getSelectedItem()))
		{
			//g.clearRect(0, 0, this.getWidth(), this.getHeight());
			int[] cishu=StringStat.cal_density(input.name[i]);
			//System.out.println(cishu[100]);
			float max=0;
			for(int j=0;j<600;j++)
				if(max<cishu[j])
					max=cishu[j];//找到最大次数
			for(int k=0;k<600;k++)
			{ 
				g.setColor(new Color((float)1, 1-cishu[k]/max, (float)1));
				g.drawRect(3*k,0,3,600);
				g.fillRect(3*k,0,3,600);
			}
			System.out.println(max);
		}
	}
}

public void actionPerformed(ActionEvent e) {
	
	for(int i=0;i<=9;i++)
		if(input.name[i].equals(comboBox.getSelectedItem()))
		{
			repaint();
		}
}

}
