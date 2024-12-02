/**
 * product detail js
 */

// image mouse over
document.querySelectorAll('.link').forEach((v, i) => {
    v.addEventListener('click', e => {
        e.preventDefault();
    });

    v.addEventListener('mouseover', e => {
        const src = e.currentTarget.getAttribute("href");

        const target = document.querySelector("#main-img-target");
        target.setAttribute("src", src);
    });
});

// iteminfo tab menu
document.querySelectorAll('.tab-button').forEach((v, i) => {
    v.addEventListener('click', (e) => {
        e.preventDefault();
        const currentIndex = i;
        const href = e.currentTarget.getAttribute("href");

        document.querySelectorAll('.tab-button').forEach((v1, i1) => {
            if (currentIndex == i1) {
                v1.classList.add('active');
            } else {
                v1.classList.remove('active');
            }
        });

        document.querySelectorAll('.tab-page').forEach((v2, i2) => {
            v2.classList.remove('active');
        });

        document.querySelector(href).classList.add('active');
    });
});


// 리뷰 열기
document.querySelectorAll('.reviews').forEach((v, i) => {
    v.addEventListener('click', (e) => {
        document.querySelectorAll('.review-collapse-content').forEach((w, j) => {
            w.style.maxHeight = null;
        });

        const current = e.currentTarget;

        current.classList.toggle('active');

        document.querySelectorAll('.reviews').forEach((w, j) => {
            if (w !== current) {
                w.classList.remove('active');
            }
        });

        const parent = current.closest('.collapse');

        const content = parent.querySelector('.review-collapse-content');

        if (!current.classList.contains('active')) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + 'px';
        }
    });
});

document.querySelectorAll('.inquirys').forEach((v, i) => {
    v.addEventListener('click', (e) => {
        document.querySelectorAll('.inquiry-collapse-content').forEach((w, j) => {
            w.style.maxHeight = null;
        });

        const current = e.currentTarget;

        current.classList.toggle('active');

        document.querySelectorAll('.inquirys').forEach((w, j) => {
            if (w !== current) {
                w.classList.remove('active');
            }
        });

        const parent = current.closest('.collapse');

        const content = parent.querySelector('.inquiry-collapse-content');

        if (!current.classList.contains('active')) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + 'px';
        }
    });
});

const qty = document.querySelector("#qty");

(function() {
    document.querySelector("#qty-box").style.display = "none";
})();

document.querySelector("#qty-up").addEventListener("click", e => {
    e.preventDefault();

    qty.value++;

    qty.dispatchEvent(new Event("change"));
});

document.querySelector("#qty-down").addEventListener("click", e => {
    e.preventDefault();

    if (qty.value > 0) {
        qty.value--;
    }

    qty.dispatchEvent(new Event("change"));
});

const option = document.querySelector("#product-option");

option.addEventListener("change", (e) => {
    if (option.value == 1) {
        document.querySelector("#qty-box").style.display = "block";
        qty.value = option.value;

        qty.dispatchEvent(new Event("change"));
    } else {
        document.querySelector("#qty-box").style.display = "none";
    
        qty.value = 0;
    
        qtyPrice.innerHTML = "";
        totalPrice.innerHTML = "";
        totalQty.innerHTML = "";
    }
});

const qtyPrice = document.querySelector("#qty-price");
const totalPrice = document.querySelector("#total-price");
const totalQty = document.querySelector("#total-qty");

qty.addEventListener("change", (e) => {
    e.preventDefault();

    qtyPrice.innerHTML = (qty.value * prodPrice).toLocaleString() + "원";
    totalPrice.innerHTML = (qty.value * prodPrice).toLocaleString() + "원";
    totalPrice.insertAdjacentHTML("beforeend", " ("+qty.value+"개)");
});

document.querySelector("#qty-delete").addEventListener("click", (e) => {
    e.preventDefault();
    document.querySelector("#qty-box").style.display = "none";

    option[0].selected = true;

    qty.value = 0;

    qtyPrice.innerHTML = "";
    totalPrice.innerHTML = "";
    totalQty.innerHTML = "";
});