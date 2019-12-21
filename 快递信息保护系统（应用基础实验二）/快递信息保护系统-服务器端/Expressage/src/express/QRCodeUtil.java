package express;

import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeUtil {
    public static void qrCodeEncode(String encodeddata, File destFile) throws IOException {
        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');
        qrcode.setQrcodeEncodeMode('B');
        int version = 15;
        qrcode.setQrcodeVersion(version);

        int width = 67+(15-1)*12;
        int length = 67+(15-1)*12;

        byte[] d = encodeddata.getBytes("UTF-8");
        BufferedImage bi = new BufferedImage(width, length, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bi.createGraphics();

        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, width, length);
        g.setColor(Color.BLACK);

        if (d.length > 0 && d.length < 400) {
            boolean[][] b = qrcode.calQrcode(d);
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                    }
                }
            }
        }
        g.dispose();
        bi.flush();
        ImageIO.write(bi, "png", destFile);
    }
//    public static String qrCodeDecode(File imageFile) {
//        String decodedData = null;
//        QRCodeDecoder decoder = new QRCodeDecoder();
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(imageFile);
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        try {
//            decodedData = new String(decoder.decode(new J2SEImage(image)), "UTF-8");
//        } catch (DecodingFailedException dfe) {
//            System.out.println("Error: " + dfe.getMessage());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return decodedData;
//    }

    static class J2SEImage implements QRCodeImage {
        BufferedImage image;
        public J2SEImage(BufferedImage image) {
            this.image = image;
        }

        public int getWidth() {
            return image.getWidth();
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getPixel(int x, int y) {
            return image.getRGB(x, y);
        }
    }

}
