package JP.java_pro;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public interface Ibase {
    public static void initBtn ( JButton btn , int width , int height , int x , int y ) {
        btn.setLocation ( x , y );
        btn.setSize ( width , height );
    }

    public static void initRBtn ( JRadioButton rBtn , int width , int height , int x , int y ) {
        rBtn.setLocation ( x , y );
        rBtn.setSize ( width , height );
    }

    public static void initLabel ( JLabel l , int width , int height , int x , int y ) {
        l.setLocation ( x , y );
        l.setSize ( width , height );
    }

    public static void initLabel ( JLabel l , int width , int height , int x , int y , String text ) {
        l.setLocation ( x , y );
        l.setSize ( width , height );
        l.setText ( text );
    }

    public static String chooseDir ( JFrame srcFrame ) {
        // open file chooser
        JFileChooser fileBrowser = new JFileChooser ( System.getProperty ( "user.dir" ) );
        fileBrowser.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
        final int res = fileBrowser.showOpenDialog ( srcFrame );

        // if choose a dir 
        if ( res == JFileChooser.APPROVE_OPTION )
            return fileBrowser.getSelectedFile ( ).toString ( );
        // close file chooser or cancel 
        else
            return "CANCEL";
    }
}