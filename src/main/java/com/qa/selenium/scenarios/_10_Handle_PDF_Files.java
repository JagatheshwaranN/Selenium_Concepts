package com.qa.selenium.scenarios;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
		int pages = getPDFDocument().getNumberOfPages();
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
			String pdfFileContent = pdfTextStripper.getText(getPDFDocument());
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
			String pdfFileContent = pdfTextStripper.getText(getPDFDocument());
			System.out.println(pdfFileContent);
			Assert.assertTrue(pdfFileContent.contains("Sample Files"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		waitForSomeTime();
		driver.close();
	}

	private PDDocument getPDFDocument() {
		PDDocument document = null;
		try {
			URL url = new URL(pdfURL);
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
