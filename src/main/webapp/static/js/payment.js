function payWithPayPal(payContainer) {
    let payPalButton = document.querySelector("#paypal");
    payPalButton.addEventListener('click', function () {
        clearContainer();
        payContainer.classList.add('card')
        payContainer.classList.add('payment-card')
        payContainer.innerHTML = `<form action="/email" method="get">
                                <label for="email">Email:</label>
                                <input style="width: 80%;" type='email' id="email" required><br>
                                <label for="password">Password:</label>
                                <input style="width: 70%;" type='password' id="password"><br>
                                <button style="margin: 2%" type="submit">Pay</button>
                                </form>`;
    })
}

function payWithCreditCard(payContainer) {
    let creditCardButton = document.querySelector("#credit-card");
    creditCardButton.addEventListener('click', function (event) {

        clearContainer();
        let date = new Date();
        let thisYear = date.getFullYear();
        payContainer.classList.add('card')
        payContainer.classList.add('payment-card')
        let selectOptions = `
                                    <form action="/email" method="get">
                                    <label for="card-number">Card number:</label><br>
                                    <input type='text' id='card-number' pattern="[0-9]+" minlength="16" maxlength="16" required><br>
                                    <label for="card-holder">Bank:</label><br>
                                    <input type='text' id='card-holder' required><br>`;

        selectOptions += `<label  for="month">Month:</label><select class="month-label" id='month'>`;
        for (let month = 1; month <= 12; month++) {
            selectOptions += `<option value="${month}">${month}</option>`;
        }

        selectOptions += `</select><br><label for="year">Year:</label><select id="year">`;
        for (let year = thisYear; year < thisYear + 6; year++) {
            selectOptions += `<option value="${year}">${year}</option>`;
        }
        selectOptions += `          </select><br>
                                    <label for="CVC">CVC:</label><br>
                                    <input type='password' pattern="[0-9]+" id='CVC' minlength="3" maxlength="3" required><br>
                                    <button style="margin: 2%" type="submit">Pay</button>
                                    </form>`;
        payContainer.innerHTML += selectOptions;
    })
}

function clearContainer() {
    document.getElementById("payment-container").innerHTML = ' ';
}

function main() {
    let payContainer = document.querySelector("#payment-container");
    payWithCreditCard(payContainer);
    payWithPayPal(payContainer);
}

main();
