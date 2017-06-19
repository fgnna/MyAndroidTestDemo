package testrootapp.someday.cn.testaudioplay.service.audio;

import android.media.MediaPlayer;

/**
 * 歌曲信息实体类
 * @author shaojunjie on 17-6-16
 * @Email fgnna@qq.com
 */
public class Music
{

    private String id;
    private String name;
    private String path;
    private MediaPlayer mMediaPlayer;
    public Music(String id, String name, String path)
    {
        this.id = id;
        this.name = name;
        this.path = path;
    }


    void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        mMediaPlayer = mediaPlayer;
    }
    MediaPlayer getMediaPlayer()
    {
        return mMediaPlayer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
