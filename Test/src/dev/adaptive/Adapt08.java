package dev.adaptive;

public class Adapt08 {
	public static void main(String[] args) {
		// Default parameter values
		double feedbackGain = 0.0001;
		int numberIterations = 2001;
		double noiseScale = 10;
		double signalScale = 10;

		// Define the path impulse response as a simulation of
		// an acoustic signal with time-delayed echoes.
		double[] pathOperator = { 0.0, 0.0, 0.0, 1.0, 0.0, 0.64, 0.0, 0.0, 0.32768, 0.0, 0.0, 0.0, 0.1342176, 0.0, 0.0,
				0.0, 0.0, 0.0439803, 0.0 };

		// Instantiate a new object of the Adapt08 class
		// and invoke the method named process on that object.
		new Adapt08(feedbackGain, numberIterations, noiseScale, signalScale).process(pathOperator);
	}// end main
		// -----------------------------------------------------//

	int numberIterations;
	double feedbackGain;
	double noiseScale;
	double signalScale;

	public Adapt08(double feedbackGain, int numberIterations, double noiseScale, double signalScale) {
		this.feedbackGain = feedbackGain;
		this.noiseScale = noiseScale;
		this.signalScale = signalScale;
		this.numberIterations = numberIterations;
	}
	// This is the primary adaptive processing and plotting
	// method for the program.

	public double[] process(double[] pathOperator) {

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

			whiteNoise = 2 * noiseScale;

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

		 // End for loop,
		return filter;

	}// end process method
		// -----------------------------------------------------//

	// This method simulates a tapped delay line. It receives
	// a reference to an array and a value. It discards the
	// value at index 0 of the array, moves all the other
	// values by one element toward 0, and inserts the new
	// value at the top of the array.
	void flowLine(double[] line, double val) {
		for (int cnt = 0; cnt < (line.length - 1); cnt++) {
			line[cnt] = line[cnt + 1];
		} // end for loop
		line[line.length - 1] = val;
	}// end flowLine

	// This method receives two arrays and treats each array
	// as a vector. The two arrays must have the same length.
	// The program reverses the order of one of the vectors
	// and returns the vector dot product of the two vectors.
	double reverseDotProduct(double[] v1, double[] v2) {
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