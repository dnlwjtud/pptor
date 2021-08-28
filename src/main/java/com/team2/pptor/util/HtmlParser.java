package com.team2.pptor.util;

import com.team2.pptor.domain.Article.Content;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

	private String extractedCode;
	private int status;

	private List<Content> result;

	public HtmlParser() {

		this.status = 0;
		this.result = new ArrayList<>();

	}

	public List<Content> getParsedHtml(String html) {

		List<String> htmlLines = splitHTML(html);

		readHtmlLine(htmlLines);

		return this.result;
	}

	/*
	 * HTML 문자열을 한줄씩 스플릿
	 */
	private List<String> splitHTML(String html) {

		List<String> htmlLines = new ArrayList<>();

		// HTML 문자열을 줄바꿈 기준으로 스플릿
		for (String htmlLine : html.trim().split("\r\n")) {
			htmlLines.add(htmlLine);
		}

		for (String arg : htmlLines ) {
			System.out.println(arg);
		}

		return htmlLines;

	}

	/*
	 * 코드 및 내용 판독기
	 */
	private void readHtmlLine(List<String> htmlLines) {

		System.out.println("=== 판독기 호출 성공, 로직 시작 ====");

		/*
		System.out.println("=== 입력된 html Lines 시작 ====");

		for (String arg : htmlLines ) {
			System.out.println(arg);
		}

		System.out.println("=== 입력된 html Lines 끝 ====");
		*/

		while (this.status < 3) {

			Content content = new Content();
			List<String> contentText = new ArrayList<>();

			for (String line : htmlLines) {

				System.out.println("현재 읽고 있는 라인 : " + line);

				if (line.trim().contains("__S")) {

					System.out.println("추출한 코드 : " + extractCode(line));
					this.extractedCode = extractCode(line);
					System.out.println("저장된 코드 : " + this.extractedCode);
					content.setCode(extractCode(line));
					this.status = 1;
					//System.out.println("추출한 코드 : " + content.getCode());

					line = "<section>";
					contentText.add(line);
					continue;

				}



				if (line.startsWith("__")) {

					line = "</section>";
					contentText.add(line);
					System.out.println("슬라이드 마감, 현재라인 :  " + line);
					this.status = 2;

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;
					System.out.println("객체가 추가되었습니다.");
					content = new Content();
					contentText = new ArrayList<>();
					continue;

				} else if (line.trim().contains("<p>__!</p>")) {

					line = "</section>";
					contentText.add(line);
					this.status = 2;
					System.out.println("슬라이드 마감, 현재라인 :  " + line);

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;
					System.out.println("객체가 추가되었습니다.");
					content = new Content();
					contentText = new ArrayList<>();
					continue;

				}
				else if (line.trim().contains("__!")) {

					line = "</section>";
					contentText.add(line);
					this.status = 2;
					System.out.println("슬라이드 마감, 현재라인 :  " + line);

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;
					System.out.println("객체가 추가되었습니다.");
					content = new Content();
					contentText = new ArrayList<>();
					continue;

				}

				contentText.add(line);
				//System.out.println("현재 추가한 라인 : " + line);
				/*
				if (this.count == 2) {

					content.setContentText(contentText);
					this.result.add(content);
					this.count = 0;
					System.out.println("객체가 추가되었습니다.");
					continue;

				}
				*/

			}

			status = 3;
			System.out.println("정상 파싱 완료!");

			System.out.println("=== 판독기 호출 성공, 로직 끝 ====");

		}

	}

	private String extractCode(String line) {

		if ( !line.startsWith("<p>") ) {
			line = "<p>" + line;
		}

		System.out.println("확인 1 : " + line);

		String[] lineBit = line.split("<p>");

		String[] lineBitBits = lineBit[1].split("</p>");

		return lineBitBits[0].split("__")[1];

	}

}
