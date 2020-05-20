/**
 * validates whether the checkbox is ticked or not.
 * Replicates the content of Billing's Card towards the Shipping's Card.
 */
function validate() {
    if (this.checked) {
        document.getElementById("countryS").value = document.getElementById("country").value;
        document.getElementById("cityS").value = document.getElementById("city").value;
        document.getElementById("zipS").value = document.getElementById("zip").value;
        document.getElementById("addressS").value = document.getElementById("address").value;
    } else {
        document.getElementById("countryS").value = "";
        document.getElementById("cityS").value = "";
        document.getElementById("zipS").value = "";
        document.getElementById("addressS").value = "";
    }
}

function main() {
    let button = document.getElementById("fill-or-delete");
    button.addEventListener('click', validate);
}

main();
