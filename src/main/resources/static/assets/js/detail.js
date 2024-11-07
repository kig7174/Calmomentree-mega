/**
 * product detail js
 */

// tab menu img


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
document.querySelectorAll('.collapsible-title').forEach((v, i) => {
    v.addEventListener('click', (e) => {
        document.querySelectorAll('.review-collapse-content').forEach((w, j) => {
            w.style.maxHeight = null;
        });

        const current = e.currentTarget;

        current.classList.toggle('active');

        document.querySelectorAll('.collapsible-title').forEach((w, j) => {
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