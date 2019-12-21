package text_disposal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes.Name;

import javax.accessibility.AccessibleAction;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class textprocess extends JFrame implements ActionListener {
	public static String msg=null;
	private JPanel pn=new JPanel();
	private JPanel pc=new JPanel();
	private JButton btopen=new JButton("打开文件");
	private JButton btrole=new JButton("输入人物");
	private JButton btnum=new JButton("次数");
	private JButton btdensity=new JButton("密度");
	private JButton btanaly=new JButton("选择人物");
	private JButton btshow=new JButton("亲密度");
	private JTextArea screen=new JTextArea(20, 55);
	public textprocess()
	{
		screen.setWrapStyleWord(true);//激活断行不断字功能
		screen.setLineWrap(true); //添加jtextarea自动换行的功能
		this.setSize(800,700);
		pc.add(new JScrollPane(screen));//给jtextarea添加滚动条
		int windowWidth = this.getWidth(); //获得窗口宽
		int windowHeight = this.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pn.setLayout(new GridLayout(1, 6, 40, 40));
		screen.setFont(new Font("宋体", Font.PLAIN, 25));
		this.setLayout(new BorderLayout(10,10));
		pn.add(btopen);
		btopen.addActionListener(this);
		pn.add(btrole);
		btrole.addActionListener(this);
		pn.add(btnum);
		btnum.addActionListener(this);
		pn.add(btdensity);
		btdensity.addActionListener(this);
		pn.add(btanaly);
		btanaly.addActionListener(this);
		pn.add(btshow);
		btshow.addActionListener(this);
		this.add(pn,BorderLayout.NORTH);
		this.add(pc,BorderLayout.CENTER);
	}
			

public void actionPerformed(ActionEvent e)
{
	if(e.getSource()==btopen)
	{
		File file=null;
		JFileChooser chooser=new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        ".txt", "txt");
	    chooser.setFileFilter(filter);
	    chooser.setDialogTitle("请选择文件：");
	    int result=chooser.showOpenDialog(this);
	    if(result==JFileChooser.APPROVE_OPTION)
	       file=chooser.getSelectedFile();
	    FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] data=new byte[(int)file.length()];
		try {
			fis.read(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		textprocess.msg=new String(data);
		try {
			fis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		screen.append("正在加载小说……\n");
		screen.append("小说加载完毕，该小说为《"+file.getName().substring(0, file.getName().indexOf("."))+"》\n");
		screen.append("共"+msg.length()+"字\n");
	}
	else if(e.getSource()==btrole)
	{
		if(textprocess.msg==null)
			{
			new error();
			}
		else 
		{
			new input();
		}	
	}
	else if(e.getSource()==btnum)
	{
		if(textprocess.msg==null)
		{
			new error();
		}
		else if(input.tap==false)
		{
			new error2();
		}
		else 
		{
			TreeMap<String, Integer> tx = new TreeMap<String, Integer>();
			for(int i=0;i<10;i++)
				tx.put(input.name[i], StringStat.number[i]);
			List<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>(tx.entrySet());
	        Collections.sort(entryArrayList, Comparator.comparing(Map.Entry::getValue));
	        screen.append("-------------------------------------------------\n");
	        for (Map.Entry<String, Integer> entry : entryArrayList) {
	            screen.append(entry.getKey() + "出现次数为：" + entry.getValue()+"\n");
			
		}
		}
	}
	else if(e.getSource()==btdensity)
	{
		if(textprocess.msg==null)
		{
			new error();
		}
		else if(input.tap==false)
		{
			new error2();
		}
		else
		{
			new showdensity();
		}
	}
	else if(e.getSource()==btanaly)
	{
		if(textprocess.msg==null)
		{
			new error();
		}
		else if(input.tap==false)
		{
			new error2();
		}
		else 
		{
			new analy();
	    }
		}
	
else if(e.getSource()==btshow)
{
	if(textprocess.msg==null)
	{
		new error();
	}
	else if(input.tap==false)
	{
		new error2();
	}
	else 
	{
		TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
		tm=StringStat.cal_analy();
		List<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>(tm.entrySet());
        Collections.sort(entryArrayList, Comparator.comparing(Map.Entry::getValue));
        screen.append("-------------------------------------------------\n");
        screen.append(analy.select_name+"与其他人的其密度为：\n");
        for (Map.Entry<String, Integer> entry : entryArrayList) {
            screen.append(entry.getKey() + "：" + entry.getValue()+"\n");
        }
    }
	}
}

public static void main(String[] args) {
	new textprocess();
}
}
