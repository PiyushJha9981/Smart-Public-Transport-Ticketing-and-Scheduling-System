package service;

import com.google.zxing.*;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

// import java.nio.file.Path;
import java.io.File;

public class QRGenerator {

    public static String generateQR(String text, int id) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        String path = "qr_" + id + ".png";
        MatrixToImageWriter.writeToPath(matrix, "PNG", new File(path).toPath());

        return path;
    }
}