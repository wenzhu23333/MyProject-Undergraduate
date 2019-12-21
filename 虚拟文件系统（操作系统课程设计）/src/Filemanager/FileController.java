package Filemanager;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileController implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<FileFCB> FCBList = null;
    private List<FileFAT> FatList = null;

    public FileController()
    {
        FCBList = new LinkedList<FileFCB>();
        FatList = new LinkedList<FileFAT>();
        FileFCB rootFCB = new FileFCB("Root");
        rootFCB.setParentID(Conf.PARENT_OF_ROOT);
        rootFCB.setFCBType(FCB_Type.directory);
        FCBList.add(rootFCB);
    }

    public void formatFileSystem() {
        FileFCB rootFCB = FCBList.get(0);
        FCBList.clear();
        FatList.clear();
        rootFCB.setParentID(Conf.PARENT_OF_ROOT); // 根目录的父节点ID规定为0
        rootFCB.setFCBType(FCB_Type.directory);
        FCBList.add(rootFCB);
        this.fresh();
    }

    public Status_Type createDir(String dirName, int parentID, FileFCB dirFCB) {
        if (this.isDupilicationOfName(dirName, parentID, FCB_Type.directory))
            return Status_Type.dupilication_of_name;
        if (this.isNameLegal(dirName))
            return Status_Type.illegal_name;
        dirFCB.setParentID(parentID);
        dirFCB.setFCBType(FCB_Type.directory);
        FCBList.add(dirFCB);
        return Status_Type.all_right;
    }

    public Status_Type createFile(String fileName, int parentID, FileFCB fileFCB) {
        if (this.isDupilicationOfName(fileName, parentID, FCB_Type.file))
            return Status_Type.dupilication_of_name;
        if (this.isNameLegal(fileName))
            return Status_Type.illegal_name;
        fileFCB.setParentID(parentID);
        fileFCB.setFCBType(FCB_Type.file);
        FileFAT tmpFat = null;
        FileFAT tmpFat2 = null;
        for (int i = 0;i<FatList.size();i++) {
            tmpFat = FatList.get(i);
            if (!tmpFat.isUsed())
            {
                tmpFat2 = tmpFat;
                break;
            }
        }
        if (tmpFat2 == null)
        {
            FileFAT fileFat = new FileFAT();
            fileFat.setData("");
            fileFat.setUsed(true);
            fileFat.setNextID(Conf.END_OF_FAT);
            fileFCB.setFileFat(fileFat);
            FatList.add(fileFat);
            FCBList.add(fileFCB);
        }
        else
        {
            FileFAT fileFat = tmpFat2;
            fileFat.setData("");
            fileFat.setUsed(true);
            fileFat.setNextID(Conf.END_OF_FAT);
            fileFCB.setFileFat(fileFat);
            FCBList.add(fileFCB);
        }

        return Status_Type.all_right;
    }

    public void deleteDir(int dirID) {
        List<FileFCB> toDeleteList = new LinkedList<FileFCB>();
        this.searchFCBByParentID(dirID, toDeleteList);
        int deleteSize = toDeleteList.size();
        for (int i = 0; i < deleteSize; i++) {
            FileFCB tmpFCB = toDeleteList.get(i);
            if (tmpFCB.getFCBType() == FCB_Type.directory)
                this.recursiveDeleteDir(tmpFCB.getID(), toDeleteList);
        }
        // 首先删除所有需要删除的文件
        for (Iterator<FileFCB> it = toDeleteList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getFCBType() == FCB_Type.file)
                this.deleteFile(tmpFCB.getID());
        }
        FCBList.removeAll(toDeleteList);
        FCBList.remove(this.searchFCBByID(dirID));
    }

    public void deleteFile(int fileID) {
        // 获得文件FCB和Fat表首项
        FileFCB fileFCB = this.searchFCBByID(fileID);
        FileFAT fileFat = fileFCB.getFileFat();
        int nextID;
        FCBList.remove(fileFCB);
        do {
            fileFat.setUsed(false);
            nextID = fileFat.getNextID();
            fileFat = this.searchNextFatByID(nextID);
        } while (nextID != Conf.END_OF_FAT);
        fileFat.setUsed(false);
    }

    public Status_Type rename(int fileID, String newName) {
        if (this.isNameLegal(newName)) {
            return Status_Type.illegal_name;
        }
        FileFCB changedFCB = this.searchFCBByID(fileID);
        int parentID = changedFCB.getParentID();
        List<FileFCB> siblingList = new LinkedList<FileFCB>();
        this.searchFCBByParentID(parentID, siblingList);
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = it.next();
            if (tmpFCB.getFilename().equals(newName)) {
                return Status_Type.dupilication_of_name;
            }
        }
        changedFCB.setModifyDate(System.currentTimeMillis());
        changedFCB.setFilename(newName);
        return Status_Type.all_right;
    }

    public Status_Type move(int fileID, int parentID) {
        FileFCB movedFCB = this.searchFCBByID(fileID);
        List<FileFCB> targetSubFileList = new LinkedList<FileFCB>();
        this.searchFCBByParentID(parentID, targetSubFileList);
        for (Iterator<FileFCB> it = targetSubFileList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getFilename().equals(movedFCB.getFilename())) {
                return Status_Type.dupilication_of_name;
            }
        }
        movedFCB.setParentID(parentID);
        for (int i = 0; i < 50; i++)
            this.fresh();
        return Status_Type.all_right;
    }

    public Status_Type saveFile(int fileID, String buffer)
    {
        FileFCB fileFCB = this.searchFCBByID(fileID);
        FileFCB parentFCB = this.searchFCBByID(fileFCB.getParentID());
        FileFAT fileFat = fileFCB.getFileFat();
        if (this.getRoot().getFileSize() - fileFCB.getFileSize() + buffer.length() > Conf.MEMORY_SIZE)
            return Status_Type.memory_lack;
        String[] subString = this.splitString(buffer);
        if (subString.length == 1 || subString.length == 0)
        {
            fileFat.setData(buffer);
            fileFat.setUsedSize(buffer.length());
            fileFat.setNextID(Conf.END_OF_FAT);
        } else if (buffer.length() <= Conf.BLOCK_SIZE) {
            fileFat.setData(buffer);
            fileFat.setUsedSize(buffer.length());
            fileFat.setNextID(Conf.END_OF_FAT);
        } else {
            FileFAT[] tmpFats = new FileFAT[subString.length - 1];
            for (int i = 0; i < tmpFats.length; i++) {

                FileFAT tmpFat = null;
                FileFAT tmpFat2 = null;
                for (int j = 0;j<FatList.size();j++) {
                    tmpFat = FatList.get(j);
                    if (!tmpFat.isUsed())
                    {
                        tmpFat2 = tmpFat;
                        break;
                    }
                }
                if (tmpFat2 == null)
                {
                    tmpFats[i] = new FileFAT();
                    tmpFats[i].setUsed(true);
                    tmpFats[i].setData(subString[i]);
                    tmpFats[i].setUsedSize(subString[i].length());
                    FatList.add(tmpFats[i]);
                }
                else
                {
                    tmpFats[i] = tmpFat2;
                    tmpFats[i].setUsed(true);
                    tmpFats[i].setData(subString[i]);
                    tmpFats[i].setUsedSize(subString[i].length());
                    //fileFCB.setFileFat(fileFat);
                }
            }
            fileFat.setUsed(true);
            fileFat.setNextID(tmpFats[0].getID());
            for (int i = 0; i < tmpFats.length - 1; i++) {
                tmpFats[i].setNextID(tmpFats[i + 1].getID());
            }
            tmpFats[tmpFats.length - 1].setNextID(Conf.END_OF_FAT);
        }
        fileFCB.setFileSize(buffer.length());
        fileFCB.setModifyDate(System.currentTimeMillis());
        parentFCB.setModifyDate(System.currentTimeMillis());
        for (int i = 0; i < 50; i++)
            this.fresh();
        this.getRoot().setFileSize(this.getRootSize());
        return Status_Type.all_right;
    }

    public int getRootSize() {
        FileFCB rootFCB = this.getRoot();
        int totalSize = 0;
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getParentID() == rootFCB.getID()) {
                totalSize += tmpFCB.getFileSize();
            }
        }
        return totalSize;
    }

    public String readFile(int fileID) {
        FileFCB fileFCB = this.searchFCBByID(fileID);
        FileFAT fileFat = fileFCB.getFileFat();
        FileFAT tmpFat = fileFat;
        String buffer = new String("");
        while (tmpFat.getNextID() != Conf.END_OF_FAT)
        {
            buffer = String.format(buffer + tmpFat.getData());
            tmpFat = this.searchFatByID(tmpFat.getNextID());
        }
        buffer = String.format(buffer + tmpFat.getData());
        return buffer;
    }

    public int getSubNumber(int dirID) {
        List<FileFCB> SubFileList = new LinkedList<FileFCB>();
        this.searchFCBByParentID(dirID, SubFileList);
        return SubFileList.size();
    }

    public void fresh() {
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getFCBType() == FCB_Type.directory) {
                int newSize = this.getDirSize(tmpFCB.getID());
                tmpFCB.setFileSize(newSize);
            }
        }
    }

    private int getDirSize(int dirID) {
        int totalSize = 0;
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getParentID() == dirID)
                totalSize += tmpFCB.getFileSize();
        }
        return totalSize;
    }

    private String[] splitString(String buffer) {
        int number = buffer.length() / Conf.BLOCK_SIZE;
        int surplus = buffer.length() % Conf.BLOCK_SIZE;
        if (surplus != 0)
            number++;
        String[] subString = new String[number];
        for (int i = 0; i < number; i++) {
            if (i == number - 1)
                subString[i] = buffer.substring(i * Conf.BLOCK_SIZE);
            else
                subString[i] = buffer.substring(i * Conf.BLOCK_SIZE, (i + 1) * Conf.BLOCK_SIZE);
        }
        return subString;
    }

    // 判断命名是否合法
    private boolean isNameLegal(String fileName) {
        if (fileName == null || fileName.length() <= 0 || fileName.length() >= 255)
            return false;
        String regex = "^[a-zA-Z_]+[a-zA-Z0-9_]*";
        return !Pattern.compile(regex).matcher(fileName).matches();
    }

    // 检测是否重名
    private boolean isDupilicationOfName(String fileName, int parentID, FCB_Type fileType) {
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            FileFCB tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getFilename().endsWith(fileName) &&
                    tmpFCB.getParentID() == parentID &&
                    tmpFCB.getFCBType() == fileType)
                return true;
        }
        return false;
    }

    // 一些与FCB和Fat表相关的搜索方法
    private FileFCB searchFCBByID(int ID) {
        FileFCB tmpFCB = null;
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getID() == ID)
                break;
        }
        return tmpFCB;
    }

    private void searchFCBByParentID(int parentID, List<FileFCB> subFCBList) {
        FileFCB tmpFCB = null;
        for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
            tmpFCB = (FileFCB)it.next();
            if (tmpFCB.getParentID() == parentID) {
                subFCBList.add(tmpFCB);
            }
        }
    }

    public FileFAT searchNextFatByID(int ID) {
        FileFAT tmpFat = null;
        for (Iterator<FileFAT> it = FatList.iterator(); it.hasNext();) {
            tmpFat = (FileFAT) it.next();
            if (tmpFat.getID() == ID)
                break;
        }
        return tmpFat;
    }

    private FileFAT searchFatByID(int ID) {
        FileFAT tmpFat = null;
        for (Iterator<FileFAT> it = FatList.iterator(); it.hasNext();) {
            tmpFat = (FileFAT) it.next();
            if (tmpFat.getID() == ID)
                break;
        }
        return tmpFat;
    }

    private void recursiveDeleteDir(int dirID, List<FileFCB> toDeleteList) {
        int beforeSize = toDeleteList.size();
        this.searchFCBByParentID(dirID, toDeleteList);
        int afterSize = toDeleteList.size();
        if (beforeSize == afterSize) {
            return ;
        }
        for (int i = beforeSize; i < afterSize; i++) {
            FileFCB tmpFCB = toDeleteList.get(i);
            if (tmpFCB.getFCBType() == FCB_Type.directory) {
                this.recursiveDeleteDir(tmpFCB.getID(), toDeleteList);
            }
        }
    }


    public List<FileFAT> getFatList() {
        return FatList;
    }

    public void setFatList(List<FileFAT> fatList) {
        FatList = fatList;
    }

    public void setFCBList(List<FileFCB> FCBList) {
        this.FCBList = FCBList;
    }

    public List<FileFCB> getFCBList() {
        return FCBList;
    }
    public FileFCB getRoot()
    {
        return FCBList.get(0);
    }
}
