package Filemanager;
//控制cmd面板的相关内容
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class CmdOutput extends JTextArea implements Serializable {
    private static final long serialVersionUID = 1L;
    public CmdOutput()
    {
        this.setBackground(Color.BLACK);
        Font newFont = new Font("Times New Roman", Font.BOLD, 18);
        this.setFont(newFont);
        this.setForeground(Color.white);
        this.append("welcome to virtual file manager! Made by Wenzhuo/Qiaoyan/Guanghu\n");
        this.append("You can see some Output information here~\n");
        this.append("Contact me : 769586919@qq.com/github:wenzhu23333/www.yangwenzhuo.tk\n");
        this.append("root@me:~#\n");
        this.setEnabled(false);
        this.setLineWrap(true);
    }
    public void LocateTrace(int TraceNum)
    {
        this.append("root@me:~# display TrackNum = "+TraceNum+";\n");
        this.setCaretPosition(this.getText().length());
    }
    public void FormatDisk()
    {
        this.append("root@me:~# format disk --delete all file and directory; \n");
        this.setCaretPosition(this.getText().length());
    }
    public void Error()
    {
        this.append("root@me:~# error operating ;   \n");
        this.setCaretPosition(this.getText().length());
    }
    public void select(FileFCB fileFCB)
    {
        if (fileFCB.getFCBType() == FCB_Type.directory)
        {
            this.append("root@me:~# select directory " + fileFCB.getFilename()+", ID = "+fileFCB.getID()+";\n" );
        }
        else if (fileFCB.getFCBType() == FCB_Type.file)
        {
            this.append("root@me:~# select file " + fileFCB.getFilename()+", ID = "+fileFCB.getID()+";\n" );
        }
        this.setCaretPosition(this.getText().length());
    }

    public void makeDir(FileFCB fileFCB)
    {
        this.append("root@me:~# make directory " + fileFCB.getFilename()+"  ID = " +fileFCB.getID()+" ;\n");
        this.setCaretPosition(this.getText().length());
    }

    public void dupilication_of_name()
    {
        this.append("root@me:~# dupilication_of_name ! ; \n");
        this.setCaretPosition(this.getText().length());
    }
    public void illegal_name()
    {
        this.append("root@me:~# illegal_name, please use letter,figure or underline!  ; \n");
        this.setCaretPosition(this.getText().length());
    }
    public void memory_lack()
    {
        this.append("root@me:~# memory_lack , please delete some file or format disk ; \n");
        this.setCaretPosition(this.getText().length());
    }
    public void makeFile(FileFCB fileFCB,int Track,int block_num)
    {
        this.append("root@me:~# make file "+fileFCB.getFilename()+" ID = "+ fileFCB.getID()+" ; \n");
        this.append("This file saved in Track :"+Track+" Block : "+block_num+ " ; \n");
        this.setCaretPosition(this.getText().length());
    }
    public void Delete(FileFCB fileFCB)
    {
        if (fileFCB.getFCBType() == FCB_Type.directory)
        {
            this.append("root@me:~# delete directory and its files " + fileFCB.getFilename()+", ID = "+fileFCB.getID()+";\n" );
        }
        else if (fileFCB.getFCBType() == FCB_Type.file)
        {
            this.append("root@me:~# delete file " + fileFCB.getFilename()+", ID = "+fileFCB.getID()+";\n" );
        }
        this.setCaretPosition(this.getText().length());
    }
    public void rename(FileFCB fileFCB,String Oldname)
    {
        if (fileFCB.getFCBType() == FCB_Type.directory)
        {
            this.append("root@me:~# rename directory from '" + Oldname +"' to '"+fileFCB.getFilename()+"', ID = "+fileFCB.getID()+";\n" );
        }
        else if (fileFCB.getFCBType() == FCB_Type.file)
        {
            this.append("root@me:~# rename file from '" + Oldname +"' to '"+fileFCB.getFilename()+"', ID = "+fileFCB.getID()+";\n");
        }
        this.setCaretPosition(this.getText().length());
    }

    public void openFile(FileFCB fileFCB)
    {
        this.append("root@me:~# open file " + fileFCB.getFilename()+"  ID = " +fileFCB.getID()+" ;\n");
        this.setCaretPosition(this.getText().length());
    }
    public void closeFile(FileFCB fileFCB)
    {
        this.append("root@me:~# close and save file " + fileFCB.getFilename()+"  ID = " +fileFCB.getID()+" ;\n");
        this.setCaretPosition(this.getText().length());
    }
    public void OutputExDispatch()
    {
        this.append("root@me:~#  (Example Track trace) Track num: 100,25,57,97,50,60 ;\n");
        this.setCaretPosition(this.getText().length());
    }
    public void Tracktracing(int Tracenum)
    {
        this.append("root@me:~# Next trace Num : "+Tracenum+"  ;\n");
        this.setCaretPosition(this.getText().length());
    }
}
