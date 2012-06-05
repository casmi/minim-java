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

package ddf.minim.effects;

/**
 * A band pass filter is a filter that filters out all frequencies except for
 * those in a band centered on the current frequency of the filter.
 * 
 * @author Damien Di Fede
 * 
 */
public class BandPass extends IIRFilter
{
  private float bw;

  /**
   * Constructs a band pass filter with the requested center frequency,
   * bandwidth and sample rate.
   * 
   * @param freq
   *          the center frequency of the band to pass (in Hz)
   * @param bandWidth
   *          the width of the band to pass (in Hz)
   * @param sampleRate
   *          the sample rate of audio that will be filtered by this filter
   */
  public BandPass(float freq, float bandWidth, float sampleRate)
  {
    super(freq, sampleRate);
    setBandWidth(bandWidth);
  }

  /**
   * Sets the band width of the filter. Doing this will cause the coefficients
   * to be recalculated.
   * 
   * @param bandWidth
   *          the band width (in Hz)
   */
  public void setBandWidth(float bandWidth)
  {
    bw = bandWidth / sampleRate();
    calcCoeff();
  }

  /**
   * Returns the band width of this filter.
   * 
   * @return the band width (in Hz)
   */
  public float getBandWidth()
  {
    return bw * sampleRate();
  }

  protected void calcCoeff()
  {
    float R = 1 - 3 * bw;
    float fracFreq = frequency() / sampleRate();
    float T = 2 * (float) Math.cos(2 * Math.PI * fracFreq);
    float K = (1 - R * T + R * R) / (2 - T);
    a = new float[] { 1 - K, (K - R) * T, R * R - K };
    b = new float[] { R * T, -R * R };
  }
}
