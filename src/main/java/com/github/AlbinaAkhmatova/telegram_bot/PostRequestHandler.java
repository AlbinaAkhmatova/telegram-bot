package com.github.AlbinaAkhmatova.telegram_bot;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Jsoup;
import org.jsoup.Connection;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


public class PostRequestHandler {
    private static final String urlString = "https://www.astroworld.ru/horon/person_gpt.htm?ysclid=m3i6kh22iy36893650";

    public static void processPosts(String BirthDateDay, String BirthDateMonth, String BirthDateYear, String BirthPlace, String birtHour, String birthMinute, Bot bot, Long id) {
        try {
            checkConnection();
            WebDriver driver = initializeWebDriver();
            try {
                fillForm(driver, BirthDateDay, BirthDateMonth, BirthDateYear, birtHour, birthMinute, BirthPlace, bot, id);
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

    private static void checkConnection() throws IOException {
        Connection.Response response = Jsoup.connect(urlString).method(Connection.Method.POST).execute();
        int status = response.statusCode();
        if (status == 200) {
            System.out.println("Соединение успешно");
        } else {
            System.out.println("Ошибка соединения: " + status);
        }
    }

    private static WebDriver initializeWebDriver() throws Exception {
        File driverFile = new File(PostRequestHandler.class.getClassLoader().getResource("driver/chromedriver.exe").toURI());
        System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    private static void fillForm(WebDriver driver, String BirthDateDay, String BirthDateMonth, String BirthDateYear, String birtHour, String birthMinute, String BirthPlace, Bot bot, Long id) throws MalformedURLException, UnsupportedEncodingException {
        driver.get(urlString);
        WebElement dayField = driver.findElement(By.id("den"));
        WebElement monthField = driver.findElement(By.id("mes"));
        WebElement yearField = driver.findElement(By.id("god"));
        WebElement hourField = driver.findElement(By.id("chas"));
        WebElement minuteField = driver.findElement(By.name("minuta"));
        WebElement cityField = driver.findElement(By.name("city"));

        dayField.sendKeys(BirthDateDay);
        monthField.sendKeys(BirthDateMonth);
        yearField.sendKeys(BirthDateYear);
        hourField.clear();
        hourField.sendKeys(birtHour);
        minuteField.clear();
        minuteField.sendKeys(birthMinute);
        cityField.sendKeys(BirthPlace);

        if (!handleCityInput(driver, bot, id)) {
            return;
        }

        WebElement submitButton = driver.findElement(By.name("Submit"));
        submitButton.click();
        GetRequestHandler getRequest = new GetRequestHandler();
        getRequest.processGets(driver, bot, id);
        bot.status.removeUserState(id);
    }

    private static boolean handleCityInput(WebDriver driver, Bot bot, Long id) {
        try {
            driver.findElement(By.tagName("ul")).click();
            return true;
        } catch (Exception e) {
            bot.sendText(id, "Ошибка при вводе города: " + "\n"
                    + "1) Удостоверьтесь в том, что Вы правильно ввели город." + "\n"
                    + "2) Если же название Вашего города корректно, то попробуйте ввести город " +
                    "из вашей области (или региона), который, с большей вероятностью, " +
                    "должен быть в нашей базе!");
            bot.sendText(id, "Введите город ещё раз ");
            bot.status.setUserState(id, UserStatus.UserState.ENTERED_BIRTH_TIME);
            return false;
        }
    }
}