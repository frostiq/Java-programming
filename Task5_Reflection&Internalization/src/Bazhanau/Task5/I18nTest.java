package Bazhanau.Task5;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class I18nTest {
    public static void main(String[] args) {
        ArrayList<Locale> locales = new ArrayList<>();
        locales.add(Locale.ROOT);
        locales.add(Locale.US);
        locales.add(Locale.UK);
        locales.add(Locale.CHINA);
        locales.add(new Locale("be", "BY"));
        locales.add(new Locale("ru", "RU"));

        DateFormat dateFormat;
        Date date = new Date();
        System.out.println(date);


        NumberFormat numberFormat;
        Double number = -1.123;
        System.out.println(number);

        System.out.println();

        for (Locale locale : locales) {
            System.out.println(locale.getDisplayName());

            dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
            System.out.println(dateFormat.format(date));
            dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
            System.out.println(dateFormat.format(date));
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
            System.out.println(dateFormat.format(date));

            numberFormat = NumberFormat.getCurrencyInstance(locale);
            System.out.println(numberFormat.format(number));

            System.out.println();
        }
    }
}
