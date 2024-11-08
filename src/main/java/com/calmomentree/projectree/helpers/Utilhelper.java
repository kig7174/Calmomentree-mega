package com.calmomentree.projectree.helpers;

import org.springframework.stereotype.Component;

@Component
public class Utilhelper {

    public int random(int min, int max) {
        int num = (int) ((Math.random() * (max - min + 1)) + min);
        return num;
    }
}
