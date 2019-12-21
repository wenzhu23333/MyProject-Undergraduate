package Filemanager;import javax.swing.*;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.awt.event.WindowEvent;import java.awt.event.WindowListener;import java.io.*;public class FileManager extends JFrame implements WindowListener,ActionListener {    private static final long serialVersionUID = 1L;private FileManagerUIController fileManagerUIController = null;private JMenuBar menuBar = null;//菜单栏private JMenu fileMenu, helpMenu = null;//文件栏和帮助栏private JMenuItem exitMenuItem = null, helpMenuItem = null, aboutMenuItem = null,dispatchItem = null,DispatchExItem = null;//退出按钮，帮助按钮和关于按钮public FileManager(FileManagerUIController fileManagerUIController){    super("虚拟文件管理系统");    this.setSize(Conf.FRAME_LENGTH,Conf.FRAME_WIDTH);    this.fileManagerUIController = this.get();    //读取存储在文件的信息，每次关闭都会自动存储，下次打开会自动还原    if (this.fileManagerUIController== null) {        this.fileManagerUIController = fileManagerUIController;    }// Create menu    menuBar = new JMenuBar();    fileMenu = new JMenu("设置（F）");    helpMenu = new JMenu("帮助（H）");    fileMenu.setMnemonic('F');    helpMenu.setMnemonic('H');    exitMenuItem = new JMenuItem("退出（E）");    helpMenuItem = new JMenuItem("帮助（H）");    dispatchItem = new JMenuItem("调度（D）");    DispatchExItem = new JMenuItem("调度示例（M）");    aboutMenuItem = new JMenuItem("关于（A）");    exitMenuItem.setMnemonic('E');    helpMenuItem.setMnemonic('H');    aboutMenuItem.setMnemonic('A');    dispatchItem.setMnemonic('D');    DispatchExItem.setMnemonic('M');    // 添加监听    exitMenuItem.addActionListener(this);    helpMenuItem.addActionListener(this);    aboutMenuItem.addActionListener(this);    dispatchItem.addActionListener(this);    DispatchExItem.addActionListener(this);    // 添加到frame上面    fileMenu.add(exitMenuItem);    helpMenu.add(helpMenuItem);    helpMenu.add(aboutMenuItem);    helpMenu.add(dispatchItem);    helpMenu.add(DispatchExItem);    //添加二级菜单    menuBar.add(fileMenu);    menuBar.add(helpMenu);    // 添加监听    this.addWindowListener(this);    this.setJMenuBar(menuBar);    //设定菜单栏    GUIUtil.toCenter(this);    //设定位置    this.setResizable(false);    //不能随意改动大小    this.setVisible(true);    this.add(this.fileManagerUIController.getFileManagerUIview());    //添加写好的面板（该类继承自面板），由于FileManager中只存有后台逻辑类，必须通过后台逻辑类来获得界面类}    public void actionPerformed(ActionEvent e) {        Object event = e.getSource();        if (event == exitMenuItem) {            System.exit(0);        } else if (event == helpMenuItem) {            JOptionPane.showMessageDialog(null,                    "傻瓜式的很好用~~需要帮助联系作者QQ769586919",                    "帮助",                    JOptionPane.INFORMATION_MESSAGE);        } else if (event == aboutMenuItem) {            JOptionPane.showMessageDialog(null,                    "这是操作系统课程设计的任务~~" +                            "制作by CSU计科1604杨文卓，汪光虎，李俏岩~~",                    "Copyright(c)",                    JOptionPane.INFORMATION_MESSAGE);        }        else if (event == dispatchItem)        {            String[] possibleValues = {"FCFS","SSTF","SCAN","CSCAN"};            String selectedValue =(String)JOptionPane.showInputDialog(null,"请选择磁盘调度算法(默认FCFS)","磁盘调度",                    JOptionPane.INFORMATION_MESSAGE,null,possibleValues,possibleValues[0]);            Conf.Dispatch = selectedValue;        }        else if (event == DispatchExItem)        {            fileManagerUIController.getFileManagerUIview().Cmd.OutputExDispatch();            Tracktracing.UntracedNum.addElement(100);            Tracktracing.UntracedNum.addElement(50);            Tracktracing.UntracedNum.addElement(25);            Tracktracing.UntracedNum.addElement(57);            Tracktracing.UntracedNum.addElement(97);            Tracktracing.UntracedNum.addElement(50);            Tracktracing.UntracedNum.addElement(60);            new Thread(fileManagerUIController.getFileManagerUIview().tracktracing).start();        }    }    public FileManagerUIController getFileManagerUIController() {        return fileManagerUIController;    }    public void setFileManagerUIController(FileManagerUIController fileManagerUIController) {        this.fileManagerUIController = fileManagerUIController;    }    public void windowClosing(WindowEvent e) {        this.save();        System.exit(0);    }    private void save() {        File f = new File(Conf.INFO_FILE);        FileOutputStream fos = null;        ObjectOutputStream oos = null;        try {            fos = new FileOutputStream(f, false);            oos = new ObjectOutputStream(fos);            oos.writeObject(fileManagerUIController);            fos.close();            oos.close();        } catch (FileNotFoundException e) {            e.printStackTrace();        } catch (IOException e) {            e.printStackTrace();        }    }    private FileManagerUIController get() {        File f = new File(Conf.INFO_FILE);        FileManagerUIController tmpFileSysUIController = null;        FileInputStream fis = null;        ObjectInputStream ois = null;        try {            fis = new FileInputStream(f);            ois =  new ObjectInputStream(fis);            tmpFileSysUIController = (FileManagerUIController) ois.readObject();            fis.close();            ois.close();        } catch (FileNotFoundException e) {            tmpFileSysUIController = null;        } catch (IOException e) {            tmpFileSysUIController = null;        } catch (ClassNotFoundException e) {            tmpFileSysUIController = null;        }        return tmpFileSysUIController;    }    public void windowOpened(WindowEvent e) {}    public void windowClosed(WindowEvent e) {}    public void windowIconified(WindowEvent e) {}    public void windowDeiconified(WindowEvent e) {}    public void windowActivated(WindowEvent e) {}    public void windowDeactivated(WindowEvent e) {}}