import java.io.File;
import org.opencv.core.Core;

public class CVLoader
{
  public static void load()
  {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    //System.load(new File("libs/libopencv_java248.dylib").getAbsolutePath());
  }
}
