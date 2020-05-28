package JP.java_pro;

import javax.swing.JButton;
import javax.swing.JRadioButton;

public interface Ibase {
    public static void initBtn ( JButton btn , int width , int height , int x , int y ){
        btn.setLocation ( x , y );
        btn.setSize ( width , height );
    }

    public static void initRBtn( JRadioButton rBtn, int width , int height , int x , int y ){
        rBtn.setLocation ( x , y );
        rBtn.setSize ( width , height );
    }
}