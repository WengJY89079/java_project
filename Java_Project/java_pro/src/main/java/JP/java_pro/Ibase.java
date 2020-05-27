package JP.java_pro;

import javax.swing.JButton;

public interface Ibase {
    public static void initBtn ( JButton btn , int width , int height , int x , int y ){
        btn.setLocation ( x , y );
        btn.setSize ( 100 , height );
    }
}