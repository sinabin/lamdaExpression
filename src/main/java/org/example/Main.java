package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        // 1. lamdaExpression
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id("ASD123");

        // (매개변수) -> 몸체
        UserService userService = (userDTO1) ->
            System.out.println(userDTO.getUser_id() +"님 안녕하세요.");

        userService.printHello(userDTO);

        // 2. 대용량 데이터 처리하기
        // txt파일이 엄청크다고 가정
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\sinab\\OneDrive\\바탕 화면\\텍스트파일.txt"))) {
            br.lines()
                    .map(line -> line +"딩동") //스트림의 각 요소를 지정된 함수에 따라 변환하는 중간 연산
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3. 0~9까지 정수 리스트가 주어질 때 각 원소에 10을 더해서 새로운 리스트를 만들기
        // 3.1 Stream을 사용하지 않는 방법
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            int newNumber = numbers.get(i) + 10;
            result.add(newNumber);
        }

        for(int i=0; i<10; i++){
            System.out.println(result.get(i));
        }

        System.out.println("-----------------------스트림 사용후----------------------");

        // 3.2 Stream을 사용하는 방법
        List<Integer> numbers2 = IntStream.range(0, 10)
                .boxed() // StreamAPU는 객체를 다루는 API이기 때문에 primitive type을 처리하는데 문제가 발생할 수있어서
                         // (filter메소드 같은 경우 int나 long 같은 원시 타입 사용이 불가) int ->Integer로 Boxing 한다.
                .toList();

        List<Integer> result2 = numbers.stream()
                .map(n -> n + 10)
                .toList();
        result2.forEach(System.out::println);


    }


}