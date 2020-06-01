function addToCart(buttons) {
    for (let button of buttons) {
        button.addEventListener('click', handleAddProduct);
    }
}

function handleAddProduct() {
    apiFetch(this.dataset.id);
    raiseCartListLength();
}

function apiFetch(parameter) {
    fetch(`/add-product?id=${parameter}`)
        .then(data => data);
}

function raiseCartListLength() {
    let cartListPlace = document.querySelector(".cart-size");

    if (cartListPlace.textContent === "") {
        cartListPlace.textContent = "0";
    }
    let placeContent = parseInt(cartListPlace.textContent);
    placeContent++;
    cartListPlace.textContent = placeContent.toString();
    cartListPlace.classList.add("cart-items");
}

function main() {
    let buttons = document.querySelectorAll(".add-button");
    addToCart(buttons);
}

main();