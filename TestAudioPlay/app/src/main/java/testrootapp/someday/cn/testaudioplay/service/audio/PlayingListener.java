package testrootapp.someday.cn.testaudioplay.service.audio;
/**
 * 当前service播放状态监听接口
 *
 * @see #onAllPlaySuccess() 注意事项
 *
 * @author shaojunjie on 17-6-16
 * @Email fgnna@qq.com
 */
public interface PlayingListener
{
    void onPlayingMusic(Music music);
    void onPlayingProgress(int duration, int currentPosition);
    void onMusicStop();
    void onMusicError();

    /**
     * 列表中所有播放结束，注意，在该事件被调用了，
     * service 将会 stopSelf(),
     * 所以请在该事件内清理所有service绑定
     */
    void onAllPlaySuccess();

    /**
     * 停止并清除列表
     */
    void onCancel();
}