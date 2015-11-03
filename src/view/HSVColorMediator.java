package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import model.Conversion;
import model.ObserverIF;
import model.Pixel;

/***
 * Classe permettant de calculer les sliders pour HSV
 * 
 * @author SuperFacile exemple http://colorizer.org/
 */
public class HSVColorMediator extends Object implements SliderObserver, ObserverIF {

	public Color returnTest() {
		Color c = Color.getHSBColor((float) 0.25, (float) 0.9, (float) 0.9);
		return c;
	}

	public void setHue(float hue) {
		this.hue = hue;
	}

	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	public void setValue(float value) {
		this.value = value;
	}

	ColorSlider hueCS;
	ColorSlider saturationCS;
	ColorSlider valueCS;
	float hue;
	float saturation;
	float value;
	BufferedImage hueImage;
	BufferedImage saturationImage;
	BufferedImage valueImage;
	int imagesWidth;
	int imagesHeight;
	ColorDialogResult result;

	public HSVColorMediator(ColorDialogResult result, int imagesWidth, int imagesHeight) {
		this.imagesWidth = imagesWidth;
		this.imagesHeight = imagesHeight;

		float recievedHue[] = Conversion.RGBtoHSV(result.getPixel());

		this.hue = recievedHue[0];
		this.saturation = recievedHue[1];
		this.value = recievedHue[2];
		this.result = result;
		result.addObserver(this);

		hueImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		saturationImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		valueImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		computeHueImage(hue, saturation, value);
		computeSaturationImage(hue, saturation, value);
		computeValueImage(hue, saturation, value);
	}

	/**
	 * @param slider
	 */
	public void setHueCS(ColorSlider slider) {
		hueCS = slider;

		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setSaturationCS(ColorSlider slider) {
		saturationCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setValueCS(ColorSlider slider) {
		valueCS = slider;
		slider.addObserver(this);
	}

	@Override
	public void update(ColorSlider cs, int v) {

		boolean updateHue = false;
		boolean updateSaturation = false;
		boolean updateValue = false;
		if (cs == hueCS && v != hue) {
			hue = ((float) v / (float) 255) * 360;
			updateSaturation = true;
			updateValue = true;
		}
		if (cs == saturationCS && v != saturation) {
			saturation = ((float) v / (float) 255);
			updateHue = true;
			updateValue = true;
		}
		if (cs == valueCS && v != value) {
			value = ((float) v / (float) 255);
			updateHue = true;
			updateSaturation = true;
		}
		if (updateHue) {
			computeHueImage(hue, saturation, value);
		}
		if (updateSaturation) {
			computeSaturationImage(hue, saturation, value);
		}
		if (updateValue) {
			computeValueImage(hue, saturation, value);
		}

		Pixel pixel = Conversion.HSVtoRGB(hue, saturation, value);
		result.setPixel(pixel);
	}

	//
	public void computeHueImage(float hue, float saturation, float value) {
		for (int i = 0; i < imagesWidth; ++i) {
			Pixel p = Conversion.HSVtoRGB(((float) i / (float) imagesWidth) * 360, saturation, value);
			int rgb = p.getARGB();
			// System.out.println(((float)i/(float)imagesWidth)*360);
			for (int j = 0; j < imagesHeight; ++j) {
				hueImage.setRGB(i, j, rgb);
			}
		}
		if (hueCS != null) {
			hueCS.update(hueImage);
		}
	}

	//
	public void computeSaturationImage(float hue, float saturation, float value) {

		for (int i = 0; i < imagesWidth; ++i) {
			Pixel p = Conversion.HSVtoRGB(hue, ((float) i / (float) imagesWidth), value);
			int rgb = p.getARGB();
			for (int j = 0; j < imagesHeight; ++j) {
				saturationImage.setRGB(i, j, rgb);
			}
		}
		if (saturationCS != null) {
			saturationCS.update(saturationImage);
		}
	}

	public void computeValueImage(float hue, float saturation, float value) {

		for (int i = 0; i < imagesWidth; ++i) {
			Pixel p = Conversion.HSVtoRGB(hue, saturation, ((float) i / (float) imagesWidth));
			int rgb = p.getARGB();
			for (int j = 0; j < imagesHeight; ++j) {
				valueImage.setRGB(i, j, rgb);
			}
		}
		if (valueCS != null) {
			valueCS.update(valueImage);
		}
	}

	/**
	 * @return
	 */
	public BufferedImage getValueImage() {
		return valueImage;
	}

	/**
	 * @return
	 */
	public BufferedImage getSaturationImage() {
		return saturationImage;
	}

	/**
	 * @return
	 */
	public BufferedImage getHueImage() {
		return hueImage;
	}

	/**
	 * @param slider
	 */
	public void setRedCS(ColorSlider slider) {
		hueCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setGreenCS(ColorSlider slider) {
		saturationCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setBlueCS(ColorSlider slider) {
		valueCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @return
	 */
	public double getSaturation() {
		return saturation;
	}

	/**
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return
	 */
	public double getHue() {
		return hue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.ObserverIF#update()
	 */
	public void update() {
		// When updated with the new "result" color, if the "currentColor"
		// is aready properly set, there is no need to recompute the images.
		 Pixel currentColor = Conversion.HSVtoRGB(hue, saturation, value);
		 if(currentColor.getARGB() == result.getPixel().getARGB()) return;

		hueCS.setValue((int) hue);
		saturationCS.setValue((int) saturation);
		valueCS.setValue((int) value);
		computeHueImage(hue, saturation, value);
		computeSaturationImage(hue, saturation, value);
		computeValueImage(hue, saturation, value);

		// Efficiency issue: When the color is adjusted on a tab in the
		// user interface, the sliders color of the other tabs are recomputed,
		// even though they are invisible. For an increased efficiency, the
		// other tabs (mediators) should be notified when there is a tab
		// change in the user interface. This solution was not implemented
		// here since it would increase the complexity of the code, making it
		// harder to understand.
	}

}
