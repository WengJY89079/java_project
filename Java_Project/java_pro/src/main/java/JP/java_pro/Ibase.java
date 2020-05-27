package JP.java_pro;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface Ibase {
    public static void initBtn ( JButton btn , int width , int height , int x , int y ){
        btn.setLocation ( x , y );
        btn.setSize ( width , height );
    }
}