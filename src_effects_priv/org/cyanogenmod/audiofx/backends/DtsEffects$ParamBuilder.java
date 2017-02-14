/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  android.media.AudioManager
 *  android.os.Handler
 *  android.os.Handler$Callback
 */
package com.cyngn.audiofx.backends;

import android.media.AudioManager;
import android.os.Handler;
import com.cyngn.audiofx.backends.DtsEffects$ParamBuilder$1;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class DtsEffects$ParamBuilder {
    private static final int MSG_DO_DELAYED_DELTA_PUSH = 1;
    private final Map mAppliedEffects;
    AudioManager mAudioManager;
    Handler mHandler;
    private final Map mPendingEffects;

    static /* synthetic */ Map cfr_renamed_2(DtsEffects$ParamBuilder dtsEffects$ParamBuilder) {
        return dtsEffects$ParamBuilder.mAppliedEffects;
    }

    static /* synthetic */ Map cfr_renamed_5(DtsEffects$ParamBuilder dtsEffects$ParamBuilder) {
        return dtsEffects$ParamBuilder.mPendingEffects;
    }

    static /* synthetic */ String cfr_renamed_0(Map map) {
        return DtsEffects$ParamBuilder.buildCommand(map);
    }

    public DtsEffects$ParamBuilder(AudioManager audioManager) {
        Handler handler;
        this.mPendingEffects = handler = new Handler();
        this.mAppliedEffects = handler = new Handler();
        DtsEffects$ParamBuilder$1 dtsEffects$ParamBuilder$1 = new DtsEffects$ParamBuilder$1(this);
        this.mHandler = handler = new Handler((Handler.Callback)dtsEffects$ParamBuilder$1);
        this.mAudioManager = audioManager;
    }

    private static String buildCommand(Map map) {
        boolean bl;
        StringBuilder stringBuilder = new StringBuilder();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (bl = iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            CharSequence charSequence = (String)entry.getKey();
            CharSequence charSequence2 = stringBuilder.append((String)charSequence).append("=");
            charSequence = (String)entry.getValue();
            charSequence = charSequence2.append((String)charSequence);
            charSequence2 = ";";
            charSequence.append((String)charSequence2);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String formatValue(Object object) {
        Object[] arrobject;
        Class<Boolean> class_ = object.getClass();
        boolean bl = class_.isAssignableFrom(arrobject = Boolean.class);
        if (!bl) {
            Class<Boolean> class_2 = object.getClass();
            bl = class_2.isAssignableFrom(arrobject = Float.class);
            if (!bl) return String.valueOf(object);
            String string2 = "%.2f";
            int n = 1;
            arrobject = new Object[n];
            arrobject[0] = object = (Float)object;
            object = String.format(string2, arrobject);
            return String.valueOf(object);
        }
        bl = (object = (Boolean)object).booleanValue();
        if (bl) {
            object = "1";
            return String.valueOf(object);
        }
        object = "0";
        return String.valueOf(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean valueChangedLocked(String string2, String string3) {
        boolean bl = true;
        Object object = this.mAppliedEffects;
        boolean bl2 = object.containsKey(string2);
        if (!bl2) return bl;
        object = (String)this.mAppliedEffects.get(string2);
        bl2 = object.equals(string3);
        if (!bl2) return bl;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ DtsEffects$ParamBuilder addParam(String string2, Object object, String ... arrstring) {
        String string3 = DtsEffects$ParamBuilder.formatValue(object);
        Map map = this.mPendingEffects;
        synchronized (map) {
            if (arrstring == null) {
                boolean bl = this.valueChangedLocked(string2, string3);
                if (bl) {
                    Map map2 = this.mPendingEffects;
                    map2.put(string2, string3);
                }
            } else {
                Object var7_9 = null;
                for (String string4 : arrstring) {
                    Object object2 = new Object();
                    object2 = object2.append(string4);
                    String string5 = ":";
                    object2 = object2.append(string5);
                    String string6 = (object2 = object2.append(string2)).toString();
                    boolean bl = this.valueChangedLocked(string6, string3);
                    if (!bl) continue;
                    object2 = this.mPendingEffects;
                    object2.put(string6, string3);
                }
            }
            return this;
        }
    }

    public DtsEffects$ParamBuilder push() {
        Handler handler = this.mHandler;
        int n = 1;
        boolean bl = handler.hasMessages(n);
        if (!bl) {
            handler = this.mHandler;
            handler.sendEmptyMessage(n);
        }
        return this;
    }
}

