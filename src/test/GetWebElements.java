package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GetWebElements {

	public CaptureScreenShot objCapture = new CaptureScreenShot();

	public String getElementIsEnabled(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String isEnabled = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				isEnabled = pageElements.getIsEnabled();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return isEnabled;
	}

	public String getElementIsDisplayed(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String isDisplayed = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				isDisplayed = pageElements.getIsDisplayed();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return isDisplayed;
	}

	public String getElementIsSelected(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String isSelected = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				isSelected = pageElements.getIsSelected();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return isSelected;
	}

	public String getElementIsWaterMarkText(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String isWaterMarkText = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				isWaterMarkText = pageElements.getIsWaterMarkText();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return isWaterMarkText;
	}

	public String getElementDisplayName(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String elementDisplayName = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementDisplayName = pageElements.getElementDisplayName();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return elementDisplayName;
	}

	public String getElementType(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String elementType = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementType = pageElements.getElementType();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return elementType;
	}

	public String getElementText(WebDriver driver,List<PageElements> pageWebElements, String elementName) {
		String elementText = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementText = pageElements.getElementText();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return elementText;
	}

	public String getLocatorValue(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String locatorValue = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				locatorValue = pageElements.getLocatorValue();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return locatorValue;
	}

	public WebElement getWebElementOrNull(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		List<WebElement> matchingElements = getListElements(driver,
				pageWebElements, elementName);

		if (matchingElements.size() > 0){
			return matchingElements.get(0);
		}
		else {
			return null;
		}

	}
	
	public WebElement getWebElementOrNullValue(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		List<WebElement> matchingElements = getListElements(driver,
				pageWebElements, elementName);

		if (matchingElements.size() > 0)
			return matchingElements.get(0);
		else {
			BaseTest.log.error(elementName + " not found");
			BaseTest.commonErrorList.add(elementName + " not found");
			objCapture.captureScreen(driver, "Error_" + elementName, true,
					BaseTest.failFolder.toString());
			return null;
		}

	}

	public List<WebElement> getListElements(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String elementLocator = "";
		String locatorValue = null;
		List<WebElement> webElementList = null;
		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementLocator = pageElements.getElementLocator();
				locatorValue = pageElements.getLocatorValue();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}

		if (elementLocator.contains("id")) {
			webElementList = driver.findElements(By.id(locatorValue));
		} else if (elementLocator.contains("className")) {
			webElementList = driver.findElements(By.className(locatorValue));
		} else if (elementLocator.contains("linkText")) {
			webElementList = driver.findElements(By.linkText(locatorValue));
		}else if (elementLocator.contains("tagname")) {
			webElementList = driver.findElements(By.tagName(locatorValue));
		} else if (elementLocator.contains("name")) {
			webElementList = driver.findElements(By.name(locatorValue));
		} else if (elementLocator.contains("xpath")) {
			webElementList = driver.findElements(By.xpath(locatorValue));
		} else if (elementLocator.contains("partialLinkText")) {
			webElementList = driver.findElements(By
					.partialLinkText(locatorValue));
		} else if (elementLocator.contains("cssSelector")) {
			webElementList = driver.findElements(By.cssSelector(locatorValue));
		}else{try{
			webElementList = driver.findElements(By.xpath(elementName));
		}catch(Exception e){
		}
		}
		return webElementList;
	}

	public WebElement getVal(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String elementLocator = null;
		String locatorValue = null;
		WebElement webElement = null;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementLocator = pageElements.getElementLocator();
				locatorValue = pageElements.getLocatorValue();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		if (elementLocator.contains("id")) {
			webElement = driver.findElement(By.id(locatorValue));
		} else if (elementLocator.contains("class")) {
			webElement = driver.findElement(By.className(locatorValue));
		} else if (elementLocator.contains("linkText")) {
			webElement = driver.findElement(By.linkText(locatorValue));
		} else if (elementLocator.contains("xpath")) {
			// webElement =
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			webElement = driver.findElement(By.xpath(locatorValue));
		} else if (elementLocator.contains("name")) {
			webElement = driver.findElement(By.name(locatorValue));
		} else if (elementLocator.contains("partialLinkText")) {
			webElement = driver.findElement(By.partialLinkText(locatorValue));
		} else if (elementLocator.contains("cssSelector")) {
			webElement = driver.findElement(By.cssSelector(locatorValue));
		}else if (elementLocator.contains("tagname")) {
			webElement = driver.findElement(By.tagName(locatorValue));
		}
		return webElement;
	}

	public String getLocValue(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String locatorValue = null;

		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				locatorValue = pageElements.getLocatorValue();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return locatorValue;
	}
	
	public String getElementLocatorType(WebDriver driver,
			List<PageElements> pageWebElements, String elementName) {
		String elementLocator = null;

		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementLocator = pageElements.getElementLocator();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		return elementLocator;
	}
	
	public WebElement getDynamicVal(WebDriver driver,
			List<PageElements> pageWebElements, String elementName , String textToReplace , String replaceValue) {
		String elementLocator = null;
		String locatorValue = null;
		List<WebElement> webElement = null;

		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementLocator = pageElements.getElementLocator();
				locatorValue = pageElements.getLocatorValue().replace(textToReplace, replaceValue);
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}
		if (elementLocator.contains("id")) {
			webElement = driver.findElements(By.id(locatorValue));
		} else if (elementLocator.contains("class")) {
			webElement = driver.findElements(By.className(locatorValue));
		} else if (elementLocator.contains("linkText")) {
			webElement = driver.findElements(By.linkText(locatorValue));
		} else if (elementLocator.contains("xpath")) {
			webElement = driver.findElements(By.xpath(locatorValue));
		} else if (elementLocator.contains("name")) {
			webElement = driver.findElements(By.name(locatorValue));
		} else if (elementLocator.contains("partialLinkText")) {
			webElement = driver.findElements(By.partialLinkText(locatorValue));
		} else if (elementLocator.contains("cssSelector")) {
			webElement = driver.findElements(By.cssSelector(locatorValue));
		}else if (elementLocator.contains("tagname")) {
			webElement = driver.findElements(By.tagName(locatorValue));
		}
		if(webElement.size()>0){
			return webElement.get(0);
		}
		return null;
	}
	
	public List<WebElement> getDynamicListElements(WebDriver driver,
			List<PageElements> pageWebElements, String elementName, String textToReplace , String replaceValue) {
		String elementLocator = "";
		String locatorValue = null;
		String tagName = null;
		List<WebElement> webElementList = null;

		for (PageElements pageElements : pageWebElements) {
			if (pageElements.getElementName().equalsIgnoreCase(elementName)) {
				elementLocator = pageElements.getElementLocator();
				locatorValue = pageElements.getLocatorValue().replace(textToReplace, replaceValue);
				tagName = pageElements.getTagName();
				BaseTest.getLocatorStatus.put(pageElements.getPageName() + "###" + elementName, pageElements.getLocatorValue());
				break;
			}
		}

		if (elementLocator.contains("id")) {
			webElementList = driver.findElements(By.id(locatorValue));
		} else if (elementLocator.contains("className")) {
			webElementList = driver.findElements(By.className(locatorValue));
		} else if (elementLocator.contains("linkText")) {
			webElementList = driver.findElements(By.linkText(locatorValue));
		}else if (elementLocator.contains("tagname")) {
			webElementList = driver.findElements(By.tagName(locatorValue));
		} else if (elementLocator.contains("name")) {
			webElementList = driver.findElements(By.name(locatorValue));
		} else if (elementLocator.contains("xpath")) {
			webElementList = driver.findElements(By.xpath(locatorValue));
		} else if (elementLocator.contains("partialLinkText")) {
			webElementList = driver.findElements(By
					.partialLinkText(locatorValue));
		} else if (elementLocator.contains("cssSelector")) {
			webElementList = driver.findElements(By.cssSelector(locatorValue));
		}else{try{
			webElementList = driver.findElements(By.xpath(elementName));
		}catch(Exception e){
		}
		}
		return webElementList;
	}

}
