package com.muzammilpeer.commandapp.executer;

import android.content.Context;
import android.util.Log;

import com.muzammilpeer.commandapp.util.CpuArch;
import com.muzammilpeer.commandapp.util.FileUtils;

import java.io.File;

/**
 * Created by muzammilpeer on 11/1/15.
 */
public class FFMpegSetup {

    private final String cpuArchNameFromAssets;
    private final Context context;

    public FFMpegSetup(String cpuArchNameFromAssets, Context context) {
        this.cpuArchNameFromAssets = cpuArchNameFromAssets;
        this.context = context;
    }

    public Boolean setupFFMpeg()
    {
        File ffmpegFile = new File(FileUtils.getFFmpeg(context));
        if (ffmpegFile.exists() && isDeviceFFmpegVersionOld() && !ffmpegFile.delete()) {
            return false;
        }
        if (!ffmpegFile.exists()) {
            boolean isFileCopied = FileUtils.copyBinaryFromAssetsToData(context,
                    cpuArchNameFromAssets + File.separator + FileUtils.ffmpegFileName,
                    FileUtils.ffmpegFileName);

            // make file executable
            if (isFileCopied) {
                if(!ffmpegFile.canExecute()) {
                    Log.d("canExecute ","FFmpeg is not executable, trying to make it executable ...");
                    if (ffmpegFile.setExecutable(true)) {
                        return true;
                    }
                } else {
                    Log.d("canExecute","FFmpeg is executable");
                    return true;
                }
            }
        }
        return ffmpegFile.exists() && ffmpegFile.canExecute();

    }

    private boolean isDeviceFFmpegVersionOld() {
        return CpuArch.fromString(FileUtils.SHA1(FileUtils.getFFmpeg(context))).equals(CpuArch.NONE);
    }


}
