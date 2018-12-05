import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


@RunWith(Parameterized.class)
public class RealizarCompra {

	String url;
	WebDriver driver;
	Actions acao;
	WebDriverWait wait;
	WebElement comboElement;
	JavascriptExecutor jse;
	String primeiraExecucao;
	public static String nomePasta;
		
	
	// Métodos para Leitura de Massa de Teste
		// Atributos da massa de teste
	private String sPNome, sUNome,sSenha, sEndereco, sCidade, sCEP, 
	sCelular, sNomeEndereco, sEmail,sDiaNasc,sMesNasc, sAnoNasc, resultadoEsperado;

		
		// Campos do arquivo de massa de teste
		public RealizarCompra(String sPNome, String sUNome,
				       String sEmail, String sSenha, 
				       String sDiaNasc, String sMesNasc, String sAnoNasc, String sEndereco,
				       String sCidade,String sCEP,String sCelular,String sNomeEndereco, String resultadoEsperado) {
			
			this.sPNome = sPNome;
			this.sUNome = sUNome;
			this.sEmail = sEmail;
			this.sSenha = sSenha;
			this.sDiaNasc = sDiaNasc;
			this.sMesNasc = sMesNasc;
			this.sAnoNasc = sAnoNasc;
			this.sEndereco = sEndereco;
			this.sCidade = sCidade;
			this.sCEP = sCEP;
			this.sCelular = sCelular;
			this.sNomeEndereco = sNomeEndereco;
			this.resultadoEsperado = resultadoEsperado;
			
		}
		
		// Leitura do arquivo
		@Parameters
		public static Collection<String[]> LerArquivo() throws IOException{
			return lerCSV("C:\\DBServer\\TesteAutomacao\\massa\\massaDB.csv");
		}
		
		// Collection Genérica de Leitura de Arquivo Texto
		public static Collection <String[]> lerCSV(String nomeMassa) throws IOException{
			List<String[]> dados = new ArrayList<String[]>();
			String linha;
			BufferedReader arquivo = new BufferedReader(new FileReader(nomeMassa));
			while ((linha = arquivo.readLine()) != null) {
				String campos[] = linha.split(";");
				dados.add(campos);
			}
			arquivo.close();
			return dados;
			
		}
	
	
	// Método para capturar e organizar evidências
		
		public void Print(String nomeArquivo) throws IOException {
			
			if(this.primeiraExecucao == "S") {
				nomePasta = "C:\\DBServer\\TesteAutomacao" +
		           new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime()).toString();
				
			}    
			
			File foto =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(foto, new File(nomePasta + "\\" + "Chrome" + "\\" + nomeArquivo + ".png"));
	
		}
		
	@BeforeClass
	public static void AntesDaClasse() {
		nomePasta = "C:\\DBServer\\TesteAutomacao" +
		           new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime()).toString();
	}
	
	
	@Before
	public void Iniciar() {
		
		url = "http://automationpractice.com";
		
		System.setProperty("webdriver.chrome.driver", 
					"C:\\DBServer\\TesteAutomacao\\drivers\\Chrome\\v2.44\\chromedriver.exe");
		driver = new ChromeDriver();
		acao = new Actions(driver);
		wait = new WebDriverWait(driver, 10);
		jse = (JavascriptExecutor)driver;
		
	}
	
	@After
	public void Finalizar() {
		
		//driver.quit();
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	
	public void CT01EscolherProduto() throws InterruptedException,IOException{
		
		String idCaso = "CT 01";
		
		//Tela Inicial
		driver.get(url);
		Print(idCaso+"\\"+"Passo 1 - Acessar URL");
		
		//Tela para escola de produto T-Shirt
	    driver.findElement(By.xpath("(//a[@href='http://automationpractice.com/index.php?id_category=5&controller=category'])[2]")).click();
	    Print(idCaso+"\\"+"Passo 2 - Acessar o menu 'T-Shirt'");
	    acao.moveToElement(driver.findElement(By.xpath("//div[2]/a/span")));
		driver.findElement(By.xpath("//div[2]/a/span")).click();
		Print(idCaso+"\\"+"Passo 3 - Selecionar item");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Print(idCaso+"\\"+"Passo 4 - Adicionar produto no carrinho");
		String nomeItem = driver.findElement(By.className("product-name")).getText();
		driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/div[4]/a/span")).click();
	    Print(idCaso+"\\"+"Passo 5 - Clicar no botão 'Proceed to checkout'");
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    
	    //Aba 01.Summary
	    //verifica se o item foi incluído na lista
	    jse.executeScript("scroll(0,250)", "");
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    assertEquals(nomeItem, driver.findElement(By.className("product-name")).getText());
	    assertEquals("1", driver.findElement(By.name("quantity_1_1_0_0")).getAttribute("value"));
	    Print(idCaso+"\\"+"Passo 6 - Verificar se o item foi incluído corretamente");	    
	    driver.findElement(By.xpath("//div[@id='center_column']/p[2]/a/span")).click();
	    Print(idCaso+"\\"+"Passo 7 - Acessar tela de login");
	    
	    //Aba 02.Sign in
	    driver.findElement(By.id("email_create")).clear();
		driver.findElement(By.id("email_create")).sendKeys(sEmail);
		Print(idCaso+"\\"+"Passo 8 - Preencher campo E-mail");
		driver.findElement(By.id("SubmitCreate")).click();
		Print(idCaso+"\\"+"Passo 8 - Clicar  no botão 'Created an account' para acessar o formulário de cadastro");
		
		
		//preenchimento do formulário de cadastro
		driver.findElement(By.id("uniform-id_gender1")).click();
		driver.findElement(By.id("customer_firstname")).clear();
		driver.findElement(By.id("customer_firstname")).sendKeys(sPNome);
		driver.findElement(By.id("customer_lastname")).clear();
		driver.findElement(By.id("customer_lastname")).sendKeys(sUNome);
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(sEmail);
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(sSenha);
		Print(idCaso+"\\"+"Passo 9 - Preencher campos do formulário");
		Select comboboxDia = new Select(driver.findElement(By.id("days")));
		comboboxDia.selectByValue(sDiaNasc);
		Select comboboxMes = new Select(driver.findElement(By.id("months")));
		comboboxMes.selectByValue(sMesNasc);
		Select comboboxAno = new Select(driver.findElement(By.id("years")));
		comboboxAno.selectByValue(sAnoNasc);
		driver.findElement(By.id("address1")).clear();
		driver.findElement(By.id("address1")).sendKeys(sEndereco);
		String vEndereco = sEndereco;
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys(sCidade);
		Print(idCaso+"\\"+"Passo 9 - Preencher campos do formulário2");
		Select comboboxState = new Select(driver.findElement(By.id("id_state")));
		comboboxState.selectByValue("1");
		driver.findElement(By.id("postcode")).clear();
		driver.findElement(By.id("postcode")).sendKeys(sCEP);
		Select comboboxCountry = new Select(driver.findElement(By.id("id_country")));
		comboboxCountry.selectByValue("21");
		driver.findElement(By.id("phone_mobile")).clear();
		driver.findElement(By.id("phone_mobile")).sendKeys(sCelular);
		driver.findElement(By.id("alias")).clear();
		driver.findElement(By.id("alias")).sendKeys(sNomeEndereco);
		Print(idCaso+"\\"+"Passo 9 - Preencher campos do formulário3");
		driver.findElement(By.id("submitAccount")).click();
		Print(idCaso+"\\"+"Passo 10 - Clicar no botão Register para efetuar o cadastro");
		
		//Aba 03.Address
		//escolher endereço de entrega
		Select comboEntrega = new Select(driver.findElement(By.id("id_address_delivery")));
		comboEntrega.selectByVisibleText(sNomeEndereco);
		jse.executeScript("scroll(0,250)", "");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Print(idCaso+"\\"+"Passo 10 - Clicar no botão Register para efetuar o cadastro");
		
		assertEquals(vEndereco, driver.findElement(By.cssSelector("li.address_address1.address_address2")).getText());
		Print(idCaso+"\\"+"Passo 11 - Selecionar opção de endereço de entrega");
		driver.findElement(By.name("processAddress")).click();
		
		//Aba 04.Shipping		
		//Transportadora
		driver.findElement(By.id("cgv")).click();
		Print(idCaso+"\\"+"Passo 12 - Selecionar Transportadora");
		driver.findElement(By.name("processCarrier")).click();
		
		//Aba 05.Forma de Pagamento
		//Forma de Pagamento
		
		//Validar valor da compra
		//transformar variável String em Double
		String sPUnit = driver.findElement(By.xpath("//td[4]/span/span")).getText();
		String rPUnit = sPUnit.substring(1,6);
		double pUnit = Double.parseDouble(rPUnit);
		
		//transformar variável String em Double
		double qtde = Double.parseDouble(driver.findElement(By.cssSelector("td.cart_quantity.text-center > span")).getText());
		
		//transformar variável String em Double
		String sShipping = driver.findElement(By.id("total_shipping")).getText();
		String rShipping = sShipping.substring(1,5);
		double tShipping = Double.parseDouble(rShipping);
		
		//transformar variável String em Double
		String sTAX = driver.findElement(By.id("total_tax")).getText();
		String rTAX = sTAX.substring(1,5);
		double tTAX = Double.parseDouble(rTAX);
		
		//cálculo total da compra
		double total = (pUnit*qtde)+tShipping+tTAX;
		
		//converter variável total para String
		String sTotal = "$"+total;
		
		assertEquals(sTotal, driver.findElement(By.id("total_price_container")).getText());
		
		jse.executeScript("scroll(0,500)", "");
		Print(idCaso+"\\"+"Passo 13 - Selecionar Forma de pagamanto");
		driver.findElement(By.linkText("Pay by bank wire (order processing will be longer)")).click();
		jse.executeScript("scroll(0,250)", "");
		Print(idCaso+"\\"+"Passo 14 - Confirmar pagamento");
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		jse.executeScript("scroll(0,250)", "");
		assertEquals("Your order on My Store is complete.", driver.findElement(By.cssSelector("p.cheque-indent > strong.dark")).getText());
		Print(idCaso+"\\"+"Passo 15 - Pagamento realizado");
	    
	}
	
			
}
