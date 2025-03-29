package com.bantvegas.aidocspro.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public static File convertToStandardImageFormat(File inputFile) throws IOException {
        BufferedImage original = ImageIO.read(inputFile);
        if (original == null) {
            throw new IOException("Nepodarilo sa načítať obrázok – formát nie je podporovaný.");
        }

        BufferedImage converted = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = converted.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();

        File convertedFile = File.createTempFile("converted-", ".png");
        ImageIO.write(converted, "png", convertedFile);

        return convertedFile;
    }
}
