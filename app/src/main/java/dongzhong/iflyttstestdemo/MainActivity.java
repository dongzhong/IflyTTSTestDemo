package dongzhong.iflyttstestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startButton;
    private Button stopButton;

    private SpeechSynthesizer speechSynthesizer;

    Timer timer = new Timer();
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start_tts);
        stopButton = (Button) findViewById(R.id.stop_tts);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=56e7bd2c" + "," + SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        Setting.setShowLog(true);

        speechSynthesizer = SpeechSynthesizer.createSynthesizer(getApplicationContext(), new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });
        speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisxmeng");
        //设置合成语速
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
        //设置播放器音频流类型
        speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        speechSynthesizer.setParameter(SpeechConstant.TTS_BUFFER_TIME, "0");
        speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_tts:
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        String fileName = "/mnt/sdcard/tts/" + simpleDateFormat.format(calendar.getTime()) + ".wav";
                        speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, fileName);
                        speechSynthesizer.startSpeaking("中南建设现在7.31元，跌幅2.27%，已收盘", new SynthesizerListener() {
                            @Override
                            public void onSpeakBegin() {

                            }

                            @Override
                            public void onBufferProgress(int i, int i1, int i2, String s) {

                            }

                            @Override
                            public void onSpeakPaused() {

                            }

                            @Override
                            public void onSpeakResumed() {

                            }

                            @Override
                            public void onSpeakProgress(int i, int i1, int i2) {

                            }

                            @Override
                            public void onCompleted(SpeechError speechError) {

                            }

                            @Override
                            public void onEvent(int i, int i1, int i2, Bundle bundle) {

                            }
                        });
                    }
                }, 1000, 10000);
                break;
            case R.id.stop_tts:
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                break;
            default:
                break;
        }
    }
}
