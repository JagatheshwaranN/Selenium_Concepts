package com.qa.selenium.scenarios.pdf;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class HandlePDFTest {

	public static PDDocument getPDFDocument(String pdfFileURL) {
		PDDocument document = null;
		try {
			URL url = URI.create(pdfFileURL).toURL();
			InputStream inputStream = url.openStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			document = PDDocument.load(bufferedInputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * ====================================================================
	 * Below code section helps in dealing with PDF File image/s validation
	 * ====================================================================
	 */
	public static List<RenderedImage> getImagesFromPDFDocument(PDDocument document) {
		List<RenderedImage> images = new ArrayList<>();
		for (PDPage page : document.getPages()) {
			images.addAll(getImagesFromResource(page.getResources()));
		}
		return images;
	}

	public static List<RenderedImage> getImagesFromResource(PDResources resources) {
		List<RenderedImage> images = new ArrayList<>();
		for (COSName cosName : resources.getXObjectNames()) {
			PDXObject pdxObject = null;
			try {
				pdxObject = resources.getXObject(cosName);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (pdxObject instanceof PDFormXObject) {
				images.addAll(getImagesFromResource(((PDFormXObject) pdxObject).getResources()));
			} else if (pdxObject instanceof PDImageXObject) {
				try {
					images.add(((PDImageXObject) pdxObject).getImage());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return images;
	}

	public static void pdfBoxExtractImages(PDDocument document) {
		PDPageTree pdPageTree = document.getPages();
		for (PDPage pdPage : pdPageTree) {
			PDResources pdResources = pdPage.getResources();
			for (COSName cosName : pdResources.getXObjectNames()) {
				try {
					PDXObject pdxObject = pdResources.getXObject(cosName);
					if (pdxObject instanceof PDImageXObject) {
						File file = new File(
								System.getProperty("user.dir") + "//src//main//resources//supportFiles//pdfImages//" + System.nanoTime() + ".png");
						ImageIO.write(((PDImageXObject) pdxObject).getImage(), "png", file);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void compareImages(File file1, File file2) {
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		try {
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		assert image1 != null;
		int image1Width = image1.getWidth();
		assert image2 != null;
		int image2Width = image2.getWidth();
		int image1Height = image1.getHeight();
		int image2Height = image2.getHeight();
		if (image1Width != image2Width && image1Height != image2Height) {

			System.out.println("Error: Image1 and Image2 dimensions are mismatch");
		} else {
			long imagesDifference = 0;
			for (int yAxis = 0; yAxis < image1Height; yAxis++) {

				for (int xAxis = 0; xAxis < image1Width; xAxis++) {

					int image1RGBValue = image1.getRGB(xAxis, yAxis);
					int image2RGBValue = image2.getRGB(xAxis, yAxis);
					int image1RedValue = (image1RGBValue >> 16) & 0xff;
					int image1GreenValue = (image1RGBValue >> 8) & 0xff;
					int image1BlueValue = (image1RGBValue) & 0xff;
					int image2RedValue = (image2RGBValue >> 16) & 0xff;
					int image2GreenValue = (image2RGBValue >> 8) & 0xff;
					int image2BlueValue = (image2RGBValue) & 0xff;
					imagesDifference += Math.abs(image1RedValue - image2RedValue);
					imagesDifference += Math.abs(image1GreenValue - image2GreenValue);
					imagesDifference += Math.abs(image1BlueValue - image2BlueValue);
				}
			}
			double totalImagePixels = image1Width * image1Height * 3;
			double averageImageDifferencePixels = imagesDifference / totalImagePixels;
			double imageDifferenceInpercentage = (averageImageDifferencePixels / 255) * 100;
			System.out.println("Image Difference In Percentage: " + String.format("%.2f", imageDifferenceInpercentage));
		}
	}

}
