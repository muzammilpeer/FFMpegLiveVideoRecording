package com.muzammilpeer.commandapp.util;

import android.text.TextUtils;

public enum CpuArch {
    x86("1b3daf0402c38ec0019ec436d71a1389514711bd"),
    ARMv7("e27cf3c432b121896fc8af2d147eff88d3074dd5"),
    ARMv7_NEON("9463c40e898c53dcac59b8ba39cfd590e2f1b1bf"),
    NONE(null);

    private String sha1;

    CpuArch(String sha1) {
        this.sha1 = sha1;
    }

    String getSha1(){
        return sha1;
    }

    public static CpuArch fromString(String sha1) {
        if (!TextUtils.isEmpty(sha1)) {
            for (CpuArch cpuArch : CpuArch.values()) {
                if (sha1.equalsIgnoreCase(cpuArch.sha1)) {
                    return cpuArch;
                }
            }
        }
        return NONE;
    }
}
