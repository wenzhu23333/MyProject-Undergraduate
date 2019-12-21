package crawler;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame implements ActionListener {

    private JTextField jTextField = new JTextField();
    private JTextPane jTextPane = new JTextPane();
    private JScrollPane jScrollPane = new JScrollPane(jTextPane);
    private JPanel jPanel = new JPanel();
    private JButton extract = new JButton("提取");
    private JButton BatchExtract = new JButton("批量提取");
    private JButton Select = new JButton("选择词库");
    private JButton ExtractText = new JButton("提取文本");
    private JButton HighLight = new JButton("高亮显示");
    private JButton Output = new JButton("输出敏感词");

    private String htmls = null;
    private String extractText = null;

    private String[] BatWeb = null;
    private String[] BatHtmls = null;
    private String[] words = null;


    public MainWindow() {
        this.setVisible(true);
        this.setSize(900, 800);

        this.setLayout(null);
        this.setTitle("JAVA爬虫");
        this.add(jPanel);
        jPanel.setSize(900, 800);
        jPanel.setLayout(null);

        jPanel.add(jTextField);
        jTextField.setBounds(50, 50, 600, 25);

        jPanel.add(jScrollPane);
        jScrollPane.setBounds(50, 100, 600, 600);

        jPanel.add(extract);
        extract.setBounds(700, 50, 100, 50);
        extract.addActionListener(this);

        jPanel.add(BatchExtract);
        BatchExtract.setBounds(700, 150, 100, 50);
        BatchExtract.addActionListener(this);

        jPanel.add(Select);
        Select.setBounds(700, 250, 100, 50);
        Select.addActionListener(this);

        jPanel.add(ExtractText);
        ExtractText.setBounds(700, 350, 100, 50);
        ExtractText.addActionListener(this);

        jPanel.add(HighLight);
        HighLight.setBounds(700, 450, 100, 50);
        HighLight.addActionListener(this);

        jPanel.add(Output);
        Output.setBounds(700, 550, 100, 50);
        Output.addActionListener(this);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jTextPane.setEditable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == extract) {
            String getText = this.jTextField.getText().trim();

            if (getText.equals(""))
                JOptionPane.showMessageDialog(null, "输入网址不能为空！",
                        "Error", JOptionPane.ERROR_MESSAGE);
            else {
                String html = WebPage.getPageHtml(getText);
                this.htmls = html;
                jTextPane.setText("");
                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setFontSize(attributeSet, 15);
                FontMetrics fontMetrics = jTextPane.getFontMetrics(jTextPane.getFont());
                Document document = jTextPane.getDocument();
                int paneWith = jTextPane.getWidth();
                try {

                    document.insertString(document.getLength(), html, attributeSet);
                    for (int i = 0, cnt = 0; i < html.length(); ++i)
                        if ((cnt += fontMetrics.charWidth(htmls.charAt(i))) >= paneWith) {
                            cnt = 0;
                            document.insertString(i, "\n", attributeSet);
                            continue;
                        }

                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                //System.out.println(getText);

            }
        } else if (object == ExtractText) {
            String text = WebPage.getText(this.jTextPane.getText());
            this.extractText = text;
            FontMetrics fontMetrics = jTextPane.getFontMetrics(jTextPane.getFont());
            int paneWith = jTextPane.getWidth();
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            Document document = jTextPane.getDocument();
            jTextPane.setText(text);
            for (int i = 0, cnt = 0; i < text.length(); ++i)
                if ((cnt += fontMetrics.charWidth(text.charAt(i))) >= paneWith) {
                    cnt = 0;
                    try {
                        document.insertString(i, "\n", attributeSet);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }
        } else if (object == BatchExtract) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);
            File file = jFileChooser.getSelectedFile();
            BatWeb = WebPage.getURL(file);
            BatHtmls = new String[BatWeb.length];
            for (int i = 0; i < BatWeb.length; i++) {
                BatHtmls[i] = WebPage.getPageHtml(BatWeb[i]);
            }

            System.out.println(BatHtmls[3]);
            jTextPane.setText("");
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            StyleConstants.setFontSize(attributeSet, 15);
            Document document = jTextPane.getDocument();
            int paneWith = jTextPane.getWidth();

            for (int i = 0; i < BatWeb.length; i++) {
                try {
                    document.insertString(document.getLength(), BatWeb[i] + "\n\n", attributeSet);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                try {
                    document.insertString(document.getLength(), BatHtmls[i] + "\n\n", attributeSet);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (object == Select) {


            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);
            File file = jFileChooser.getSelectedFile();
            words = WebPage.getWords(file);
            //System.out.println(words[0]);
        } else if (object == HighLight) {
            String text = this.jTextPane.getText();
            Highlighter highlighter = jTextPane.getHighlighter();
            DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
            for (int i = 0; i < words.length; i++) {
                String keyWord = words[i];
                int pos = 0;
                while ((pos = text.indexOf(keyWord, pos)) != -1) {
                    try {
                        highlighter.addHighlight(pos, pos + keyWord.length(), p);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    pos += keyWord.length();
                }
            }

        } else if (object == Output) {
            String text = this.jTextPane.getText();
            File file = new File("敏感词输出.txt");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file));

                for (int i = 0; i < words.length; i++) {
                    String keyWord = words[i];
                    int pos = 0;
                    int count = 0;
                    while ((pos = text.indexOf(keyWord, pos)) != -1) {
                        pos += keyWord.length();
                        count++;
                    }
                    if (count != 0)
                        out.write(words[i] + "  出现了：" + count + "次\r\n");
                    out.flush();
                }
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }
}
