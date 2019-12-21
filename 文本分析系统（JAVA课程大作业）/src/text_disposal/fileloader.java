package text_disposal;
import java.io.File;
import java.io.FileInputStream;
public class fileloader {
public static String getdata(String path) throws Exception {
	File srcfile=new File(path);
	FileInputStream fis=new FileInputStream(srcfile);
	byte[] data=new byte[(int)srcfile.length()];
	fis.read(data);
	String msg=new String(data);
	fis.close();
	return msg;
}
}
//将所有文本读取到string msg里面
