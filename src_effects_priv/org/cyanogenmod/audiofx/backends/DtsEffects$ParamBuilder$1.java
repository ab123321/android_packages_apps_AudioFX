/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  android.media.AudioManager
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package com.cyngn.audiofx.backends;

import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import com.cyngn.audiofx.backends.DtsEffects$ParamBuilder;
import java.util.Map;

class DtsEffects$ParamBuilder$1
implements Handler.Callback {
    final /* synthetic */ DtsEffects$ParamBuilder this$1;

    DtsEffects$ParamBuilder$1(DtsEffects$ParamBuilder paramBuilder) {
        this.this$1 = paramBuilder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message message) {
        Object object;
        String string2 = null;
        int n = message.what;
        switch (n) {
            case 1: {
                object = this.this$1;
                Map map = DtsEffects$ParamBuilder.cfr_renamed_5((DtsEffects$ParamBuilder)object);
                synchronized (map) {
                    object = this.this$1;
                    object = DtsEffects$ParamBuilder.cfr_renamed_5((DtsEffects$ParamBuilder)object);
                    string2 = DtsEffects$ParamBuilder.cfr_renamed_0((Map)object);
                    object = this.this$1;
                    object = DtsEffects$ParamBuilder.cfr_renamed_2((DtsEffects$ParamBuilder)object);
                    Object object2 = this.this$1;
                    object2 = DtsEffects$ParamBuilder.cfr_renamed_5((DtsEffects$ParamBuilder)object2);
                    object.putAll(object2);
                    object = this.this$1;
                    object = DtsEffects$ParamBuilder.cfr_renamed_5((DtsEffects$ParamBuilder)object);
                    object.clear();
                }
            }
        }
        if (string2 == null) return true;
        n = (int)string2.isEmpty() ? 1 : 0;
        if (n != 0) {
            return true;
        }
        object = this.this$1.mAudioManager;
        object.setParameters(string2);
        return true;
    }
}

