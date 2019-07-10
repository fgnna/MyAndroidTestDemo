package com11.ai.qianhong.launcherapptest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    private String ss ="content:wxid_y53k899ttb0q21:\n" +
            "    <msg>\n" +
            "    \t<appmsg appid=\"\" sdkver=\"\">\n" +
            "    \t\t<des><![CDATA[我给你发了一个红包，赶紧去拆!]]></des>\n" +
            "    \t\t<url><![CDATA[https://wxapp.tenpay.com/mmpayhb/wxhb_personalreceive?showwxpaytitle=1&msgtype=1&channelid=1&sendid=1000039501201904256012119396886&ver=6&sign" +
            "=d323833c60f64dd0258a41c516a8b83bdc5f5f91df7663fcd227e76427b4809ae5b4c5f36f0de7dc7b33b7e1d5746d28ff4cd1fee7f685ec7740740bd6bec6afd8986be2460bdc1ac93462ae995e1f96]]></url>\n" +
            "    \t\t<type><![CDATA[2001]]></type>\n" +
            "    \t\t<title><![CDATA[微信红包]]></title>\n" +
            "    \t\t<thumburl><![CDATA[https://wx.gtimg.com/hongbao/1800/hb.png]]></thumburl>\n" +
            "    \t\t<wcpayinfo>\n" +
            "    \t\t\t<templateid><![CDATA[7a2a165d31da7fce6dd77e05c300028a]]></templateid>\n" +
            "    \t\t\t<url><![CDATA[https://wxapp.tenpay.com/mmpayhb/wxhb_personalreceive?showwxpaytitle=1&msgtype=1&channelid=1&sendid=1000039501201904256012119396886&ver=6&sign" +
            "=d323833c60f64dd0258a41c516a8b83bdc5f5f91df7663fcd227e76427b4809ae5b4c5f36f0de7dc7b33b7e1d5746d28ff4cd1fee7f685ec7740740bd6bec6afd8986be2460bdc1ac93462ae995e1f96]]></url>\n" +
            "    \t\t\t<iconurl><![CDATA[https://wx.gtimg.com/hongbao/1800/hb.png]]></iconurl>\n" +
            "    \t\t\t<receivertitle><![CDATA[恭喜发财，大吉大利]]></receivertitle>\n" +
            "    \t\t\t<sendertitle><![CDATA[恭喜发财，大吉大利]]></sendertitle>\n" +
            "    \t\t\t<scenetext><![CDATA[微信红包]]></scenetext>\n" +
            "    \t\t\t<senderdes><![CDATA[查看红包]]></senderdes>\n" +
            "    \t\t\t<receiverdes><![CDATA[领取红包]]></receiverdes>\n" +
            "    \t\t\t<nativeurl><![CDATA[wxpay://c2cbizmessagehandler/hongbao/receivehongbao?msgtype=1&channelid=1&sendid=1000039501201904256012119396886&sendusername=wxid_y53k899ttb0q21&ver=6" +
            "&sign=\n" +
            "d323833c60f64dd0258a41c516a8b83bdc5f5f91df7663fcd227e76427b4809ae5b4c5f36f0de7dc7b33b7e1d5746d28ff4cd1fee7f685ec7740740bd6bec6afd8986be2460bdc1ac93462ae995e1f96]]></nativeurl>\n" +
            "    \t\t\t<sceneid><![CDATA[1002]]></sceneid>\n" +
            "    \t\t\t<innertype><![CDATA[0]]></innertype>\n" +
            "    \t\t\t<paymsgid><![CDATA[1000039501201904256012119396886]]></paymsgid>\n" +
            "    \t\t\t<scenetext>微信红包</scenetext>\n" +
            "    \t\t\t<locallogoicon><![CDATA[c2c_hongbao_icon_cn]]></locallogoicon>\n" +
            "    \t\t\t<invalidtime><![CDATA[1556263201]]></invalidtime>\n" +
            "    \t\t\t<broaden />\n" +
            "    \t\t</wcpayinfo>\n" +
            "    \t</appmsg>\n" +
            "    \t<fromusername><![CDATA[wxid_y53k899ttb0q21]]></fromusername>\n" +
            "    </msg>";
    @Test
    public void addition_isCorrect()
    {

        ss= ss.replaceAll("[\\S\\s]*<nativeurl><!\\[CDATA\\[","");
        ss= ss.replaceAll("\\]\\]></nativeurl>[\\S\\s]*","");
        System.out.print(ss);

    }
}