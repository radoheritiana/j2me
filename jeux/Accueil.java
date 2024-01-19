package jeux;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Font;
import java.io.IOException;
import java.io.InputStream;

public class Accueil extends Canvas {
    private Image image;
    private Font largeFont;

    public Accueil() {
        super();
        loadAndDisplayImage();
        int largeFontSize = Font.SIZE_LARGE;
        largeFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, largeFontSize);
    }

    private void loadAndDisplayImage() {
        try {
            // Charger l'image depuis le chemin spécifié
            InputStream inputStream = getClass().getResourceAsStream("/fleche.png");
            image = Image.createImage(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paint(Graphics g) {

        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        int imageX = getWidth() / 2;
        int imageY = getHeight() / 2;

        // Dessiner l'image sur le canvas
        if (image != null) {
            g.drawImage(image, imageX, imageY, Graphics.VCENTER | Graphics.HCENTER);
        }

        // Ajouter un texte en bas de l'image
        String text = "Flesy Daoro";
        int textX = imageX; // Aligné avec le centre de l'image
        int textY = imageY + image.getHeight() / 2 + g.getFont().getHeight() + 20; // En bas de l'image
        g.setFont(largeFont);
        g.setColor(0xff0000);
        g.drawString(text, textX, textY, Graphics.BASELINE | Graphics.HCENTER);
    }
}
