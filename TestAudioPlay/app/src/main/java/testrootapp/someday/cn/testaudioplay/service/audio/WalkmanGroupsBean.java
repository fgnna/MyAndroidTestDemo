package testrootapp.someday.cn.testaudioplay.service.audio;

/**
 * 随身听
 * @author shaojunjie on 17-6-15
 * @Email fgnna@qq.com
 *
 */
public class WalkmanGroupsBean
{

    public String type = "1";
    public String title = "轻。听";
    public WalkManBean[] list = new WalkManBean[]{new WalkManBean(),new WalkManBean(),new WalkManBean(),new WalkManBean(),new WalkManBean(),new WalkManBean()};

    public static class WalkManBean
    {
        public String id = "1";
        public String img_url = "https://hxsupload-oss.hxsapp.com/2016-05-16/e101c60dcd6148bea9dfbc8a4ce475af.jpg?x-oss-process=style/thumb";
        public String audio_url;
        public String content = "以前总是认是认是认是认是认是认是认是认是认是认是认是认是认是认是认是认为";

        public WalkManBean(){}
        public WalkManBean(String id, String content, String audio_url)
        {
            this.id = id;
            this.content = content;
            this.audio_url = audio_url;
        }
    }


}
