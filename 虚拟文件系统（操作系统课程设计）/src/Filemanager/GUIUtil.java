package Filemanager;//将程序放在页面最中央import java.awt.*;public class GUIUtil {    public static void toCenter(Component comp)    {        GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();        Rectangle rec =  ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();        comp.setLocation(((int)rec.getWidth()-comp.getWidth())/2,                ((int)rec.getHeight()-comp.getHeight())/2);  }}