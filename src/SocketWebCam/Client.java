

package SocketWebCam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client
{
    public static void main (String[] args) throws IOException
    {
        Webcam wCam = null;
       
        try (DataOutputStream sender = new DataOutputStream(new BufferedOutputStream(new Socket("localhost", 54339).getOutputStream())))
        {

            wCam = Webcam.getDefault();
            wCam.setViewSize(WebcamResolution.VGA.getSize());
            wCam.open();
                         WebcamPanel panel = new WebcamPanel(wCam);

            while (true)
            {
                BufferedImage frame = wCam.getImage(); //get frame from webcam

                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                sender.writeInt(frameWidth);
                sender.writeInt(frameHeight);

                int[] pixelData = new int[frameWidth * frameHeight];
                frame.getRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

                for (int i = 0; i < pixelData.length; i++)
                {
                    sender.writeInt(pixelData[i]);
                }
            }
        } finally
        {
            //release resources used by library
            if (wCam != null)
            {
                wCam.close();
            }
            //Webcam.shutdown();
        }

    }
}