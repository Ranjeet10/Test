package com.envent.bottlesup.test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by ronem on 5/18/18.
 */

public class TestMain {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {
        TestMain tm = new TestMain();
//        double v1 = 22.05;
//        double v2 = 22.00;
//        double v3 = 0;
//        double v4 = 22.5;
//        double v5 = 22.136;
//        double v6 = 2222.249;
//        System.out.println("Value for " + String.valueOf(v1) + "=" + tm.printFormated(v1));
//        System.out.println("Value for " + String.valueOf(v2) + "=" + tm.printFormated(v2));
//        System.out.println("Value for " + String.valueOf(v3) + "=" + tm.printFormated(v3));
//        System.out.println("Value for " + String.valueOf(v4) + "=" + tm.printFormated(v4));
//        System.out.println("Value for " + String.valueOf(v5) + "=" + tm.printFormated(v5));
//        System.out.println("Value for " + String.valueOf(v6) + "=" + tm.printFormated(v6));

//        String word = "\\u0628\\u0631\\u0646\\u0627\\u0645\\u0647 \\u0646\\u0648\\u06cc\\u0633\\u06cc";
//        System.out.print("Value for " + word + " is :" + tm.convertToUnicode(word));

        tm.tryBuilderPattern();


    }

    private void tryBuilderPattern(){
        Student s = new Student()
                .name("Ram Mandal")
                .address("Biratnagar");


        System.out.print("StudentInfo " + s.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String convertToUnicode(String normalText) {
        String balckSlasElapsed = normalText.replaceAll("\\.", "\'");
        byte[] bytes = balckSlasElapsed.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String printFormated(double value) {
        //input output
        //22.05 ->22.05
        //22.00 ->22.00
        //22    ->22.00
        //22.5  ->22.50
        //22.136  ->22.14
        //22.29  ->22.29

        //round Off and formatting upTo two decimal places

        double roundOff = Math.round(value * 100) / 100.00;
        if (value == 0) {
            return String.format(Locale.CANADA, "%.2f", roundOff);
        } else {
            return new DecimalFormat("#,###.00").format(roundOff);
        }

    }
}
