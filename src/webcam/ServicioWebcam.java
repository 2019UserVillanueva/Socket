package webcam;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ServicioWebcam {

    private static PanelImagen panelImagen;
    // private static PanelImagen panelImage2;
    private static ClienteWebcam img;

    private static int puerto = 6699;

    public static void main(String[] args) {

        DatagramSocket dataSocket = null;

        //Crea el marco para la imagen
        JFrame jframe = new JFrame();
        jframe.setSize(640, 480);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BorderLayout());
        
        JFrame jframe2 = new JFrame();
        jframe2.setSize(640, 480);
        jframe2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe2.setLayout(new BorderLayout());

        panelImagen = new PanelImagen();
        // panelImage2 = new PanelImagen();
        
        
        jframe.add(panelImagen, BorderLayout.CENTER);
        //jframe.setResizable(true);
        //jframe.pack();
        //Muestra la ventana
        jframe.setVisible(true);
        
           
        //jframe2.add(panelImage2, BorderLayout.CENTER);
        //jframe.setResizable(true);
        //jframe.pack();
        //Muestra la ventana
        jframe2.setVisible(true);
        //Pone el servidor a la escucha de una nueva conexion
        try {
            dataSocket = new DatagramSocket(puerto);
            receiveCam(dataSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        } finally {
            if (dataSocket != null) {
                dataSocket.close();
            }
        }

    }

    public static void receiveCam(DatagramSocket dataSocket)
            throws IOException {
        System.out.println("Esperando a recibir webcam");

        byte[] bytes = new byte[1024 * 64];
        DatagramPacket paquete = new DatagramPacket(bytes, bytes.length);
        while (true) {
            dataSocket.receive(paquete);
            BufferedImage frame = ImageIO.read(new ByteArrayInputStream(bytes));
            panelImagen.setFondo(frame);
             // BufferedImage frame2 = ImageIO.read(new ByteArrayInputStream(bytes));
           // panelImage2.setFondo(frame2);
            
        }
    }

}
