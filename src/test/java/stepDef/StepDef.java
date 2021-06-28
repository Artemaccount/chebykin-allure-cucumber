package stepDef;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StepDef {

    public static void screenshot(WebDriver driver) {
        Allure.addAttachment("Скриншот", new ByteArrayInputStream(((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES)));
    }

    @Пусть("открыт ресурс Авито")
    public static void openAvito() {
        Hook.getDriver().manage().window().maximize();
        Hook.getDriver().get("https://www.avito.ru/");
        screenshot(Hook.getDriver());
    }

    @ParameterType(".*")
    public Category category(String categoryName) {
        return Category.valueOf(categoryName);
    }

    @И("в выпадающем списке категорий выбрана {category}")
    public static void chooseCat(Category category) {
        Select categories = new Select(Hook.getDriver().findElement(By.xpath("//select[@name='category_id']")));
        categories.selectByValue(category.getNumber());
        screenshot(Hook.getDriver());
    }

    @И("в поле поиска введено значение {word}")
    public static void printPrinter(String string) throws InterruptedException {
        synchronized (Hook.getWaiter()) {
            Hook.getWaiter().wait(2000);
        }
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/suggest']")).sendKeys(string);
        screenshot(Hook.getDriver());
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public static void clickReg() throws IOException {
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/region']")).click();
        screenshot(Hook.getDriver());
    }

    @Тогда("в поле регион ввести значение {word}")
    public static void printVladivostok(String string) {
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='popup-location/region/input']"))
                .sendKeys(string);
        screenshot(Hook.getDriver());
    }

    @И("нажать кнопка показать объявления")
    public static void showAds() throws InterruptedException {
        synchronized (Hook.getWaiter()) {
            Hook.getWaiter().wait(1500);
        }
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='suggest(0)']")).click();
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='popup-location/save-button']")).click();
        screenshot(Hook.getDriver());
    }

    @Тогда("открылась страница результаты по запросу {word}")
    public static void openPage(String expected) {
        String actual = Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/suggest']"))
                .getAttribute("value");
        if(actual.equalsIgnoreCase(expected)){
            System.out.println("Всё ок, та загрузилась");
        }
        screenshot(Hook.getDriver());
    }

    @И("активирован чекбокс только с фотографией")
    public static void checkBox() {
        List<WebElement> elements =
                Hook.getDriver().findElements(By.xpath("//*[contains(@class,'checkbox-size-s-yHrZq')]"));
        elements.get(1).click();
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/submit-button']")).click();
        screenshot(Hook.getDriver());
    }

    @И("в выпадающем списке сортировка выбрано значение {category}")
    public static void sortBy(Category category) {
        List<WebElement> elements =
                Hook.getDriver().findElements(By.xpath("//*[@class='select-select-3CHiM']"));
        Select categories = new Select(elements.get(1));
        categories.selectByValue(category.getNumber());
        screenshot(Hook.getDriver());
    }

    @И("в консоль выведено значение название и цены {int} первых товаров")
    public static void printFirstThree(int firstItemsCount) throws IOException {
        List<WebElement> elements = Hook.getDriver().findElements(By.xpath("//div[@data-marker='item']"));
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < firstItemsCount; i++) {
            String input = (elements.get(i)
                    .findElement(By.xpath(".//*[@data-marker='item-title']")))
                    .getAttribute("title");
            String name = input.replace(" в Владивостоке", "");
            int price = Integer.parseInt(elements.get(i)
                    .findElement(By.xpath(".//*[@itemprop='price']"))
                    .getAttribute("content"));
            Item item = new Item(name, price);
            items.add(item);
        }
        items.forEach(System.out::println);
        screenshot(Hook.getDriver());
    }
}