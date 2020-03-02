
package CameraSocket;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClientCamera  {
    
    private static final long serialVersionUID = 1L;

    static Socket socket;
    public static void main(String[] args)throws IOException {
        
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        
        //WebcamPanel panel = new WebcamPanel(webcam);
        
        
        socket = new Socket("192.168.0.13",6000);
        
        BufferedImage bm= webcam.getImage();
        
        ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
        
        ImageIcon im = new ImageIcon(bm);
        
        JFrame frame = new JFrame("PC 1");
        frame.setSize(640,360);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        
        JLabel l = new JLabel();
        l.setVisible(true);
        
        frame.add(l);
        frame.setVisible(true);
        
        while(true){
            bm = webcam.getImage();
            im= new ImageIcon(bm);
            dout.writeObject(im);
            l.setIcon(im);
            dout.flush();
        }
        
        
        
        
    }
    
}
