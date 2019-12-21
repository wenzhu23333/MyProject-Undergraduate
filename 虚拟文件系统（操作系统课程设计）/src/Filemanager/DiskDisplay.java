package Filemanager;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class DiskDisplay extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int blockNum=128;
    private  int LengthOfSide = 35;
     private Color[] SavaColor = new Color[128];
    public DiskDisplay()
    {
        this.setVisible(true);
        this.setBackground(Color.white);
        for(Color color:SavaColor)
        {
            color = null;
        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        int x = 0,y = 0;
        int columnNum = 16;
        int rowNum = 8;
        g.setColor(Color.black);
        for(int i = 0;i<=rowNum;i++)
        {
            g.drawLine(x,y+i*LengthOfSide,x+560,y+i*LengthOfSide);
        }

        for (int i = 0;i<=columnNum;i++)
        {
            g.drawLine(x+i*LengthOfSide,y,x+i*LengthOfSide,y+LengthOfSide*8);
        }

        for(int i = 0;i<blockNum;i++)
        {
            if (SavaColor[i] != null) {
                int temp = i;
                int tempX = temp % columnNum * LengthOfSide;
                int tempY = (temp / columnNum) * LengthOfSide;
                g.setColor(SavaColor[i]);
                g.fillRect(tempX+1, tempY+1, LengthOfSide-1, LengthOfSide-1);
            }
        }
    }
    public void paintSquare(int BlockNum,Color color)
    {
        this.SavaColor[BlockNum] = color;
        this.repaint();
    }
    public void clearSquare(int BlockNum)
    {
        this.SavaColor[BlockNum] = null;
        this.repaint();
    }

    public void clearALLSquare()
    {
        for (int i =0;i<blockNum;i++)
        {
            this.SavaColor[i] = null;
        }
        this.repaint();
    }
}
