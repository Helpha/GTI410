package controller;

import model.ImageDouble;
import model.ImageX;
import model.Pixel;
import model.PixelDouble;

public class NormAbs255Strategy extends ImageConversionStrategy {
	
	double maxRed;
	double maxGreen;
	double maxBlue;
	double maxAlpha;
	
	
	@Override
	public ImageX convert(ImageDouble image) {
		
		
		int imageWidth = image.getImageWidth();
		int imageHeight = image.getImageHeight();
		ImageX newImage = new ImageX(0, 0, imageWidth, imageHeight);
		PixelDouble curPixelDouble = null;

		newImage.beginPixelUpdate();
		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				curPixelDouble = image.getPixel(x,y);
				
				newImage.setPixel(x, y, new Pixel((int)(normalizedValue(curPixelDouble.getRed(),curPixelDouble.getRed(),curPixelDouble.getGreen(),curPixelDouble.getBlue())),
												  (int)(normalizedValue(curPixelDouble.getGreen(),curPixelDouble.getRed(),curPixelDouble.getGreen(),curPixelDouble.getBlue())),
												  (int)(normalizedValue(curPixelDouble.getBlue(),curPixelDouble.getRed(),curPixelDouble.getGreen(),curPixelDouble.getBlue())),
												  (int)(normalizedValue(curPixelDouble.getAlpha(),curPixelDouble.getAlpha(),curPixelDouble.getAlpha(),curPixelDouble.getAlpha()))));
			}
		}
		newImage.endPixelUpdate();
		return newImage;
	}
	
	/**
	 * @param chosenColor couleur choisie
	 * @param red valeur rouge
	 * @param green valeur verte
	 * @param blue valeur bleue
	 * @return valeur normalisée
	 */
	private double normalizedValue(double chosenColor, double red, double green, double blue){
		//http://www.roborealm.com/help/Normalize%20Color.php
		double sum = 0;
		double result = 0; 
		sum = Math.abs(red) + Math.abs(blue) + Math.abs(green);
		result = (Math.abs(chosenColor)/sum)*255.0;
		return result;
	}
	

}
