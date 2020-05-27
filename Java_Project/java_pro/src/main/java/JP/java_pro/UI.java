package JP.java_pro;

import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
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

        // init text field 
        this.inputBox.setLocation ( this.searchBtn.getX ( ) - 230 , this.searchBtn.getY ( ) );
        this.inputBox.setSize ( this.inputBox.getColumns ( ) , 30 );

        // set events 
        this.searchBtn.addActionListener ( act -> this.search ( this.inputBox.getText ( ) ) );
        		
        // add components 
        this.add ( searchBtn );
        this.add ( inputBox );

        // show this frame 
        this.setVisible ( true );
    }

    private void search ( String keywords ) {
        // do search 
        System.out.println ( "SEARCH : " + keywords );
        Controller main_controller = new Controller();
        try {
        	main_controller.searchPic(keywords);
        }
        catch ( Exception e ) {
			System.out.println ( e );
		}
    }

    private JButton searchBtn = new JButton ( "Search" );
    private JTextField inputBox = new JTextField ( "Enter Search Keywords Here !", 200 );

}