package pageObjects;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CaseStatus {

	WebDriver driver = null;

	public CaseStatus(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "receipt_number")
	WebElement receiptNumber;

	@FindBy(xpath = "//input[@class='btn2 border-radius5']")
	WebElement chkstatusBtn;

	@FindBy(xpath = "//div[@class='rows text-center']/h1")
	WebElement result;

	@FindBy(xpath = "//div[contains(@class,'rows text-center')]/p")
	WebElement fullResult;

	@FindBy(xpath = "//span[contains(text(),'OK')]")
	List<WebElement> okAlert;

	@FindBy(xpath = "//div[@id='popupDialogArea']//following::li")
	WebElement msgOnAlert;

	public void enterReceiptNumber(String recNum) {
		receiptNumber.sendKeys(recNum);
	}

	public void clickchkstatusBtn() {
		chkstatusBtn.click();

	}

	public String getResult() {
		return result.getText();
	}

	public String getFullResult() {
		return fullResult.getText();
	}

	public String isI765Present() {
		String fulltext = fullResult.getText();
		String True = "true";
		String False = "false";
		if (fulltext.contains("I-765")) {
			return True;
		}
		return False;
	}

	public void clickOkonAlertifPresent() {

		okAlert.get(0).click();

	}

	public boolean alertDisplayed() {

		if (okAlert.size() > 0) {
			return true;
		}
		return false;
	}

	public String getMsgOnAlert() {
		return msgOnAlert.getText();
	}

}
