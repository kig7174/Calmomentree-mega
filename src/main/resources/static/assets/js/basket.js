const formData = new FormData();

// ==================장바구니 max-height 조절 ========================
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

// ============ '+' 버튼 클릭시 수량 증가 ====================
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

// =============== '-' 버튼 클릭시 수량 감소 =======================
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

// =================== 선택삭제 버튼 클릭 ========================= 
document.querySelector("#checkDelete").addEventListener("click", async (e) => {
  const checkedChks = document.querySelectorAll(".prodCheck:checked");

  if (checkedChks.length == 0) {
    alert("선택된 상품이 없습니다.");
    return;
  } else {
    
    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      let data = null;
      
      checkedChks.forEach((v, i) => {
        const parent = v.closest(".listTable");
        // console.log("삭제할상품박스: " + parent);
        const basketId = parent.querySelector("#basketId").value;
        // console.log("삭제할 장바구니 번호: " + basketId);
        
        let basketIdNum = parseInt(basketId);
        formData.append("basketIdTmp", basketIdNum);
        
      });
      data = await axiosHelper.delete(basketListDeleteLink, formData);
    
      if (data) {
        alert("삭제되었습니다.");
        window.location.reload();
      }
    }
  }
});

// ================ 개별 상품 삭제 버튼 ====================
document.querySelectorAll(".cancel").forEach((v, i) => {
  v.addEventListener("click", async (e) => {
    e.preventDefault();

   const current = e.currentTarget.closest(".listTable");
    const basketId = current.querySelector("#basketId").value;
    
    formData.append('basketIdTmp',basketId);

    if (confirm("선택하신 상품을 삭제하시겠습니까?")) {
      const data = await axiosHelper.delete(basketDeleteLink, formData);

      if (data) {
        alert("삭제 되었습니다.");
        window.location = basketList;
      }
    }
  });
});

// ================ 주문하기 버튼 클릭 ===========================
document.querySelectorAll("#btnBuy").forEach((v, i) => {
  v.addEventListener("click", async (e) => {
    e.preventDefault();

    const parent = e.currentTarget.closest(".listTable");
    const check = parent.querySelector(".prodCheck");
    check.checked = true;

    // prodId quantity orderPrice prodNameKor imgUrl
    const prodId = parent.querySelector("#prodId").value;
    const quantity = parent.querySelector("#qty").value;
    const orderPrice = parent.querySelector("#orderPrice").value;
    const prodNameKor = parent.querySelector("#prodNameKor").value;
    const imgUrl = parent.querySelector("#imgUrl").value;

    formData.append("prodIdTmp",prodId);  // int
    formData.append("quantityTmp",quantity);  // int
    formData.append("orderPriceTmp",orderPrice);   // int
    formData.append("prodNameKor",prodNameKor);   
    formData.append("imgUrl",imgUrl);

    console.log(prodId);
    console.log(quantity);
    console.log(orderPrice);
    console.log(prodNameKor);
    console.log(imgUrl);
    console.log(formData);

    const data = await axiosHelper.post(orderFormAdd, formData);
    console.log(data);
    // if(data) {                         
    //   window.location = orderList;
    // }
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
