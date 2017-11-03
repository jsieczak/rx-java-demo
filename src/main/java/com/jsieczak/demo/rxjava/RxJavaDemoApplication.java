package com.jsieczak.demo.rxjava;

import java.util.Arrays;
import java.util.Date;

import static java.lang.Math.pow;

public class RxJavaDemoApplication {
    public static void main(String[] args) {
//          SpringApplication.run(RxJavaDemoApplication.class, args);
        int size = 0xB;
        int []ints = new int[2];
        Arrays.sort(ints,0,4);
    }

}


 class A {
    int age;
    String name;
    void method() {
        System.out.println("A");
    }

     protected void aMethod(){

     };
}

class B extends A {
    public B(int a, String n) {
    age=a;
    name=n;
    }

    @Override
    void method() {
        System.out.println("B");
    }
    boolean check(short x){
        if(x<10) return true;
        else return false;
    }
    static double divide(int i, int j){
        return i/j;
    }
    @Override
    public void aMethod() {
        System.out.print(true);
    }

}