package Filemanager;

import java.io.Serializable;
import java.util.Vector;

//用于磁盘调度的类
public class DiskDispatch implements Serializable {
    private static final long serialVersionUID = 1L;
    //先来先服务
    public static int FCFS(Vector input){
        if(!input.isEmpty())
        {
            Object object = input.firstElement();
            input.remove(input.firstElement());
            return (int) object;
        }
        else return -1;
    }
    //最短寻道时间优先
    public static  int SSTF(int TraceNum,Vector input)
    {
        if(!input.isEmpty())
        {
            int max = 9999;
            int index = 0;
            Object object = 0;
            for (int i = 0;i<input.size();i++)
                if(Math.abs((int)input.get(i)-TraceNum)<max)
                {
                    max = Math.abs((int)input.get(i)-TraceNum);
                    index = i;
                    object = input.get(i);
                }
                input.remove(index);
            return (int)object;
        }
        else return -1;
    }
    //电梯调度 ,1为向大磁道号移动，0为向小磁道号移动
    public static int SCAN(int direction,int TraceNum,Vector input)
    {
        if(!input.isEmpty())
        {
            int max = 9999;

            int index = -1;
            Object object = 0;
            if(direction == 1)
            {
                for(int i = 0;i<input.size();i++)
                {
                    if ((int)input.get(i)>=TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                    {
                        max = Math.abs((int)input.get(i)-TraceNum);
                        object = input.get(i);
                        index = i;
                    }
                }
                if (index == -1)
                {
                    for(int i = 0;i<input.size();i++)
                    {
                        if ((int)input.get(i)<TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                        {
                            max = Math.abs((int)input.get(i)-TraceNum);
                            object = input.get(i);
                            index = i;
                        }
                    }
                }
            }
            else if(direction == 0)
            {
                for(int i = 0;i<input.size();i++)
                {
                    if ((int)input.get(i)<=TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                    {
                        max = Math.abs((int)input.get(i)-TraceNum);
                        object = input.get(i);
                        index = i;
                    }
                }
                if (index == -1)
                {

                    for(int i = 0;i<input.size();i++)
                    {
                        if ((int)input.get(i)>=TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                        {
                            max = Math.abs((int)input.get(i)-TraceNum);
                            object = input.get(i);
                            index = i;
                        }
                    }
                }
            }
            input.remove(index);
            return (int)object;
        }
        else return -1;
    }
//循环扫描算法
    public static int CSCAN(int direction,int TraceNum,Vector input)
    {
        if(!input.isEmpty())
        {
            int max = 9999;
            int min = -1;
            int index = -1;
            Object object = 0;
            if(direction == 1)
            {
                for(int i = 0;i<input.size();i++)
                {
                    if ((int)input.get(i)>=TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                    {
                        max = Math.abs((int)input.get(i)-TraceNum);
                        object = input.get(i);
                        index = i;
                    }
                }
                if (index == -1)
                {
                    for(int i = 0;i<input.size();i++)
                    {
                        if ((int)input.get(i)<TraceNum&&Math.abs((int)input.get(i)-TraceNum)>min)
                        {
                            min = Math.abs((int)input.get(i)-TraceNum);
                            object = input.get(i);
                            index = i;
                        }
                    }
                }
            }
            else if(direction == 0)
            {
                for(int i = 0;i<input.size();i++)
                {
                    if ((int)input.get(i)<=TraceNum&&Math.abs((int)input.get(i)-TraceNum)<max)
                    {
                        max = Math.abs((int)input.get(i)-TraceNum);
                        object = input.get(i);
                        index = i;
                    }
                }
                if (index == -1)
                {

                    for(int i = 0;i<input.size();i++)
                    {
                        if ((int)input.get(i)>=TraceNum&&Math.abs((int)input.get(i)-TraceNum)>min)
                        {
                            min = Math.abs((int)input.get(i)-TraceNum);
                            object = input.get(i);
                            index = i;
                        }
                    }
                }
            }
            input.remove(index);
            return (int)object;
        }
        else return -1;
    }
}

