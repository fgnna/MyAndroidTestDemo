package testrootapp.someday.cn.testaudioplay;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,2);
        calendar.set(Calendar.MINUTE,5);
        new SimpleDateFormat("mm:ss").format(calendar.getTime());
        //System.out.print( );
    }
}