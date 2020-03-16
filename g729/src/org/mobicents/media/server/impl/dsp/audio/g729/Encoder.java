/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.media.server.impl.dsp.audio.g729;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Encoder  {

  
    int frame = 0;
    CodLD8K encoder = new CodLD8K();
    PreProc preProc = new PreProc();
    CircularBuffer circularBuffer = new CircularBuffer(32000);
    int prm[] = new int[LD8KConstants.PRM_SIZE];
    short serial[] = new short[LD8KConstants.SERIAL_SIZE];

    /* For Debugging Only */
    FileInputStream testData = null;
    FileOutputStream outdbg = null;

    public Encoder() {
        preProc.init_pre_process();
        encoder.init_coder_ld8k();
        try {
            // testData = new FileInputStream("speech-java.bit.itu");
            // testData = new FileInputStream("french.in");
            // outdbg = new FileOutputStream("/home/vralev/speech-dbg.bit");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Perform compression.
     * 
     * @param input
     *            media
     * @return compressed media.
     */
    public byte[] process(byte[] media) {
        frame++;

        float[] new_speech = new float[media.length];
        short[] shortMedia = Util.byteArrayToShortArray(media);
        for (int i = 0; i < LD8KConstants.L_FRAME; i++) {
            new_speech[i] = (float) shortMedia[i];
        }
        preProc.pre_process(new_speech, LD8KConstants.L_FRAME);

        encoder.loadSpeech(new_speech);
        encoder.coder_ld8k(prm, 0);

        //byte[] a = new byte[10];
        Bits.prm2bits_ld8k(prm, serial);
        // return a;
        return Bits.toRealBits(serial);
    }

    /* These methods are just for debugging */

}
