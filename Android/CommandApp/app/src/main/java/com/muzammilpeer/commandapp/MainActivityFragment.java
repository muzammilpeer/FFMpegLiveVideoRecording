package com.muzammilpeer.commandapp;

import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.muzammilpeer.commandapp.executer.FFMpegSetup;
import com.muzammilpeer.commandapp.executer.ShellExecuter;
import com.muzammilpeer.commandapp.util.CpuArchHelper;
import com.muzammilpeer.commandapp.util.FileUtils;
import com.muzammilpeer.commandapp.util.Log4a;

import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    EditText input;
    Button btn;
    Button btnStop;
    TextView out;
    String command;

    View mView;

    Thread mThread;

    public MainActivityFragment() {
    }

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_main, container, false);

        enableStrictMode();

        String cpuArchNameFromAssets = null;
        switch (CpuArchHelper.getCpuArch()) {
            case x86:
                Log.e("","Loading FFmpeg for x86 CPU");
                cpuArchNameFromAssets = "x86";
                break;
            case ARMv7:
                Log.e("","Loading FFmpeg for armv7 CPU");
                cpuArchNameFromAssets = "armeabi-v7a";
                break;
            case ARMv7_NEON:
                Log.e("","Loading FFmpeg for armv7-neon CPU");
                cpuArchNameFromAssets = "armeabi-v7a-neon";
                break;
            case NONE:
                Log4a.printException(new Exception("Device not supported"));
        }

        if (!TextUtils.isEmpty(cpuArchNameFromAssets)) {
            FFMpegSetup ffmpegManager = new FFMpegSetup(cpuArchNameFromAssets,getActivity());
            ffmpegManager.setupFFMpeg();
        } else {
            Log4a.printException(new Exception("Device not supported"));
        }



        input = (EditText) mView.findViewById(R.id.txt);
        btn = (Button)mView.findViewById(R.id.btn);
        btnStop = (Button)mView.findViewById(R.id.btnStop);
        out = (TextView)mView.findViewById(R.id.out);
        String internalStoragePath  = Environment.getExternalStorageDirectory().getAbsolutePath()  +"/test/"+ UUID.randomUUID() +".mp4";
        String accessLog = Environment.getExternalStorageDirectory().getAbsolutePath()  +"/test/access_log.log";

        String fullCommand = " -re -i http://pitelevision.com:1935/pitelevision/samaanews3/playlist.m3u8?token=bs2x5n69bo8d58vc -c copy -bsf:a aac_adtstoasc " + internalStoragePath ;
//        String fullCommand = " -version";
        input.setText(FileUtils.getFFmpeg(getActivity()) + fullCommand);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log4a.e("Btn pressed = ", "Start");

                //it will work without threading, but if i put in thread then on intreput call it call the process interupt,
                // and process gives me the file exception. and also the in this technique whole ui is stuck
//                ShellExecuter exe = ShellExecuter.getInstance(getActivity());
//                exe.Executer(input.getText().toString());


                mThread = new Thread (new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ShellExecuter exe = ShellExecuter.getInstance(getActivity());
                            exe.Executer(input.getText().toString());
                        } catch (Exception e) {
                            Log4a.printException(e);
                        }
                    }
                });
                mThread.start();


            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log4a.e("Btn pressed = ", "Stop");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShellExecuter exe = ShellExecuter.getInstance(getActivity());
                        exe.stopExecutor();
                    }
                });


            }
        });

        return mView;
    }
}
