package com.it.testtelephonyservice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by je on 17-3-6.
 */

public class CallingServiceImpl extends BaseCallingService
{
    private static final String TAG = "CallingServiceImpl";
    private boolean isRecording = false;


    @Override
    protected void onEndCallInComingNotAnswer(CallInfoEntity infoEntity) {

        Log.d(TAG, "来电，未接听");
    }

    @Override
    protected void onEndCallInComingIsAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "来电，已接听");
    }

    @Override
    protected void onEndCallOutNotAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "去电，未接听");
        isRecording = false;
    }

    @Override
    protected void onEndCallOutIsAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "去电，已接听");
        isRecording = false;
    }

    /**
     * 来电-待接听
     * @param number
     */
    protected  void onComeingCallRinging(String number)
    {
        Log.d(TAG, "来电-待接听");
    }

    @Override
    protected void onAnswerComeingCall(String number) {
        Log.d(TAG, "来电，接听");
    }

    /**
     * 呼出-正在通话
     * @param number
     */
    protected  void onOutCall(String number)
    {
    }

    @Override
    protected void onErrorNoCalllog() {
        Log.d(TAG, "无log");
    }

    @Override
    protected void onCallStateIdel() {
        Log.d(TAG, "电话回归空闲");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    public void record() {
//        AudioRecord audioRecorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL,
//                AudioFormat.SAMPLE_RATE_UNSPECIFIED,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                AudioRecord.getMinBufferSize(AudioFormat.SAMPLE_RATE_UNSPECIFIED,
//                        AudioFormat.CHANNEL_IN_MONO,
//                        AudioFormat.ENCODING_PCM_16BIT)
//        );
//        audioRecorder.startRecording();
//        audioRecorder.read()
//        audioRecorder.stop();
        Log.d(TAG, "呼出-正在通话");

        int frequency = 11025;
        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");

        // Delete any previous recording.
        if (file.exists())
            file.delete();


        // Create the new file.
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create " + file.toString());
        }

        try {
            // Create a DataOuputStream to write the audio data into the saved file.
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            // Create a new AudioRecord object to record the audio.
            int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration,  audioEncoding);
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL,
                    frequency, channelConfiguration,
                    audioEncoding, bufferSize);

            short[] buffer = new short[bufferSize];
            audioRecord.startRecording();

            isRecording = true ;
            while (isRecording) {
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                for (int i = 0; i < bufferReadResult; i++)
                    dos.writeShort(buffer[i]);
            }


            audioRecord.stop();
            dos.close();

        } catch (Throwable t) {
            Log.e("AudioRecord","Recording Failed");
        }
    }
}
