package JP.java_pro;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.sleepycat.je.rep.monitor.NewMasterEvent;

public class UI extends JFrame implements Ibase {
    //check if a string is a number
    public static boolean isInteger ( String s ) {
        try {
            Integer.parseInt ( s );
        }
        catch ( NumberFormatException e ) {
            return false;
        }
        catch ( NullPointerException e ) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * constructor, title, size
     * 
     * @param title
     * @param width
     * @param height
     */
    public UI ( String title, int width, int height ) {
        // call parent ctor 
        super ( title );

        // set frame size 
        this.setSize ( width , height );
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        this.setLayout ( null );

        // init 目前下載位置的 Label 以及 change button 
        Ibase.initBtn ( this.changeDestDir , 60 , 30 , 80 , height - 70 );
        Ibase.initBtn ( this.hide , 60 , 30 , 10 , height - 70 );
        Ibase.initLabel ( this.destPathLabel , 100 , 30 , 150 , height - 70 );
        Ibase.initLabel ( this.currentDestDir , 1000 , 30 , 240 , height - 70 , Controller.destDir );
        this.destPathLabel.setForeground(Color.yellow);
        this.currentDestDir.setForeground(Color.yellow);
        this.changeDestDir.addActionListener ( act -> this.changeSaveDir ( ) );

        this.hide.addActionListener(act->{
        	times++;
        	if(times%2==0) {
        		this.destPathLabel.setVisible( false );
        		this.currentDestDir.setVisible( false );
        		this.changeDestDir.setVisible( false );
        		this.hide.setText("顯示");
        	}
        	else {
        		this.destPathLabel.setVisible( true );
            	this.currentDestDir.setVisible( true );
            	this.changeDestDir.setVisible( true );
            	this.hide.setText("隱藏");
        	}
        });
        // certification

        try {
            Turn_off_cert_Validation ( );
        }
        catch ( KeyManagementException | NoSuchAlgorithmException e2 ) {
            // TODO Auto-generated catch block
            e2.printStackTrace ( );
        }
        //starting background
        //this.background.setLocation(0,-17);
        this.backg.setBounds(0,-25,backGroundPic1.getIconWidth(), backGroundPic1.getIconHeight());
        this.backg.setSize(width, height);
        this.backg.setVisible(true);
        this.backg.setIcon( backGroundPic1 );
        // init search button and radio button 
        Ibase.initBtn ( this.searchBtn , 100 , 30 , width - 150 , 75 );
        Ibase.initRBtn ( this.searchByKeywords , 100 , 20 , 10 , 75 );
        Ibase.initRBtn ( this.searchByAuthor , 100 , 20 , 10 , 115 );
        this.searchByAuthor.setOpaque(false);
        this.searchByKeywords.setOpaque(false);
        this.searchByAuthor.setForeground(Color.white);
        this.searchByKeywords.setForeground(Color.white);
        this.searchByKeywords.setSelected ( true );

        this.radioBtnGrp.add ( this.searchByKeywords );
        this.radioBtnGrp.add ( this.searchByAuthor );

        this.maxImgs.setLocation ( 10 , 170);
        this.maxImgs.setSize ( 100 , 30 );
        this.maxImgs.setText ( "-1" );
        
        this.maxImgs_label.setLocation( 10 , 145 );
        this.maxImgs_label.setSize ( 120 , 20 );
        this.maxImgs_label.setText ( "圖片搜尋數量上限" );
        this.maxImgs_label.setForeground(Color.white);

        // loading text
        this.loadIcon.setImage ( loadIcon.getImage ( ).getScaledInstance ( width , height , Image.SCALE_DEFAULT ) );
        this.loadingJLabel = new JLabel ( loadIcon );
        this.loadingJLabel.setLocation ( -20 , -20 );
        this.loadingJLabel.setSize ( width , height );
        this.loadingJLabel.setVisible ( false );

        // init text field 
        this.inputBox.setLocation ( this.searchBtn.getX ( ) - 230 , this.searchBtn.getY ( ) );
        this.inputBox.setSize ( this.inputBox.getColumns ( ) , 30 );

        //init Picture button
        Ibase.initBtn ( this.nextButton , 150 , 30 , width - 200 , height - 100 );
        Ibase.initBtn ( this.saveButton , 150 , 30 , width - 200 , height - 150 );
        Ibase.initBtn ( this.preButton , 150 , 30 , width - 350 , height - 100 );
        Ibase.initBtn ( this.backButton , 150 , 30 , 10 , height - 100 );
        this.nextButton.setVisible ( false );
        this.saveButton.setVisible ( false );
        this.preButton.setVisible ( false );
        this.backButton.setVisible ( false );

        //init show picture label
        this.showPictureJLabel.setLocation ( 0 , 0 );
        this.showPictureJLabel.setSize ( width - 100 , height - 100 );
        this.showPictureJLabel.setVisible ( false );

        //display picture page
        this.picPageLJLabel.setSize ( 30 , 20 );
        this.picPageLJLabel.setLocation ( width - 100 , 300 );
        this.picPageLJLabel.setVisible ( false );
        this.picPageLJLabel.setText ( "" );
        this.gotoPage.setSize ( 80 , 20 );
        this.gotoPage.setLocation ( width - 100 , 280 );
        this.gotoPage.setVisible ( false );
        this.gotoThis.setSize ( 45 , 20 );
        this.gotoThis.setLocation ( width - 65 , 300 );
        this.gotoThis.setVisible ( false );
        
        // set author backpic
        this.searchByAuthor.addActionListener(act->{
        	this.backg.setIcon( backGroundPic2 );
        });
        
        // set keyword backpic
        this.searchByKeywords.addActionListener(act->{
        	this.backg.setIcon( backGroundPic1 );
        });
        
        // set events
        this.searchBtn.addActionListener ( act -> {
        	((JPanel)this.getContentPane()).setOpaque(true);
        	//backg.setVisible( false );
            maxImgs.setVisible ( false );
            maxImgs_label.setVisible( false );
            changeDestDir.setEnabled ( false );
            inputBox.setVisible ( false );
            searchBtn.setVisible ( false );
            searchByKeywords.setVisible ( false );
            searchByAuthor.setVisible ( false );
            loadingJLabel.setVisible ( true );
            hide.setVisible(false);
            hide.setEnabled(false);
            this.destPathLabel.setVisible( false );
    		this.currentDestDir.setVisible( false );
    		this.changeDestDir.setVisible( false );

            try {
                this.search ( this.inputBox.getText ( ) );
            }
            catch ( Exception e1 ) {
                e1.printStackTrace ( );
            }
        } );

        this.inputBox.addFocusListener ( new FocusListener ( ) {
            @Override
            public void focusLost ( FocusEvent e ) {
            }

            @Override
            public void focusGained ( FocusEvent e ) {
                inputBox.setText ( "" );
            }
        } );
        //儲存按紐
        this.saveButton.addActionListener ( act -> {
            try {
                Controller.saveImage ( myCrawler.seed.get ( pic_index ) );
            }
            catch ( IOException e1 ) {
                e1.printStackTrace ( );
            }
        } );
        //gotoPage
        this.gotoPage.addActionListener ( act -> {
            if ( isInteger ( gotoThis.getText ( ) ) ) {
                try {
                    if ( Integer.parseInt ( gotoThis.getText ( ) ) - 1 > 0 && Integer.parseInt ( gotoThis.getText ( ) ) - 1 < myCrawler.seed.size ( ) ) {
                        pic_index = Integer.parseInt ( gotoThis.getText ( ) ) - 1;
                    }
                    else if ( Integer.parseInt ( gotoThis.getText ( ) ) <= 0 ) {
                        pic_index = 0;
                    }
                    else if ( Integer.parseInt ( gotoThis.getText ( ) ) >= myCrawler.seed.size ( ) ) {
                        pic_index = myCrawler.seed.size ( ) - 1;
                    }
                    this.picture = returnIcon ( new URL ( myCrawler.seed.get ( pic_index ) ) );
                    this.picture.setImage ( picture.getImage ( ).getScaledInstance ( showPictureJLabel.getWidth ( ) , showPictureJLabel.getHeight ( ) , Image.SCALE_DEFAULT ) );
                    this.showPictureJLabel.setIcon ( picture );
                    if ( pic_index - 1 >= 0 ) {
                        this.preButton.setEnabled ( true );
                        this.picturePre = returnIcon ( new URL ( myCrawler.seed.get ( pic_index - 1 ) ) );
                        System.out.println ( myCrawler.seed.get ( pic_index - 1 ) );
                    }
                    else {
                        this.preButton.setEnabled ( false );
                    }
                    if ( pic_index + 1 < myCrawler.seed.size ( ) ) {
                        this.nextButton.setEnabled ( true );
                        this.pictureNext = returnIcon ( new URL ( myCrawler.seed.get ( pic_index + 1 ) ) );
                        System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                    }
                    else {
                        this.nextButton.setEnabled ( false );
                    }
                    picPageLJLabel.setText ( ( pic_index + 1 ) + "/" + ( myCrawler.seed.size ( ) ) );
                }
                catch ( MalformedURLException e1 ) {
                    e1.printStackTrace ( );
                    System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                }
                catch ( IOException e1 ) {
                    e1.printStackTrace ( );
                    System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                }
            }
        } );

        //next picture
        this.nextButton.addActionListener ( act -> {
            if ( pic_index < myCrawler.seed.size ( ) ) {
                pic_index++;
                try {
                    this.picture = pictureNext;
                    this.picture.setImage ( picture.getImage ( ).getScaledInstance ( showPictureJLabel.getWidth ( ) , showPictureJLabel.getHeight ( ) , Image.SCALE_DEFAULT ) );
                    this.showPictureJLabel.setIcon ( picture );
                    if ( pic_index - 1 >= 0 ) {
                        this.preButton.setEnabled ( true );
                        this.picturePre = returnIcon ( new URL ( myCrawler.seed.get ( pic_index - 1 ) ) );
                        System.out.println ( myCrawler.seed.get ( pic_index - 1 ) );
                    }
                    else {
                        this.preButton.setEnabled ( false );
                    }
                    if ( pic_index + 1 < myCrawler.seed.size ( ) ) {
                        this.nextButton.setEnabled ( true );
                        this.pictureNext = returnIcon ( new URL ( myCrawler.seed.get ( pic_index + 1 ) ) );
                        System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                    }
                    else {
                        this.nextButton.setEnabled ( false );
                    }
                    picPageLJLabel.setText ( ( pic_index + 1 ) + "/" + ( myCrawler.seed.size ( ) ) );
                }
                catch ( MalformedURLException e1 ) {
                    e1.printStackTrace ( );
                    System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                }
                catch ( IOException e1 ) {
                    e1.printStackTrace ( );
                    System.out.println ( myCrawler.seed.get ( pic_index + 1 ) );
                }
            }
        } );
        //back to main MENU
        this.backButton.addActionListener ( act -> {
            this.maxImgs.setVisible ( true );
            this.maxImgs_label.setVisible( true );
            this.inputBox.setVisible ( true );
            this.searchBtn.setVisible ( true );
            this.searchByKeywords.setVisible ( true );
            this.searchByAuthor.setVisible ( true );
            this.preButton.setVisible ( false );
            this.saveButton.setVisible ( false );
            this.nextButton.setVisible ( false );
            this.backButton.setVisible ( false );
            this.showPictureJLabel.setVisible ( false );
            this.pic_index = 0;
            this.picPageLJLabel.setVisible ( false );
            this.gotoPage.setVisible ( false );
            this.gotoThis.setVisible ( false );

            hide.setVisible(true);
            hide.setEnabled(true);
            if(times%2==0) {
            this.destPathLabel.setVisible( false );
    		this.currentDestDir.setVisible( false );
    		this.changeDestDir.setVisible( false );
            }
            else {
            	this.destPathLabel.setVisible( true );
        		this.currentDestDir.setVisible( true );
        		this.changeDestDir.setVisible( true );
            }
            
        	((JPanel)this.getContentPane()).setOpaque(false);
            myCrawler.seed.clear ( );
        } );
        //previous picture
        this.preButton.addActionListener ( act -> {
            if ( pic_index > 0 ) {
                pic_index--;
                try {
                    this.picture = picturePre;
                    this.picture.setImage ( picture.getImage ( ).getScaledInstance ( showPictureJLabel.getWidth ( ) , showPictureJLabel.getHeight ( ) , Image.SCALE_DEFAULT ) );
                    this.showPictureJLabel.setIcon ( picture );
                    if ( pic_index - 1 >= 0 ) {
                        this.preButton.setEnabled ( true );
                        this.picturePre = returnIcon ( new URL ( myCrawler.seed.get ( pic_index - 1 ) ) );
                    }
                    else {
                        this.preButton.setEnabled ( false );
                    }
                    if ( pic_index + 1 < myCrawler.seed.size ( ) ) {
                        this.nextButton.setEnabled ( true );
                        this.pictureNext = returnIcon ( new URL ( myCrawler.seed.get ( pic_index + 1 ) ) );
                    }
                    else {
                        this.nextButton.setEnabled ( false );
                    }
                    picPageLJLabel.setText ( ( pic_index + 1 ) + "/" + ( myCrawler.seed.size ( ) ) );
                }
                catch ( MalformedURLException e1 ) {
                    e1.printStackTrace ( );
                }
                catch ( IOException e1 ) {
                    e1.printStackTrace ( );
                }
            }
        } );

        // add components 
        this.add ( destPathLabel );
        this.getLayeredPane().add( backg, new Integer(Integer.MIN_VALUE));
        this.add ( currentDestDir );
        this.add ( changeDestDir );

        this.add ( searchBtn );
        this.add ( maxImgs );
        this.add ( maxImgs_label );
        this.add ( inputBox );
        this.add ( loadingJLabel );
        this.add ( hide );

        this.add ( searchByKeywords );
        this.add ( searchByAuthor );

        this.add ( nextButton );
        this.add ( saveButton );
        this.add ( preButton );
        this.add ( backButton );
        this.add ( showPictureJLabel );

        this.add ( picPageLJLabel );
        this.add ( gotoPage );
        this.add ( gotoThis );

        ((JPanel)this.getContentPane()).setOpaque(false);
        // show this frame 
        this.setVisible ( true );
    }

    // skip some Certification Validation. Check it out on https://stackoverflow.com/questions/4325263/how-to-import-a-cer-certificate-into-a-java-keystore

    private void Turn_off_cert_Validation ( ) throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager ( ) {
            public java.security.cert.X509Certificate[] getAcceptedIssuers ( ) {
                return null;
            }

            @Override
            public void checkClientTrusted ( X509Certificate[] arg0 , String arg1 ) throws CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted ( X509Certificate[] arg0 , String arg1 ) throws CertificateException {
                // TODO Auto-generated method stub

            }
        } };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance ( "SSL" );
        sc.init ( null , trustAllCerts , new java.security.SecureRandom ( ) );
        HttpsURLConnection.setDefaultSSLSocketFactory ( sc.getSocketFactory ( ) );

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier ( ) {
            @Override
            public boolean verify ( String hostname , SSLSession session ) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier ( allHostsValid );
    }

    private void search ( String keywords ) throws Exception {
        System.out.println ( "SEARCH : " + keywords );

        int mode = 0;
        if ( this.searchByKeywords.isSelected ( ) ) {
            mode = SearchKeywords;
        }
        else if ( this.searchByAuthor.isSelected ( ) ) {
            mode = SearchAuthor;
        }
        this.setTitle ( "SEARCH " + ( ( mode == 0 ) ? "Title " : "Author " ) + keywords );

        int setLimit = Integer.parseInt ( this.maxImgs.getText ( ) );
        myCrawler.imgLimit = ( setLimit < 0 ) ? 9999999 : setLimit;
        main_controller = new Controller ( this, keywords, mode );
    }

    /**
     * finishCraw, show button for control picture(next, pre, backToMENU, save)
     */
    public void finishCraw ( ) {
        this.changeDestDir.setEnabled ( true );
        this.loadingJLabel.setVisible ( false );
        this.backButton.setVisible ( true );
        if ( myCrawler.seed.size ( ) != 0 ) {
        	this.nextButton.setVisible ( true );
            this.saveButton.setVisible ( true );
            this.preButton.setVisible ( true );
            this.showPictureJLabel.setVisible ( true );
            this.gotoPage.setVisible ( true );
            this.gotoThis.setVisible ( true );
            this.showPictureJLabel.setLocation ( 0 , 0 );
            this.showPictureJLabel.setSize ( 750 - 100 , 500 - 100 );
            
            hide.setVisible(true);
            hide.setEnabled(true);
            if(times%2==0) {
            this.destPathLabel.setVisible( false );
    		this.currentDestDir.setVisible( false );
    		this.changeDestDir.setVisible( false );
            }
            else {
            	this.destPathLabel.setVisible( true );
        		this.currentDestDir.setVisible( true );
        		this.changeDestDir.setVisible( true );
            }
            try {
                url = new URL ( myCrawler.seed.get ( pic_index ) );

                this.picture = returnIcon ( url );
                this.picture.setImage ( picture.getImage ( ).getScaledInstance ( showPictureJLabel.getWidth ( ) , showPictureJLabel.getHeight ( ) , Image.SCALE_DEFAULT ) );
                this.showPictureJLabel.setIcon ( picture );
                if ( pic_index - 1 >= 0 ) {
                    this.preButton.setEnabled ( true );
                    this.picturePre = returnIcon ( new URL ( myCrawler.seed.get ( pic_index - 1 ) ) );
                }
                else {
                    this.preButton.setEnabled ( false );
                }
                if ( pic_index + 1 < myCrawler.seed.size ( ) ) {
                    this.nextButton.setEnabled ( true );
                    this.pictureNext = returnIcon ( new URL ( myCrawler.seed.get ( pic_index + 1 ) ) );
                }
                else {
                    this.nextButton.setEnabled ( false );
                }
                picPageLJLabel.setVisible ( true );
                picPageLJLabel.setText ( ( pic_index + 1 ) + "/" + ( myCrawler.seed.size ( ) ) );
            }
            catch ( Exception e ) {
                e.printStackTrace ( );
            }
        }
        else {
            System.out.println ( "no pic" );
            this.showPictureJLabel.setLocation ( 0 , -25 );
            this.showPictureJLabel.setVisible ( true );
            this.showPictureJLabel.setSize ( 750 , 500 );
            if ( this.searchByKeywords.isSelected ( ) ) {           	
            	this.showPictureJLabel.setIcon ( backGroundPic1_2 );
            }
            else if ( this.searchByAuthor.isSelected ( ) ) {
            	this.showPictureJLabel.setIcon ( backGroundPic2_2 );
            }
            
        }
    }

    //for some fxxking 404, we need something

    public ImageIcon returnIcon ( URL url ) throws IOException {
        URLConnection connection = url.openConnection ( );
        connection.setRequestProperty ( "User-Agent" , "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" );
        InputStream in = connection.getInputStream ( );
        ByteArrayOutputStream out = new ByteArrayOutputStream ( );

        int c;
        while ( ( c = in.read ( ) ) != -1 ) {
            out.write ( c );
        }

        ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit ( ).createImage ( out.toByteArray ( ) ) );
        return icon;
    }

    /**
     * use file browser to choose default img save path
     */
    public void changeSaveDir ( ) {
        String newPath = Ibase.chooseDir ( this );
        if ( !newPath.equals ( "CANCEL" ) ) {
            // change dest dir 
            Controller.destDir = newPath;
            // show new path 
            this.currentDestDir.setText ( Controller.destDir );
        }
    }

    /* Method END  */

    // 
    private JLabel backg = new JLabel();
    private ImageIcon backGroundPic1 = new ImageIcon( "./icon/BackGroundPic1.jpg" );
    private ImageIcon backGroundPic2 = new ImageIcon( "./icon/BackGroundPic2.jpg" );
    private ImageIcon backGroundPic1_2 = new ImageIcon( "./icon/BackGroundPic1_2.jpg" );
    private ImageIcon backGroundPic2_2 = new ImageIcon( "./icon/BackGroundPic2_2.jpg" );
    private Controller main_controller = null;
    private int pic_index = 0;
    private JLabel showPictureJLabel = new JLabel ( );
    private ImageIcon loadIcon = new ImageIcon ( "./icon/icon.gif" );
    private ImageIcon picture = new ImageIcon ( );
    private ImageIcon picturePre = new ImageIcon ( );
    private ImageIcon pictureNext = new ImageIcon ( );
    private JLabel loadingJLabel;
    private JButton nextButton = new JButton ( "Next Picture" );
    private JButton saveButton = new JButton ( "Save Picture" );
    private JButton preButton = new JButton ( "Previous Picture" );
    private JButton backButton = new JButton ( "Back to MENU" );
    private JButton hide = new JButton ( "隱藏" );
    //
    private JLabel picPageLJLabel = new JLabel ( );
    private JButton gotoPage = new JButton ( "goto" );
    private JTextField gotoThis = new JTextField ( );
    // 搜尋相關 components

    private JTextField maxImgs = new JTextField ( );
    private JLabel maxImgs_label = new JLabel();

    private URL url;

    private ButtonGroup radioBtnGrp = new ButtonGroup ( );
    private JRadioButton searchByKeywords = new JRadioButton ( "Keywords" );
    private JRadioButton searchByAuthor = new JRadioButton ( "Author" );

    private JTextField inputBox = new JTextField ( "Enter Search Keywords Here !", 200 );
    private JButton searchBtn = new JButton ( "Search" );

    public static final int SearchKeywords = 0;
    public static final int SearchAuthor = 1;
    //隱藏
    int times=1;
    // Default path 
    private JButton changeDestDir = new JButton ( "更改" );
    private JLabel destPathLabel = new JLabel ( "目前下載目錄 : " );
    private JLabel currentDestDir = new JLabel ( );
}