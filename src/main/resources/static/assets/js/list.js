/**
 * product list js
 */


// image mouse over
const mouseover = document.querySelectorAll('.item-img');
const visible = document.querySelectorAll('.list-item-img');
const hidden = document.querySelectorAll('.hover-item-img');

for (let i = 0; i < mouseover.length; i++) {
    if (hidden[i].classList.contains("hover-item-img")) {
        mouseover[i].addEventListener('mouseover', e => {
            visible[i].style.display = "none";
            hidden[i].style.display = "block"
        });

        mouseover[i].addEventListener('mouseout', e => {
            visible[i].style.display = "block";
            hidden[i].style.display = "none"
        });
    }
}
