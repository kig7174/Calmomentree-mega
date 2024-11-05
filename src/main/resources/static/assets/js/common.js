// hidden menu
document.querySelectorAll(".nav-menu-on").forEach((v, i) => {
    v.addEventListener("mouseover", (e) => {
        e.preventDefault();
        const current = e.currentTarget;
        const sub = current.querySelector('#sub-menu');
        sub.classList.add('menu-on');
        sub.classList.remove('menu-off');
    });
    v.addEventListener("mouseout", (e) => {
        e.preventDefault();
        const current = e.currentTarget;
        const sub = current.querySelector('#sub-menu');
        sub.classList.remove('menu-on');
        sub.classList.add('menu-off');
    });
});