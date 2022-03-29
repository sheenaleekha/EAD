package pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcessingTimes {
	
	WebDriver driver=null;
	
	public ProcessingTimes(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(id="selectForm")
	private WebElement selectionDropDown;
	
	@FindBy(id="officeOrCenter")
	private WebElement fieldOfficeDropDown;
	
	@FindBy(id="getProcTimes")
	private WebElement getProcTimesBtn;
	
	@FindBy(id="more")
	private WebElement readLinesBtn;
	
	@FindBy(id="resultList")
	private WebElement resultList;
	
	
	public void selectForm(String formName) {
		Select selectDD = new Select(selectionDropDown);
		List<WebElement> allforms=selectDD.getOptions();
		for(WebElement myForm:allforms) {
			if(myForm.getText().contains(formName)) {
				selectDD.selectByVisibleText(myForm.getText());
			}
			
		}
		
	}
	
	public void selectFieldOffice(String centerName) {
		
		WebDriverWait wait= new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.elementToBeClickable(fieldOfficeDropDown));
		
		Select selectDD = new Select(fieldOfficeDropDown);
		List<WebElement> allcentres=selectDD.getOptions();
		
		for(WebElement mycenter:allcentres) {
			if(mycenter.getText().contains(centerName)) {
				selectDD.selectByVisibleText(mycenter.getText());
			}
			
		}
	}
	
	
	public void clickGetProcessingTimesBtn() throws InterruptedException {
		Thread.sleep(3000);
		if(getProcTimesBtn.isDisplayed()) {
			getProcTimesBtn.click();
		}		
	}
	
	public void clickreadLinesBtn() {
		WebDriverWait wait= new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.elementToBeClickable(readLinesBtn));
		readLinesBtn.click();
	}
	
	public Map<String,String>  getResult(String FormType) {
		//List<WebElement> estTimes= resultList.findElements(By.xpath("//ol[@id='resultList']/li/div[@id='est']"));
		Map<String,String> finalResult=new HashMap<String,String>();
		List<WebElement> formTypes= resultList.findElements(By.xpath("//ol[@id='resultList']/li/div[@id='est']//following-sibling::div[1]"));
		//List<WebElement> receiptDate= resultList.findElements(By.xpath("//ol[@id='resultList']/li/div[@id='est']//following-sibling::div[2]"));
		
		for(WebElement myForm:formTypes) {
			if(myForm.getText().contains(FormType)) {
				//String estTimeXpath = myForm.
				WebElement receiptDate= myForm.findElement(By.xpath("following-sibling::div"));
				WebElement estTime= myForm.findElement(By.xpath("preceding-sibling::div"));
				
				finalResult.put("ReceiptDate", receiptDate.getText());
				finalResult.put("EstimatedTime", estTime.getText());
				return finalResult;			
			}
			
		}
		return finalResult;
		
	}
	

}
