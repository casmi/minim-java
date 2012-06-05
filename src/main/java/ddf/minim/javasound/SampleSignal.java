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

import org.tritonus.share.sampled.FloatSampleBuffer;

import ddf.minim.AudioSignal;
import ddf.minim.BufferedAudio;
import ddf.minim.Sound;
import ddf.minim.Triggerable;

class SampleSignal implements AudioSignal, Triggerable
{
	private FloatSampleBuffer	buffer;
	private int[]					marks;
	private int						markAt;

	public SampleSignal(FloatSampleBuffer samps)
	{
		buffer = samps;
		marks = new int[20];
		for (int i = 0; i < marks.length; i++)
		{
			marks[i] = -1;
		}
		markAt = 0;
	}

	public void generate(float[] signal)
	{
		//	 build our signal from all the marks
      for (int i = 0; i < marks.length; i++)
      {
        int begin = marks[i];
        if (begin == -1) 
        {
      	  continue;
        }
        
        //JSSound.debug("Sample trigger in process at marks[" + i + "] = " + marks[i]);
        int j, k;
        for (j = begin, k = 0; j < buffer.getSampleCount()
                            && k < signal.length; j++, k++)
        {
          signal[k] += buffer.getChannel(0)[j];
        }
        if ( j < buffer.getSampleCount() )
        {
          marks[i] = j;
        }
        else
        {
          //Sound.debug("Sample trigger ended.");
          marks[i] = -1;
        }
      }

	}

	public void generate(float[] left, float[] right)
	{
		//	 build our signal from all the marks
      for (int i = 0; i < marks.length; i++)
      {
        int begin = marks[i];
        if (begin == -1) 
        {
      	  continue;
        }
        
        //Sound.debug("Sample trigger in process at marks[" + i + "] = " + marks[i]);
        int j, k;
        for (j = begin, k = 0; j < buffer.getSampleCount() && k < left.length; j++, k++)
        {
          left[k] += buffer.getChannel(0)[j];
          right[k] += buffer.getChannel(1)[j];
        }
        if ( j < buffer.getSampleCount() )
        {
          marks[i] = j;
        }
        else
        {
          //Sound.debug("Sample trigger ended.");
          marks[i] = -1;
        }
      }

	}

	public void trigger()
	{
		marks[markAt] = 0;
		markAt++;
		if (markAt == marks.length)
		{
			markAt = 0;
		}

	}
  
  public void stop()
  {
    for(int i = 0; i < marks.length; ++i)
    {
      marks[i] = -1;
    }
  }

	public float[] getChannel(int channelNumber)
	{
		if ( channelNumber == BufferedAudio.LEFT )
		{
			return buffer.getChannel(0);
		}
		else if ( channelNumber == BufferedAudio.RIGHT )
		{
			return buffer.getChannel(1);
		}
		Sound.error("getChannel: Illegal channel number " + channelNumber);
		return null;
	}
}
