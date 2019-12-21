package Filemanager;

import java.io.Serializable;

public class FileFAT implements Serializable {
    private static final long serialVersionUID = 1L;
private int ID;
private int nextID;
private String data = null;
private boolean isUsed ;
private int usedSize;

public FileFAT()
{
    ID = this.hashCode();
    nextID = -1;
    isUsed = false;
    usedSize = 0;
}
    public int getID() {
        return ID;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public int getNextID() {
        return nextID;
    }

    public int getUsedSize() {
        return usedSize;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNextID(int nextID) {
        this.nextID = nextID;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void setUsedSize(int usedSize) {
        this.usedSize = usedSize;
    }
}
