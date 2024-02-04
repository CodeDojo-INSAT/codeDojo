package com.zs.codeDojo.models.checkTestCases;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String A=sc.next();
        String B=sc.next();

        System.out.println((A + B).length());
        System.out.println(A.length() > B.length() ? "Yes" : "No");
        System.out.println(A.substring(0, 1).toUpperCase() + A.substring(1) + " " + B.substring(0,1).toUpperCase() + B.substring(1));

        sc.close();
    }
}