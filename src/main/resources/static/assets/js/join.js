/** 우편번호 주소 검색 - daum 주소 */
document.querySelector("#post-btn").addEventListener("click", e => {
    e.preventDefault();
    utilHelper.findPostcode();
});

/** 비밀번호 입력 시 입력조건 */
document.querySelector("#user_pw").addEventListener("focus", e => {
    e.preventDefault();
    document.querySelector("#password-focus").classList.remove("password-off");
    document.querySelector("#password-focus").classList.add("password-on");
});

document.querySelector("#user_pw").addEventListener("blur", e => {
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
        regexHelper.engNum("#user_id","아이디는 영문소문자 또는 숫자 4~16자로 입력해 주세요." )
    } catch (error) {
        idMsg.classList.remove("id-unique-check");
        idMsg.innerHTML = error.message;
        return;
    }

    const userid = document.querySelector("#user_id").value;
    const data = await axiosHelper.get(idUniqueCheckLink, {
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

/** 비밀번호 확인 검사 */
document.querySelector("#pw_confirm").addEventListener("blur", e => {
    e.preventDefault();
    const pwMsg = document.querySelector(".pw-confirm-msg");
    try {
        regexHelper.compareTo("#user_pw", "#pw_confirm", "비밀번호가 일치하지 않습니다.");
    } catch (error) {
        pwMsg.innerHTML = error.message;
    }

    document.querySelector("#pw_confirm").addEventListener("change", e => {
        pwMsg.innerHTML = "";
    });
});

document.querySelector("#user_id").addEventListener("change", e => {
    document.querySelector("#user_id_check").value = "N";
});

document.querySelector("#join").addEventListener("submit", async (e) => {
    e.preventDefault();
    const tel1 = document.querySelector("#tel1");
    const tel1Selected = tel1.selectedIndex;
    const tel = tel1[tel1Selected].value + document.querySelector("#tel2").value + document.querySelector("#tel3").value;
    document.querySelector("#tel").value = tel;

    /** 유효성 검사 */
    try {
        regexHelper.value("#user_id", "아이디를 입력해주세요.");
        regexHelper.minLength("#user_id", 4, "아이디는 4자 이상으로 입력해주세요.");
        regexHelper.maxLength("#user_id", 16, "아이디는 16자 이하로 입력해주세요.");
        regexHelper.lowerEngNum("#user_id", "아이디는 영문소문자와 숫자로만 입력해주세요.");

        regexHelper.value("#user_pw", "비밀번호를 입력해주세요.");
        regexHelper.minLength("#user_pw", 10, "비밀번호는 10자 이상으로 입력해주세요.");
        regexHelper.maxLength("#user_pw", 16, "비밀번호는 16자 이하로 입력해주세요.");
        regexHelper.password("#user_pw", "비밀번호 형식이 잘못되었습니다.");
        regexHelper.compareTo("#user_pw", "#pw_confirm", "비밀번호 확인이 일치하지 않습니다..");

        regexHelper.value("#user_name", "이름을 입력해주세요.");
        regexHelper.kor("#user_name", "이름은 한글로만 입력해주세요.");
        regexHelper.minLength("#user_name", "이름은 2자 이상으로 입력해주세요.");
        regexHelper.maxLength("#user_name", "이름은 10자 이하로 입력해주세요.");

        regexHelper.value("#tel", "전화번호를 입력해주세요.");
        regexHelper.num("#tel", "전화번호는 숫자로만 입력 가능합니다.");
        regexHelper.phone("#tel", "전화번호 형식이 잘못되었습니다.");

        regexHelper.value("#email", "이메일을 입력해주세요.");
        regexHelper.email("#email", "이메일 형식이 잘못되었습니다.");

        regexHelper.value("#postcode", "우편번호를 검색해주세요.");
        regexHelper.minLength("#postcode", 5, "우편번호는 5자로 입력해주세요.");
        regexHelper.maxLength("#postcode", 5, "우편번호는 5자로 입력해주세요.");
        regexHelper.num("#postcode", "우편번호는 숫자로만 입력해주세요.");
        
        regexHelper.value("#addr1", "주소를 검색해주세요.");
        regexHelper.value("#addr2", "상세 주소를 입력해주세요.");

        regexHelper.check("#agree_service_check", "이용약관 동의는 필수 항목입니다.");
        regexHelper.check("#agree_privacy_check", "개인정보처리방침 약관 동의는 필수 항목입니다.");
    } catch (error) {
        alert(error.message);
        return;
    }

    const idCheck = document.querySelector("#user_id_check");

    if (idCheck === 'N') {
        alert("중복된 아이디입니다. 중복검사를 진행해주세요.");
    }

    const formData = new FormData(e.currentTarget);

    const data = await axiosHelper.post(memberJoinLink, formData);

    if (data) {
        if (data.error) {
            alert("회원가입에 실패했습니다. 다시 시도해주세요.");
            return;
        }
        
        await utilHelper.alertSuccess("회원가입에 성공했습니다.");
        window.location = pageLoginLink;
    }
});