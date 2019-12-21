package com.bignerdranch.android.expressagesystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.UnsupportedEncodingException;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QRCodeUtil {

    public static String qrCodeDecode(String imageFile) {
        String decodedData = null;
        QRCodeDecoder decoder = new QRCodeDecoder();
        Bitmap image = null;
        image = BitmapFactory.decodeFile(imageFile);
        try {
            decodedData = new String(decoder.decode(new J2SEImage(image)), "UTF-8");
        } catch (DecodingFailedException dfe) {
            System.out.println("Error: " + dfe.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedData;
    }

    static class J2SEImage implements QRCodeImage {
        Bitmap image;
        public J2SEImage(Bitmap image) {
            this.image = image;
        }

        public int getWidth() {
            return image.getWidth();
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getPixel(int x, int y) {
            return image.getPixel(x, y);
        }
    }

}
