package com.qa.selenium.scenarios;

import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _10_Handle_PDF_Files {

	private WebDriver driver;
	String pdfURL = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf";

	@Test(priority = 1, enabled = false)
	private void getPDFFilePageCount() {
		browserSetup();
		driver.get(pdfURL);
		int pages = getPDFDocument(pdfURL).getNumberOfPages();
		Assert.assertEquals(pages, 4);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = false)
	private void getPDFFileContent() {
		browserSetup();
		driver.get(pdfURL);
		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String pdfFileContent = pdfTextStripper.getText(getPDFDocument(pdfURL));
			System.out.println(pdfFileContent);
			Assert.assertTrue(pdfFileContent.contains("PDF BOOKMARK SAMPLE"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = false)
	private void setPageNumberAndGetPDFFileContent() {
		browserSetup();
		driver.get(pdfURL);
		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			pdfTextStripper.setStartPage(3);
			String pdfFileContent = pdfTextStripper.getText(getPDFDocument(pdfURL));
			System.out.println(pdfFileContent);
			Assert.assertTrue(pdfFileContent.contains("Sample Files"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = false)
	private void readPDFFileFromBrowser() {
		browserSetup();
		driver.get("https://www.inkit.com/blog/pdf-the-best-digital-document-management");
		driver.findElement(By.id("ecSubmitAgreeButton")).click();
		waitForSomeTime();
		driver.findElement(By.xpath("//a[text()='trillions of PDFs']")).click();
		waitForSomeTime();
		String pdfFileURL = driver.getCurrentUrl();
		try {
			URL url = new URL(pdfFileURL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "Chrome"); // Optional statement
			InputStream ipInputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(ipInputStream);
			PDDocument pdDocument = PDDocument.load(bufferedInputStream);
			int pdfFilePages = pdDocument.getNumberOfPages();
			Assert.assertEquals(pdfFilePages, 43);
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String pdfFileContent = pdfTextStripper.getText(pdDocument);
			System.out.println(pdfFileContent);
			Assert.assertTrue(pdfFileContent.contains("PDF Days Europe 2018"));
			Assert.assertTrue(pdfFileContent.contains("Thank you! Any questions?"));
			pdDocument.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private void getPDFFileMetaData() {
		browserSetup();
		driver.get("https://www.inkit.com/blog/pdf-the-best-digital-document-management");
		driver.findElement(By.id("ecSubmitAgreeButton")).click();
		waitForSomeTime();
		driver.findElement(By.xpath("//a[text()='trillions of PDFs']")).click();
		waitForSomeTime();
		String pdfFileURL = driver.getCurrentUrl();
		try {
			URL url = new URL(pdfFileURL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "Chrome"); // Optional statement
			InputStream ipInputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(ipInputStream);
			PDDocument pdDocument = PDDocument.load(bufferedInputStream);
			System.out.println("PDF File Version 		  ==> " + pdDocument.getVersion());
			System.out.println("PDF File Print Option 	  ==> " + pdDocument.getCurrentAccessPermission().canPrint());
			System.out.println("PDF File ReadOnly 		  ==> " + pdDocument.getCurrentAccessPermission().isReadOnly());
			System.out.println(
					"PDF File Owner Permission ==> " + pdDocument.getCurrentAccessPermission().isOwnerPermission());
			System.out.println("PDF File Author 		  ==> " + pdDocument.getDocumentInformation().getAuthor());
			System.out.println("PDF File Subject 		  ==> " + pdDocument.getDocumentInformation().getSubject());
			System.out.println("PDF File Title            ==> " + pdDocument.getDocumentInformation().getTitle());
			System.out.println("PDF File Creator          ==> " + pdDocument.getDocumentInformation().getCreator());
			System.out
					.println("PDF File Creator Date     ==> " + pdDocument.getDocumentInformation().getCreationDate());
			System.out.println("PDF File Encrypted        ==> " + pdDocument.isEncrypted());
			System.out.println("PDF File Document Id      ==> " + pdDocument.getDocumentId());
			pdDocument.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		waitForSomeTime();
		driver.close();

	}

	private PDDocument getPDFDocument(String pdfFileURL) {
		PDDocument document = null;
		try {
			URL url = new URL(pdfFileURL);
			InputStream inputStream = url.openStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			document = PDDocument.load(bufferedInputStream);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * =================================================================== Below
	 * code section Helps in dealing with PDF File image/s validation
	 * ====================================================================
	 */

	@SuppressWarnings("unused")
	private List<RenderedImage> getImagesFromPDFDocument(PDDocument document) {
		List<RenderedImage> images = new ArrayList<>();
		for (PDPage page : document.getPages()) {
			images.addAll(getImagesFromResource(page.getResources()));
		}
		return images;
	}

	private List<RenderedImage> getImagesFromResource(PDResources resources) {
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

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
