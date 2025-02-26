document.addEventListener("DOMContentLoaded", ()=>{

	/**
     * 필수 입력 필드에 빨간색 *를 추가하는 함수
     */
    function addRequiredAsterisks() {
      // 필수 입력 필드가 있는 레이블 검색
      const requiredFields = document.querySelectorAll("label[data-required='true']");

      // 각 레이블에 * 추가
      requiredFields.forEach((label) => {
        const asterisk = createAsterisk();
        label.appendChild(asterisk);
      });
    }

    /**
     * 빨간색 * 스팬 요소를 생성하는 함수
     * @returns {HTMLSpanElement} 생성된 스팬 요소
     */
    function createAsterisk() {
      const asterisk = document.createElement("span");
      asterisk.textContent = " *";
      asterisk.className = "required-asterisk"; // CSS 클래스 추가
      return asterisk;
    }
	addRequiredAsterisks();
});