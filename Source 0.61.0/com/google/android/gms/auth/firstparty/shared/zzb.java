package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.upsight.mediation.mraid.properties.MRAIDResizeProperties;
import spacemadness.com.lunarconsole.C1628R;

public class zzb implements Creator<FACLData> {
    static void zza(FACLData fACLData, Parcel parcel, int i) {
        int zzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, fACLData.version);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, fACLData.zzTD, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, fACLData.zzTE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, fACLData.zzTF);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, fACLData.zzTG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, zzaq);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzU(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaL(x0);
    }

    public FACLData zzU(Parcel parcel) {
        boolean z = false;
        String str = null;
        int zzap = zza.zzap(parcel);
        String str2 = null;
        FACLConfig fACLConfig = null;
        int i = 0;
        while (parcel.dataPosition() < zzap) {
            int zzao = zza.zzao(parcel);
            switch (zza.zzbM(zzao)) {
                case C1628R.styleable.RecyclerView_layoutManager /*1*/:
                    i = zza.zzg(parcel, zzao);
                    break;
                case C1628R.styleable.RecyclerView_spanCount /*2*/:
                    fACLConfig = (FACLConfig) zza.zza(parcel, zzao, FACLConfig.CREATOR);
                    break;
                case C1628R.styleable.RecyclerView_reverseLayout /*3*/:
                    str2 = zza.zzp(parcel, zzao);
                    break;
                case C1628R.styleable.RecyclerView_stackFromEnd /*4*/:
                    z = zza.zzc(parcel, zzao);
                    break;
                case MRAIDResizeProperties.CUSTOM_CLOSE_POSITION_BOTTOM_CENTER /*5*/:
                    str = zza.zzp(parcel, zzao);
                    break;
                default:
                    zza.zzb(parcel, zzao);
                    break;
            }
        }
        if (parcel.dataPosition() == zzap) {
            return new FACLData(i, fACLConfig, str2, z, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzap, parcel);
    }

    public FACLData[] zzaL(int i) {
        return new FACLData[i];
    }
}
