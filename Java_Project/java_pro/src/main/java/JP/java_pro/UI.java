package JP.java_pro;

import javax.swing.JFrame;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

public class UI extends JFrame implements Ibase {

    // constructor, title, size
    public UI ( String title, int width, int height ) {
        // call parent ctor 
        super ( title );

        // set frame size 
        this.setSize ( width , height );
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        this.setLayout ( null );

        // init search button 
        Ibase.initBtn ( this.searchBtn , 50 , 30 , width - 150 , 100 );
        Ibase.initBtn ( this.listBtn , 50 , 30 , width - 150 , 150 );

        // init text field 
        this.inputBox.setLocation ( this.searchBtn.getX ( ) - 230 , this.searchBtn.getY ( ) );
        this.inputBox.setSize ( this.inputBox.getColumns ( ) , 30 );

        // set events 
        this.searchBtn.addActionListener ( act -> this.search ( this.inputBox.getText ( ) ) );
        this.listBtn.addActionListener ( act -> this.listFiles ( ) );

        // add components 
        this.add ( searchBtn );
        this.add ( listBtn );
        this.add ( inputBox );

        // show this frame 
        this.setVisible ( true );
    }

    private void listFiles ( ) {
        File dir = new File ( "./figures" );
        String[] imgs = dir.list ( );
        for ( int i = 0 ; i < imgs.length ; ++i ) {
            System.out.println ( imgs[i] );
        }

        showPanel ( "./figures/" , 400 , 350 , 400 , 0 );
    }

    private void showPanel ( String dir , int width , int height , int x , int y ) {
        this.panelOpend = true;
        
        // show images in panel 
        this.imgPanel = new ImgPanel ( dir, width, height, x, y );
    }

    private void closePanel ( ) {
        this.panelOpend = false;
    }

    private void search ( String keywords ) {
        // do search 
        System.out.println ( "SEARCH : " + keywords );
        Controller main_controller = new Controller ( );
        try {
            main_controller.searchPic ( keywords );
        }
        catch ( Exception e ) {
            System.out.println ( e );
        }

        System.out.println ( "Search End" );
    }

    private JButton searchBtn = new JButton ( "Search" );
    private JButton listBtn = new JButton ( "List" );
    private JTextField inputBox = new JTextField ( "Enter Search Keywords Here !", 200 );

    private boolean panelOpend = false;
    private ImgPanel imgPanel;

}