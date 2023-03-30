
public class PointwiseTransform extends Object {

	/**
	 * Question 2.1 Contrast reversal
	 */
	static public ImageAccess inverse(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		ImageAccess output = new ImageAccess(nx, ny);
		double value = 0.0;
		for (int x = 0; x < nx; x++)
			for (int y = 0; y < ny; y++) {
				value = input.getPixel(x, y);
				value = 255 - value;
				output.putPixel(x, y, value);
			}
		return output;
	}

	/**
	 * Question 2.2 Stretch normalized constrast
	 */
	static public ImageAccess rescale(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		double max = input.getMaximum();
		double min = input.getMinimum();
		ImageAccess output = new ImageAccess(nx, ny);

		// alpha = 255/ (max(f(x,y)) – min(f(x,y)))
		double alpha = 255 / (max - min);
		double pixel;
		for (int x = 0; x < nx; x++) {

			for (int y = 0; y < ny; y++) {
				pixel = input.getPixel(x, y);
				pixel = alpha * (pixel - min);
				output.putPixel(x, y, pixel);
			}
		}
		return output;
	}

	/**
	 * Question 2.3 Saturate an image
	 */
	static public ImageAccess saturate(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		ImageAccess output = new ImageAccess(nx, ny);

		for (int x = 0; x < nx; x++) {

			for (int y = 0; y < ny; y++) {
				double pixel = input.getPixel(x, y);
				if (pixel > 10000) {
					output.putPixel(x, y, 10000);
				} else {
					output.putPixel(x, y, pixel);
				}
			}
		}

		return output;
	}

	/**
	 * Question 4.1 Maximum Intensity Projection
	 */
	static public ImageAccess zprojectMaximum(ImageAccess[] zstack) {
		int nx = zstack[0].getWidth();
		int ny = zstack[0].getHeight();
		int nz = zstack.length;
		ImageAccess output = new ImageAccess(nx, ny);

		double maxValue = 0;
		double pixel = 0;
		for (int x = 0; x < nx; x++) {

			for (int y = 0; y < ny; y++) {

				for (int i = 0; i < zstack.length; i++) {

					pixel = zstack[i].getPixel(x, y);
					if (pixel > maxValue) {
						maxValue = pixel;
					}
				}

				output.putPixel(x, y, maxValue);
				maxValue = 0;
			}
		}

		return output;
	}

	/**
	 * Question 4.2 Z-stack mean
	 */
	static public ImageAccess zprojectMean(ImageAccess[] zstack) {
		int nx = zstack[0].getWidth();
		int ny = zstack[0].getHeight();
		int nz = zstack.length;
		ImageAccess output = new ImageAccess(nx, ny);
		
		double med = 0;
		double pixel = 0;
		for (int x = 0; x < nx; x++) {

			for (int y = 0; y < ny; y++) {

				for (int i = 0; i < zstack.length; i++) {

					pixel = zstack[i].getPixel(x, y);
					med+=pixel;
				}

				med = med / zstack.length;
				output.putPixel(x, y, med);
				med = 0;
			}
		}
		return output;
	}

}
