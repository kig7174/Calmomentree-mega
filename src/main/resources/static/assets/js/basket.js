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

    // 이벤트 강제 전송????
    const eve = new Event("change");
    qty.dispatchEvent(eve);
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

      // 이벤트 강제 전송????
      const eve = new Event("change");
      qty.dispatchEvent(eve);
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
  const checkedCnt = document.querySelectorAll(".prodCheck:checked");

  if (checkedCnt.length == 0) {
    alert("선택된 상품이 없습니다.");
    return;
  } else {
    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      checkedCnt.forEach(async (v, i) => {
        const form = v.form;
        const formData = form;
        let data = await axiosHelper.delete(basketDeleteLink, formData);

        // console.log("1" + data);
        if (data) {
          // console.log("2" + data);
          alert("삭제되었습니다.");
          window.location.reload();
        }
      });
    }
    // console.log("3" + data);
  }
});

// 개별 상품 삭제 버튼
document.querySelectorAll(".cancel").forEach((v, i) => {
  v.addEventListener("click", async (e) => {
    e.preventDefault();

    const form = v.form;
    const formData = new FormData(form);
    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      const data = await axiosHelper.delete(basketDeleteLink, formData);

      if (data) {
        alert("삭제 되었습니다.");
        window.location = basketList;
      }
    }
  });
});

// 주문하기 버튼 클릭
/** ---------- 해당 제품 정보 주문페이지로 넘겨줘야 됨. (일단 선택만 되도록) --------- */
// document.querySelectorAll(".btnBuy").forEach((v, i) => {
//   v.addEventListener("submit", async (e) => {
//     e.preventDefault();

//     const parent = e.currentTarget.closest(".listTable");
//     const check = parent.querySelector(".prodCheck");
//     check.checked = true;

//     const formData = new FormData(check.form);
//     console.log(formData);
//     const data = await axiosHelper.post(orderFormAdd, formData);
//     if(data) {
//       window.location = orderForm;
//     }
//   });
// });

// 전체상품주문 버튼 클릭 (일단은 전체 선택만 되도록 설정함. 주문페이지로 연결해야됨)
document.querySelector("#allOrder").addEventListener("click", (e) => {
  document.querySelectorAll(".prodCheck").forEach((v, i) => {
    v.checked = true;
  });
});

// // 선택상품주문 버튼 클릭 (선택된 상품이 있는지 여부만 확인함. 주문페이지로 연결해야됨)
// document.querySelector("#selectOrder").addEventListener("click", (e) => {
//   const formdata = new FormData();

//   // 체크된것 확인
//   const checkedCnt = document.querySelectorAll(".prodCheck:checked");
//   if (checkedCnt.length == 0) {
//     alert("선택된 상품이 없습니다.");
//     return;
//   } else {
   
//       checkedCnt.forEach(async (v, i) => {
//         // 체크된것 form태그
//         const parent = v.closest(".orderForm");
//         formdata.append = parent;
//         console.log(formdata);
//         await axiosHelper.post(`[[@{/order/order_form}]]`,formdata);
//       });
    
//   }
// });
