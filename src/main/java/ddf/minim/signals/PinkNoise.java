/*
 *   minim-java
 *   https://github.com/casmi/minim-java/
 *   Copyright (C) 2012, Xcoo, Inc.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 *  The original code are licensed under the GNU Lesser General Public License.
 *  
 *   minim
 *   http://code.compartmental.net/tools/minim/
 *   Copyright (c) 2007 - 2009 by Damien Di Fede <ddf@compartmental.net>
 */

package ddf.minim.signals;

import ddf.minim.AudioSignal;

/**
 * PinkNoise generates a pink noise signal.
 * 
 * @author Damien Di Fede
 * @see <a href="http://en.wikipedia.org/wiki/Pink_noise">Pink Noise</a>
 * 
 */
public class PinkNoise implements AudioSignal
{
  protected float amp;
  protected float pan;
  protected float leftScale, rightScale;

  /**
   * Constructs a pink noise signal with an amplitude of 1.
   * 
   */
  public PinkNoise()
  {
    amp = 1;
    pan = 0;
    leftScale = rightScale = 1;
    initPink();
  }

  /**
   * Constructs a pink noise signal with an amplitude of <code>amp</code>.
   * <code>amp</code> should be between 0 and 1.
   * 
   * @param amp
   */
  public PinkNoise(float amp)
  {
    setAmp(amp);
    pan = 0;
    leftScale = rightScale = 1;
    initPink();
  }

  /**
   * Sets the amplitude of the signal to <code>a</code>.
   * 
   * @param a
   *          the new amplitude, it will be constrained to [0, 1].
   */
  public void setAmp(float a)
  {
    amp = (float) Oscillator.constrain(a, 0, 1);
  }

  /**
   * Sets the pan of the signal to <code>p</code>.
   * 
   * @param p
   *          the new pan, it will be constrained to [-1, 1]
   */
  public void setPan(float p)
  {
    pan = (float) Oscillator.constrain(p, -1, 1);
    calcLRScale();
  }

  public void generate(float[] signal)
  {
    for (int i = 0; i < signal.length; i++)
    {
      signal[i] = amp * pink();
    }
  }

  public void generate(float[] left, float[] right)
  {
    for (int i = 0; i < left.length; i++)
    {
      left[i] = leftScale * amp * pink();
      right[i] = rightScale * amp * pink();
    }
  }

  // This is the Voss algorithm for creating pink noise

  private int maxKey, key, range;
  private float whiteValues[];
  private float maxSumEver;

  private void initPink()
  {
    maxKey = 0x1f;
    range = 128;
    maxSumEver = 90;
    key = 0;
    whiteValues = new float[6];
    for (int i = 0; i < 6; i++)
      whiteValues[i] = ((float) Math.random() * Long.MAX_VALUE) % (range / 6);
  }

  // return a pink noise value
  private float pink()
  {
    int last_key = key;
    float sum;

    key++;
    if (key > maxKey) key = 0;
    // Exclusive-Or previous value with current value. This gives
    // a list of bits that have changed.
    int diff = last_key ^ key;
    sum = 0;
    for (int i = 0; i < 6; i++)
    {
      // If bit changed get new random number for corresponding
      // white_value
      if ((diff & (1 << i)) != 0)
      {
        whiteValues[i] = ((float) Math.random() * Long.MAX_VALUE) % (range / 6);
      }
      sum += whiteValues[i];
    }
    if (sum > maxSumEver) maxSumEver = sum;
    sum = 2f * (sum / maxSumEver) - 1f;
    return sum;
  }

  private void calcLRScale()
  {
    if (pan <= 0)
    {
      rightScale =  Oscillator.map(pan, -1, 0, 0, 1);
      leftScale = 1;
    }
    if (pan >= 0)
    {
      leftScale =  Oscillator.map(pan, 0, 1, 1, 0);
      rightScale = 1;
    }
    if (pan == 0)
    {
      leftScale = rightScale = 1;
    }
  }

}
