document.querySelector("#cancel").addEventListener("submit", async e => {
    e.preventDefault();

    const current = e.currentTarget;
    const passwordValue = document.querySelector(".password").value;
    const deleteQna = current.action;
    console.log(deleteQna);

     if (confirm('정말로 삭제 하시겠습니까?')) {
       const data = await axiosHelper.post(boardDeleteLink);
       
       if(data) {
        alert("게시글이 삭제되었습니다.");
        window.location = qnaListPageLink;
       }
    }
  });
