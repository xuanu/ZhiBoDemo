package apk.cn.zeffect.zhibodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tv.danmaku.ijk.widget.media.IjkVideoView;

/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/07/10
 *      desc    ：
 *      version:：1.0
 * </pre>
 *
 * @author zzx
 */

public class SeeActivity extends Activity {
    private IjkVideoView mVideoView;
    private Button mPlay;
    private EditText mEditText;
    public static final String URL = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see);
        mVideoView = (IjkVideoView) findViewById(R.id.play_video);
        mPlay = (Button) findViewById(R.id.start);
        mEditText = (EditText) findViewById(R.id.push_url);
        mEditText.setText(URL);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlay.getText().equals("开始")) {
                    String tempUrl = mEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(tempUrl)) {
                        return;
                    }
                    mVideoView.setVideoPath(tempUrl);
                    mVideoView.start();
                    mPlay.setText("结束");
                } else {
                    mVideoView.stopPlayback();
                    mPlay.setText("开始");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
