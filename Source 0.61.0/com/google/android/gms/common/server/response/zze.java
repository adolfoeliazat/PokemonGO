package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import spacemadness.com.lunarconsole.C1628R;

public class zze implements Creator<SafeParcelResponse> {
    static void zza(SafeParcelResponse safeParcelResponse, Parcel parcel, int i) {
        int zzaq = zzb.zzaq(parcel);
        zzb.zzc(parcel, 1, safeParcelResponse.getVersionCode());
        zzb.zza(parcel, 2, safeParcelResponse.zzpV(), false);
        zzb.zza(parcel, 3, safeParcelResponse.zzpW(), i, false);
        zzb.zzI(parcel, zzaq);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzaz(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbX(x0);
    }

    public SafeParcelResponse zzaz(Parcel parcel) {
        FieldMappingDictionary fieldMappingDictionary = null;
        int zzap = zza.zzap(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < zzap) {
            int zzao = zza.zzao(parcel);
            switch (zza.zzbM(zzao)) {
                case C1628R.styleable.RecyclerView_layoutManager /*1*/:
                    i = zza.zzg(parcel, zzao);
                    break;
                case C1628R.styleable.RecyclerView_spanCount /*2*/:
                    parcel2 = zza.zzE(parcel, zzao);
                    break;
                case C1628R.styleable.RecyclerView_reverseLayout /*3*/:
                    fieldMappingDictionary = (FieldMappingDictionary) zza.zza(parcel, zzao, FieldMappingDictionary.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzao);
                    break;
            }
        }
        if (parcel.dataPosition() == zzap) {
            return new SafeParcelResponse(i, parcel2, fieldMappingDictionary);
        }
        throw new zza.zza("Overread allowed size end=" + zzap, parcel);
    }

    public SafeParcelResponse[] zzbX(int i) {
        return new SafeParcelResponse[i];
    }
}
