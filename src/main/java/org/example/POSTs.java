package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Jsoup;
import org.jsoup.Connection;



public class POSTs {
    public static void main(String[] args) {
        String urlString = "https://www.astroworld.ru/horon/person_gpt.htm?ysclid=m3i6kh22iy36893650";
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
                WebElement hintsField = driver.findElement(By.id("hints"));

                dayField.sendKeys("22");
                monthField.sendKeys("сентябрь");
                yearField.sendKeys("2005");
                hourField.clear();
                hourField.sendKeys("9");
                minuteField.clear();
                minuteField.sendKeys("30");
                cityField.sendKeys("Екатеринбурд");
                try{
                    driver.findElement(By.tagName("ul")).click();
                }catch (Exception e){
                    //...
                    System.out.println("Ошибка при вводе города");
                }
                WebElement submitButton = driver.findElement(By.name("Submit"));
                submitButton.click();


            } catch (Exception e) {
                System.out.println("Ошибка при работе с Selenium: " + e.getMessage());
            } finally {
                driver.quit();
            }


        } catch (Exception e) {
            System.out.println("Ошибка работы с сайтом");
            e.printStackTrace();
        }
    }
}