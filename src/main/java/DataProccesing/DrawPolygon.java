package DataProccesing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPolygon {
    private final ImageView prevImage;
    private final Text text;
    private int sizePolygon = 0;
    private static final int SIZE = 720;
    private ArrayList<Integer> xList = new ArrayList<>();
    private ArrayList<Integer> yList = new ArrayList<>();

    public DrawPolygon(ImageView prevImage, Text text) {
        this.prevImage = prevImage;
        this.text = text;
    }

    public void drawPolygon(String cords) {
        int[] position = getCord(cords);
        if (position != null) {
            xList.add(position[0]);
            yList.add(position[1]);
            sizePolygon++;
            text.setText(String.valueOf(sizePolygon));
            drawImage();
        }
    }

    public void resetPolygon() {
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        sizePolygon = 0;
        text.setText(String.valueOf(sizePolygon));
        drawImage();
    }

    public void getProgramCode() { // Выдать код в буффер обмена
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        String x = getCode('x');
        String y = getCode('y');
        content.putString(x+"\n"+y);
        clipboard.setContent(content);
    }

    private String getCode(char type) {
        StringBuilder r;
        if (type == 'x') {
            r = new StringBuilder();
            r.append(xList);
            String str = r.toString();
            str = str.replaceAll("^\\[|\\]$","");
            return "int[] x = {" + str + "};";
        } else {
            r = new StringBuilder();
            r.append(yList);
            String str = r.toString();
            str = str.replaceAll("^\\[|\\]$", "");
            return "int[] y = {" + str + "};";
        }
    }

    private int[] getCord(String cords) {
        if (cords.contains(",")) {
            String[] s = cords.split(",",2);
            return new int[]{Integer.parseInt(s[0]), Integer.parseInt(s[1])};
        }
        return null;
    }
    private void drawImage() {
        if (xList.size() == 0) {
            BufferedImage image = new BufferedImage(SIZE,SIZE,BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setColor(Color.white);
            graphics2D.fillRect(0,0,SIZE, SIZE);

            prevImage.setImage(SwingFXUtils.toFXImage(image,null));

            return;
        }

        int[] x = new int[xList.size()];
        int[] y = new int[yList.size()];

        for (int i = 0; i < x.length; i++) {
            x[i] = xList.get(i);
        }

        for (int i = 0; i < y.length; i++) {
            y[i] = yList.get(i);
        }

        Image img;

        if (x.length >= 4) {
            Polygon polygon = new Polygon(x, y, x.length);
            img = drawPolygonImage(new Shape[]{polygon});
        } else {
            Shape[] shapes = new Shape[x.length];
            for (int i = 0; i < x.length; i++) {
                shapes[i] = new RoundRectangle2D.Double(x[i],y[i],5.0,5.0,45.0,45.0);
            }
            img = drawPolygonImage(shapes);
        }

        prevImage.setImage(img);
    }

    private Image drawPolygonImage(Shape[] shape) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.CYAN);

        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (shape[0] instanceof Polygon) {

            graphics2D.fillPolygon((Polygon) shape[0]);

            WritableImage img = SwingFXUtils.toFXImage(image, null);
            return img;
        } else {

            for (Shape s: shape) {
                graphics2D.fill(s);
            }
            WritableImage img = SwingFXUtils.toFXImage(image,null);

            return img;
        }
    }
}