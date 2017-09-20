package me.hays.learn4j.jdk;

public class Apple {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(a==c);
        System.out.println(a.equals(c));
    }
}