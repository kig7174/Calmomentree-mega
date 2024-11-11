// 댓글 등록 버튼
document.querySelector(".uploadBtn").addEventListener("click", (e) => {
    e.preventDefault();

    const coment = document.querySelector("#comment");

    if (!coment.value) {
      alert("댓글내용 항목은 필수 입력값입니다.");
      coment.focus();
    }
  });

  // 삭제 버튼
  document.querySelector(".delete").addEventListener("click", (e) => {
    e.preventDefault();

    if (confirm("정말로 삭제하시겠습니까?") == true) {
      
    } else {
      return;
    }
  });