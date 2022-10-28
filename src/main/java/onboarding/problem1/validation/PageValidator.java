package onboarding.problem1.validation;

import onboarding.problem1.vo.Pages;

/**
 * Pages 객체가 유효한 객체인 지 검증하는 작업을 모아둔 Validator 클래스입니다.
 * @author BackFoxx
 */
public class PageValidator {
	public static final int PAGE_MAX_LIMIT = 400;
	public static final int PAGE_MIN_LIMIT = 1;

	/**
	 * Pages 객체를 검증하기 위해 외부에서 사용할 수 있는 유일한 메소드입니다.
	 * 필요한 검증 작업을 ||(or) 를 사용해 연이어 실행하도록 설계할 수 있습니다.
	 * 각 검증 작업에서 유효성 검사에 실패한 경우 예외가 발생합니다.
	 * @param pages 검증 대상인 Pages 객체를 인자로 받을 수 있습니다.
	 */
	public static void isInValidPages(Pages pages) {
		isPageSeparated(pages);
		isLeftPageNotOdd(pages);
		isPageLastOrHigher(pages);
		isPageFirstOrLower(pages);
	}

	/**
	 * 오른쪽 페이지와 왼쪽 페이지가 1 이외의 비정상적인 값이 나오는 지 검증합니다.
	 */
	private static void isPageSeparated(Pages pages) {
		Integer higherPageNumber = Math.max(pages.getLeftPage().getNumber(), pages.getRightPage().getNumber());
		Integer lowerPageNumber = Math.min(pages.getLeftPage().getNumber(), pages.getRightPage().getNumber());
		if ((higherPageNumber - lowerPageNumber) != 1) {
			throw new IllegalArgumentException("오른쪽 페이지와 왼쪽 페이지의 차이가 비정상적입니다.");
		}
	}

	/**
	 * 왼쪽 페이지 숫자가 홀수가 아닌 지 검증합니다.
	 * isPageSeparated 메소드 실행 후에 실행하는 경우
	 * 오른쪽 페이지가 짝수인 지도 동시에 검증이 이루어집니다.
	 */
	private static void isLeftPageNotOdd(Pages pages) {
		if (!((pages.getLeftPage().getNumber() % 2) == 1)) {
			throw new IllegalArgumentException("왼쪽 페이지의 숫자가 홀수가 아닙니다.");
		}
	}

	/**
	 * 페이지가 책의 마지막 페이지 또는 초과한 페이지를 펼치고 있는 지 검증합니다.
	 */
	private static void isPageLastOrHigher(Pages pages) {
		if (PAGE_MAX_LIMIT <= pages.getLeftPage().getNumber() || PAGE_MAX_LIMIT <= pages.getRightPage().getNumber()) {
			throw new IllegalArgumentException("페이지가 책의 마지막 페이지 또는 초과한 페이지를 펼치고 있습니다.");
		}
	}

	/**
	 * 페이지가 책의 첫번 째 페이지 또는 미만인 페이지를 펼치고 있는 지 검증합니다.
	 */
	private static void isPageFirstOrLower(Pages pages) {
		if (pages.getLeftPage().getNumber() <= PAGE_MIN_LIMIT || pages.getRightPage().getNumber() <= PAGE_MIN_LIMIT) {
			throw new IllegalArgumentException("페이지가 책의 첫번 째 페이지 또는 미만인 페이지를 펼치고 있습니다.");
		}
	}
}
