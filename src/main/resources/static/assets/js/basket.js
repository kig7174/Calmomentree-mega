// 장바구니 max-height 조절
document.querySelector(".prodBox-title").addEventListener("click", (e) => {
  const current = e.currentTarget;
  const parent = current.closest(".prodBox");
  const content = parent.querySelector(".prodBox-info");
  const select = document.querySelector("#agree-content-btn");

  select.classList.toggle("select");

  content.classList.toggle("active");

  if (!content.classList.contains("active")) {
    content.style.maxHeight = null;
  } else {
    content.style.maxHeight = content.scrollHeight + "px";
  }
});

// + 버튼 클릭시 수량 증가
/** ------ 가격 조절 해야됨 ----- */
document.querySelectorAll(".plus").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    const parent = current.closest(".qtyBox");
    const qty = parent.querySelector(".qty");

    qty.value++;
  });
});

//  - 버튼 클릭시 수량 감소
/** ------ 가격 조절 해야됨 ----- */
document.querySelectorAll(".minus").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    const parent = current.closest(".qtyBox");
    const qty = parent.querySelector(".qty");

    if (qty.value > 1) {
      qty.value--;
    }
  });
});

// 전체선택 버튼 클릭
document.querySelector("#checkAll").addEventListener("click", (e) => {
  document.querySelectorAll(".prodCheck").forEach((v, i) => {
    v.checked = true;
  });
});

// 선택삭제 버튼 클릭
/** --------- 삭제기능 구현 해야됨 ---------- */
document.querySelector("#checkDelete").addEventListener("click", (e) => {
  const checkedCnt = document.querySelectorAll(".prodCheck:checked").length;
  if (checkedCnt) {
    confirm("선택하신 상품을 삭제하시겠습니까?");
  } else {
    alert("선택된 상품이 없습니다.");
  }
});

// 개별 상품 삭제 버튼
document.querySelectorAll(".cancel").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    e.preventDefault();

    const current = e.currentTarget;
    console.log(current);
    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
    }
  });
});

// 주문하기 버튼 클릭
/** ---------- 해당 제품 정보 주문페이지로 넘겨줘야 됨. (일단 선택만 되도록) --------- */
document.querySelectorAll(".btnBuy").forEach((v, i) => {
  v.addEventListener("click", (e) => {
    const current = e.currentTarget;
    const parent = current.closest(".listTable");
    console.log(parent);
    const check = parent.querySelector(".prodCheck");
    check.checked = true;
  });
});

// 전체상품주문 버튼 클릭 (일단은 전체 선택만 되도록 설정함. 주문페이지로 연결해야됨)
document.querySelector("#allOrder").addEventListener("click", (e) => {
  document.querySelectorAll(".prodCheck").forEach((v, i) => {
    v.checked = true;
  });
});

// 선택상품주문 버튼 클릭 (선택된 상품이 있는지 여부만 확인함. 주문페이지로 연결해야됨)
document.querySelector("#selectOrder").addEventListener("click", (e) => {
  const checkedCnt = document.querySelectorAll(".prodCheck:checked").length;
  if (checkedCnt == 0) {
    alert("선택된 상품이 없습니다.");
  }
});

// 장바구니 수량 표시
(async () => {
  const cnt = document.querySelectorAll(".listTable").length;
  const count = document.querySelector(".count");
  count.innerHTML = cnt;
})();
