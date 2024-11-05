package com.jw.pdfrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication // Spring Boot 애플리케이션을 정의하는 어노테이션
public class PdfrdApplication {
    public static void main(String[] args) {
        // Spring Boot 애플리케이션 실행
        SpringApplication.run(PdfrdApplication.class, args);

        // PDF 파일의 절대 경로 지정
        String fileName = "D:\\sjw\\3-2\\생성형AI\\4주차\\test.pdf";

        // 사용자 입력을 받기 위해 Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. 모든 페이지 출력"); // 옵션 1 안내
        System.out.println("2. 특정 페이지 출력"); // 옵션 2 안내
        System.out.print("원하는 옵션을 선택하세요 (1 또는 2): "); // 사용자에게 선택 요청
        int option = scanner.nextInt(); // 사용자 입력을 정수로 받음

        String text; // 추출된 텍스트를 저장할 변수
        if (option == 1) {
            // 모든 페이지의 텍스트를 추출하기 위해 0을 전달
            text = extractTextFromPDF(fileName, 0);
        } else if (option == 2) {
            // 특정 페이지 번호를 입력받음
            System.out.print("출력할 페이지 번호를 입력하세요: ");
            int pageNumber = scanner.nextInt(); // 사용자로부터 페이지 번호 입력 받기
            text = extractTextFromPDF(fileName, pageNumber); // 해당 페이지 번호를 전달
        } else {
            // 잘못된 옵션이 입력된 경우 메시지 출력 및 종료
            System.out.println("잘못된 옵션입니다.");
            scanner.close(); // Scanner 닫기
            return; // 프로그램 종료
        }

        // 추출된 텍스트를 콘솔에 출력
        System.out.println(text);
        scanner.close(); // Scanner 닫기
    }

    // PDF 파일에서 텍스트를 추출하는 메서드
    public static String extractTextFromPDF(String filePath, int pageNumber) {
        StringBuilder result = new StringBuilder(); // 결과를 저장할 StringBuilder
        File source = new File(filePath); // PDF 파일 객체 생성

        try (PDDocument pdfDoc = PDDocument.load(source)) { // PDF 문서 로드 (try-with-resources 구문)
            PDFTextStripper pdfStripper = new PDFTextStripper(); // PDF 텍스트 추출기 생성

            if (pageNumber == 0) {
                // 모든 페이지의 텍스트를 추출
                String text = pdfStripper.getText(pdfDoc);
                result.append(text); // 결과에 추가
            } else {
                // 특정 페이지의 텍스트를 추출
                pdfStripper.setStartPage(pageNumber); // 시작 페이지 설정
                pdfStripper.setEndPage(pageNumber); // 종료 페이지 설정
                String text = pdfStripper.getText(pdfDoc);
                result.append(text); // 결과에 추가
            }
        } catch (IOException e) {
            e.printStackTrace(); // 오류 발생 시 스택 트레이스 출력
            return "Error: " + e.getMessage(); // 오류 메시지 반환
        }

        return result.toString(); // 최종 결과 반환
    }
}
