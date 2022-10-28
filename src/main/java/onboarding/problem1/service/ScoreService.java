package onboarding.problem1.service;

import onboarding.problem1.vo.Pages;
import onboarding.problem1.vo.Page;
import onboarding.problem1.vo.Score;

/**
 * Score 과 관련한 비즈니스 로직을 진행하는 서비스 레이어 클래스입니다.
 * @author BackFoxx
 */
public class ScoreService {
	/**
	 * 문제 1의 요구사항에서 만든 게임 규칙에 따라
	 * 인자로 넘어온 Pages 객체를 분석해
	 * 가장 높은 점수를 계산하는 메소드입니다.
	 */
	public static Score getHighestScore(Pages pages) {
		int highestScore = Math.max(
			Math.max(
				add(pages.getLeftPage()), add(pages.getRightPage())
			),
			Math.max(
				multiply(pages.getLeftPage()), multiply(pages.getRightPage())
			)
		);
		return Score.of(highestScore);
	}

	/**
	 * Page 각 자리수의 숫자를 모두 더한 값을 반환하는 메소드입니다.
	 */
	private static Integer add(Page page) {
		return page.getEachNumberList()
			.stream().mapToInt(number -> number)
			.sum();
	}

	/**
	 * Page 각 자리수의 숫자를 모두 곱한 값을 반환하는 메소드입니다.
	 */
	private static Integer multiply(Page page) {
		int result = 1;
		for (Integer number : page.getEachNumberList()) {
			result = result * number;
		}
		return result;
	}
}
