/** 이름과 아이디가 일치 및 아이디 유효성 검사 */
document.querySelector("#user_id").addEventListener("blur", async (e) => {
    e.preventDefault();

    const idMatch = document.querySelector(".name-match");

    try {
        regexHelper.value("#user_name", "이름을 입력해주세요.");
        regexHelper.kor("#user_name", "이름은 한글로만 입력해주세요.");
        regexHelper.minLength("#user_name", "이름은 2자 이상으로 입력해주세요.");
        regexHelper.maxLength("#user_name", "이름은 10자 이하로 입력해주세요.");
    } catch (error) {
        alert(error.message);
        return;
    }

    // const userid = document.querySelector("#user_id").value;
    // const data = await axiosHelper.get(idUniqueCheckLink, {
    //     user_id : userid
    // });

    if (user_name === email) {
        return "이름과 이메일이 일치합니다.";
    } else {
        return "이름과 이메일이 일치하지 않습니다.";
    } 
});