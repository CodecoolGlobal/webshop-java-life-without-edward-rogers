function payWithPayPal(payContainer) {
    let payPalButton = document.querySelector("#paypal");
    payPalButton.addEventListener('click', function () {
        clearContainer();
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
    let creditCardButton = document.querySelector("#credit-card");
    creditCardButton.addEventListener('click', function (event) {

        clearContainer();
        let date = new Date();
        let thisYear = date.getFullYear();
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
            selectOptions +=       `<option value="${month}">${month}</option>`;
        }






        
<<<<<<< HEAD
        selectOptions +=           `</select><br><label for="year">Year:</label><select id="year">`;
        for (let year = thisYear; year < thisYear + 6; year++) {
            selectOptions +=       `<option value="${year}">${year}</option>`;
        }
        selectOptions += `          </select><br>
                                    <label for="CVC">CVC:</label>
                                    <input type='password' id='CVC' maxlength="3" required><br>
                                    <button type="submit">Pay</button>
=======
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
>>>>>>> feature/style
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
