/*
*The MIT License (MIT)
*
*Copyright (c) 2015 Michael Gunderson
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
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