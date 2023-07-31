package org.example;

import java.util.Random;

// Parašykite automatinį testą, tikrinantį internetinės svetainės www.livinn.lt registracijos formą.

public class Generator {
    public static boolean echo = true;

    public static String rndName() {
        String ABC = "ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz-'";
        Random random = new Random();
        int length = random.nextInt(3, 255);    // Atsitiktinis vardo ilgis nuo 3 iki 255 simbolių.
        String result = rndStr(ABC, length);
        if (echo) {
            System.out.println(result);
        }
        return result;
    }

    public static String rndStr(String symbols, int length) {
        Random random = new Random();
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            char letter = symbols.charAt(index);
            name.append(letter);
        }
        return name.toString();
    }
    public static String rndEmail() {
        String ABC = "ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz-'.";
        String randomName = rndStr(ABC, 110);
        String result = randomName + "." + randomName + "@gmail.com";
        if (echo) {
            System.out.println(result);
        }
        return result;
    }


    public static String rndPhoNo() {
        String numbers = "0123456789";
        String phoneNumber = "6" + rndStr(numbers, 7);
        if (echo) {
            System.out.println(phoneNumber);
        }
        return phoneNumber;
    }

    public static String rndPassword() {
        String ABC = "ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz-#@$$^%$W14182'";
        Random random = new Random();
        int length = random.nextInt(8, 255);    // Atsitiktinis paswordo ilgis nuo 8 iki 255 simbolių.
        String result = rndStr(ABC, length);
        if (echo) {
            System.out.println(result);
        }
        return rndStr(ABC, length);
    }

}


