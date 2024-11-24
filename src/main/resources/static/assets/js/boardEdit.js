// ------------------- 게시글 수정하기 -------------------------
/**
 * 파일 삭제 여부 체크에 따른 업로드 필드 활성화 처리
 * - data-disabled 라는 속성을 갖는 모든 요소에 대해 일괄 처리한다.
 */
document.querySelectorAll("*[data-disabled]").forEach((v, i) => {
    v.addEventListener("change", (e) => {
      e.preventDefault();
  
      const current = e.currentTarget;
      document.querySelector(current.dataset.disabled).disabled = !current.checked;
    });
  });
  
  // 첨부파일 이미지 이름 출력하기(추후 보완 필요...)
  const imgFakeName = document.querySelector(".imgFakeName").innerText;
  const imgRealName = imgFakeName.substring(imgFakeName.lastIndexOf("/") + 1);
  const imgName = document.querySelector(".imgName");
  imgName.innerHTML = imgRealName;
  
  // textarea의 값 DB에 전달하기
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
    const data = await axiosHelper.post(boardEditOkLink, formData);
  
    if (data) {
      alert("게시글이 수정되었습니다.");
      window.location = qnaListPageLink;
    }
  });