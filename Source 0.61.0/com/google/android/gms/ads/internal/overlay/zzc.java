package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View.MeasureSpec;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzid;
import com.mopub.volley.DefaultRetryPolicy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@zzgr
public class zzc extends zzi implements OnAudioFocusChangeListener, OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceTextureListener {
    private static final Map<Integer, String> zzAO;
    private final zzp zzAP;
    private int zzAQ;
    private int zzAR;
    private MediaPlayer zzAS;
    private Uri zzAT;
    private int zzAU;
    private int zzAV;
    private int zzAW;
    private int zzAX;
    private int zzAY;
    private float zzAZ;
    private boolean zzBa;
    private boolean zzBb;
    private int zzBc;
    private zzh zzBd;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.1 */
    class C03401 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03401(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.zzeR();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.2 */
    class C03412 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03412(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.zzeT();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.3 */
    class C03423 implements Runnable {
        final /* synthetic */ zzc zzBe;
        final /* synthetic */ String zzBf;
        final /* synthetic */ String zzBg;

        C03423(zzc com_google_android_gms_ads_internal_overlay_zzc, String str, String str2) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
            this.zzBf = str;
            this.zzBg = str2;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.zzh(this.zzBf, this.zzBg);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.4 */
    class C03434 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03434(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.zzeQ();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.5 */
    class C03445 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03445(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.onPaused();
                this.zzBe.zzBd.zzeU();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.6 */
    class C03456 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03456(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.zzeS();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.7 */
    class C03467 implements Runnable {
        final /* synthetic */ zzc zzBe;

        C03467(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzBe = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzBe.zzBd != null) {
                this.zzBe.zzBd.onPaused();
            }
        }
    }

    static {
        zzAO = new HashMap();
        zzAO.put(Integer.valueOf(-1004), "MEDIA_ERROR_IO");
        zzAO.put(Integer.valueOf(-1007), "MEDIA_ERROR_MALFORMED");
        zzAO.put(Integer.valueOf(-1010), "MEDIA_ERROR_UNSUPPORTED");
        zzAO.put(Integer.valueOf(-110), "MEDIA_ERROR_TIMED_OUT");
        zzAO.put(Integer.valueOf(100), "MEDIA_ERROR_SERVER_DIED");
        zzAO.put(Integer.valueOf(1), "MEDIA_ERROR_UNKNOWN");
        zzAO.put(Integer.valueOf(1), "MEDIA_INFO_UNKNOWN");
        zzAO.put(Integer.valueOf(700), "MEDIA_INFO_VIDEO_TRACK_LAGGING");
        zzAO.put(Integer.valueOf(3), "MEDIA_INFO_VIDEO_RENDERING_START");
        zzAO.put(Integer.valueOf(701), "MEDIA_INFO_BUFFERING_START");
        zzAO.put(Integer.valueOf(702), "MEDIA_INFO_BUFFERING_END");
        zzAO.put(Integer.valueOf(800), "MEDIA_INFO_BAD_INTERLEAVING");
        zzAO.put(Integer.valueOf(801), "MEDIA_INFO_NOT_SEEKABLE");
        zzAO.put(Integer.valueOf(802), "MEDIA_INFO_METADATA_UPDATE");
        zzAO.put(Integer.valueOf(901), "MEDIA_INFO_UNSUPPORTED_SUBTITLE");
        zzAO.put(Integer.valueOf(902), "MEDIA_INFO_SUBTITLE_TIMED_OUT");
    }

    public zzc(Context context, zzp com_google_android_gms_ads_internal_overlay_zzp) {
        super(context);
        this.zzAQ = 0;
        this.zzAR = 0;
        this.zzAZ = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        setSurfaceTextureListener(this);
        this.zzAP = com_google_android_gms_ads_internal_overlay_zzp;
        this.zzAP.zza((zzi) this);
    }

    private void zzb(float f) {
        if (this.zzAS != null) {
            try {
                this.zzAS.setVolume(f, f);
                return;
            } catch (IllegalStateException e) {
                return;
            }
        }
        zzb.zzaH("AdMediaPlayerView setMediaPlayerVolume() called before onPrepared().");
    }

    private void zzeA() {
        zzb.m484v("AdMediaPlayerView audio focus lost");
        this.zzBb = false;
        zzeB();
    }

    private void zzeB() {
        if (this.zzBa || !this.zzBb) {
            zzb(0.0f);
        } else {
            zzb(this.zzAZ);
        }
    }

    private AudioManager zzeC() {
        return (AudioManager) getContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
    }

    private void zzes() {
        Throwable e;
        zzb.m484v("AdMediaPlayerView init MediaPlayer");
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (this.zzAT != null && surfaceTexture != null) {
            zzt(false);
            try {
                this.zzAS = new MediaPlayer();
                this.zzAS.setOnBufferingUpdateListener(this);
                this.zzAS.setOnCompletionListener(this);
                this.zzAS.setOnErrorListener(this);
                this.zzAS.setOnInfoListener(this);
                this.zzAS.setOnPreparedListener(this);
                this.zzAS.setOnVideoSizeChangedListener(this);
                this.zzAW = 0;
                this.zzAS.setDataSource(getContext(), this.zzAT);
                this.zzAS.setSurface(new Surface(surfaceTexture));
                this.zzAS.setAudioStreamType(3);
                this.zzAS.setScreenOnWhilePlaying(true);
                this.zzAS.prepareAsync();
                zzt(1);
            } catch (IOException e2) {
                e = e2;
                zzb.zzd("Failed to initialize MediaPlayer at " + this.zzAT, e);
                onError(this.zzAS, 1, 0);
            } catch (IllegalArgumentException e3) {
                e = e3;
                zzb.zzd("Failed to initialize MediaPlayer at " + this.zzAT, e);
                onError(this.zzAS, 1, 0);
            }
        }
    }

    private void zzet() {
        if (zzew() && this.zzAS.getCurrentPosition() > 0 && this.zzAR != 3) {
            zzb.m484v("AdMediaPlayerView nudging MediaPlayer");
            zzb(0.0f);
            this.zzAS.start();
            int currentPosition = this.zzAS.getCurrentPosition();
            long currentTimeMillis = zzp.zzbz().currentTimeMillis();
            while (zzew() && this.zzAS.getCurrentPosition() == currentPosition) {
                if (zzp.zzbz().currentTimeMillis() - currentTimeMillis > 250) {
                    break;
                }
            }
            this.zzAS.pause();
            zzeB();
        }
    }

    private void zzeu() {
        AudioManager zzeC = zzeC();
        if (zzeC != null && !this.zzBb) {
            if (zzeC.requestAudioFocus(this, 3, 2) == 1) {
                zzez();
            } else {
                zzb.zzaH("AdMediaPlayerView audio focus request failed");
            }
        }
    }

    private void zzev() {
        zzb.m484v("AdMediaPlayerView abandon audio focus");
        AudioManager zzeC = zzeC();
        if (zzeC != null && this.zzBb) {
            if (zzeC.abandonAudioFocus(this) == 1) {
                this.zzBb = false;
            } else {
                zzb.zzaH("AdMediaPlayerView abandon audio focus failed");
            }
        }
    }

    private boolean zzew() {
        return (this.zzAS == null || this.zzAQ == -1 || this.zzAQ == 0 || this.zzAQ == 1) ? false : true;
    }

    private void zzez() {
        zzb.m484v("AdMediaPlayerView audio focus gained");
        this.zzBb = true;
        zzeB();
    }

    private void zzt(int i) {
        if (i == 3) {
            this.zzAP.zzfe();
        } else if (this.zzAQ == 3 && i != 3) {
            this.zzAP.zzff();
        }
        this.zzAQ = i;
    }

    private void zzt(boolean z) {
        zzb.m484v("AdMediaPlayerView release");
        if (this.zzAS != null) {
            this.zzAS.reset();
            this.zzAS.release();
            this.zzAS = null;
            zzt(0);
            if (z) {
                this.zzAR = 0;
                zzu(0);
            }
            zzev();
        }
    }

    private void zzu(int i) {
        this.zzAR = i;
    }

    public int getCurrentPosition() {
        return zzew() ? this.zzAS.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return zzew() ? this.zzAS.getDuration() : -1;
    }

    public int getVideoHeight() {
        return this.zzAS != null ? this.zzAS.getVideoHeight() : 0;
    }

    public int getVideoWidth() {
        return this.zzAS != null ? this.zzAS.getVideoWidth() : 0;
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange > 0) {
            zzez();
        } else if (focusChange < 0) {
            zzeA();
        }
    }

    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.zzAW = percent;
    }

    public void onCompletion(MediaPlayer mp) {
        zzb.m484v("AdMediaPlayerView completion");
        zzt(5);
        zzu(5);
        zzid.zzIE.post(new C03412(this));
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        String str = (String) zzAO.get(Integer.valueOf(what));
        String str2 = (String) zzAO.get(Integer.valueOf(extra));
        zzb.zzaH("AdMediaPlayerView MediaPlayer error: " + str + UpsightEndpoint.SIGNED_MESSAGE_SEPARATOR + str2);
        zzt(-1);
        zzu(-1);
        zzid.zzIE.post(new C03423(this, str, str2));
        return true;
    }

    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        String str = (String) zzAO.get(Integer.valueOf(extra));
        zzb.m484v("AdMediaPlayerView MediaPlayer info: " + ((String) zzAO.get(Integer.valueOf(what))) + UpsightEndpoint.SIGNED_MESSAGE_SEPARATOR + str);
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultSize = getDefaultSize(this.zzAU, widthMeasureSpec);
        int defaultSize2 = getDefaultSize(this.zzAV, heightMeasureSpec);
        if (this.zzAU > 0 && this.zzAV > 0) {
            int mode = MeasureSpec.getMode(widthMeasureSpec);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = MeasureSpec.getMode(heightMeasureSpec);
            defaultSize2 = MeasureSpec.getSize(heightMeasureSpec);
            if (mode == 1073741824 && mode2 == 1073741824) {
                if (this.zzAU * defaultSize2 < this.zzAV * size) {
                    defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                } else if (this.zzAU * defaultSize2 > this.zzAV * size) {
                    defaultSize2 = (this.zzAV * size) / this.zzAU;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode == 1073741824) {
                defaultSize = (this.zzAV * size) / this.zzAU;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode2 == 1073741824) {
                defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                if (mode == Cue.TYPE_UNSET && defaultSize > size) {
                    defaultSize = size;
                }
            } else {
                int i = this.zzAU;
                defaultSize = this.zzAV;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = i;
                } else {
                    defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                }
                if (mode == Cue.TYPE_UNSET && r1 > size) {
                    defaultSize2 = (this.zzAV * size) / this.zzAU;
                    defaultSize = size;
                }
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
        if (VERSION.SDK_INT == 16) {
            if ((this.zzAX > 0 && this.zzAX != defaultSize) || (this.zzAY > 0 && this.zzAY != defaultSize2)) {
                zzet();
            }
            this.zzAX = defaultSize;
            this.zzAY = defaultSize2;
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        zzb.m484v("AdMediaPlayerView prepared");
        zzt(2);
        this.zzAP.zzeR();
        zzid.zzIE.post(new C03401(this));
        this.zzAU = mediaPlayer.getVideoWidth();
        this.zzAV = mediaPlayer.getVideoHeight();
        if (this.zzBc != 0) {
            seekTo(this.zzBc);
        }
        zzet();
        zzb.zzaG("AdMediaPlayerView stream dimensions: " + this.zzAU + " x " + this.zzAV);
        if (this.zzAR == 3) {
            play();
        }
        zzeu();
        zzeB();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        zzb.m484v("AdMediaPlayerView surface created");
        zzes();
        zzid.zzIE.post(new C03434(this));
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        zzb.m484v("AdMediaPlayerView surface destroyed");
        if (this.zzAS != null && this.zzBc == 0) {
            this.zzBc = this.zzAS.getCurrentPosition();
        }
        zzid.zzIE.post(new C03445(this));
        zzt(true);
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int w, int h) {
        Object obj = 1;
        zzb.m484v("AdMediaPlayerView surface changed");
        Object obj2 = this.zzAR == 3 ? 1 : null;
        if (!(this.zzAU == w && this.zzAV == h)) {
            obj = null;
        }
        if (this.zzAS != null && obj2 != null && r1 != null) {
            if (this.zzBc != 0) {
                seekTo(this.zzBc);
            }
            play();
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        this.zzAP.zzb(this);
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        zzb.m484v("AdMediaPlayerView size changed: " + width + " x " + height);
        this.zzAU = mp.getVideoWidth();
        this.zzAV = mp.getVideoHeight();
        if (this.zzAU != 0 && this.zzAV != 0) {
            requestLayout();
        }
    }

    public void pause() {
        zzb.m484v("AdMediaPlayerView pause");
        if (zzew() && this.zzAS.isPlaying()) {
            this.zzAS.pause();
            zzt(4);
            zzid.zzIE.post(new C03467(this));
        }
        zzu(4);
    }

    public void play() {
        zzb.m484v("AdMediaPlayerView play");
        if (zzew()) {
            this.zzAS.start();
            zzt(3);
            zzid.zzIE.post(new C03456(this));
        }
        zzu(3);
    }

    public void seekTo(int millis) {
        zzb.m484v("AdMediaPlayerView seek " + millis);
        if (zzew()) {
            this.zzAS.seekTo(millis);
            this.zzBc = 0;
            return;
        }
        this.zzBc = millis;
    }

    public void setMimeType(String mimeType) {
    }

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        this.zzAT = uri;
        this.zzBc = 0;
        zzes();
        requestLayout();
        invalidate();
    }

    public void stop() {
        zzb.m484v("AdMediaPlayerView stop");
        if (this.zzAS != null) {
            this.zzAS.stop();
            this.zzAS.release();
            this.zzAS = null;
            zzt(0);
            zzu(0);
            zzev();
        }
        this.zzAP.onStop();
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    public void zza(float f) {
        this.zzAZ = f;
        zzeB();
    }

    public void zza(zzh com_google_android_gms_ads_internal_overlay_zzh) {
        this.zzBd = com_google_android_gms_ads_internal_overlay_zzh;
    }

    public String zzer() {
        return "MediaPlayer";
    }

    public void zzex() {
        this.zzBa = true;
        zzeB();
    }

    public void zzey() {
        this.zzBa = false;
        zzeB();
    }
}
