package fgnna.com.xposedtest;

import android.content.ContentValues;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedTest implements IXposedHookLoadPackage
{
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable
    {
        log("Loaded app: " + loadPackageParam.packageName);
        Log.d("YOUR_TAG", "Loaded app: " + loadPackageParam.packageName);
        // Xposed模块自检测
        if (loadPackageParam.packageName.equals("fgnna.com.xposedtest")){
            XposedHelpers.findAndHookMethod("fgnna.com.xposedtest.MainActivity", loadPackageParam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }
        if (loadPackageParam.packageName.equals("com.tencent.mm")){
            findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", loadPackageParam.classLoader, "insertWithOnConflict", String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("------------------------insertWithOnConflict called---------------------" + "\n");
                    log("------------------------insert start---------------------" + "\n\n");
                    log("param args1:" + (String)param.args[0]);
                    log("param args1:" + (String)param.args[1]);
                    ContentValues contentValues = (ContentValues) param.args[2];
                    log("param args3 contentValues:");
                    for (Map.Entry<String, Object> item : contentValues.valueSet())
                    {
                        if (item.getValue() != null) {
                            log(item.getKey() + "---------" + item.getValue().toString());
                        } else {
                            log(item.getKey() + "---------" + "null");
                        }
                    }

                    log("------------------------insert over---------------------" + "\n\n");

                }
            });
        }

    }
}
