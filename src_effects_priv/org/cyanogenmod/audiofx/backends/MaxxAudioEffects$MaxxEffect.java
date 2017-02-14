/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  android.media.audiofx.AudioEffect
 */
package com.cyngn.audiofx.backends;

import android.media.audiofx.AudioEffect;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

class MaxxAudioEffects$MaxxEffect
extends AudioEffect {
    private static final int SL_CMD_WAVESFX_AF_DEVICE_DISABLE = 65562;
    private static final int SL_CMD_WAVESFX_AF_DEVICE_ENABLE = 65561;
    private static final int SL_CMD_WAVESFX_CLEAR_PARAMETERS = 65545;
    private static final int SL_CMD_WAVESFX_PRESET_GET_PARAMETER = 65550;
    private static final int SL_CMD_WAVESFX_PRESET_SET_PARAMETER = 65549;
    private static final int SL_CMD_WAVESFX_SET_OUTDEVICE = 65544;
    private static final int SL_CMD_WAVESFX_SET_SMOOTHING = 65558;
    private static final int SL_CMD_WAVESFX_SET_SOUNDMODE = 65539;
    private static final short SOUNDMODE_MUSIC = 2;
    private static final short SOUNDMODE_RINGTONE = 0;
    private static final short SOUNDMODE_VOICE = 1;

    public MaxxAudioEffects$MaxxEffect(int n, int n2) {
        UUID uUID = UUID.fromString("a122acc0-5943-11e0-acd3-0002a5d5c51b");
        UUID uUID2 = EFFECT_TYPE_NULL;
        super(uUID, uUID2, n, n2);
        this.setDeviceDetectionEnabled(false);
    }

    private byte[] doubleToByteArray(double d) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        byteBuffer.order(byteOrder);
        byteBuffer.putDouble(d);
        return byteBuffer.array();
    }

    /*
     * Enabled aggressive block sorting
     */
    private int setDeviceDetectionEnabled(boolean bl) {
        int n = 4;
        byte[] arrby = new byte[n];
        n = bl ? 65561 : 65562;
        byte[] arrby2 = new byte[]{};
        n = this.command(n, arrby2, arrby);
        this.checkStatus(n);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby);
    }

    public int clearUserParameters() {
        byte[] arrby = new byte[4];
        byte[] arrby2 = new byte[]{};
        int n = this.command(65545, arrby2, arrby);
        this.checkStatus(n);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int setBypass(boolean bl) {
        int n = 4;
        int n2 = 1;
        int n3 = 2;
        byte[] arrby = new byte[n];
        byte[] arrby2 = new byte[n];
        byte[] arrby3 = MaxxAudioEffects$MaxxEffect.shortToByteArray((short)n2);
        System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)0, (int)n3);
        if (!bl) {
            n2 = 0;
        }
        System.arraycopy((byte[])MaxxAudioEffects$MaxxEffect.shortToByteArray((short)n2), (int)0, (byte[])arrby2, (int)n3, (int)n3);
        n2 = this.command(65558, arrby2, arrby);
        this.checkStatus(n2);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby);
    }

    public int setEnabled(boolean bl) {
        return super.setEnabled(bl);
    }

    public int setOutputMode(short s) {
        byte[] arrby = new byte[4];
        byte[] arrby2 = MaxxAudioEffects$MaxxEffect.shortToByteArray((short)s);
        int n = this.command(65544, arrby2, arrby);
        this.checkStatus(n);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby);
    }

    public int setParameter(int n, double d) {
        byte[] arrby = MaxxAudioEffects$MaxxEffect.intToByteArray((int)n);
        byte[] arrby2 = this.doubleToByteArray(d);
        return this.setParameter(arrby, arrby2);
    }

    public int setPresetParameter(int n, double d, short s, short s2) {
        int n2 = 2;
        int n3 = 4;
        byte[] arrby = new byte[16];
        byte[] arrby2 = new byte[n3];
        System.arraycopy((byte[])MaxxAudioEffects$MaxxEffect.intToByteArray((int)n), (int)0, (byte[])arrby, (int)0, (int)n3);
        System.arraycopy((byte[])this.doubleToByteArray(d), (int)0, (byte[])arrby, (int)n3, (int)8);
        System.arraycopy((byte[])MaxxAudioEffects$MaxxEffect.shortToByteArray((short)s), (int)0, (byte[])arrby, (int)12, (int)n2);
        System.arraycopy((byte[])MaxxAudioEffects$MaxxEffect.shortToByteArray((short)s2), (int)0, (byte[])arrby, (int)14, (int)n2);
        int n4 = this.command(65549, arrby, arrby2);
        this.checkStatus(n4);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby2);
    }

    public int setSoundMode(short s) {
        byte[] arrby = new byte[4];
        byte[] arrby2 = MaxxAudioEffects$MaxxEffect.shortToByteArray((short)s);
        int n = this.command(65539, arrby2, arrby);
        this.checkStatus(n);
        return MaxxAudioEffects$MaxxEffect.byteArrayToInt((byte[])arrby);
    }
}

