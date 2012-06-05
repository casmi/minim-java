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

package ddf.minim;

/**
 * An <code>AudioEffect</code> is anything that can process one or two float
 * arrays. Typically it is going to be some kind of time-based process because
 * the float arrays passed to it will be consecutive chunks of audio data. The
 * effect is expected to modify these arrays in such a way that the values
 * remain in the range [-1, 1]. All of the effects included with Minim implement
 * this interface and all you need to do to write your own effects is to create
 * a class that implements this interface and then add an instance of it to an
 * anything that is {@link Effectable}, such as an {@link AudioOutput}.
 * 
 * @author Damien Di Fede
 * 
 */
public interface AudioEffect
{ 
  /**
   * Processes <code>signal</code> in some way.
   * 
   * @param signal
   *          an array of audio samples, representing a mono sound stream.
   */
  void process(float[] signal);

  /**
   * Processes <code>sigLeft</code> and <code>sigRight</code> in some way.
   * 
   * @param sigLeft
   *          an array of audio samples, representing the left channel of a
   *          stereo sound stream
   * @param sigRight
   *          an array of audio samples, representing the right channel of a
   *          stereo sound stream
   */
  void process(float[] sigLeft, float[] sigRight);
}
