package com.google.android.gms.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.exoplayer.util.NalUnitUtil;

public class zzrv {
    private final byte[] zzbhX;
    private int zzbhY;
    private int zzbhZ;

    public zzrv(byte[] bArr) {
        int i;
        this.zzbhX = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY];
        for (i = 0; i < AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY; i++) {
            this.zzbhX[i] = (byte) i;
        }
        i = 0;
        for (int i2 = 0; i2 < AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY; i2++) {
            i = ((i + this.zzbhX[i2]) + bArr[i2 % bArr.length]) & NalUnitUtil.EXTENDED_SAR;
            byte b = this.zzbhX[i2];
            this.zzbhX[i2] = this.zzbhX[i];
            this.zzbhX[i] = b;
        }
        this.zzbhY = 0;
        this.zzbhZ = 0;
    }

    public void zzA(byte[] bArr) {
        int i = this.zzbhY;
        int i2 = this.zzbhZ;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & NalUnitUtil.EXTENDED_SAR;
            i2 = (i2 + this.zzbhX[i]) & NalUnitUtil.EXTENDED_SAR;
            byte b = this.zzbhX[i];
            this.zzbhX[i] = this.zzbhX[i2];
            this.zzbhX[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.zzbhX[(this.zzbhX[i] + this.zzbhX[i2]) & NalUnitUtil.EXTENDED_SAR]);
        }
        this.zzbhY = i;
        this.zzbhZ = i2;
    }
}
