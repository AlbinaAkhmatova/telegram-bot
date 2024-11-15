package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class POSTs {
    public static void main(String[] args) {
        String urlString = "https://www.astroworld.ru/horon/person_gpt.htm?ysclid=m3i6kh22iy36893650";
        String birthDate = "23";
        try {
            //пробная штука
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String postData = "birthDate=" + birthDate;


            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Альбина\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

            // экземпляр драйвера с опциями
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // без окна драйвера
            WebDriver driver = new ChromeDriver(options);

            try {
                //открываем страницу
                driver.get(urlString);

                // ищем ключевые поля ввода
                WebElement dayField = driver.findElement(By.id("den"));
                WebElement monthField = driver.findElement(By.id("mes"));
                WebElement yearField = driver.findElement(By.id("god"));
                WebElement hourField = driver.findElement(By.id("chas"));
                WebElement minuteField = driver.findElement(By.name("minuta"));
                WebElement cityField = driver.findElement(By.name("city"));

                // пример
                dayField.sendKeys("22");
                monthField.sendKeys("сентябрь");
                yearField.sendKeys("2005");
                hourField.clear();
                hourField.sendKeys("9");
                minuteField.clear();
                minuteField.sendKeys("30");
                cityField.sendKeys("Екатеринбург");

                // отправляем введённые значения
                WebElement submitButton = driver.findElement(By.name("Submit"));
                submitButton.click();

            } catch (Exception e) {
                System.out.println("Ошибка при работе с Selenium: " + e.getMessage());
            } finally {
                // закрытие драйвера
                driver.quit();
            }

            // отправка данных через HttpURLConnection (к пробной штуке)
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            // получаем код ответа (пробная штука)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // читаем заглавную страницу сайта, если все хорошо
            /*
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                String encoding = "UTF-8";

                if (contentType != null && contentType.contains("charset=")) {
                    encoding = contentType.substring(contentType.indexOf("charset=") + 8);
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Вывод ответа
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("Request failed");
            }*/

        } catch (Exception e) {
            System.out.println("Ошибка при отправке POST-запроса: " + e.getMessage());
        }
    }
}