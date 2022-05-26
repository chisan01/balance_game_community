let dropdown_btn = document.getElementById("dropdown_btn");
let dropdown = document.getElementById("dropdown");
let dropdown_item1 = document.getElementById("dropdown_item1");
let active = 0;
dropdown_btn.onclick = function () {
    if (active == 0) {
        dropdown.classList.add("is-active");

        active = 1;
    } else {
        dropdown.classList.remove("is-active");
        active = 0;
    }
};
dropdown_item1.onclick = function () {
    this.classList.remove("dropdown_content_hang");
    this.classList.add("animate__hinge");
};
