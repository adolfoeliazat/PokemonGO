package com.google.android.gms.internal;

import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzp;

@zzgr
public abstract class zzbu<T> {
    private final String zzue;
    private final T zzuf;

    /* renamed from: com.google.android.gms.internal.zzbu.1 */
    static class C06441 extends zzbu<Boolean> {
        C06441(String str, Boolean bool) {
            super(bool, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzb(sharedPreferences);
        }

        public Boolean zzb(SharedPreferences sharedPreferences) {
            return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzde()).booleanValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbu.2 */
    static class C06452 extends zzbu<Integer> {
        C06452(String str, Integer num) {
            super(num, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzc(sharedPreferences);
        }

        public Integer zzc(SharedPreferences sharedPreferences) {
            return Integer.valueOf(sharedPreferences.getInt(getKey(), ((Integer) zzde()).intValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbu.3 */
    static class C06463 extends zzbu<Long> {
        C06463(String str, Long l) {
            super(l, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzd(sharedPreferences);
        }

        public Long zzd(SharedPreferences sharedPreferences) {
            return Long.valueOf(sharedPreferences.getLong(getKey(), ((Long) zzde()).longValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbu.4 */
    static class C06474 extends zzbu<String> {
        C06474(String str, String str2) {
            super(str2, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zze(sharedPreferences);
        }

        public String zze(SharedPreferences sharedPreferences) {
            return sharedPreferences.getString(getKey(), (String) zzde());
        }
    }

    private zzbu(String str, T t) {
        this.zzue = str;
        this.zzuf = t;
        zzp.zzbD().zza(this);
    }

    public static zzbu<String> zzP(String str) {
        zzbu<String> zzc = zzc(str, (String) null);
        zzp.zzbD().zzb(zzc);
        return zzc;
    }

    public static zzbu<String> zzQ(String str) {
        zzbu<String> zzc = zzc(str, (String) null);
        zzp.zzbD().zzc(zzc);
        return zzc;
    }

    public static zzbu<Integer> zza(String str, int i) {
        return new C06452(str, Integer.valueOf(i));
    }

    public static zzbu<Boolean> zza(String str, Boolean bool) {
        return new C06441(str, bool);
    }

    public static zzbu<Long> zzb(String str, long j) {
        return new C06463(str, Long.valueOf(j));
    }

    public static zzbu<String> zzc(String str, String str2) {
        return new C06474(str, str2);
    }

    public T get() {
        return zzp.zzbE().zzd(this);
    }

    public String getKey() {
        return this.zzue;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzde() {
        return this.zzuf;
    }
}
