package concepts.bidi;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.v119.fetch.Fetch;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.BlockedReason;
import org.openqa.selenium.devtools.v119.network.model.ResourceType;
import org.openqa.selenium.devtools.v119.network.model.Response;
import org.openqa.selenium.devtools.v119.security.Security;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;

public class _23_Bidirectional_API {

	private WebDriver driver;
	private ChromeDriver chromeDriver;

	@Test(priority = 3, enabled = false)
	private void handleJSException() {
		chromeBrowserSetup();
		DevTools devTools = chromeDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		List<JavascriptException> jsExceptions = new ArrayList<>();
		Consumer<JavascriptException> addEntry = jsExceptions::add;
		devTools.getDomains().events().addJavascriptExceptionListener(addEntry);
		chromeDriver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/WebTable.html");
		WebElement link = chromeDriver.findElement(By.xpath("//div//a[text()='Canada']"));
		((JavascriptExecutor) chromeDriver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
				link, "onclick", "throw new Error('JavaScriptException Demo!')");
		link.click();
		for (JavascriptException jse : jsExceptions) {
			System.out.println("JS Exception Message : " + jse.getMessage());
			System.out.println("JS Exception SysInfo : " + jse.getSystemInformation());
			jse.printStackTrace();
		}
		waitForSomeTime();
		chromeDriver.close();
	}

	// Websocket - It is a next generation bidirectional communication technology for web 
	// applications which operates over a single socket. It's exposed a javascript interface
	// in a HTML5 component browsers. It's makes it possible to open a 2way interactive
	// communication session between the user browser (client) and server.
	// Using this API we can send the messages to the server and receive event driven responses
	// without calling the server for a while.
	// Many modern chat applications for example Whatsapp, viber and multiplier games uses 
	// underneath Websockets to operate. So, we are able to work with them.
	@Test(priority = 8, enabled = false)
	private void websocketOperations() {
		chromeBrowserSetup();
		DevTools devTools = chromeDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.webSocketCreated(), socket -> {
			System.out.println("WebSocket Created");
			System.out.println(socket.getUrl());
			System.out.println(socket.getRequestId());
			System.out.println(socket.getInitiator().get().getLineNumber());
		});
		
		devTools.addListener(Network.webSocketFrameReceived(), socket -> {
			System.out.println("WebSocket Received");
			System.out.println(socket.getResponse().getPayloadData());
			System.out.println(socket.getResponse().getOpcode());
		});

		devTools.addListener(Network.webSocketFrameError(), socket -> {
			System.out.println(socket.getErrorMessage());
		});
		
		devTools.addListener(Network.webSocketClosed(), socket -> {
			System.out.println("WebSocket Closed");
			System.out.println(socket.getTimestamp());
		});
		
		chromeDriver.get("https://www.piesocket.com/websocket-tester");
		waitForSomeTime();
		WebElement connect = chromeDriver.findElement(By.xpath("//button[@type='submit']"));
		new Actions(chromeDriver).scrollToElement(connect).perform();
		connect.click();
		waitForSomeTime();
		WebElement disconnect = chromeDriver.findElement(By.xpath("//button[text()='Disconnect']"));
		disconnect.click();
		waitForSomeTime();
		chromeDriver.close();
	}

	// Event source messages - On live Websockets, this even source messages sometimes they are called as
	// server sent events or one-way messaging are indirectional. The data messages are delivered in 
	// one direction from the server to the client (basically to the browser) and we can use them when
	// there is no need to send data from the client to the server in message form. 
	// For example, event source messages are good for handling social media status updates, news feeds,
	// delivering data into client-side storage mechanism.
	@Test(priority = 8, enabled = false)
	private void eventSourceMessages() {
	chromeBrowserSetup();
	DevTools devTools = chromeDriver.getDevTools();
	devTools.createSessionIfThereIsNotOne();
	devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
	devTools.addListener(Network.eventSourceMessageReceived(), message -> {
		System.out.println(message.getEventName());
		System.out.println(Optional.ofNullable(message.getEventId()));
		System.out.println(message.getRequestId());
		System.out.println(message.getData());
	});
	chromeDriver.get("https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_sse");
	waitForSomeTime();
	chromeDriver.close();
	}
	
	@Test(priority = 9, enabled = true)
	private void captureHttpTraffic() {
		chromeBrowserSetup();
		DevTools devTools = chromeDriver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		List<Response> captureResponses = Collections.synchronizedList(new ArrayList<>());
		devTools.addListener(Network.responseReceived(), response -> {
			captureResponses.add(response.getResponse());
		});
		chromeDriver.get("https://www.selenium.dev/");
		assertNoErrorResponse(captureResponses);
		assertRequestMade(captureResponses, "https://www.selenium.dev/");
		assertRequestNotMade(captureResponses, "https://www.youtube.com/playlist?list=PLRdSclUtJDYXDEsWI0vwBmJxW17NgsaAk");
		assertNotLargeImagesRequested(captureResponses, 500);
		waitForSomeTime();
		chromeDriver.close();
	}
	
	private void assertNoErrorResponse(List<Response> captureResponses) {
		Boolean doWehaveErrorCodes = captureResponses.stream().anyMatch(response -> response.getStatus() > 400 && response.getStatus() < 599);
		Assert.assertFalse(doWehaveErrorCodes,"Error response detected on the page");
	}
	
	private void assertRequestMade(List<Response> captureResponses, String url) {
		Boolean anyRequestsMade = captureResponses.stream().anyMatch(response -> response.getUrl(). contains(url));
		Assert.assertTrue(anyRequestsMade, String.format("Request %s not made.", url));
	}
	
	private void assertRequestNotMade(List<Response> captureResponses, String url) {
		Boolean anyRequestsMade = captureResponses.stream().anyMatch(response -> response.getUrl(). contains(url));
		Assert.assertFalse(anyRequestsMade, String.format("Request %s made.", url));
	}
	
	private void assertNotLargeImagesRequested(List<Response> captureResponses, int contentLength) {
		Boolean doWehaveLargeImages = captureResponses.stream().anyMatch(response -> response.getMimeType() == ResourceType.IMAGE.toString() &&
				response.getHeaders() != null && Integer.parseInt(response.getHeaders().get("Content-Length").toString()) < contentLength);
		Assert.assertFalse(doWehaveLargeImages, String.format("Images larger than %s size detected.", contentLength));
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver chromeBrowserSetup() {
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().window().maximize();
		return chromeDriver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
