/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  android.media.AudioDeviceInfo
 *  android.util.Log
 */
package com.cyngn.audiofx.backends;

import android.media.AudioDeviceInfo;
import android.util.Log;
import com.cyngn.audiofx.activity.MasterConfigControl;
import com.cyngn.audiofx.backends.EffectSetWithAndroidEq;
import com.cyngn.audiofx.backends.MaxxAudioEffects$MaxxEffect;
import java.util.BitSet;

class MaxxAudioEffects
extends EffectSetWithAndroidEq {
    private static final int MAAP_CENTER_ACTIVE = 150;
    private static final int MAAP_CENTER_GAIN_CENTER = 151;
    private static final int MAAP_IVOLUME_ACTIVE = 20;
    private static final int MAAP_MAXX_3D_ACTIVE = 13;
    private static final int MAAP_MAXX_3D_EFFECT = 10;
    private static final int MAAP_MAXX_3D_LOW_FREQUENCY = 12;
    private static final int MAAP_MAXX_3D_SPAN = 11;
    private static final int MAAP_MAXX_BASS_ACTIVE = 6;
    private static final int MAAP_MAXX_BASS_EFFECT = 4;
    private static final int MAAP_MAXX_BASS_ORIGINAL_BASS = 37;
    private static final int MAAP_MAXX_HF_ACTIVE = 9;
    private static final int MAAP_MAXX_HF_EFFECT = 7;
    private static final int MAXXBASS = 0;
    private static final int MAXXSPACE = 2;
    private static final int MAXXTREBLE = 1;
    private static final int MAXXVOLUME = 3;
    private static final short PRESET_BLUETOOTH = 6;
    private static final short PRESET_CAST = 6;
    private static final short PRESET_HEADSET = 2;
    private static final short PRESET_LINE_OUT = 6;
    private static final short PRESET_SPEAKER = 1;
    private static final short PRESET_USB = 6;
    private static final String TAG = "AudioFx-MaxxAudio";
    private int mBypassed;
    private MaxxAudioEffects$MaxxEffect mMaxxEffect;
    private final BitSet mSubEffectBits;

    public MaxxAudioEffects(int n, AudioDeviceInfo audioDeviceInfo) {
        BitSet bitSet;
        super(n, audioDeviceInfo);
        this.mSubEffectBits = bitSet = new BitSet();
        this.mBypassed = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean enableSubEffect(int n, boolean bl) {
        int n2 = -1;
        switch (n) {
            case 0: {
                n2 = 6;
                break;
            }
            case 1: {
                n2 = 9;
                break;
            }
            case 2: {
                n2 = 13;
                break;
            }
            case 3: {
                n2 = 20;
            }
        }
        if (n2 < 0) {
            return false;
        }
        double d = bl ? 1.0 : 0.0;
        boolean bl2 = this.setParameterSafe(n2, d);
        if (!bl2) return false;
        this.mSubEffectBits.set(n, bl);
        return true;
    }

    private short getWavesPresetForDevice(AudioDeviceInfo audioDeviceInfo) {
        String string2;
        short s = 6;
        String string3 = MasterConfigControl.getDeviceIdentifierString(audioDeviceInfo);
        boolean bl = string3.equals(string2 = "headset");
        if (bl) {
            return 2;
        }
        string2 = "lineout";
        bl = string3.equals(string2);
        if (bl) {
            return s;
        }
        string2 = "bluetooth";
        bl = string3.startsWith(string2);
        if (bl) {
            return s;
        }
        string2 = "wireless";
        bl = string3.startsWith(string2);
        if (bl) {
            return s;
        }
        string2 = "usb";
        bl = string3.startsWith(string2);
        if (bl) {
            return s;
        }
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean setParameterSafe(int n, double d) {
        synchronized (this) {
            Object object = this.mMaxxEffect;
            if (object == null) {
                object = "AudioFx-MaxxAudio";
                String string2 = "MaxxEffect went away!";
                Log.e((String)object, (String)string2);
                return false;
            }
            try {
                object = this.mMaxxEffect;
                AudioDeviceInfo audioDeviceInfo = this.getDevice();
                short s = this.getWavesPresetForDevice(audioDeviceInfo);
                short s2 = 2;
                object.setPresetParameter(n, d, s, s2);
                return true;
            }
            catch (Exception exception) {
                object = "AudioFx-MaxxAudio";
                CharSequence charSequence = new CharSequence();
                String string3 = "Unable to set parameter + ";
                charSequence = charSequence.append(string3);
                charSequence = charSequence.append(n);
                string3 = " value=";
                charSequence = charSequence.append(string3);
                charSequence = charSequence.append(d);
                charSequence = charSequence.toString();
                Log.e((String)object, (String)charSequence, (Throwable)exception);
                return false;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateDeviceSpecificParameters() {
        int n;
        int n2;
        double d = 1.0;
        double d2 = 0.0;
        Object object = this.getDevice();
        boolean bl = object != null ? (n2 = (object = this.getDevice()).getType()) == (n = 2) : true;
        object = this.mSubEffectBits;
        n = 0;
        n2 = (int)object.get(0) ? 1 : 0;
        boolean bl2 = n2 == 0 || !bl;
        double d3 = bl2 ? d : d2;
        int n3 = 37;
        this.setParameterSafe(n3, d3);
        if (!bl) {
            d2 = 6.0;
        }
        n2 = 12;
        this.setParameterSafe(n2, d2);
        if (!bl) {
            d = 2.0;
        }
        this.setParameterSafe(11, d);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean beginUpdate() {
        MaxxAudioEffects$MaxxEffect maxxAudioEffects$MaxxEffect = this.mMaxxEffect;
        if (maxxAudioEffects$MaxxEffect == null) return false;
        return super.beginUpdate();
    }

    public boolean commitUpdate() {
        MaxxAudioEffects$MaxxEffect maxxAudioEffects$MaxxEffect = this.mMaxxEffect;
        if (maxxAudioEffects$MaxxEffect != null) {
            this.updateDeviceSpecificParameters();
            return super.commitUpdate();
        }
        return false;
    }

    public void enableBassBoost(boolean bl) {
        this.enableSubEffect(0, bl);
    }

    public void enableTrebleBoost(boolean bl) {
        this.enableSubEffect(1, bl);
    }

    public void enableVirtualizer(boolean bl) {
        this.enableSubEffect(2, bl);
    }

    public void enableVolumeBoost(boolean bl) {
        this.enableSubEffect(3, bl);
    }

    public int getBrand() {
        return 2;
    }

    public int getReleaseDelay() {
        return 8000;
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

    public boolean hasVolumeBoost() {
        return true;
    }

    protected void onCreate() {
        MaxxAudioEffects$MaxxEffect maxxAudioEffects$MaxxEffect;
        short s = this.mSessionId;
        this.mMaxxEffect = maxxAudioEffects$MaxxEffect = new MaxxAudioEffects$MaxxEffect(100, s);
        this.mMaxxEffect.setSoundMode(2);
        maxxAudioEffects$MaxxEffect = this.mMaxxEffect;
        AudioDeviceInfo audioDeviceInfo = this.getDevice();
        s = this.getWavesPresetForDevice(audioDeviceInfo);
        maxxAudioEffects$MaxxEffect.setOutputMode(s);
        super.onCreate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        synchronized (this) {
            super.release();
            Object object = this.mMaxxEffect;
            if (object != null) {
                String string2;
                try {
                    object = this.mMaxxEffect;
                    string2 = null;
                    object.setEnabled(false);
                }
                catch (Exception exception) {
                    object = "AudioFx-MaxxAudio";
                    string2 = "Error disabling MaxxEffects!";
                    Log.e((String)object, (String)string2, (Throwable)exception);
                }
                try {
                    object = this.mMaxxEffect;
                    object.release();
                }
                catch (Exception exception) {
                    object = "AudioFx-MaxxAudio";
                    string2 = "Error releasing MaxxEffects!";
                    Log.e((String)object, (String)string2, (Throwable)exception);
                }
                object = null;
                this.mMaxxEffect = null;
            }
            return;
        }
    }

    public void setBassBoostStrength(short s) {
        double d = (double)s / 10.0;
        this.setParameterSafe(4, d);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDevice(AudioDeviceInfo audioDeviceInfo) {
        synchronized (this) {
            super.setDevice(audioDeviceInfo);
            Object object = this.mSubEffectBits;
            object.clear();
            try {
                object = this.mMaxxEffect;
                object.clearUserParameters();
                object = this.mMaxxEffect;
                short s = this.getWavesPresetForDevice(audioDeviceInfo);
                object.setOutputMode(s);
            }
            catch (Exception exception) {
                object = "AudioFx-MaxxAudio";
                String string2 = "Error sending WAVESFX commands!";
                Log.e((String)object, (String)string2, (Throwable)exception);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setGlobalEnabled(boolean bl) {
        synchronized (this) {
            int n = bl ? 0 : 1;
            try {
                int n2 = this.mBypassed;
                if (n2 != n) {
                    MaxxAudioEffects$MaxxEffect maxxAudioEffects$MaxxEffect;
                    if (!bl) {
                        maxxAudioEffects$MaxxEffect = this.mMaxxEffect;
                        maxxAudioEffects$MaxxEffect.clearUserParameters();
                    }
                    MaxxAudioEffects$MaxxEffect maxxAudioEffects$MaxxEffect2 = this.mMaxxEffect;
                    if (bl) {
                        n2 = 0;
                        maxxAudioEffects$MaxxEffect = null;
                    } else {
                        n2 = 1;
                    }
                    maxxAudioEffects$MaxxEffect2.setBypass((boolean)n2);
                    this.mBypassed = n;
                }
            }
            catch (Exception exception) {
                String string2 = "AudioFx-MaxxAudio";
                String string3 = "Unable to set effects enabled!";
                Log.e((String)string2, (String)string3, (Throwable)exception);
            }
            super.setGlobalEnabled(bl);
            return;
        }
    }

    public void setTrebleBoostStrength(short s) {
        double d = s;
        this.setParameterSafe(7, d);
    }

    public void setVirtualizerStrength(short s) {
        double d = (double)s / 10.0;
        this.setParameterSafe(10, d);
    }
}

