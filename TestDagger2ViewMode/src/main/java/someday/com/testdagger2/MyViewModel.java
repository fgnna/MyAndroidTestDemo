package someday.com.testdagger2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel
{

    private MutableLiveData<List<User>> users;
    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers()
    {
        Log.d("ssssssssssss","开始获取用户");

        new AsyncTask()
        {

            @Override
            protected Object doInBackground(Object[] objects)
            {
                try
                {
                    Thread.sleep(5000);
                    List<User> list = new ArrayList();
                    list.add(new User());
                    users.postValue(list);
                    Thread.sleep(5000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    public static class User
    {

    }


}
