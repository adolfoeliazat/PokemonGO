package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzlr<T> {
    private static zza zzadc;
    private static int zzadd;
    private static String zzade;
    private static final Object zzpy;
    private T zzOX;
    protected final String zzue;
    protected final T zzuf;

    /* renamed from: com.google.android.gms.internal.zzlr.1 */
    static class C07791 extends zzlr<Boolean> {
        C07791(String str, Boolean bool) {
            super(str, bool);
        }

        protected /* synthetic */ Object zzbY(String str) {
            return zzbZ(str);
        }

        protected Boolean zzbZ(String str) {
            return zzlr.zzadc.zzb(this.zzue, (Boolean) this.zzuf);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlr.2 */
    static class C07802 extends zzlr<Long> {
        C07802(String str, Long l) {
            super(str, l);
        }

        protected /* synthetic */ Object zzbY(String str) {
            return zzca(str);
        }

        protected Long zzca(String str) {
            return zzlr.zzadc.getLong(this.zzue, (Long) this.zzuf);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlr.3 */
    static class C07813 extends zzlr<Integer> {
        C07813(String str, Integer num) {
            super(str, num);
        }

        protected /* synthetic */ Object zzbY(String str) {
            return zzcb(str);
        }

        protected Integer zzcb(String str) {
            return zzlr.zzadc.zzb(this.zzue, (Integer) this.zzuf);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlr.4 */
    static class C07824 extends zzlr<Float> {
        C07824(String str, Float f) {
            super(str, f);
        }

        protected /* synthetic */ Object zzbY(String str) {
            return zzcc(str);
        }

        protected Float zzcc(String str) {
            return zzlr.zzadc.zzb(this.zzue, (Float) this.zzuf);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlr.5 */
    static class C07835 extends zzlr<String> {
        C07835(String str, String str2) {
            super(str, str2);
        }

        protected /* synthetic */ Object zzbY(String str) {
            return zzcd(str);
        }

        protected String zzcd(String str) {
            return zzlr.zzadc.getString(this.zzue, (String) this.zzuf);
        }
    }

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zzb(String str, Boolean bool);

        Float zzb(String str, Float f);

        Integer zzb(String str, Integer num);
    }

    static {
        zzpy = new Object();
        zzadc = null;
        zzadd = 0;
        zzade = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzlr(String str, T t) {
        this.zzOX = null;
        this.zzue = str;
        this.zzuf = t;
    }

    public static boolean isInitialized() {
        return zzadc != null;
    }

    public static zzlr<Float> zza(String str, Float f) {
        return new C07824(str, f);
    }

    public static zzlr<Integer> zza(String str, Integer num) {
        return new C07813(str, num);
    }

    public static zzlr<Long> zza(String str, Long l) {
        return new C07802(str, l);
    }

    public static zzlr<Boolean> zzg(String str, boolean z) {
        return new C07791(str, Boolean.valueOf(z));
    }

    public static int zzoo() {
        return zzadd;
    }

    public static zzlr<String> zzu(String str, String str2) {
        return new C07835(str, str2);
    }

    public final T get() {
        return this.zzOX != null ? this.zzOX : zzbY(this.zzue);
    }

    protected abstract T zzbY(String str);

    public final T zzop() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            T t = get();
            return t;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
