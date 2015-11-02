package controller;

import model.ImageDouble;
import model.ImageX;
import model.Pixel;
import model.PixelDouble;

/**
 * Fait la normalisation 0 a 255
 */
public class NormAbs0to255Strategy extends ImageConversionStrategy {

	private final double newMinValue = 0.0;
	private final double newMaxValue = 255.0;
	private final double newRangeValue = newMaxValue - newMinValue;

	// anciennes valeur minimum de chaque pixel
	private double oldMinRed;
	private double oldMinGreen;
	private double oldMinBlue;
	private double oldMinAlpha;

	// anciennes valeurs maximum
	private double oldMaxRed;
	private double oldMaxGreen;
	private double oldMaxBlue;
	private double oldMaxAlpha;

	//valeur courante des pixels
	private double oldRangeRedValue;
	private double oldRangeGreenValue;
	private double oldRangeBlueValue;
	private double oldRangeAlphaValue;

	@Override
	public ImageX convert(ImageDouble image) {

		setMinMaxRangeValue(image);

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		ImageX newImage = new ImageX(0, 0, imageWidth, imageHeight);
		PixelDouble curPixelDouble = null;

		newImage.beginPixelUpdate();
		// pour chacun des pixels on ajuste en mettant la valeur normalisée de RGB et Alpha
		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, y);

				newImage.setPixel(x, y, new Pixel((int) (normalizedValue(
						newRangeValue, oldRangeRedValue, oldMinRed,
						curPixelDouble.getRed())), (int) (normalizedValue(
						newRangeValue, oldRangeGreenValue, oldMinGreen,
						curPixelDouble.getGreen())), (int) (normalizedValue(
						newRangeValue, oldRangeBlueValue, oldMinBlue,
						curPixelDouble.getBlue())), (int) (normalizedValue(
						newRangeValue, oldRangeAlphaValue, oldMinAlpha,
						curPixelDouble.getAlpha()))));
			}
		}
		newImage.endPixelUpdate();
		return newImage;
	}

	/**
	 * ajuste les valeurs minimums et maximums.
	 * 
	 * @param image
	 */
	private void setMinMaxRangeValue(ImageDouble image) {
		setMaxValue(image);
		setMinValue(image);
		oldRangeRedValue = oldMaxRed - oldMinRed;
		oldRangeGreenValue = oldMaxGreen - oldMinGreen;
		oldRangeBlueValue = oldMaxBlue - oldMinBlue;
		oldRangeAlphaValue = oldMaxAlpha - oldMinAlpha;
	}

private void setMinValue(ImageDouble image) {

		// the max value
		double rCurValue;
		double gCurValue;
		double bCurValue;
		double aCurValue;

		double rValue = Double.MAX_VALUE;
		double gValue = Double.MAX_VALUE;
		double bValue = Double.MAX_VALUE;
		double aValue = Double.MAX_VALUE;

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		PixelDouble curPixelDouble = null;
		for (int x = 0; x < imageWidth; x++) {

			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, 0);
				rCurValue = Math.abs(curPixelDouble.getRed());
				bCurValue = Math.abs(curPixelDouble.getGreen());
				gCurValue = Math.abs(curPixelDouble.getBlue());
				aCurValue = Math.abs(curPixelDouble.getAlpha());

				if (rCurValue < rValue) {
					rValue = rCurValue;
				} else if (gCurValue < gValue) {
					gValue = gCurValue;
				} else if (bCurValue < bValue) {
					bValue = bCurValue;
				} else if (aCurValue < aValue) {
					aValue = aCurValue;
				}
				rValue = Math.abs(rCurValue);
				gValue = Math.abs(gCurValue);
				bValue = Math.abs(bCurValue);
				aValue = Math.abs(aCurValue);

			}
		}

		this.oldMinRed = rValue;
		this.oldMinGreen = gValue;
		this.oldMinBlue = bValue;
		this.oldMinAlpha = aValue;

	}

	private void setMaxValue(ImageDouble image) {

		// the max value
		double rCurValue;
		double gCurValue;
		double bCurValue;
		double aCurValue;

		double rValue = Double.MIN_VALUE;
		double gValue = Double.MIN_VALUE;
		double bValue = Double.MIN_VALUE;
		double aValue = Double.MIN_VALUE;

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		PixelDouble curPixelDouble = null;
		for (int x = 0; x < imageWidth; x++) {

			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x, 0);
				rCurValue = Math.abs(curPixelDouble.getRed());
				bCurValue = Math.abs(curPixelDouble.getGreen());
				gCurValue = Math.abs(curPixelDouble.getBlue());
				aCurValue = Math.abs(curPixelDouble.getAlpha());
				if (rCurValue > rValue) {
					rValue = rCurValue;
				} else if (gCurValue > gValue) {
					gValue = gCurValue;
				} else if (bCurValue > bValue) {
					bValue = bCurValue;
				} else if (aCurValue > aValue) {
					aValue = aCurValue;
				}
				rValue = Math.abs(rCurValue);
				gValue = Math.abs(gCurValue);
				bValue = Math.abs(bCurValue);
				aValue = Math.abs(aCurValue);

			}
		}

		this.oldMaxRed = rValue;
		this.oldMaxGreen = gValue;
		this.oldMaxBlue = bValue;
		this.oldMaxAlpha = aValue;

	}

	

	/**
	 * 
	 * @param oldRange
	 * @param newRange
	 * @param oldMin
	 * @param value
	 * @return
	 */
	private double normalizedValue(double newRange, double oldRange,
			double oldMin, double value) {
		// http://stackoverflow.com/questions/695084/how-do-i-normalize-an-image
		double val = 0;
		val = (Math.abs(value) - oldMin) * (newRange / oldRange) + newMinValue;
		return val;
	}

}