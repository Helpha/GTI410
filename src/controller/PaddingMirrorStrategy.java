package controller;

import model.ImageDouble;
import model.ImageX;
import model.Pixel;
import model.PixelDouble;

public class PaddingMirrorStrategy extends PaddingStrategy {

	public Pixel pixelAt(ImageX image, int x, int y) {

		// Utilisation de 
		// https://ena.etsmtl.ca/pluginfile.php/236294/mod_resource/content/0/GTI410_A15_C06_P00_FiltrageLineaire.pdf

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();

		if (x == -1 && y == -1) {
			return image.getPixel(x + 1, y + 1);
		}
		
		else if (x == -1 && y == imageHeight) {
			return image.getPixel(x + 1, y - 1);
		}

		else if (x == -1) {
			return image.getPixel(x + 1, y);
		}
		
		if (x == imageWidth && y == -1) {
			return image.getPixel(x - 1, y + 1);

		}
		
		else if (x == imageWidth && y == imageHeight) {
			return image.getPixel(x - 1, y - 1);
		}

		else if (x == imageWidth) {
			return image.getPixel(x - 1, y);
		}

		if (y == -1) {
			return image.getPixel(x, y + 1);

		}

		if (y == imageHeight) {
			return image.getPixel(x, y - 1);
		}

		return image.getPixel(x, y);

	}

	public PixelDouble pixelAt(ImageDouble image, int x, int y) {

		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();

		if (x == -1 && y == -1) {
			return image.getPixel(x + 1, y + 1);

		}

		else if (x == -1 && y == imageHeight) {
			return image.getPixel(x + 1, y - 1);

		}

		else if (x == -1) {
			return image.getPixel(x + 1, y);
		}
		
		if (x == imageWidth && y == -1) {
			return image.getPixel(x - 1, y + 1);
		}
		
		else if (x == imageWidth && y == imageHeight) {
			return image.getPixel(x - 1, y - 1);
		}

		else if (x == imageWidth) {
			return image.getPixel(x - 1, y);
		}

		if (y == -1) {
			return image.getPixel(x, y + 1);

		}
		
		if (y == imageHeight) {
			return image.getPixel(x, y - 1);
		}

		return image.getPixel(x, y);

	}
}
