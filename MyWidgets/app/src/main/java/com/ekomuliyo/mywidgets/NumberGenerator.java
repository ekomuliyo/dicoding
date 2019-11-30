package com.ekomuliyo.mywidgets;

import java.util.Random;

public class NumberGenerator {

    public static int Generator(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

}
