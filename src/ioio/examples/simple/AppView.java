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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class AppView extends View {
	
	private IOIOSimpleApp context_;
    private Paint paint = new Paint();
    private Canvas canvas = new Canvas();
    private int width;
    private int height;
    private int x_= 0;
    private int y = 0;
    int freq = 523; // Variable to keep track of note frequency
    boolean playTone; // Variable to keep track of whether a note should be played or not
    
	private static final float sin135 = -0.7071f;
	private static final float cos135 = - sin135;


    
    public AppView(IOIOSimpleApp context) {
        super(context);
        context_ = context;
        paint.setColor(Color.BLACK);        
        
    }
    
    

    @Override
    public void onDraw(Canvas canvas) {
    	this.canvas = canvas;
		width = getWidth();
		height = getHeight();	
		paint.setStrokeWidth(0);
		paint.setColor(Color.WHITE);
		//left, top, right, bottom
		//leftsidex, leftsidey, rightsidex, rightsidey
	    canvas.drawRect(0, 50, 200, 1000, paint); // c
	    canvas.drawRect(205, 50, 405, 1000, paint); // d
	    canvas.drawRect(410, 50, 605, 1000, paint); // e
	    canvas.drawRect(610, 50, 805, 1000, paint); // f
	    canvas.drawRect(810, 50, 1005, 1000, paint); // g
	    canvas.drawRect(1010, 50, 1205, 1000, paint); // a
	    canvas.drawRect(1210, 50, 1405, 1000, paint); // b
	    canvas.drawRect(1410, 50, 1605, 1000, paint); // c

	  // Draw the black keys, for looks, no current functional implementation.
	    paint.setColor(Color.BLACK);
	    canvas.drawRect(100, 50, 300, 500, paint); // c#
	    canvas.drawRect(305, 50, 505, 500, paint); // d#
	    canvas.drawRect(715, 50, 915, 500, paint); // f#
	    canvas.drawRect(920, 50, 1120, 500, paint); // g#
	    canvas.drawRect(1125, 50, 1325, 500, paint); // a#
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// There was a touch event - what kind?
		  int action = event.getAction();

		  // Get the X position of where the touch was, so we know which note to play.
		  int posx = (int)event.getX();
		  
		// Get the Y position of where the touch was, so we know which note to play.
		  int posy = (int)event.getY();

		  // If the action was a touch on the screen, play a note based on the positon of the touch.
		  if (action == MotionEvent.ACTION_DOWN) {

		    // Checks if you hit left most key
		    // If so set the frequency of the tone to be played as well as the status variable to tell it to play a note
		    if (posx < 200 && posy > 500) {
		    	paint.setColor(Color.RED);
//			    paint.setAlpha(100);
			    canvas.drawRect(0, 50, 200, 1000, paint); // c
		        freq = 523;
		        playTone = true; 
		    }  

		    if (posx >= 205 && posx < 406 && posy > 500) {
		      freq = 587;
		      playTone = true; 
		    }

		    if (posx >= 410 && posx < 606 && posy > 500) {
		      freq = 659;
		      playTone = true; 
		    }

		    if (posx >= 610 && posx < 806 && posy > 500) {
		      freq = 698;
		      playTone = true; 
		    }

		    if (posx >= 810 && posx < 1006 && posy > 500) {
		      freq = 784;
		      playTone = true; 
		    }

		    if (posx >= 1010 && posx < 1206 && posy > 500) {
		      freq = 880;
		      playTone = true; 
		    }

		    if (posx >= 1210 && posx < 1406 && posy > 500) {
		      freq = 988;
		      playTone = true; 
		    }
		    if (posx >= 1410 && posx < 1606 && posy > 500) {
		      freq = 1047;
		      playTone = true; 
		    }
		  }
		  return playTone;

	}
	
	public int getFreq() {
		return freq;
	}
	
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public boolean getPlayTone() {
		return playTone;
	}
	
	public void setPlayTone(boolean playTone) {
		this.playTone = playTone;
	}
}
