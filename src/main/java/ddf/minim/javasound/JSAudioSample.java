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

package ddf.minim.javasound;

import ddf.minim.AudioMetaData;
import ddf.minim.AudioSample;
import ddf.minim.spi.AudioSynthesizer;

final class JSAudioSample extends AudioSample
{
  private SampleSignal sample;
  private AudioMetaData meta;
  
  JSAudioSample(AudioMetaData mdata, SampleSignal ssig, AudioSynthesizer out)
  {
    super(out);
    sample = ssig;
    meta = mdata;
  }
  
  public void trigger()
  {
    sample.trigger();
  }
  
  public void stop()
  {
    sample.stop();
  }
  
  public float[] getChannel(int channelNumber)
  {
    return sample.getChannel(channelNumber);
  }

  public int length()
  {
    return meta.length();
  }
  
  public AudioMetaData getMetaData()
  {
	  return meta;
  }
}
