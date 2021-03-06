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
 * LowPassSP is a single pole low pass filter. It is not super great, but it gets the job 
 * done.
 * 
 * @author Damien Di Fede
 *
 */
public class LowPassSP extends IIRFilter 
{
  /**
   * Constructs a low pass filter with a cutoff frequency of <code>freq</code>
   * that will be used to filter audio recorded at <code>sampleRate</code>.
   * 
   * @param freq
   *          the cutoff frequency
   * @param sampleRate
   *          the sample rate of the audio that will be filtered
   */
	public LowPassSP(float freq, float sampleRate) 
	{
	  super(freq, sampleRate);
	}

	protected void calcCoeff() 
	{
    float fracFreq = frequency()/sampleRate();
	  float x = (float)Math.exp(-2*Math.PI*fracFreq);
	  a = new float[] { 1 - x };
	  b = new float[] { x };
	}
}
