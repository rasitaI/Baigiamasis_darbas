import org.example.Generator;

import org.example.User;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class userRegisterTests {


    @Test
    public void possitiveTest() {
        User u = new User("Vardas", "Pavardė", "VardasPavarde4@gmail.com", "68514167", "Aa3456789");

        u.register();
        String text = "";
        try {
            text = User.driver.findElement(By.className("form-title")).getText();
        } catch (Exception e) {
        }
        Assert.assertEquals(text, "Please verify Your account.");
    }

    //    @Ignore
    @Test
    public void noName() {
        User u = new User("", Generator.rndName(), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("name"), "Please enter Your Name.");
    }

    //    @Ignore
    @Test
    public void spaceBeforeName() {
        User u = new User(" " + Generator.rndName(), Generator.rndName(), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("name"), "Remove space(s) at the beginning of Name.");
    }

    //    @Ignore
    @Test
    public void spaceAfterName() {
        User u = new User(Generator.rndName() + " ", Generator.rndName(), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("name"), "Remove spaces at the end of Name.");
    }

    @Test
    public void tooShortName() {
        User u = new User("A.", Generator.rndName(), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("name"), "Name is too short. It should be longer than 3 symbols.");
    }

    @Test
    public void tooLongName() {
        User u = new User("Aaaaaaaaaaaaaaaaaaaaa".repeat(20), Generator.rndName(), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("name"), "Name is too long. It should be no longer than 255 symbols.");
    }

    @Test
    public void noSurname() {
        User u = new User(Generator.rndName(), "", Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("surname"), "Please enter Your Surname.");
    }

    @Test
    public void tooShortSurname() {
        User u = new User(Generator.rndName(), "P.", Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("surname"), "Surname is too short. It should be longer than 3 symbols.");
    }

    @Test
    public void tooLongSurname() {
        User u = new User(Generator.rndName(), "Paaaaaaaaaaaaaaaaaaaav".repeat(20), Generator.rndEmail(), Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("surname"), "Surname is too long. It should be no longer than 255 symbols.");
    }

    @Test
    public void noPhoNo() {
        User u = new User(Generator.rndName(), Generator.rndName(), Generator.rndEmail(), "", Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("telephone"), "Please enter Your phone number.");
    }

    @Test
    public void tooShortPhoNo() {
        String PhoNo = Generator.rndPhoNo();
        String shortPhoNo = PhoNo.substring(0, PhoNo.length() - 1);
        User u = new User(Generator.rndName(), Generator.rndName(), Generator.rndEmail(), shortPhoNo, Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("telephone"), "Phone number format is invalid: too short.");
    }

    @Test
    public void tooLongPhoNo() {
        String PhoNo = Generator.rndPhoNo();
        User u = new User(Generator.rndName(), Generator.rndName(), Generator.rndEmail(), PhoNo + "0", Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("telephone"), "Phone number format is invalid: too long.");
    }

    @Test
    public void noEmailAddressTest() {
        User u = new User(Generator.rndName(), Generator.rndName(), "", Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("email"), "The field is empty. Please enter email address.");
    }

    @Test
    public void emailUserNoEtaNoDomainNoTopDomainTest() {
        String email = Generator.rndEmail();
        String fragmentToRemove = "@gmail.com";
        String emailNoEta = email.substring(0, email.length() - fragmentToRemove.length());
        User u = new User(Generator.rndName(), Generator.rndName(), emailNoEta, Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("email"), "Email format is invalid: no '@gmail.com' .");
    }

    @Test
    public void emailUserEtaDomainNoTopDomainTest() {
        String email = Generator.rndEmail();
        String fragmentToRemove = ".com";
        String emailNoEta = email.substring(0, email.length() - fragmentToRemove.length());
        User u = new User(Generator.rndName(), Generator.rndName(), emailNoEta, Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("email"), "Email format is invalid: no '.com' .");
    }

    @Test
    public void emailWithSpaceInUserTest() {
        String email = Generator.rndEmail();
        String fragmentToAdd = " ";
        String emailWithSpace = fragmentToAdd + email;
        User u = new User(Generator.rndName(), Generator.rndName(), emailWithSpace, Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("email"), "Email format is invalid: remove space in front.");
    }

    @Test
    public void emailTooLongTest() {
        User u = new User(Generator.rndName(), Generator.rndName(), Generator.rndName().repeat(2) + "@gmail.com", Generator.rndPhoNo(), Generator.rndPassword());
        u.register();
        Assert.assertEquals(errorMsg("email"), "Email format is invalid: too long part before '@'.");
    }

    @Test
    public void emailWithLeadingTreilingSpacesTest() {
        User u = new User(Generator.rndName(), Generator.rndName(), " kazkaska2zkur@arba.lt ", "63712354", "Aa3454569");
        u.register();
        Assert.assertEquals(errorMsg("email"), "Šis adresas yra neteisingas.");
    }

    @BeforeClass
    public void beforeClass() {
        User.driver = new ChromeDriver();
        User.driver.manage().window().maximize();
        User.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        acceptCookies();

        printGeneratedValues();
    }

    public static void printGeneratedValues() {
        String name = Generator.rndName();
        System.out.println(name);
        String email = Generator.rndEmail();
        System.out.println(email);
        String phoneNumber = Generator.rndPhoNo();
        System.out.println(phoneNumber);
        String password = Generator.rndPassword();
        System.out.println(password);
    }

    public String errorMsg(String field) {
        String errorMsg = "";
        int position = 0;

        switch (field) {
            case "name":
                position = 0;
                break;
            case "surname":
                position = 1;
                break;
            case "email":
                position = 2;
                break;
            case "phoNo":
                position = 3;
                break;
            case "password":
                position = 4;
                break;
        }
        try {
            errorMsg = User.driver.findElement(By.className("login-page-body--form")).findElements(By.className("form-block")).get(position).findElement(By.className("field-error")).getText();
        } catch (Exception e) {
        }
        return errorMsg;
    }

    public void acceptCookies() {
        User.driver.get("https://www.livinn.lt/register");
        User.driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    public void click() {
        User.driver.findElement(By.className("CloseButton__ButtonElement-sc-79mh24-0 qldFT tulsa-CloseButton tulsa-close tulsa-ClosePosition--top-right")).click();
    }

    @AfterClass
    public void afterClass() {
        click();
        //driver.quit();
    }


}
