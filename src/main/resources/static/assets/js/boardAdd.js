// 게시글 업로드
document.querySelector("#boardEdit").addEventListener("submit", async (e) => {
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

  // if(data) {
  //   window.location = qnaListPageLink
  // }
});
