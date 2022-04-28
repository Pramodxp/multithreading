package com.pramod.performanceOptimization.example1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LatencyImageProcessing {

	private static final String SOURCE_FILE = "./resources/b.jpg";
	private static final String DESTINATION_FILE = "./out/original-flower.jpg";

	public static void main(String[] args) throws IOException {
		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		// set the length,width,and image type.
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		long startTime = System.currentTimeMillis();
//		recolorSingleThreaded(originalImage, resultImage);
		recolorMultiThreaded(originalImage, resultImage, 4);
		long endTime = System.currentTimeMillis();

		File outputFile = new File(DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", outputFile);
		long duration = endTime - startTime;
		System.out.println("process finished in duration of : " + duration);
	}

	public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
		recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
	}

	public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage,
			int numberOfThreads) {
		List<Thread> threads = new ArrayList<>();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight() / numberOfThreads;

		for (int i = 0; i < numberOfThreads; i++) {
			final int threadMultiplier = i;

			Thread thread = new Thread(() -> {
				int leftCornor = 0;
				int rightCornor = height * threadMultiplier;

				recolorImage(originalImage, resultImage, leftCornor, rightCornor, width, height);
			});

			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner,
			int topCorner, int width, int height) {
		for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
			for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
				recolorPixel(originalImage, resultImage, x, y);
			}

		}
	}

	public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		int rgb = originalImage.getRGB(x, y);

		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);

		int newRed;
		int newGreen;
		int newBlue;

		if (isShadeOfGray(red, green, blue)) {
			newRed = Math.min(255, red + 10);
			newGreen = Math.max(0, green - 80);
			newBlue = Math.max(0, blue - 20);
		} else {
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}

		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		setRGB(resultImage, x, y, newRGB);
	}

	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}

	// taking red, green and blue and building the compound pixel out of it.
	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;

		// since the green value is the right most in the ARGB value
		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;

		// to make the pixel opaque
		rgb |= 0xFF000000;

		return rgb;
	}

	// determineof the pixel is shade of grey.
	// this checks all three colors has the same color intensity.
	public static boolean isShadeOfGray(int red, int green, int blue) {
		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(blue - green) < 30;
	}

	// below methods are used to get the infividual rgb colors.
	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}

	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8;
	}

	public static int getBlue(int rgb) {
		return rgb & 0x000000FF;
	}
}
