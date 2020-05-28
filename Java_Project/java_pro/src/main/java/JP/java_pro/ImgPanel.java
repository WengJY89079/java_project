package JP.java_pro;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ImgPanel
 */
public class ImgPanel extends JPanel {

    // CTOR 
    public ImgPanel ( String dir, int width, int height, int x, int y ) {
        this.dirPath = dir;
        this.setSize ( width , height );
        this.setLocation ( x , y );

        // btn init 

        // add components 
        this.add ( last );
        this.add ( next );
    }

    // methods 
    public void setImgs ( String[] imgs ) {
        this.images = imgs;
    }

    public void addIndex ( int add ) {
        this.currIndex += add;
        // show image 
    }

    // components 
    JButton next = new JButton ( "Next Image" );
    JButton last = new JButton ( "Last Image" );

    // variables
    private int currIndex = 0;
    private BufferedImage image;

    private String[] images;
    private String dirPath;
}