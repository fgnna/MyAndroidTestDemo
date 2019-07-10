package someday.com.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
{

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-libl");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Process process = null;
        DataOutputStream os = null;
        Log.e("*** DEBUG ***", "111111111111111111");
        try
        {
            process = Runtime.getRuntime().exec("su");
            InputStream inputstream = process.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);


            Log.e("*** DEBUG ***", "3333333333333333331");

            os = new DataOutputStream(process.getOutputStream());
            //os.writeBytes(command+"\n");
            os.writeBytes("ls \n");
            os.writeBytes("exit\n");
            os.flush();
            Log.e("*** DEBUG ***", "444444444444444444444");

            String line = "";

            StringBuilder sb = new StringBuilder(line);
            while ((line = bufferedreader.readLine()) != null)
            {
                sb.append(line);
                sb.append('\n');
            }
            //////////////
            process.destroy();
            Log.e("*** DEBUG ***", "555555555555555 - " + sb.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        initCustomNativeHook("libeagleeyenativec");

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native static void initSystemNativeHook();

    public native static void initCustomNativeHook(String libName);

    public native static boolean logFilePathFromFd(int uid, int pid, int fd, int id);
}
