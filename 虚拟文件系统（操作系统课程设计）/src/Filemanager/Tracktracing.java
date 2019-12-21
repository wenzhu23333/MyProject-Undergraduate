package Filemanager;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Vector;
//这是实现路由追踪的类
public class Tracktracing extends JPanel implements Runnable,Serializable {
    private static final long serialVersionUID = 1L;
    public static volatile Vector<Integer> UntracedNum = null;
    public static volatile Vector<Integer> TracedNum = null;
    private int x ;
    private transient Thread th = new Thread(this);
    //在存储文件时，线程不能被序列化，transient关键字用来解决这个问题。
    public Tracktracing(Vector<Integer> UntracedNum,Vector<Integer> TracedNum)

    {
        this.setVisible(true);
        this.TracedNum = TracedNum;
        this.UntracedNum = UntracedNum;
        TracedNum.addElement(0);
        x = TracedNum.lastElement();
        th.start();
    }

    public void paint(Graphics g)
    {

            super.paint(g);
            g.setColor(Color.BLACK);
            int distance = 0;
            for (int i = 1;i<TracedNum.size();i++)
            {
                int a = TracedNum.get(i-1);
                int b = TracedNum.get(i);
                if(b>a)
                {
                    g.drawLine(a*4,distance,4*b,distance+b-a);
                    g.fillOval(4*b-5,distance+b-a-5,10,10);
                    distance+=(b-a);
                }
                else if (a>=b)
                {
                    g.drawLine(a*4,distance,4*b,distance+a-b);
                    g.fillOval(4*b-5,distance+a-b-5,10,10);
                    distance+=(a-b);
                }
            }
            int c = TracedNum.lastElement();
            if(x>c)
            {
                g.drawLine(c*4,distance,x*4,distance+x-c);
                g.fillOval(4*x-5,distance+x-c-5,10,10);

            }
            else if (x<c)
            {
                g.drawLine(c*4,distance,x*4,distance+c-x);
                g.fillOval(4*x-5,distance+c-x-5,10,10);
            }

    }
    @Override
    public void run() {
        while (!UntracedNum.isEmpty())
        {
            int a = 1;
            int nextTrack;
            int thisTrack = TracedNum.lastElement();
            if(Conf.Dispatch.equals("FCFS"))
            nextTrack = DiskDispatch.FCFS(UntracedNum);
            else if(Conf.Dispatch.equals("SSTF"))
                nextTrack = DiskDispatch.SSTF(thisTrack,UntracedNum);
            else if(Conf.Dispatch.equals("SCAN"))
            {
                nextTrack = DiskDispatch.SCAN(a,thisTrack,UntracedNum);
                if(nextTrack>=thisTrack) a = 1;
                else a = 0;
            }
            else if (Conf.Dispatch.equals("CSCAN"))
            {
                nextTrack = DiskDispatch.CSCAN(a,thisTrack,UntracedNum);
                if(nextTrack>=thisTrack) a = 1;
                else a = 0;
            }
            else nextTrack = DiskDispatch.FCFS(UntracedNum);
            this.x = thisTrack;
            while (x!=nextTrack) {

                    if (x < nextTrack)
                    {
                        x++;

                    }
                    else if(x>nextTrack)
                    {
                        x--;
                    }
                    this.repaint();
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TracedNum.addElement(nextTrack);
        }
    }
}
