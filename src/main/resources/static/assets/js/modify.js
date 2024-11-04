/** 우편번호 주소 검색 - daum 주소 */
document.querySelector("#post-btn").addEventListener("click", e => {
    e.preventDefault();

    new daum.Postcode({
        oncomplete: function(data) {
            document.querySelector("#postcode").value = data.zonecode;
            document.querySelector("#addr1").value = data.roadAddress;
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
