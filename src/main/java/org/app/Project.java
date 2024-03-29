package org.app;

import com.sun.tools.javac.Main;
import org.app.Countries;

import java.io.IOException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args)  throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<메뉴를 선택하세요. >>");
        System.out.println("1. 여행경보 발령 현황 일람");
        System.out.println("2. 여행할 국가의 정보");
        System.out.print(">> ");
        int num = sc.nextInt();

        if (num == 1) {
            Countries.main(null);
        }

        if (num == 2) {
            Search.main(null);
        }
    }
}
