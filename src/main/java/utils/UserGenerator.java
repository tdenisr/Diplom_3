package utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {
    static private Faker fakerEn = new Faker(new Locale("en"));
    static private Faker fakerRu = new Faker(new Locale("ru"));

    public static User getRandomUser() {
        return new User()
                .withEmail(getRandomEmail())
                .withName(getRandomName())
                .withPassword(getRandomPassword());
    }

    public static String getRandomPassword() {
        return fakerEn.internet().password(6, 16);
    }

    public static String getRandomEmail() {
        return fakerEn.internet().emailAddress();
    }

    public static String getRandomName() {
        return fakerRu.name().username() + fakerRu.name().firstName();
    }

    public static String getRandomPassword(int minimumLength, int maximuLength) {
        return fakerEn.internet().password(minimumLength, maximuLength);
    }
}
