/** 우편번호 주소 검색 - daum 주소 */
document.querySelector("#post-btn").addEventListener("click", e => {
    e.preventDefault();
    utilHelper.findPostcode();
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

/** 마케팅 전체 수신 동의 */
const marketingCheckAll = document.querySelectorAll(".marketing-check-all");

marketingCheckAll.forEach((v, i) => {
    v.addEventListener("change", e => {
        if (!v.checked) {
            document.querySelector("#agreeAllMarketingChecked").checked = false;
        }
    });
});

/** 약관 항목 버튼 클릭 시 상세 내용 열기 */
document.querySelectorAll("#agree-content-btn").forEach((v, i) => {
    v.addEventListener("click", e => {
        const current = e.currentTarget;

        current.classList.toggle('active');

        const parent = current.closest(".item");

        const content = parent.querySelector(".content");

        if (content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = "300px";
        }
    });
});

/** 아이디 중복 검사 및 유효성 검사(아이디 입력 칸 아래 메세지) */
document.querySelector("#user_id").addEventListener("blur", async (e) => {
    e.preventDefault();

    const idMsg = document.querySelector(".id-msg");

    try {
        regexHelper.value("#user_id", "아이디를 입력하세요.");
        regexHelper.minLength("#user_id", 4, "아이디는 영문소문자 또는 숫자 4~16자로 입력해 주세요.");
        regexHelper.maxLength("#user_id", 16, "아이디는 영문소문자 또는 숫자 4~16자로 입력해 주세요.");
    } catch (error) {
        idMsg.classList.remove("id-unique-check");
        idMsg.innerHTML = error.message;
        setTimeout(() => error.element.focus(), 1);
        return;
    }

    const userid = document.querySelector("#user_id").value;
    const data = await axiosHelper.get('/api/member/id_unique_check', {
        user_id : userid
    });

    if (data) {
        idMsg.innerHTML = userid + data.message;
        document.querySelector("#user_id_check").value = "Y";
        idMsg.classList.add("id-unique-check");

        if (data.error) {
            document.querySelector("#user_id_check").value = "N";
            idMsg.classList.remove("id-unique-check"); 
        }
    }
});

document.querySelector("#user_id").addEventListener("change", e => {
    document.querySelector("#user_id_check").value = "N";
});

document.querySelector("#join").addEventListener("submit", async (e) => {
    e.preventDefault();

    /** 유효성 검사 */
    
});