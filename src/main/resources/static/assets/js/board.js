//  ----------------- 게시글 삭제 ----------------------------
if (document.querySelector("#cancel") !== null) {
  document.querySelector("#cancel").addEventListener("submit", async (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    const passwordValue = document.querySelector(".password").value;

    if (confirm("정말로 삭제 하시겠습니까?")) {
      const data = await axiosHelper.delete(boardDeleteLink);

      if (data) {
        alert("게시글이 삭제되었습니다.");
        window.location = qnaListPageLink;
      }
    }
  });
}

// ----------------- 게시글 업로드 ------------------------------
if (document.querySelector("#boardAdd") != null) {
  document.querySelector("#boardAdd").addEventListener("submit", async (e) => {
    e.preventDefault();

    /** ck-editor의 값 추출 후 textBox에 복사 */
    const content = document.querySelector(".ck-content");
    const con = content.getElementsByTagName("p");
    const text = con[0].innerText;
    const box = document.querySelector("#textBox");
    box.innerHTML = text;

    /** textarea 입력 여부 판단 */
    try {
      regexHelper.value("#textBox", "내용(을/를) 입력하세요");
    } catch (e) {
      alert(e.message);
      return;
    }

    const current = e.currentTarget;
    const formData = new FormData(current);
    const data = await axiosHelper.postMultipart(boardAddLink, formData);

    if (data) {
      if (data.error) {
        alert("error : " + data.message);
        return;
      }
      alert("게시글이 등록되었습니다.");
      window.location = qnaListPageLink;
    }
  });
}
