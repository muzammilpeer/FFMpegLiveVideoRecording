package com.muzammilpeer.commandapp.util;

import android.os.Build;

import com.github.hiteshsondhi88.libffmpeg.ArmArchHelper;

public class CpuArchHelper {

    public static CpuArch getCpuArch() {
        // check if device is x86
        if (Build.CPU_ABI.equals(getx86CpuAbi())) {
            return CpuArch.x86;
        } else {
            // check if device is armeabi
            if (Build.CPU_ABI.equals(getArmeabiv7CpuAbi())) {
                ArmArchHelper cpuNativeArchHelper = new ArmArchHelper();
                String archInfo = cpuNativeArchHelper.cpuArchFromJNI();
                // check if device is arm v7
                if (cpuNativeArchHelper.isARM_v7_CPU(archInfo)) {
                    // check if device is neon
                    if (cpuNativeArchHelper.isNeonSupported(archInfo)) {
                        return CpuArch.ARMv7_NEON;
                    }
                    return CpuArch.ARMv7;
                }
            }
        }
        return CpuArch.NONE;
    }

   public static String getx86CpuAbi() {
        return "x86";
    }

   public static String getArmeabiv7CpuAbi() {
        return "armeabi-v7a";
    }
}
