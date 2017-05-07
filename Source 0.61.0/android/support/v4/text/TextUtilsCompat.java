package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.location.places.Place;
import java.util.Locale;
import spacemadness.com.lunarconsole.BuildConfig;
import spacemadness.com.lunarconsole.C1628R;

public class TextUtilsCompat {
    private static String ARAB_SCRIPT_SUBTAG;
    private static String HEBR_SCRIPT_SUBTAG;
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT;

    private static class TextUtilsCompatImpl {
        private TextUtilsCompatImpl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String s) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case Place.TYPE_ESTABLISHMENT /*34*/:
                        sb.append("&quot;");
                        break;
                    case Place.TYPE_FOOD /*38*/:
                        sb.append("&amp;");
                        break;
                    case Place.TYPE_FUNERAL_HOME /*39*/:
                        sb.append("&#39;");
                        break;
                    case Place.TYPE_MEAL_DELIVERY /*60*/:
                        sb.append("&lt;");
                        break;
                    case Place.TYPE_MOSQUE /*62*/:
                        sb.append("&gt;");
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            return sb.toString();
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            if (!(locale == null || locale.equals(TextUtilsCompat.ROOT))) {
                String scriptSubtag = ICUCompat.maximizeAndGetScript(locale);
                if (scriptSubtag == null) {
                    return getLayoutDirectionFromFirstChar(locale);
                }
                if (scriptSubtag.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || scriptSubtag.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) {
                    return 1;
                }
            }
            return 0;
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
            switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                case C1628R.styleable.RecyclerView_layoutManager /*1*/:
                case C1628R.styleable.RecyclerView_spanCount /*2*/:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        private TextUtilsCompatJellybeanMr1Impl() {
            super();
        }

        @NonNull
        public String htmlEncode(@NonNull String s) {
            return TextUtilsCompatJellybeanMr1.htmlEncode(s);
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new TextUtilsCompatJellybeanMr1Impl();
        } else {
            IMPL = new TextUtilsCompatImpl();
        }
        ROOT = new Locale(BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        ARAB_SCRIPT_SUBTAG = "Arab";
        HEBR_SCRIPT_SUBTAG = "Hebr";
    }

    @NonNull
    public static String htmlEncode(@NonNull String s) {
        return IMPL.htmlEncode(s);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        return IMPL.getLayoutDirectionFromLocale(locale);
    }
}
