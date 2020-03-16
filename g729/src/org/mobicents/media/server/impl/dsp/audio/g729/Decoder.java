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




public class Decoder  {

   
    int frame = 0;
    CircularBuffer circular = new CircularBuffer(16000);
    float synth_buf[] = new float[LD8KConstants.L_FRAME + LD8KConstants.M]; /* Synthesis */

    int synth;
    int parm[] = new int[LD8KConstants.PRM_SIZE + 1]; /*
     * Synthesis parameters +
     * BFI
     */

    short serial[] = new short[LD8KConstants.SERIAL_SIZE]; /* Serial stream */

    float Az_dec[] = new float[2 * LD8KConstants.MP1];
    int ptr_Az; /* Decoded Az for post-filter */

    IntegerPointer t0_first = new IntegerPointer();
    float pst_out[] = new float[LD8KConstants.L_FRAME]; /* postfilter output */

    int voicing; /* voicing for previous subframe */

    IntegerPointer sf_voic = new IntegerPointer(0); /* voicing for subframe */

    DecLD8K decLD = new DecLD8K();
    PostFil postFil = new PostFil();
    PostPro postPro = new PostPro();    
    public Decoder() {
        for (int i = 0; i < LD8KConstants.M; i++) {
            synth_buf[i] = (float) 0.0;
        }
        synth = 0 + LD8KConstants.M;

        decLD.init_decod_ld8k();
        postFil.init_post_filter();
        postPro.init_post_process();
        voicing = 60;
    }


    /**
     * Perform compression.
     * 
     * @param input
     *            media
     * @return compressed media.
     */
    public byte[] process(byte[] media) {
        serial = Bits.fromRealBits(media);
        // serial = Util.byteArrayToShortArray(media);
        frame++;
        Bits.bits2prm_ld8k(serial, 2, parm, 1);

        /*
         * the hardware detects frame erasures by checking if all bits are set
         * to zero
         */
        parm[0] = 0; /* No frame erasure */
        for (int i = 2; i < LD8KConstants.SERIAL_SIZE; i++) {
            if (serial[i] == 0) {
                parm[0] = 1; /* frame erased */

            /* check parity and put 1 in parm[4] if parity error */
            }
        }
        parm[4] = PParity.check_parity_pitch(parm[3], parm[4]);

        decLD.decod_ld8k(parm, 0, voicing, synth_buf, synth, Az_dec, t0_first); /* Decoder */

        /* Post-filter and decision on voicing parameter */
        voicing = 0;
        ptr_Az = 0;// Az_dec;
        for (int i = 0; i < LD8KConstants.L_FRAME; i += LD8KConstants.L_SUBFR) {
            postFil.post(t0_first.value, synth_buf, synth + i, Az_dec, ptr_Az,
                    pst_out, i, sf_voic);
            if (sf_voic.value != 0) {
                voicing = sf_voic.value;
            }
            ptr_Az += LD8KConstants.MP1;
        }
        Util.copy(synth_buf, LD8KConstants.L_FRAME, synth_buf, 0,
                LD8KConstants.M);

        postPro.post_process(pst_out, LD8KConstants.L_FRAME);

        return Util.floatArrayToByteArray(pst_out, LD8KConstants.L_FRAME);
    }

}
