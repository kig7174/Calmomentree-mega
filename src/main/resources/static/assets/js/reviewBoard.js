if (document.querySelector("#delete") !== null) {
  document.querySelector("#delete").addEventListener("submit", async (e) => {
    e.preventDefault();

    if (confirm("정말로 삭제 하시겠습니까?")) {
      const data = await axiosHelper.delete(reviewBoardDelete);

      if (data) {
        alert("게시글이 삭제되었습니다.");
        window.location = reviewBoardList;
      }
    }
  });
}
