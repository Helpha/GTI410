package controller;

import model.ImageDouble;
import model.ImageX;
import model.Pixel;
import model.PixelDouble;

public class NormImg0to255Strategy extends ImageConversionStrategy {

	private final double newMinValue = 0.0;
	private final double newMaxValue = 255.0;
	private final double newRangeValue = newMaxValue - newMinValue;

	// vieilles valeurs maximales du pixel
	private double oldMaxRed;
	private double oldMaxGreen;
	private double oldMaxBlue;
	private double oldMaxAlpha;

	// vieilles valeurs minimales du pixel
	private double oldMinRed;
	private double oldMinGreen;
	private double oldMinBlue;
	private double oldMinAlpha;

	// vieilles valeur de l'écart
	private double oldRangeRed;
	private double oldRangeGreen;
	private double oldRangeBlue;
	private double oldRangeAlpha;

	@Override
	public ImageX convert(ImageDouble image) {

		// ajuste la valeur minimum, maximum, ainsi que l'écart
		setMinMaxRangeValue(image);

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		ImageX newImage = new ImageX(0, 0, imageWidth, imageHeight);
		PixelDouble curPixelDouble = null;

		newImage.beginPixelUpdate();
		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, y);

				newImage.setPixel(
						x,
						y,
						new Pixel((int) (normalizedValue(newRangeValue,
								oldRangeRed, oldMinRed,
								curPixelDouble.getRed())),
								(int) (normalizedValue(newRangeValue,
										oldRangeGreen, oldMinGreen,
										curPixelDouble.getGreen())),
								(int) (normalizedValue(newRangeValue,
										oldRangeBlue, oldMinBlue,
										curPixelDouble.getBlue())),
								(int) (normalizedValue(newRangeValue,
										oldRangeAlpha, oldMinAlpha,
										curPixelDouble.getAlpha()))));
			}
		}
		newImage.endPixelUpdate();
		return newImage;
	}

	private void setMinMaxRangeValue(ImageDouble image) {
		setMaxValue(image);
		setMinValue(image);
		oldRangeRed = oldMaxRed - oldMinRed;
		oldRangeGreen = oldMaxGreen - oldMinGreen;
		oldRangeBlue = oldMaxBlue - oldMinBlue;
		oldRangeAlpha = oldMaxAlpha - oldMinAlpha;
	}

	private void setMaxValue(ImageDouble image) {

		// the max value
		double redCurrent;
		double greenCurrent;
		double blueCurrent;
		double alphaCurrent;

		double red = Double.MIN_VALUE;
		double green = Double.MIN_VALUE;
		double blue = Double.MIN_VALUE;
		double alpha = Double.MIN_VALUE;

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		PixelDouble curPixelDouble = null;
		for (int x = 0; x < imageWidth; x++) {

			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, 0);
				redCurrent = curPixelDouble.getRed();
				blueCurrent = curPixelDouble.getGreen();
				greenCurrent = curPixelDouble.getBlue();
				alphaCurrent = curPixelDouble.getAlpha();

				if (redCurrent > red) {
					red = redCurrent;
				} else if (greenCurrent > green) {
					green = greenCurrent;
				} else if (blueCurrent > blue) {
					blue = blueCurrent;
				} else if (alphaCurrent > alpha) {
					alpha = alphaCurrent;
				}

			}
		}

		this.oldMaxRed = red;
		this.oldMaxGreen = green;
		this.oldMaxBlue = blue;
		this.oldMaxAlpha = alpha;

	}

	private double normalizedValue(double newRange, double oldRange,
			double oldMin, double value) {
		// http://stackoverflow.com/questions/695084/how-do-i-normalize-an-image
		double val = 0.0;
		val = (value - oldMin) * (newRange / oldRange) + newMinValue;
		return val;
	}

	private void setMinValue(ImageDouble image) {

		// the max value
		double redCurrent;
		double greenCurrent;
		double blueCurrent;
		double alphaCurrent;

		double red = Double.MAX_VALUE;
		double green = Double.MAX_VALUE;
		double blue = Double.MAX_VALUE;
		double alpha = Double.MAX_VALUE;

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		PixelDouble curPixelDouble = null;
		for (int x = 0; x < imageWidth; x++) {

			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, 0);
				redCurrent = curPixelDouble.getRed();
				blueCurrent = curPixelDouble.getGreen();
				greenCurrent = curPixelDouble.getBlue();
				alphaCurrent = curPixelDouble.getAlpha();

				if (redCurrent < red) {
					red = redCurrent;
				} else if (greenCurrent < green) {
					green = greenCurrent;
				} else if (blueCurrent < blue) {
					blue = blueCurrent;
				} else if (alphaCurrent < alpha) {
					alpha = alphaCurrent;
				}

			}
		}

		this.oldMinRed = red;
		this.oldMinGreen = green;
		this.oldMinBlue = blue;
		this.oldMinAlpha = alpha;

	}

}