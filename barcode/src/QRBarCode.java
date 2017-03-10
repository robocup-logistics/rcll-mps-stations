
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;

public class QRBarCode
{
    public static void main(String arg[])
    {
        String barCode = "";
        BufferedImage img;
        JFrame frame = new JFrame("Camera");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        ViewPanel panel = new ViewPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);

        CVLoader.load();                                                 // Load the native library. 
        VideoCapture video = new VideoCapture(0);                       // Open video stream  
        if (video.isOpened())
        {
            Mat mat = new Mat();
            Map hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            while (true)
            {
                video.read(mat);
                if (!mat.empty())
                {
                    img = panel.matToBufferedImage(mat);
                    try
                    {
                        barCode = QRFunctions.getQRCode(hintMap, img);
                    } catch (IOException ex)
                    {
                        System.out.println("io-Exception");
                    } catch (NotFoundException ex)
                    {
                        barCode = "";
                        //System.out.println("nothing detected");
                    }
                    System.out.println(barCode);
                    Core.putText(mat, barCode, new Point(10, 30), 2, 1.0, new Scalar(0, 0, 255, 255), 2);
                    panel.setImage(mat);
                    frame.repaint();
                } else
                {
                    System.out.println(" --(!) No captured frame -- Break!");
                    break;
                }
            }
        }
        video.release();
    }
}
// ZBar / UPCE Jahr/Station 0-99/