package text_disposal;

import java.util.TreeMap;

import javax.xml.ws.handler.MessageContext;

public class StringStat {
public static int[] number= new int[10];
public static void cal() {
	
	for(int i=0;i<=9;i++)
	{
		int record=0;
		String[] res=input.name[i].split("，");
		for(String str1:res)
		{
			record+=stat_time(str1, textprocess.msg);
		}
		number[i]=record;
	}
	
}
public static int stat_time(String name,String msg)
{
	int num = 0;
for(int i=0;i<msg.length();i++)
{
	if(msg.regionMatches(i, name, 0, name.length())) {
		num+=1;
	}
}
return num;
}

public static int[] cal_density(String name)
{
	int [] a =new int[600];
	for(int i=0;i<600;i++)
	{
		int record=0;
		String[] res=name.split("，");
		for(String str1:res)
		{
			record+=stat_time(str1, textprocess.msg.substring(1001*i, 1001*i+1001));
		}
		a[i]=record;
	}
	return a;
}
public static TreeMap<String, Integer> cal_analy()
{
	TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
	 int[] a= new int[600];
	 a=cal_density(analy.select_name);
	 int[] b= new int[600];
	 for(int i=0;i<10;i++)
	 {
		 int record=0;
		 b=cal_density(input.name[i]);
		 if(input.name[i]==analy.select_name)
			 continue;
		 for(int j=0;j<600;j++)
		 {
			 if(b[j]>0&&a[j]>0)
				 record++;
		 }
		 tm.put(input.name[i], record);
	 }
	return tm;
}
//确定name在小说中的出现次数，并返回次数
/*public int[] stat_density(String name) {
int mark[]= new int[msg.length()];
for(int i=0;i<msg.length();i++)
        mark[i]=0;
for(int i=0;i<msg.length();i++)
{
	if(msg.regionMatches(i, name, 0, name.length())) {
		mark[i]=1;
	}
}	
return mark;
}*/
//从头到尾扫描小说文本，如果在此处找到此name，那么将标记此处为1，初始化所有为0；
/*static int[][] a=new int[10][1800];
public static void cal_density()
{
	
	int x=textprocess.msg.length()/1800;
	int begin=0,over=x;
	
}*/

//测试正确结果
/*public static void main(String[] args) throws Exception
{
	System.out.println(StringStat.stat_time("", new fileloader().getdata("C:\\Users\\杨文卓\\Desktop\\java课设\\三国演义.txt")));;
	
}*/
}
