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

import ddf.minim.signals.Oscillator;
/**
 * A pulse wave is a square wave whose peaks and valleys are different length.
 * The pulse width of a pulse wave is how wide the peaks.
 * 
 * @author Damien Di Fede
 * @see <a href="http://en.wikipedia.org/wiki/Pulse_wave">Pulse Wave</a>
 */
public class PulseWave extends Oscillator
{
  private float width;

  /**
   * Constructs a pulse wave with the given frequency, amplitude and sample
   * rate.
   * 
   * @param frequency
   *          the frequency of the pulse wave
   * @param amplitude
   *          the amplitude of the pulse wave
   * @param sampleRate
   *          the sample rate of the pulse wave
   */
  public PulseWave(float frequency, float amplitude, float sampleRate)
  {
    super(frequency, amplitude, sampleRate);
    // duty period is 1:width
    width = 2;
  }

  /**
   * Sets the pulse width of the pulse wave.
   * 
   * @param w
   *          the new pulse width, this will be constrained to [1, 30]
   */
  public void setPulseWidth(float w)
  {
    width = (float) Oscillator.constrain(w, 1, 30);
  }

  /**
   * Returns the current pulse width.
   * 
   * @return the current pulse width
   */
  public float getPulseWidth()
  {
    return width;
  }

  protected float value(float step)
  {
    float v = 0;
    if (step < 1 / (width + 1))
      v = 1;
    else
      v = -1;
    return v;
  }

}
