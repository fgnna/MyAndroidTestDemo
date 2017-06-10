package myuitest.someday.cn.myuitest;

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
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testArray() throws Exception {
        int[] sss =new int[]{4,7,8,9,};
        System.out.print(sss.length);
    }

    @Test
    public void testCalendar() throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,5,1);
        System.out.println(calendar.getTime());
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime()));
        //第一天从周几开始,第一天为周日
        int beforeNullDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
        System.out.println("第一周空出：" + beforeNullDay + "天");
        calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime()));
        int afterNullDay = 7-calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("最后一周空出：" + afterNullDay  + "天");


        int sss = 1;
        switch (sss)
        {
            case 1:
            case 2:
            case 3:
                System.out.println("3");
                break;
            case 7:
                System.out.println("3");
                break;
            case 9:
                System.out.println("3");
                break;

        }


    }


}
