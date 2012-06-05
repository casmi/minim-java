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

package ddf.minim.spi;

import ddf.minim.AudioEffect;
import ddf.minim.AudioListener;

/**
 * An <code>AudioStream</code> is a stream of samples that is coming from 
 * somewhere. Users of an <code>AudioStream</code> don't really need to know
 * where the samples are coming from. However, typically they will be read 
 * from a <code>Line</code> or a file. An <code>AudioStream</code> needs to 
 * be opened before being used and closed when you are finished with it.
 * 
 * @author Damien Di Fede
 *
 */
public interface AudioStream extends AudioResource
{  
  /**
   * Set the AudioListener to receive samples from this source.
   * 
   * @param listener
   *          the AudioListener to receive samples
   */
  void setAudioListener(AudioListener listener);

  /**
   * Set the AudioEffect to apply to this stream.
   * 
   * @param effect
   *          the AudioEffect to apply to the stream
   */
  void setAudioEffect(AudioEffect effect);
  
  /**
   * The size of the buffer that will be sent to listeners and effects.
   * 
   * @return the size of the buffer sent to listeners
   */
  int bufferSize();
}
