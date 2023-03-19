package org.example;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args){

        // 1. lamdaExpression
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id("ASD123");

        // (매개변수) -> 몸체
        UserService userService = (userDTO1) ->
            System.out.println(userDTO.getUser_id() +"님 안녕하세요.");

        userService.printHello(userDTO);

        // 2. 문자열 처리 하기
        // txt파일이 엄청큰데 이 파일의 라인을 하나씩 읽어서 뒤에 "딩동"이라는 문자열을 추가하도록 처리해야하는 상황을 가정
        try {
            URL url = Main.class.getResource("/Test.txt");
            System.out.println(url.getPath());

            BufferedReader br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(url.getPath()), "UTF-8"), 8192);

            // 2.1 Stream을 사용하지 않을 경우
            long start_time0 = System.currentTimeMillis();
            String eachLine;
            while ((eachLine = br.readLine()) != null) {
                eachLine += "딩동";
            }
            long end_time0 = System.currentTimeMillis();
            System.out.println("문자열처리 - Stream 미사용시 처리시간 : " + (end_time0 -start_time0) +"ms" );

            // 2.2 Stream을 사용할 경우
            long start_time1 = System.currentTimeMillis();
            br.lines()
                    .map(eachLine2 -> eachLine2 +"딩동"); //스트림의 각 요소를 지정된 함수에 따라 변환하는 중간 연산
            long end_time1 = System.currentTimeMillis();

            System.out.println("문자열처리 - Stream 사용시 처리시간 : " + (end_time1 -start_time1) +"ms" );

        } catch (IOException e) {
            e.printStackTrace();
        }


        // 3. 리스트 처리하기
        // 0~9까지 정수 리스트가 주어질 때 각 원소에 10을 더해서 새로운 리스트를 만들어야하는 상황을 가정


        // 3.1 Stream을 사용하지 않는 방법
        long start_time = System.currentTimeMillis();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 50000000; i++) {
            numbers.add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            int newNumber = numbers.get(i) + 10;
            result.add(newNumber);
        }

        long end_time = System.currentTimeMillis();
        System.out.println("리스트 처리 - Stream 미사용시 소요시간 : " + (end_time-start_time) +"ms");

        // 3.2 Stream을 사용하는 방법
        long start_time2 = System.currentTimeMillis();
        List<Integer> numbers2 = IntStream.range(0, 50000000)
                .boxed() // StreamAPU는 객체를 다루는 API이기 때문에 primitive type을 처리하는데 문제가 발생할 수있어서
                         // (filter메소드 같은 경우 int나 long 같은 원시 타입 사용이 불가) int ->Integer로 Boxing 한다.
                .toList();

        List<Integer> result2 = numbers.stream()
                .map(n -> n + 10) // map 연산이 병렬적으로 처리되기 때문에 기존의 Stream을 사용하지 않는 방법보다 더 빠르게 처리된다.
                .toList();

        long end_time2 = System.currentTimeMillis();
        System.out.println("리스트 처리 - Stream 사용시 소요시간 : " + (end_time2-start_time2) +"ms");

    }


}