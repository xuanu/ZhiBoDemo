package apk.cn.zeffect.zhibodemo;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.faucamp.simplertmp.RtmpHandler;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;

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

public class RecordActivity extends Activity implements View.OnClickListener, RtmpHandler.RtmpListener,
        SrsRecordHandler.SrsRecordListener, SrsEncodeHandler.SrsEncodeListener {
    private SrsPublisher mPublisher;
    private Button mStartBtn, mSwitchBtn;
    private EditText mEditText;
    public static final String URL = "rtmp://ps3.live.panda.tv/live_panda/4e3f40559b849965f4455915342d5ba7?sign=6ed2770f17c4f1ee9d195fec5d1d61d9&time=1499665921&wm=2&wml=1&vr=0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_record);
        initRecord();
    }


    private void initRecord() {
        mStartBtn = (Button) findViewById(R.id.start);
        mSwitchBtn = (Button) findViewById(R.id.switch_btn);
        mEditText = (EditText) findViewById(R.id.push_url);
        mEditText.setText(URL);
        //
        mPublisher = new SrsPublisher((SrsCameraView) findViewById(R.id.record_camera));
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution(640, 360);
        mPublisher.setOutputResolution(360, 640);
        mPublisher.setVideoHDMode();
        mPublisher.startCamera();
        //
        mStartBtn.setOnClickListener(this);
        mSwitchBtn.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
    }

    @Override
    public void onNetworkWeak() {
//        Toast.makeText(this, "网络不稳定", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkResume() {
//        Toast.makeText(this, "网络恢复", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
//        Toast.makeText(this, "编码报错IllegalArgumentException", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordPause() {
//        Toast.makeText(this, "暂停录制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordResume() {
//        Toast.makeText(this, "恢复录制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordStarted(String msg) {
//        Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordFinished(String msg) {
//        Toast.makeText(this, "结束录制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
//        Toast.makeText(this, "录制报错IllegalArgumentException", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordIOException(IOException e) {
//        Toast.makeText(this, "录制报错IOException", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpConnecting(String msg) {
//        Toast.makeText(this, "录制连接中" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpConnected(String msg) {
//        Toast.makeText(this, "连接成功" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpVideoStreaming() {
//        Toast.makeText(this, "应该是发送视频中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onRtmpStopped() {
//        Toast.makeText(this, "结束推流", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpDisconnected() {

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {

    }

    @Override
    public void onRtmpIOException(IOException e) {

    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                String showString = mStartBtn.getText().toString().trim();
                if (showString.equals("开始")) {
                    String rtmpUrl = mEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(rtmpUrl)) {
                        return;
                    }
                    mPublisher.startPublish(rtmpUrl);
                    mPublisher.startCamera();
                    mStartBtn.setText("结束");
                } else {
                    mPublisher.stopPublish();
                    mPublisher.stopRecord();
                    mStartBtn.setText("开始");
                }
                break;
            case R.id.switch_btn:
                mPublisher.switchCameraFace((mPublisher.getCamraId() + 1) % Camera.getNumberOfCameras());
                break;
        }
    }
}
