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

/**
 * A triangle wave oscillates between 1 and -1 following a straight line.
 * 
 * @author Damien Di Fede
 * @see <a href="http://en.wikipedia.org/wiki/Triangle_wave">Triangle Wave</a>
 */

public class TriangleWave extends Oscillator 
{
  /**
   * Constructs a triangle wave with the given frequency, amplitude and sample
   * rate.
   * 
   * @param frequency
   *          the frequency of the pulse wave
   * @param amplitude
   *          the amplitude of the pulse wave
   * @param sampleRate
   *          the sample rate of the pulse wave
   */
	public TriangleWave(float frequency, float amplitude, float sampleRate) 
	{
	  super(frequency, amplitude, sampleRate);
	}

	protected float value(float step) 
	{
	  return 1 - 4 * Math.abs(Math.round(step) - step);
	}

}
