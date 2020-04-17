import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://127.0.0.1:8080/login')

WebUI.setText(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_concat(Nom d  utilisateur)_username'), 
    'toto')

WebUI.setEncryptedText(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_Mot de passe_password'), '4nvbrPglk7k=')

WebUI.sendKeys(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_Mot de passe_password'), Keys.chord(
        Keys.ENTER))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/a_Crer une tche'))

WebUI.setText(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_Nom de la tche _name'), 'Ressusciter Staline')

WebUI.setText(findTestObject('Object Repository/crud/Page_Dont forget to sleep/textarea_Description _description'), 'Notre glorieux leader Staline est passé à trépas depuis de trop longues années. Le temps de son grand retour est enfin arrivé!')

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_Description _btn btn-success'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/a_Modifier'))

WebUI.setText(findTestObject('Object Repository/crud/Page_Dont forget to sleep/textarea_Notre glorieux leader Staline est _8823a1'), 
    'Notre glorieux leader Staline est passé à trépas depuis de trop longues années. Le temps de son grand retour est enfin arrivé, pour notre grande joie!')

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_Description _btn btn-success'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/a_Marquer comme termine'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_toto_finished_tasks'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/a_Supprimer'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_toto_finished_tasks'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/input_toto_finished_tasks_1'))

WebUI.click(findTestObject('Object Repository/crud/Page_Dont forget to sleep/a_Logout'))

WebUI.closeBrowser()

