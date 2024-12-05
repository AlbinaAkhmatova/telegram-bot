package org.example;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Jsoup;
import org.jsoup.Connection;



public class POSTs {
    private static final String urlString = "https://www.astroworld.ru/horon/person_gpt.htm?ysclid=m3i6kh22iy36893650";
    public static void getPosts(String BirthDateDay, String BirthDateMonth, String BirthDateYear, String BirthPlace, String birtHour, String birthMinute, Bot bot, Long id) {
        try {
            Connection.Response response = Jsoup.connect(urlString).method(Connection.Method.POST).execute();
            int status = response.statusCode();
            if (status == 200) {
                System.out.println("Соединение успешно");
            } else System.out.println("Ошибка соединения: " + status);
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Альбина\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
            /*ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");*/
            WebDriver driver = new ChromeDriver();
            try {
                driver.get(urlString);
                WebElement dayField = driver.findElement(By.id("den"));
                WebElement monthField = driver.findElement(By.id("mes"));
                WebElement yearField = driver.findElement(By.id("god"));
                WebElement hourField = driver.findElement(By.id("chas"));
                WebElement minuteField = driver.findElement(By.name("minuta"));
                WebElement cityField = driver.findElement(By.name("city"));
                //WebElement hintsField = driver.findElement(By.id("hints"));

                dayField.sendKeys(BirthDateDay);
                monthField.sendKeys(BirthDateMonth);
                yearField.sendKeys(BirthDateYear);
                hourField.clear();
                hourField.sendKeys(birtHour);
                minuteField.clear();
                minuteField.sendKeys(birthMinute);
                cityField.sendKeys(BirthPlace);
                try {
                    driver.findElement(By.tagName("ul")).click();
                } catch (Exception e) {
                    //...
                    System.out.println("Ошибка при вводе города");
                }
                WebElement submitButton = driver.findElement(By.name("Submit"));
                submitButton.click();
                Gets getRequest = new Gets();
                getRequest.getGets(driver, bot, id);

            } catch (Exception e) {
                System.out.println("Ошибка при работе с Selenium: " + e.getMessage());
            } finally {
                //driver.quit();
            }


        } catch (Exception e) {
            System.out.println("Ошибка работы с сайтом");
            e.printStackTrace();
        }
    }
}