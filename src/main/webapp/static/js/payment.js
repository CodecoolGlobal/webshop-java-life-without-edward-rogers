function payWithPayPal(payContainer) {
    clearContainer(payContainer);
    let payPalButton = document.querySelector("#paypal");
    payPalButton.addEventListener('click', function () {
        payContainer.classList.add('card')
        payContainer.classList.add('payment-card')
        payContainer.innerHTML = `<form action="/" method="get">
                                <label for="email">Email:</label>
                                <input type='email' id="email" required><br>
                                <label for="password">Password:</label>
                                <input type='password' id="password"><br>
                                <button style="margin: 1%" type="submit">Pay</button> 
                                </form>`;
    })
}

function payWithCreditCard(payContainer) {
    clearContainer(payContainer);
    let creditCardButton = document.querySelector("#credit-card");
    creditCardButton.addEventListener('click', function (event) {
        payContainer.classList.add('card')
        payContainer.classList.add('payment-card')
        let selectOptions = `
                                    <form action="/" method="get">
                                    <label for="card-number">Card number:</label><br>
                                    <input type='text' id='card-number' required><br>
                                    <label for="card-holder">Bank:</label><br>
                                    <input type='text' id='card-holder' required><br>`;

        selectOptions += `<label  for="month">Month:</label><select class="month-label" id='month'>`;
        for (let month = 1; month <= 12; month++) {
            selectOptions += `<option value="${month}">${month}</option>`;
        }
        selectOptions +=
            `</select><label  for="year">Year:</label><select id="year">`;
        let d = new Date();
        let n = d.getFullYear();
        for (let year = n; year < n + 6; year++) {
            selectOptions += `<option value="${year}">${year}</option>`;
        }
        selectOptions += `</select><br>`;


        selectOptions += `
        <label for="CVC">CVC:</label><br>
                <input type='password' id='CVC' maxlength="3" required><br>
                                    <button style="margin: 1%" type="submit">Pay</button> 
                                    </form>`;
        payContainer.innerHTML += selectOptions;
    })
}

function clearContainer(payContainer) {
    payContainer.innerHTML = '';
}

function main() {
    let payContainer = document.querySelector("#payment-container");
    payWithCreditCard(payContainer);
    payWithPayPal(payContainer);
}

main();
