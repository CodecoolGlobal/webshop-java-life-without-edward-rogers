function addToCart(buttons, cartListPlaces) {

    for (let button of buttons) {
        button.addEventListener('click', function (event) {
            apiFetch(button.dataset.id);
            raiseCartListLength(cartListPlaces);
        })
    }

}

function apiFetch(parameter) {
    fetch(`/add-product?id=${parameter}`)
        //.then(response => response.json())
        .then(data => data);
}

function raiseCartListLength(cartListPlace) {
    if (cartListPlace.textContent === "") {
        cartListPlace.textContent = "0";
    }
    let placeContent = parseInt(cartListPlace.textContent);
    placeContent++;
    cartListPlace.textContent = placeContent;
    cartListPlace.classList.add("cart-items");
}

function main() {
    let cartListPlace = document.querySelector(".cart-size");
    let buttons = document.querySelectorAll(".add-button");
    addToCart(buttons, cartListPlace);
}

main();