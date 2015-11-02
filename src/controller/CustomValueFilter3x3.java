package controller;

import model.ImageDouble;
import model.ImageX;
import model.PixelDouble;

/**
 * Classe implémentant les filtres
 */
public class CustomValueFilter3x3 extends Filter {
	private double filterMatrix[][] = null;

	private double inverseK = 0;
/**
	 * Default constructor.
	 * @param paddingStrategy PaddingStrategy used 
	 * @param conversionStrategy ImageConversionStrategy used
	 */
	public CustomValueFilter3x3(PaddingStrategy paddingStrategy,
			ImageConversionStrategy conversionStrategy) {
		super(paddingStrategy, conversionStrategy);
		filterMatrix = new double[3][3];
	}
/**
	 * Filters an ImageX and returns a ImageDouble.
	 */
	public ImageDouble filterToImageDouble(ImageX image) {
		return filter(conversionStrategy.convert(image));
	}

	/**
	 * Filters an ImageDouble and returns a ImageDouble.
	 */
	public ImageDouble filterToImageDouble(ImageDouble image) {
		return filter(image);
	}

	/**
	 * Filters an ImageX and returns an ImageX.
	 */
	public ImageX filterToImageX(ImageX image) {
		ImageDouble filtered = filter(conversionStrategy.convert(image));
		return conversionStrategy.convert(filtered);
	}

	/**
	 * Filters an ImageDouble and returns a ImageX.
	 */
	public ImageX filterToImageX(ImageDouble image) {
		ImageDouble filtered = filter(image);
		return conversionStrategy.convert(filtered);
	}

	/*
	 * Filter Implementation
	 */
	private ImageDouble filter(ImageDouble image) {
		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		ImageDouble newImage = new ImageDouble(imageWidth, imageHeight);
		PixelDouble newPixel = null;
		double result = 0;
		this.remplirArray();

		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				newPixel = new PixelDouble();

				// *******************************
				// RED
				for (int i = 0; i <= 2; i++) {
					for (int j = 0; j <= 2; j++) {
						result += filterMatrix[i][j]
								* this.inverseK
								* getPaddingStrategy().pixelAt(image,
										x + (i - 1), y + (j - 1)).getRed();
					}
				}

				newPixel.setRed(result);
				result = 0;

				// *******************************
				// Green
				for (int i = 0; i <= 2; i++) {
					for (int j = 0; j <= 2; j++) {
						result += filterMatrix[i][j]
								* this.inverseK
								* getPaddingStrategy().pixelAt(image,
										x + (i - 1), y + (j - 1)).getGreen();
					}
				}

				newPixel.setGreen(result);
				result = 0;

				// *******************************
				// Blue
				for (int i = 0; i <= 2; i++) {
					for (int j = 0; j <= 2; j++) {
						result += filterMatrix[i][j]
								* this.inverseK
								* getPaddingStrategy().pixelAt(image,
										x + (i - 1), y + (j - 1)).getBlue();
					}
				}

				newPixel.setBlue(result);
				result = 0;

				// *******************************
				// Alpha - Untouched in this filter
				newPixel.setAlpha(getPaddingStrategy().pixelAt(image, x, y)
						.getAlpha());

				// *******************************
				// Done
				newImage.setPixel(x, y, newPixel);
			}
		}

		return newImage;
	}

	/**
	 * Rempli le tableau
	 * 
	 * @param posX position x
	 * @param posY position y
	 * @param value valeur
	 */
	public void updateKernel(int posX, int posY, double value) {
		this.filterMatrix[posX][posY] = value;
	}

	/**
	 * Calculate the value of the inverse K
	 */
	private void remplirArray() {
		double kValue = 0;
		for (int i = 0; i <=2; i++) {
			for (int j = 0; j <= 2; j++) {
				kValue += this.filterMatrix[i][j];
			}
		}
		if(kValue ==0) kValue = 1;
		this.inverseK = (1 / kValue);
		
	}

}
