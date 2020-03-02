

package webcam;

import java.awt.image.BufferedImage;
import java.io.*;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class ClienteWebcam {
    

    private static InetAddress maquina;
    private static int puerto = 6699;
    private static PanelImagen panel2 ;
	public static void main(String[] args) {
		Webcam cam = null;
		DatagramSocket dataSocket = null;
                
                 JFrame jframe2 = new JFrame();
        jframe2.setSize(640, 480);
        jframe2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe2.setLayout(new BorderLayout());
      
        panel2 = new PanelImagen();
        
         jframe2.add(panel2, BorderLayout.CENTER);
       
        jframe2.setVisible(true);

		try {
                    dataSocket = new DatagramSocket();
                    maquina = InetAddress.getByName("192.168.0.13");
                    enviarCam(dataSocket, cam);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
		finally {
			if (cam != null) {
				cam.close();
			}
			//Webcam.shutdown();
                        if (dataSocket != null) {
                            dataSocket.close();
                        }
			System.out.println("Terminado");
		}
                
	}
        
        public static void enviarCam(DatagramSocket dataSocket, Webcam cam) 
        throws IOException {
            cam = Webcam.getDefault();
            cam.setViewSize(WebcamResolution.VGA.getSize());
            cam.open();
            /*WebcamPanel panel = new WebcamPanel(cam);
            panel.setMirrored(true);
            //cam.open();
            panel.setVisible(true);
            panel.revalidate();
            panel.repaint();*/
            
            System.out.println("Enviando imagenes webcam al servidor");
            while (true) {
                BufferedImage frame = cam.getImage(); //Obtiene imagen de la webcam
                
                //Convierte la imagen a JPEG y la pasa a un array de bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(frame, "jpeg", baos);
                byte[] bytes = baos.toByteArray();
                
                //Crea un paquete UDP y lo envia al receptor
                DatagramPacket paquete = new DatagramPacket(bytes, bytes.length,maquina, puerto);
                dataSocket.send(paquete);
                  BufferedImage frame2 = ImageIO.read(new ByteArrayInputStream(bytes));
            panel2.setFondo(frame2);
            }
            
        }

}