package tests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class N11PromotionLinksWriteToCSV {


    @Test
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.n11.com/kampanyalar");
        driver.manage().window().maximize();

        //'Moda' Campaign Promotion webpage defined differently than rest of other categories, xpath was opening from span section
        WebElement Moda = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/div/ul/li[2]/span"));
        Moda.click();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("N11 Kampanyalar Sayfasına girildi ve Moda kategorisine tıklandı.");



        for (int i = 3; i < 12; i++) {
            System.out.println(i);
            WebElement Page = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/div/div/ul/li[" + i + "]"));
            Page.click();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Sayfa açma işlemi başarılı.");

        }

        try {
            File oldFileDelete = new File("files/N11PromotionLinks.csv");

            //Dosya kontrolü
            if (!oldFileDelete.exists())
                throw new IllegalArgumentException("CSV dosyası bulunamıyor."
                        + oldFileDelete.getAbsolutePath());

            if (oldFileDelete.delete()) {
                System.out.println("Csv dosyası başarıyla temizlendi.");
            } else {
                System.out.println("Temizleme işlemi gerçekleşmedi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Kategoriler arasında ilerlemek için aşağıdaki döngü kullanıldı.
        int d = 2;
        // loop which gives output of categories
        for (int e = 2; e < 12; e++) {
            WebElement Title = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/section[" + e + "]/h2"));
            String name = Title.getText();
            System.out.println(name);
            // Listedeki öğe sayısına kadar kategorilerin ürünlerinin çıktısını veren döngü
            for (int b = 0; b < 100; b++) {

                List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/section[" + d + "]/ul/li[" + b + "]/a"));
                for (int a = 0; a < elements.size(); a++) {
                    System.out.println(elements.get(a).getAttribute("href"));
                    System.out.println(name);
                    try {
                        FileWriter fwriter = new FileWriter("files/N11PromotionLinks.csv", true);
                        fwriter.write(name);
                        fwriter.write("  ");
                        fwriter.write(elements.get(a).getAttribute("href"));
                        fwriter.write("\n");
                        fwriter.close();
                    } catch (Exception ex) {
                        System.out.println("Verileri csv dosyasına aktarırken hata oluştu.");
                        System.out.println(ex.getMessage());
                    }


                }

            }
            //bölüm kategori numarasına +1 ekler, böylece sonraki bölümden öğeler almaya devam ederiz.
            d = d + 1;

        }
        //Tüm işlemler tamamlandı, Driver kapatılıyor.
        driver.close();
    }
}
