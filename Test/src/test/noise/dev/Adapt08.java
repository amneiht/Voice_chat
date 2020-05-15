package test.noise.dev;

/*File Adapt08.java
Copyright 2005, R.G.Baldwin

The purpose of this program is to illustrate an adaptive
noise cancellation system.

See the following URL for a description and a block 
diagram of an adaptive noise cancellation system.

http://www.owlnet.rice.edu/~ryanking/elec431/intro.html

This program requires the following classes:
Adapt08.class
AdaptEngine02.class
AdaptiveResult.class
ForwardRealToComplex01.class
PlotALot01.class
PlotALot03.class
PlotALot07.class

This program uses the adaptive engine named AdaptEngine02 
to adaptively develop a filter.  One of the inputs to the
adaptive engine is the sum of signal plus noise.  The 
other input to the adaptive engine is noise that is 
different from, but which is correlated to the noise that
is added to the signal.  The adaptive engine develops a
filter that removes the noise from the signal plus noise
data producing an output consisting of almost pure
signal.

The signal and the original noise are derived from a
random number generator.  Therefore, they are both 
essentially white.  The signal is not correlated with the 
noise.  Before scaling, the values obtained from the
random number generator are uniformly distributed from
-1.0 to +1.0. 

The white noise is processed through a convolution 
filter before adding it to the signal.  The convolution
filter is designed to simulate the effect of acoustic
energy being emitted at one point in space and being 
modified through the addition of several time-delayed and 
attenuated echoes before being received at another point 
in space.  This results in constructive and destructive 
interference such that the noise that is added to the 
signal is no longer white.  However, it is still 
correlated with the original white noise.

The program produces five graphs with three graphs in a 
row across the top of the screen and two graphs in a row 
below the top row.

The following is a brief description of each of the five 
graphs, working from left to right, top to bottom.

1.  The impulse response of the convolution filter that is
applied to the white noise before it is added to the
white signal.
2.  The amplitude and phase response of the convolution
filter that is applied to the white noise before it is
added to the white signal.
3.  Six time series that illustrate the time behavior of 
the adaptive process.
4.  The amplitude and phase response of the adaptive 
filter at the end of every 400th iteration.
5.  The impulse response of the adaptive filter at the end 
of every 400th adaptive iteration.

Graph 3 consists of multiple pages stacked on top
of one another.  Move the pages on the top of the stack to 
view the pages further down.  The pages on the top of the 
stack represent the results produced early in the adaptive 
process while those further down represent the results 
produced later in the adaptive process.  If you increase
the number of iterations described later, graphs 4 and 5
will also consist of multiple pages stacked on top of one
another.

The six time series that are plotted are, from top to
bottom in the colors indicated:
1.  (Black) Input to the adaptive filter.  This is the
white noise.
2.  (Red) Target for the adaptive process. This is the
signal plus noise.
3.  (Blue) Output from the adaptive filter.
4.  (Green) Error computed within the adaptive process.
In this case, the error trace is actually the trace that
contains the signal with the noise removed.  In other 
words, the blue trace is an estimate of the noise and the
green trace is the difference between the red trace and 
the blue trace.
5.  (Violet) The original pure signal.  This trace is 
provided for visual comparison with the green signal trace.
6.  (Turquoise) The arithmetic difference between the 
pure signal trace (violet) and the green trace containing
the adaptive process's estimate of the signal.  This 
trace is provided for an arithmetic comparison of the
pure signal trace and the adaptive estimate of the signal.
Ideally this trace will go to zero if the adaptive process
is successful in totally eliminating the noise from the
green trace.

Near the end of the run, the adaptive update process is 
disabled.  The input data is set to zero for the remainder 
of the run except that on one occasion, an impulse is 
inserted into the white noise data.  This makes it 
possible to see:

1. The impulse response of the convolution filter that
is applied to the white noise before it is added to the
white signal.
2. The impulse response of the final adaptive filter.

There is no user input to this program.  All parameters are
hard coded into the main method.  To run the program with
different parameters, modify the parameters and recompile
the program.

The program parameters are:

feedbackGain: The gain factor that is used in the feedback 
loop to adjust the coefficient values in the adaptive 
filter.

numberIterations: This is the number of iterations that the
program executes before stopping and displaying all of the 
graphic results.

filterLength: This is the number of coefficients in the 
adaptive filter.  Must be at least 26.  If you change it
to a value that is less than 26, the plot of the impulse
responses of the adaptive filter will not be properly 
aligned.

noiseScale:  The scale factor that is applied to the values
that are extracted from the random number generator and
used as white noise.

signalScale:  The scale factor that is applied to the
values that are extracted from the random number generator
and used as white signal.

pathOperator:  Areference to an array of type double[] 
containing the coefficients of the convolution filter that
is applied to the white noise before it is added to the
white signal.

Tested using J2SE 5.0 and WinXP.  J2SE 5.0 or later is 
required.
**********************************************************/
//J2SE 5.0 req

public class Adapt08 {
	public static double[] process(double[] pathOperator) {
		double feedbackGain = 0.0001;
		int numberIterations = 2001;
		double noiseScale = 10;
		double signalScale = 10;
		return process(feedbackGain, numberIterations, noiseScale, signalScale, pathOperator);
	}

	public static double[] process(double feedbackGain, int numberIterations, double noiseScale, double signalScale,
			double[] pathOperator) {

		// The following array will be populated with the
		// adaptive filter for display purposes.
		double[] filter = null;
		int filterLength = pathOperator.length;
		AdaptEngine02 adapter = new AdaptEngine02(filterLength, feedbackGain);
		double[] rawData = new double[pathOperator.length];
		double target = 0;
		double input = 0;
		double whiteNoise = 0;
		double whiteSignal = 0;
		boolean adaptOn;

		// Perform the specified number of iterations.
		for (int cnt = 0; cnt < numberIterations; cnt++) {

			// The following variable is used to control whether
			// or not the adapt method of the adaptive engine
			// updates the filter coefficients when it is called.
			// The filters are updated when this variable is
			// true and are not updated when this variable is
			// false.
			adaptOn = true;

			// Get and scale the next sample of white noise and
			// white signal data from a random number generator.
			// Before scaling by noiseScale and signalScale, the
			// values are uniformly distributed from -1.0 to 1.0.
			whiteNoise = noiseScale * (2 * (Math.random() - 0.5));
			whiteSignal = signalScale * (2 * (Math.random() - 0.5));

			// Set white noise and white signal values near the
			// end of the run to zero and turn adaptation off.
			// Insert an impulse near the end of the whiteNoise
			// data that will produce impulse responses for the
			// path and for the adaptive filter.
			// Set values to zero.
			if (cnt > (numberIterations - 5 * filterLength)) {
				whiteNoise = 0;
				whiteSignal = 0;
				adaptOn = false;
			} // end if
				// Now insert an impulse at one specific location in
				// the whiteNoise data.
			if (cnt == numberIterations - 3 * filterLength) {
				whiteNoise = 2 * noiseScale;
			} // end if

			// Insert the white noise data into the delay line
			// that will be used to convolve the white noise
			// with the path operator.
			flowLine(rawData, whiteNoise);

			// Apply the path operator to the white noise data.
			double pathNoise = reverseDotProduct(rawData, pathOperator);

			// Declare a variable that will be populated with the
			// results returned by the adapt method of the
			// adaptive engine.
			AdaptiveResult result = null;

			// Establish the appropriate input values for an
			// adaptive noise cancellation filter and perform the
			// adaptive update.
			input = whiteNoise;
			target = pathNoise + whiteSignal;
			result = adapter.adapt(input, target, adaptOn);

			// Get and save adaptive results for plotting and
			// spectral analysis
			filter = result.filterArray;

		} // End for loop,
		return filter;

	}// end process method
		// -----------------------------------------------------//

	// This method simulates a tapped delay line. It receives
	// a reference to an array and a value. It discards the
	// value at index 0 of the array, moves all the other
	// values by one element toward 0, and inserts the new
	// value at the top of the array.
	private static void flowLine(double[] line, double val) {
		for (int cnt = 0; cnt < (line.length - 1); cnt++) {
			line[cnt] = line[cnt + 1];
		} // end for loop
		line[line.length - 1] = val;
	}// end flowLine

	// This method receives two arrays and treats each array
	// as a vector. The two arrays must have the same length.
	// The program reverses the order of one of the vectors
	// and returns the vector dot product of the two vectors.
	private static double reverseDotProduct(double[] v1, double[] v2) {
		if (v1.length != v2.length) {
			System.out.println("reverseDotProduct");
			System.out.println("Vectors must be same length.");
			System.out.println("Terminating program");
			System.exit(0);
		} // end if

		double result = 0;

		for (int cnt = 0; cnt < v1.length; cnt++) {
			result += v1[cnt] * v2[v1.length - cnt - 1];
		} // end for loop

		return result;
	}// end reverseDotProduct
		// -----------------------------------------------------//
}// end class Adapt08