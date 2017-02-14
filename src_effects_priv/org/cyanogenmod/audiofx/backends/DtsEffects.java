/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioDeviceInfo
 *  android.media.AudioManager
 *  android.util.Pair
 */
package com.cyngn.audiofx.backends;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Pair;
import com.cyngn.audiofx.backends.DtsEffects$ParamBuilder;
import com.cyngn.audiofx.backends.EffectSet;
import com.cyngn.audiofx.eq.EqUtils;

public class DtsEffects
extends EffectSet {
    private static final int[] CENTER_FREQS;
    private static final boolean DEBUG = false;
    private static final int EQUALIZER_BANDS_TO_USE = 10;
    private static final short[] EQUALIZER_BAND_LEVEL_RANGE;
    private static final String[] GEQ_BANKS;
    private static final String GEQ_DEF_GAINS = "geq_defgains";
    private static final String GEQ_EXT_ENABLE = "geq_ext_enable";
    private static final String GEQ_EXT_PRESET = "geq_ext_preset";
    private static final String GEQ_INT_ENABLE = "geq_int_enable";
    private static final String GEQ_INT_PRESET = "geq_int_preset";
    private static final String GEQ_USER_GAINS = "geq_usergains";
    private static final String TAG = "DtsEffects";
    private static final String[] TRUMEDIA_BANKS;
    private static final String TRUMEDIA_DEFER_SAVE = "srs_processing_defersave";
    private static final String TRUMEDIA_ENABLE = "trumedia_enable";
    private static final String[] WOWHDX_BANKS;
    private static final String WOWHD_DEFINITION_ENABLE = "wowhd_definition_enable";
    private static final String WOWHD_DEFINITION_MIN = "wowhd_definition_min";
    private static final String WOWHD_DEFINITION_SLIDE = "wowhd_definition_slide";
    private static final String WOWHD_DEFINITION_WINDOW = "wowhd_definition_window";
    private static final String WOWHD_SKIP = "wowhd_skip";
    private static final String WOWHD_SRS_ENABLE = "wowhd_srs_enable";
    private static final String WOWHD_SRS_SPACE = "wowhd_srs_space";
    private static final String WOWHD_SRS_SPEAKER = "wowhd_srs_speaker";
    private static final String WOWHD_TRUBASS_COMPENSATOR = "wowhd_trubass_compressor";
    private static final String WOWHD_TRUBASS_ENABLE = "wowhd_trubass_enable";
    private static final String WOWHD_TRUBASS_FREQ = "wowhd_trubass_freq";
    private static final String WOWHD_TRUBASS_MIN = "wowhd_trubass_min";
    private static final String WOWHD_TRUBASS_MODE = "wowhd_trubass_mode";
    private static final String WOWHD_TRUBASS_SLIDE = "wowhd_trubass_slide";
    private static final String WOWHD_TRUBASS_WINDOW = "wowhd_trubass_window";
    private AudioManager mAm;
    private DtsEffects$ParamBuilder mBuilder;
    private final Context mContext;
    final short[] mEqualizer;

    static {
        short[] arrs;
        int n = 2;
        int n2 = 1;
        short[] arrs2 = arrs = new short[n];
        arrs2[0] = -1500;
        arrs2[1] = 1500;
        EQUALIZER_BAND_LEVEL_RANGE = arrs;
        short[] arrs3 = arrs = new int[10];
        arrs3[0] = 31000;
        arrs3[1] = 62000;
        arrs3[2] = 125000;
        arrs3[3] = 250000;
        arrs3[4] = 500000;
        arrs3[5] = 1000000;
        arrs3[6] = 2000000;
        arrs3[7] = 4000000;
        arrs3[8] = 8000000;
        arrs3[9] = 16000000;
        CENTER_FREQS = arrs;
        arrs = new String[n2];
        arrs[0] = "srs_cfg";
        TRUMEDIA_BANKS = arrs;
        arrs = new String[5];
        arrs[0] = "srs_mus_int";
        arrs[n2] = "srs_mov_int";
        arrs[n] = "srs_pod_int";
        arrs[3] = "srs_mus_ext";
        arrs[4] = "srs_pod_ext";
        WOWHDX_BANKS = arrs;
        arrs = new String[n];
        arrs[0] = "srs_geq_0_int";
        arrs[n2] = "srs_geq_0_ext";
        GEQ_BANKS = arrs;
    }

    public DtsEffects(Context context, int n, AudioDeviceInfo audioDeviceInfo) {
        super(n, audioDeviceInfo);
        short[] arrs = new short[this.getNumEqualizerBands()];
        this.mEqualizer = arrs;
        this.mContext = context;
    }

    private static Pair parsePair(String string2) {
        String[] arrstring;
        int n;
        int n2;
        String string3 = null;
        if (string2 == null || (n = string2.isEmpty()) != 0) {
            return null;
        }
        String string4 = ";";
        n = string2.endsWith(string4);
        if (n != 0) {
            n = string2.length() + -1;
            string2 = string2.substring(0, n);
        }
        if ((n = (arrstring = string2.split(string4 = "=")).length) != (n2 = 2)) {
            return null;
        }
        String string5 = arrstring[0];
        string3 = arrstring[1];
        string4 = new String((Object)string5, (Object)string3);
        return string4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object querySetting(String string2, Class class_) {
        int n = 1;
        String string3 = this.mAm.getParameters(string2);
        Pair pair = DtsEffects.parsePair(string3);
        Boolean bl = null;
        Class<Boolean> class_2 = Boolean.class;
        int n2 = class_.isAssignableFrom(class_2);
        if (n2 != 0) {
            String string4 = (String)pair.second;
            n2 = Integer.parseInt(string4);
            if (n2 == n) {
                n2 = n;
                do {
                    return (boolean)n2;
                    break;
                } while (true);
            }
            n2 = 0;
            Object var7_9 = null;
            return (boolean)n2;
        }
        Class<Short> class_3 = Short.class;
        n2 = class_.isAssignableFrom(class_3);
        if (n2 != 0) {
            String string5 = (String)pair.second;
            n2 = Short.parseShort(string5);
            return (short)n2;
        }
        Class<Long> class_4 = Long.class;
        n2 = class_.isAssignableFrom(class_4);
        if (n2 != 0) {
            String string6 = (String)pair.second;
            long l = Long.parseLong(string6);
            return l;
        }
        Class<Integer> class_5 = Integer.class;
        n2 = (int)class_.isAssignableFrom(class_5) ? 1 : 0;
        if (n2 != 0) {
            String string7 = (String)pair.second;
            return Integer.valueOf(string7);
        }
        Class<String> class_6 = String.class;
        n2 = (int)class_.isAssignableFrom(class_6) ? 1 : 0;
        if (n2 == 0) return bl;
        return pair.second;
    }

    public void enableBassBoost(boolean bl) {
    }

    public void enableEqualizer(boolean bl) {
        DtsEffects$ParamBuilder dtsEffects$ParamBuilder = this.mBuilder;
        Comparable comparable = bl;
        String[] arrstring = TRUMEDIA_BANKS;
        dtsEffects$ParamBuilder.addParam("geq_int_enable", comparable, arrstring);
        dtsEffects$ParamBuilder = this.mBuilder;
        comparable = Integer.valueOf(0);
        arrstring = TRUMEDIA_BANKS;
        dtsEffects$ParamBuilder.addParam("geq_int_preset", comparable, arrstring);
        dtsEffects$ParamBuilder = this.mBuilder;
        comparable = bl;
        arrstring = TRUMEDIA_BANKS;
        dtsEffects$ParamBuilder.addParam("geq_ext_enable", comparable, arrstring);
        dtsEffects$ParamBuilder = this.mBuilder;
        comparable = Integer.valueOf(0);
        arrstring = TRUMEDIA_BANKS;
        dtsEffects$ParamBuilder.addParam("geq_ext_preset", comparable, arrstring);
        this.mBuilder.push();
    }

    public void enableTrebleBoost(boolean bl) {
    }

    public void enableVirtualizer(boolean bl) {
    }

    public int getBrand() {
        return 3;
    }

    public int getCenterFrequency(short s) {
        return CENTER_FREQS[s];
    }

    public int getEqualizerBandLevel(short s) {
        return this.mEqualizer[s] * 100;
    }

    public short[] getEqualizerBandLevelRange() {
        return EQUALIZER_BAND_LEVEL_RANGE;
    }

    public String getEqualizerPresetName(short s) {
        return null;
    }

    public short getNumEqualizerBands() {
        return 10;
    }

    public short getNumEqualizerPresets() {
        return 0;
    }

    public boolean hasBassBoost() {
        return true;
    }

    public boolean hasTrebleBoost() {
        return true;
    }

    public boolean hasVirtualizer() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate() {
        int n;
        int n2 = 1;
        Integer n3 = null;
        Object object = (AudioManager)this.mContext.getSystemService("audio");
        this.mAm = object;
        String[] arrstring = this.mAm;
        this.mBuilder = object = new Object((AudioManager)arrstring);
        object = this.getDevice();
        int n4 = object.getType();
        boolean bl = n4 == (n = 2);
        arrstring = this.mBuilder;
        String[] arrstring2 = "wowhd_trubass_mode";
        if (bl) {
            n4 = 0;
            object = null;
        } else {
            n4 = n2;
        }
        object = n4;
        String[] arrstring3 = WOWHDX_BANKS;
        arrstring = arrstring.addParam((String)arrstring2, object, arrstring3);
        arrstring2 = "wowhd_srs_speaker";
        if (bl) {
            n4 = 0;
            object = null;
        } else {
            n4 = n2;
        }
        object = n4;
        arrstring3 = WOWHDX_BANKS;
        arrstring.addParam((String)arrstring2, object, arrstring3).push();
        object = this.mBuilder;
        arrstring2 = (boolean)n2;
        arrstring3 = new String[]{};
        object.addParam("srs_processing_defersave", arrstring2, arrstring3);
        object = this.mBuilder;
        arrstring2 = false;
        arrstring3 = WOWHDX_BANKS;
        object.addParam("wowhd_skip", arrstring2, arrstring3);
        object = this.mBuilder;
        arrstring2 = 0;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_trubass_min", arrstring2, arrstring3);
        arrstring2 = n2;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_trubass_window", arrstring2, arrstring3);
        arrstring2 = 0;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_definition_min", arrstring2, arrstring3);
        arrstring2 = n2;
        arrstring3 = WOWHDX_BANKS;
        object.addParam("wowhd_definition_window", arrstring2, arrstring3);
        object = this.mBuilder;
        arrstring2 = (boolean)n2;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_trubass_enable", arrstring2, arrstring3);
        arrstring2 = 0;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_trubass_slide", arrstring2, arrstring3);
        arrstring2 = (boolean)n2;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_srs_enable", arrstring2, arrstring3);
        arrstring2 = 0;
        arrstring3 = WOWHDX_BANKS;
        object = object.addParam("wowhd_srs_space", arrstring2, arrstring3);
        Boolean bl2 = (boolean)n2;
        arrstring2 = WOWHDX_BANKS;
        object = object.addParam("wowhd_definition_enable", bl2, arrstring2);
        n3 = 0;
        arrstring = WOWHDX_BANKS;
        object.addParam("wowhd_definition_slide", n3, arrstring);
        this.mBuilder.push();
    }

    public void setBassBoostStrength(short s) {
        float f = (float)s / 1000.0f;
        DtsEffects$ParamBuilder dtsEffects$ParamBuilder = this.mBuilder;
        Float f2 = Float.valueOf(f);
        String[] arrstring = WOWHDX_BANKS;
        dtsEffects$ParamBuilder.addParam("wowhd_trubass_slide", f2, arrstring).push();
    }

    public void setEqualizerBandLevel(short s, float f) {
        float f2 = 100.0f;
        f /= f2;
        short[] arrs = this.mEqualizer;
        synchronized (arrs) {
            Object object = this.mEqualizer;
            short s2 = (short)f;
            object[s] = s2;
            object = this.mEqualizer;
            String[] arrstring = ",";
            String string2 = EqUtils.shortLevelsToString((short[])object, (String)arrstring);
            object = this.mBuilder;
            arrstring = GEQ_BANKS;
            object.addParam("geq_usergains", string2, arrstring);
            this.mBuilder.push();
            return;
        }
    }

    public void setEqualizerLevelsDecibels(float[] arrf) {
        short[] arrs = this.mEqualizer;
        synchronized (arrs) {
            Object object;
            int n = 0;
            do {
                int n2 = arrf.length;
                if (n >= n2) break;
                object = this.mEqualizer;
                float f = arrf[n];
                short s = (short)f;
                object[n] = s;
                ++n;
            } while (true);
            try {
                object = this.mEqualizer;
                String[] arrstring = ",";
                String string2 = EqUtils.shortLevelsToString((short[])object, (String)arrstring);
                object = this.mBuilder;
                arrstring = GEQ_BANKS;
                object.addParam("geq_usergains", string2, arrstring);
                this.mBuilder.push();
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    public void setGlobalEnabled(boolean bl) {
        DtsEffects$ParamBuilder dtsEffects$ParamBuilder = this.mBuilder;
        Boolean bl2 = bl;
        String[] arrstring = TRUMEDIA_BANKS;
        dtsEffects$ParamBuilder.addParam("trumedia_enable", bl2, arrstring).push();
    }

    public void setTrebleBoostStrength(short s) {
        float f = (float)s / 100.0f;
        DtsEffects$ParamBuilder dtsEffects$ParamBuilder = this.mBuilder;
        Float f2 = Float.valueOf(f);
        String[] arrstring = WOWHDX_BANKS;
        dtsEffects$ParamBuilder.addParam("wowhd_definition_slide", f2, arrstring).push();
    }

    public void setVirtualizerStrength(short s) {
        float f = (float)s / 1000.0f;
        DtsEffects$ParamBuilder dtsEffects$ParamBuilder = this.mBuilder;
        Float f2 = Float.valueOf(f);
        String[] arrstring = WOWHDX_BANKS;
        dtsEffects$ParamBuilder.addParam("wowhd_srs_space", f2, arrstring).push();
    }

    public void useEqualizerPreset(short s) {
    }
}

