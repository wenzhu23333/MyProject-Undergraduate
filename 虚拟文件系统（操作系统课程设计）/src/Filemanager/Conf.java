package Filemanager;
//这个文件是配置文件，记录一些字符串的配置定义等

import java.io.Serializable;

enum FCB_Type { directory, file };
enum Status_Type { all_right, dupilication_of_name, illegal_name, memory_lack };

//从左到右依次为完成
public class Conf implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String Dispatch = "FCFS";
    static int FRAME_LENGTH = 1630,FRAME_WIDTH = 900;
    static int BUTTON_PANE_LENGTH = 1000, BUTTON_PANE_WIDTH = 100;
    static int TREE_AND_EDIT_PANE_LENGTH = BUTTON_PANE_LENGTH, TREE_AND_EDIT_PANE_WIDTH = FRAME_WIDTH - BUTTON_PANE_WIDTH;
    static int NUMBER_OF_BUTTONS = 8;
    static int MAX_TEXT = 750;
    static int DISK_DISPLAY_PANE_LENGTH = 400,DISK_DISPLAY_PANE_WIDTH = 600;

    static int Track_num = 128; //确定为128个磁道，初始化时可以自定义
    static int BLOCK_SIZE = 256; // 每一个块的大小
    static  int BLOCK_NUM = 128; //每个块大小为128字节

    /*
    static int CLUSTER_SIZE = BLOCK_SIZE * 4; // 每一个簇的大小
    static int CLUSTER_NUM = 2048;
    */
    static int MEMORY_SIZE = Track_num*BLOCK_SIZE*BLOCK_NUM;//CLUSTER_SIZE * CLUSTER_NUM; // 内存大小
    static String INFO_FILE = "data.dat";
    static int END_OF_FAT = -1; // Fat表结束标志
    static int PARENT_OF_ROOT = -1; // 根结点的父节点
}
