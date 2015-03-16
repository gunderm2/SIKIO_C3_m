/*
 * Copyright 2015 Michael Gunderson. All rights reserved.
 *
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL ARSHAN POURSOHI OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied.
 */
package ioio.examples.simple;

import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.graphics.Color;
import android.os.Bundle;

public class IOIOSimpleApp extends IOIOActivity {
	
	private AppView appView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appView = new AppView(this);
		appView.setBackgroundColor(Color.BLACK);
        setContentView(appView);
	}

	class Looper extends BaseIOIOLooper {
		PwmOutput piezo; // Piezo buzzers are pulse-modulated output
		int piezoPin = 11; // Pin for our piezo speaker

		@Override
		public void setup() throws ConnectionLostException {
			
			// Open and close the piezo pin just to give us a connection and set the duty cycle
		    piezo = ioio_.openPwmOutput(piezoPin, appView.getFreq()); 
		    piezo.setDutyCycle(.5f);
		    piezo.close(); // To stop from the piezo from making sound on startup
		}

		@Override
		public void loop() throws ConnectionLostException, InterruptedException {
			// If a 'piano' key has been pressed, play that note for 100 ms
			  if (appView.getPlayTone() == true) 
			  {
			    try 
			    {
			      piezo = ioio_.openPwmOutput(piezoPin, appView.getFreq());
			      piezo.setDutyCycle(.5f);
			      Thread.sleep(100);
			    } 
			    catch (InterruptedException e) 
			    {
			    }
			    piezo.close(); // Turn off signal to piezo speaker
			    appView.setPlayTone(false); // Set to false so note doesn't continuously play, and waits for another screen press
			  }
		}
	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}
}