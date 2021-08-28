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
	 * HTML 파싱로직
	 */
	private void readHtmlLine(List<String> htmlLines) {

		while (this.status < 3) {

			Content content = new Content();
			List<String> contentText = new ArrayList<>();

			for (String line : htmlLines) {

				//System.out.println("현재 읽고 있는 라인 : " + line);

				if (line.trim().contains("__S")) {

					this.extractedCode = extractCode(line);
					content.setCode(extractCode(line));
					this.status = 1;


					line = "<section id=\"" + extractedCode + "\">";
					contentText.add(line);
					continue;

				}



				if (line.startsWith("__")) {

					line = "</section>";
					contentText.add(line);
					this.status = 2;

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;

					content = new Content();
					contentText = new ArrayList<>();
					continue;

				} else if (line.trim().contains("<p>__!</p>")) {

					line = "</section>";
					contentText.add(line);
					this.status = 2;

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;

					content = new Content();
					contentText = new ArrayList<>();
					continue;

				}
				else if (line.trim().contains("__!")) {

					line = "</section>";
					contentText.add(line);
					this.status = 2;

					content.setCode(this.extractedCode);
					content.setContentText(contentText);
					this.result.add(content);
					this.status = 0;

					content = new Content();
					contentText = new ArrayList<>();
					continue;

				}

				contentText.add(line);

			}

			status = 3;

		}

	}

	private String extractCode(String line) {

		if ( !line.startsWith("<p>") ) {
			line = "<p>" + line;
		}

		String[] lineBit = line.split("<p>");

		String[] lineBitBits = lineBit[1].split("</p>");

		return lineBitBits[0].split("__")[1];

	}

}
