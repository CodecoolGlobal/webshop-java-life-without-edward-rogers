function editQuantity(quantitiesOfProducts) {
    for (let quantity of quantitiesOfProducts) {
        quantity.addEventListener("dblclick", function (event) {
            let oldQuantityContent = quantity.innerHTML;
            let newQuantityElement = document.querySelector('#input');
            if (newQuantityElement == null) {
                quantity.innerHTML = `<input type="text" autocomplete="off" value="${oldQuantityContent}" id="input">`;
                newQuantityElement = document.querySelector('#input');
                newQuantityElement.addEventListener('keydown', function (event) {
                    if (event.key === 'Enter') {
                        quantity.innerHTML = newQuantityElement.value;
                        changeCartNumberSize(quantitiesOfProducts);
                        countSubtotal(quantity.dataset.id, quantity);
                        countTotal();
                        apiFetch(quantity.dataset.id, quantity.innerHTML);
                        removeItemIfNull(quantity);
                    } else if (event.key === 'Escape') {
                        quantity.innerHTML = oldQuantityContent;
                    }
                })
            }
        })
    }
}


function apiFetch(id, quantity) {
    fetch(`/add-product?id=${id}&quantity=${quantity}`)
        .then(data => data);
}

function removeItemIfNull(quantity) {
    if(quantity.innerHTML === "0"){
        quantity.parentNode.remove();
    }
}

function changeCartNumberSize(quantity) {
    let sum = 0;
    let cartIconNumber = document.querySelector(".cart-items");
    for (let number of quantity) {
        sum += parseInt(number.innerHTML);
    }
    cartIconNumber.innerHTML = String(sum);
}

function countSubtotal(id, quantity) {
    let prices = document.querySelectorAll(".price");
    let subTotals = document.querySelectorAll(".subtotal");
    let newSubtotalPrice;
    for (let price of prices) {
        if (price.dataset.id === id) {
            newSubtotalPrice = parseFloat(price.dataset.defaultPrice) * parseFloat(quantity.innerHTML);
            newSubtotalPrice = parseFloat(newSubtotalPrice).toFixed(2);
        }
    }
    for (let subTotal of subTotals) {
        if (subTotal.dataset.id === id) {
            subTotal.innerHTML = "Subtotal: " + String(newSubtotalPrice) + "USD";
        }
    }
}

function countTotal() {
    let sum = 0;
    let total = document.querySelector(".total");
    let subtotals = document.querySelectorAll(".subtotal");
    for (let subtotal of subtotals) {
        let number = subtotal.innerHTML.slice(10, -3);
        sum += parseFloat(number.replace(",", "."));
    }
    sum = parseFloat(sum).toFixed(2);
    total.innerHTML = `Total value: ${sum} $`;
}

function main() {
    let quantitiesOfProducts = document.querySelectorAll(".quantity");
    editQuantity(quantitiesOfProducts);
}

main();