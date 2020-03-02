
package CameraSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ServerCamera   {
     private static final long serialVersionUID = 1L;
    public static void main(String[] args)throws IOException, ClassNotFoundException {
        
        ServerSocket server = new ServerSocket(6000);
        System.out.println("esperando...");
        
        Socket socket = server.accept();
        System.out.println("Conectado...");
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        JLabel label = new JLabel();
        
        JFrame frame = new JFrame("Server");
        frame.setSize(640,360);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        label= new JLabel();
        label.setSize(640,360);
        label.setVisible(true);
        
        frame.add(label);
        //frame.pack();
        frame.setVisible(true);
        
        while(true){
            label.setIcon((ImageIcon)in.readObject());
            
        }
    }

}
