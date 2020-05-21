/**
 * validates whether the checkbox is ticked or not.
 * Replicates the content of Billing's Card towards the Shipping's Card.
 */
function validate() {
    let billingAddress = ["country", "city", "zip", "address"];
    let shippingAddress = ["countryS", "cityS", "zipS", "addressS"];
    if (this.checked) {
        for(let addressElement = 0; addressElement < billingAddress.length; addressElement++){
            document.getElementById(shippingAddress[addressElement]).value = document.getElementById(billingAddress[addressElement]).value;
        }
    } else {
        for(let element of shippingAddress){
            document.getElementById(element).value = "";
        }
    }
}

function main() {
    let button = document.getElementById("fill-or-delete");
    button.addEventListener('click', validate);
}

main();
