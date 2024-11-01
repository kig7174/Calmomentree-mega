/** 우편번호 주소 검색 - daum 주소 */
document.querySelector("#post-btn").addEventListener("click", e => {
    e.preventDefault();

    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
        }
    }).open();
});

/** 비밀번호 입력 시 입력조건 */
document.querySelector("#password").addEventListener("focus", e => {
    e.preventDefault();
    document.querySelector("#password-focus").classList.remove("password-off");
    document.querySelector("#password-focus").classList.add("password-on");
});

document.querySelector("#password").addEventListener("blur", e => {
    e.preventDefault();
    document.querySelector("#password-focus").classList.remove("password-on");
    document.querySelector("#password-focus").classList.add("password-off");
});

/** 전체 약관 동의 */
// 전체 동의 체크 시 모든 항목 체크
document.querySelectorAll("input[data-checked]").forEach((v, i) => {
    v.addEventListener('change', e => {
        const current = e.currentTarget;
        const targetSelector = current.dataset.checked;

        document.querySelectorAll(targetSelector).forEach((v2, i2) => {
            v2.checked = current.checked;
        });
    });
});

// 항목 중 체크가 풀리는 항목이 있다면 전체 동의 체크 해제
const checkAll = document.querySelectorAll(".check-all");

checkAll.forEach((v, i) => {
    v.addEventListener("change", e => {
        if (!v.checked) {
            document.querySelector("#agree-all-checked").checked = false;
        }
    });
});

/** 약관 항목 버튼 클릭 시 상세 내용 열기 */