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

import ddf.minim.AudioListener;
import ddf.minim.AudioSignal;
import ddf.minim.Sound;

/**
 * <code>Oscillator</code> is an implementation of an <code>AudioSignal</code>
 * that handles most of the work associated with an oscillatory signal like a
 * sine wave. To create your own oscillator you must extend
 * <code>Oscillator</code> and implement the {@link #value(float) value}
 * method. <code>Oscillator</code> will call this method every time it needs
 * to sample your waveform. The number passed to the method is an offset from
 * the beginning of the waveform's period and should be used to sample your
 * waveform at that point.
 * 
 * @author Damien Di Fede
 * 
 */
public abstract class Oscillator implements AudioSignal
{
  /** The float value of 2*PI. Provided as a convenience for subclasses. */
  protected static final float TWO_PI = (float) (2 * Math.PI);
  /** The current frequency of the oscillator. */
  private float freq;
  /** The frequency to transition to. */
  private float newFreq;
  /** The sample rate of the oscillator. */
  private float srate;
  /** The current amplitude of the oscillator. */
  private float amp;
  /** The amplitude to transition to. */
  private float newAmp;
  /** The current position in the waveform's period. */
  private float step;
  private float stepSize;
  /** The portamento state. */
  private boolean port;
  /** The portamento speed in milliseconds. */
  private float portSpeed; // in milliseconds
  /**
   * The amount to increment or decrement <code>freq</code> during the
   * transition to <code>newFreq</code>.
   */
  private float portStep;
  /** The current pan position. */
  private float pan;
  /** The pan position to transition to. */
  private float newPan;
  /**
   * The amount to scale the left channel's amplitude to achieve the current pan
   * setting.
   */
  private float leftScale;
  /**
   * The amount to scale the right channel's amplitude to achieve the current
   * pan setting.
   */
  private float rightScale;
  
  private AudioListener listener;
  
  private AudioSignal ampMod;
  private AudioSignal freqMod;

  /**
   * Constructs an Oscillator with the requested frequency, amplitude and sample
   * rate.
   * 
   * @param frequency
   *          the frequency of the Oscillator
   * @param amplitude
   *          the amplitude of the Oscillator
   * @param sampleRate
   *          the sample rate of the Oscillator
   */
  public Oscillator(float frequency, float amplitude, float sampleRate)
  {
    freq = frequency;
    newFreq = freq;
    amp = amplitude;
    newAmp = amp;
    srate = sampleRate;
    step = 0;
    stepSize = freq / (sampleRate);
    port = false;
    portStep = 0.01f;
    pan = 0;
    newPan = 0;
    leftScale = rightScale = 1;
    listener = null;
    ampMod = null;
    freqMod = null;
  }

public final float sampleRate()
  {
    return srate;
  }

  /**
   * Sets the frequency of the Oscillator in Hz. If portamento is on, the
   * frequency of the Oscillator will transition from the current frequency to
   * <code>f</code>.
   * 
   * @param f
   *          the new frequency of the Oscillator
   */
  public final void setFreq(float f)
  {
    newFreq = f;
    // we want to step from freq to new newFreq in portSpeed milliseconds
    // first off, we want to divide the difference between the two freqs
    // by the number of milliseconds it's supposed to take to get there
    float msStep = (newFreq - freq) / portSpeed;
    // but since freq is incremented at every sample, we need to divide
    // again by the number of samples per millisecond
    float spms = srate / 1000;
    portStep = msStep / spms;
  }
  
  /**
   * Returns the current frequency.
   * 
   * @return the current frequency
   */
  public final float frequency()
  {
    return freq;
  }
  
  /**
   * ( begin auto-generated from constrain.xml )
   *
   * Constrains a value to not exceed a maximum and minimum value.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param amt the value to constrain
   * @param low minimum limit
   * @param high maximum limit
   */

  static public final double constrain(double amt, double low, double high) {
    return (amt < low) ? low : ((amt > high) ? high : amt);
  }
  
  /**
   * ( begin auto-generated from map.xml )
   *
   * Re-maps a number from one range to another. In the example above,
   * the number '25' is converted from a value in the range 0..100 into
   * a value that ranges from the left edge (0) to the right edge (width)
   * of the screen.
   * <br/> <br/>
   * Numbers outside the range are not clamped to 0 and 1, because
   * out-of-range values are often intentional and useful.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param value the incoming value to be converted
   * @param istart lower bound of the value's current range
   * @param istop upper bound of the value's current range
   * @param ostart lower bound of the value's target range
   * @param ostop upper bound of the value's target range
   */
  static public final float map(float value,
                                float istart, float istop,
                                float ostart, float ostop) {
    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
  }

  /**
   * Set the amplitude of the Oscillator, range is [0, 1].
   * 
   * @param a
   *          the new amplitude, it will be constrained to [0, 1]
   */
  public final void setAmp(float a)
  {
    newAmp = (float) constrain(a, 0, 1);
  }
  
  /**
   * Returns the current amplitude.
   * 
   * @return the current amplitude
   */
  public final float amplitude()
  {
    return amp; 
  }

  /**
   * Set the pan of the Oscillator, range is [-1, 1].
   * 
   * @param p -
   *          the new pan value, it will be constrained to [-1, 1]
   */
  public final void setPan(float p)
  {
    newPan = (float) constrain(p, -1, 1);
  }
  
  /**
   * Set the pan of the Oscillator, but don't smoothly transition from
   * whatever the current pan value is to this new one.
   * 
   * @param p - 
   * 			the new pan value, it will be constrained to [-1,1]
   */
  public final void setPanNoGlide(float p)
  {
	setPan(p);
	pan = (float) constrain(p, -1, 1);
  }
  
  /**
   * Returns the current pan value.
   * 
   * @return the current pan value
   */
  public final float pan()
  {
    return pan;
  }

  /**
   * Sets how many milliseconds it should take to transition from one frequency
   * to another when setting a new frequency.
   * 
   * @param millis
   *          the length of the portamento
   */
  public final void portamento(int millis)
  {
    if (millis <= 0)
    {
      Sound.error("Oscillator.portamento: The portamento speed must be greater than zero.");
    }
    port = true;
    portSpeed = millis;
  }

  /**
   * Turns off portamento.
   * 
   */
  public final void noPortamento()
  {
    port = false;
  }
  
  private final void updateFreq()
  {
    if ( freq != newFreq )
    {
      if ( port )
      {
        if (Math.abs(freq - newFreq) < 0.1f)
        {
          freq = newFreq;
        }
        else
        {
          freq += portStep;
        }
      }
      else
      {
        freq = newFreq;
      }
    }
    stepSize = freq / srate;
  }
  
  // holy balls, amplitude and frequency modulation
  // all rolled up into one.
  private final float generate(float fmod, float amod)
  {
	step += fmod;
	step = (float) (step - Math.floor(step));
    return amp * amod * value(step);
  }

  public final void generate(float[] signal)
  {
    float[] fmod = new float[signal.length];
    float[] amod = new float[signal.length];
    if ( freqMod != null )
    {
      freqMod.generate(fmod);
    }
    if ( ampMod != null )
    {
      ampMod.generate(amod);
    }
    for(int i = 0; i < signal.length; i++)
    {
      // do the portamento stuff / freq updating
      updateFreq();
      if ( ampMod != null )
      {
        signal[i] = generate(fmod[i], amod[i]);
      }
      else
      {
        signal[i] = generate(fmod[i], 1);
      }
      monoStep();
    }
    // broadcast to listener
    if ( listener != null )
    {
   	 listener.samples(signal);
    }
  }

  public final void generate(float[] left, float[] right)
  {
    float[] fmod = new float[left.length];
    float[] amod = new float[right.length];
    if ( freqMod != null )
    {
      freqMod.generate(fmod);
    }
    if ( ampMod != null )
    {
      ampMod.generate(amod);
    }
    for(int i = 0; i < left.length; i++)
    {
      // do the portamento stuff / freq updating
      updateFreq();
      if ( ampMod != null )
      {
        left[i] = generate(fmod[i], amod[i]);
      }
      else
      {
        left[i] = generate(fmod[i], 1);
      }
      right[i] = left[i];
      // scale amplitude to add pan
      left[i] *= leftScale;
      right[i] *= rightScale;
      stereoStep();
    }
    if ( listener != null )
    {
   	 listener.samples(left, right);
    }
  }
  
  public final void setAudioListener(AudioListener al)
  {
	  listener = al;
  }
  
  // Not visible for 2.0.2
  final void setAmplitudeModulator(AudioSignal s)
  {
    ampMod = s;
  }
  
  // Not visible for 2.0.2
  final void setFrequencyModulator(AudioSignal s)
  {
    freqMod = s;
  }

  private void monoStep()
  {
    stepStep();
    stepAmp();
  }

  private void stereoStep()
  {
    stepStep();
    stepAmp();
    calcLRScale();
    stepPan();
  }

  private void stepStep()
  {
    step += stepSize;
    step = (float) (step - Math.floor(step));
  }

  private void calcLRScale()
  {
    if (pan <= 0)
    {
      rightScale = map(pan, -1, 0, 0, 1);
      leftScale = 1;
    }
    if (pan >= 0)
    {
      leftScale = map(pan, 0, 1, 1, 0);
      rightScale = 1;
    }
    if (pan == 0)
    {
      leftScale = rightScale = 1;
    }
  }

  private static float panAmpStep = 0.0001f;

  private void stepPan()
  {
    if (pan != newPan)
    {
      if (pan < newPan)
        pan += panAmpStep;
      else
        pan -= panAmpStep;
      if (Math.abs(pan - newPan) < panAmpStep) pan = newPan;
    }
  }

  private void stepAmp()
  {
    if (amp != newAmp)
    {
      if (amp < newAmp)
        amp += panAmpStep;
      else
        amp -= panAmpStep;
      if (Math.abs(amp - newAmp) < panAmpStep) pan = newPan;
    }
  }

  /**
   * Returns the period of the waveform (the inverse of the frequency).
   * 
   * @return the period of the waveform
   */
  public final float period()
  {
    return 1 / freq;
  }

  /**
   * Returns the value of the waveform at <code>step</code>. To take
   * advantage of all of the work that <code>Oscillator</code> does, you can
   * create your own periodic waveforms by extending <code>Oscillator</code>
   * and implementing this function. All of the oscillators included with Minim
   * were created in this way.
   * 
   * @param step
   *          an offset from the beginning of the waveform's period
   * @return the value of the waveform at <code>step</code>
   */
  protected abstract float value(float step);
}
