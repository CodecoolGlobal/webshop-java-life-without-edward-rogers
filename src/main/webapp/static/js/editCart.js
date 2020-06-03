function editQuantity(quantitiesOfProducts) {
    for (let quantity of quantitiesOfProducts) {
        quantity.addEventListener("dblclick", handleCartEdit);
    }
}

function handleCartEdit() {
    let quantitiesOfProducts = document.querySelectorAll(".quantity");
    let oldQuantityContent = this.innerHTML;
    let newQuantityElement = document.querySelector('#input');
    if (newQuantityElement == null) {
        this.innerHTML = `<input type="number" autocomplete="off" value="${oldQuantityContent}" min="0" class="quantity-input" id="input">`;
        newQuantityElement = document.querySelector('#input');
        let bindHandleEnterAndEscape = handleEnterAndEscape.bind(null, this, newQuantityElement, quantitiesOfProducts, oldQuantityContent);
        newQuantityElement.addEventListener('keydown', bindHandleEnterAndEscape)
    }
}

function handleEnterAndEscape(quantity, newQuantityElement, quantitiesOfProducts, oldQuantityContent) {
    if (event.key === 'Enter') {
        quantity.innerHTML = newQuantityElement.value;
        changeCartNumberSize(quantitiesOfProducts);
        countAndChangeSubtotal(quantity.dataset.id, quantity);
        countTotal();
        apiFetch(quantity.dataset.id, quantity.innerHTML);
        removeItemIfNull(quantity);
        setCheckoutButton();
    } else if (event.key === 'Escape') {
        setCheckoutButton();
        quantity.innerHTML = oldQuantityContent;
    }
}

function checkErrorMessage() {
    let errorMessage = document.querySelector("#error");
    if(errorMessage.textContent !== ""){
        let errorContainer = document.querySelector("#errorContainer");
        errorContainer.innerHTML += `<a href="/">Back to items.</a>`;
        let tableContainer = document.querySelector("#tableContainer");
        tableContainer.innerHTML = null;
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
    let quantitiesOfProducts = document.querySelectorAll(".quantity");
    removeEntireTable(quantitiesOfProducts);
}

function changeCartNumberSize(quantity) {
    let sum = 0;
    let cartIconNumber = document.querySelector(".cart-items");
    for (let number of quantity) {
        sum += parseInt(number.innerHTML);
    }
    cartIconNumber.innerHTML = String(sum);
}

function countAndChangeSubtotal(id, quantity) {
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
            subTotal.innerHTML = String(newSubtotalPrice) + "USD";
        }
    }
}

function countTotal() {
    let sum = 0;
    let total = document.querySelector(".total");
    let subtotals = document.querySelectorAll(".subtotal");
    for (let subtotal of subtotals) {
        let number = subtotal.innerHTML.slice(0, -3);
        sum += parseFloat(number.replace(",", "."));
    }
    sum = parseFloat(sum).toFixed(2);
    total.innerHTML = `Total value: ${sum} $`;
}

function setCheckoutButton() {
    let total = document.querySelector(".total").innerHTML;
    let cont = document.querySelector("#checkout");
    let totalInt = parseInt(total.slice(13, -2));
    cont.innerHTML = (totalInt === 0) ?
        `<button class="btn btn-success"><a id="disabled-link" href="/checkout" style="color: white">Checkout</a></button>` :
        `<button class="btn btn-success"><a href="/checkout" style="color: white">Checkout</a></button>`;
}

function removeEntireTable(quantitiesOfProducts){
    if(quantitiesOfProducts.length === 0){
        let entireTable = document.querySelector("#tableContainer");
        entireTable.innerHTML = null;
        let errorMessage = document.querySelector("#errorContainer");
        errorMessage.innerHTML = `<h1 id="error">There is nothing to display as you have nothing in your Cart.</h1>`;
        let errorContainer = document.querySelector("#errorContainer");
        errorContainer.innerHTML += `<a href="/">Back to items.</a>`;
    }
}

function main() {
    checkErrorMessage();
    setCheckoutButton();
    let quantitiesOfProducts = document.querySelectorAll(".quantity");
    editQuantity(quantitiesOfProducts);

}

main();