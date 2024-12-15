const formData = new FormData();

// 주문정보에 있는 주소검색
document.querySelector("#post-btn").addEventListener("click", (e) => {
  e.preventDefault();

  new daum.Postcode({
    oncomplete: function (data) {
      document.querySelector("#postcode").value = data.zonecode;
      document.querySelector("#addr1").value = data.roadAddress;
    },
  }).open();
});

// 배송지에 있는 주소검색
document.querySelector("#post-btn2").addEventListener("click", (e) => {
  e.preventDefault();

  new daum.Postcode({
    oncomplete: function (data) {
      document.querySelector("#receiver_postcode").value = data.zonecode;
      document.querySelector("#receiver_addr1").value = data.roadAddress;
    },
  }).open();
});

// 아코디언 ...??
document.querySelectorAll(".info-title").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    const current = e.currentTarget;
    const parent = current.closest(".collapse");
    const infoBox = parent.querySelector(".info-box");
    const select = parent.querySelector(".agree-content-btn");
    const info = parent.querySelector(".info");
    const prodBox = parent.querySelectorAll(".prod-box");

    // 화살표방향
    select.classList.toggle("select");
    // 아코디언
    infoBox.classList.toggle("selected");
    // 닫히면 나오는 정보
    if (info) {
      info.classList.toggle("active");
    }

    if (!infoBox.classList.contains("selected")) {
      infoBox.style.maxHeight = null;
    } else {
      infoBox.style.maxHeight = infoBox.scrollHeight + "px";
    }
    // 주문상품 개수 표시
    const ctn = prodBox.length;
    const count = document.querySelector(".count");
    if (prodBox) {
      count.innerHTML = ctn;
    }
  });
});
// ================ 개별 상품 삭제 버튼 ====================
// document.querySelectorAll(".cancel-btn").forEach((v, i) => {
//   v.addEventListener("click", async (e) => {
//     e.preventDefault();

//     const current = e.currentTarget;
//     console.log(current);
//     console.log(current.href.value);
//     if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
//       const data = await axiosHelper.delete(current.href, formData);

//       if (data) {
//         alert("삭제 되었습니다.");
//         window.location = orderList;
//       }
//     }
//   });
// });