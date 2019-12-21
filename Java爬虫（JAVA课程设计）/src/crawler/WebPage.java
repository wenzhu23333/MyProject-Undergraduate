package crawler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class WebPage {

    public static String getPageHtml(String pageurl)
    {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(pageurl);
            InputStream in = url.openStream();
            InputStreamReader inputStreamReader  = new InputStreamReader(in,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
               // stringBuffer.append("\n");
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     return stringBuffer.toString();
    }
    public static String getText(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        txtcontent = txtcontent.replaceAll(" ","");
        txtcontent = txtcontent.replaceAll("[^\u4e00-\u9fa5]","");
        return txtcontent;
    }

    public static String[] getURL(File file)
    {
        StringBuffer stringBuffer = new StringBuffer();
        String[] splitString = null;
        //String[] BatHtmls = null
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line ;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            String strings = stringBuffer.toString();
             splitString = strings.split(";");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  splitString;
    }
    public static String[] getWords(File file)
    {
        StringBuffer stringBuffer = new StringBuffer();
        String[] splitString = null;
        //String[] BatHtmls = null
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line ;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            String strings = stringBuffer.toString();
            splitString = strings.split(";");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  splitString;

    }

}
