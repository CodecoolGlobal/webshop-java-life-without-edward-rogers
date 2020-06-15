function validatePassword(password, passwordVer){
    passwordVer.addEventListener("keyup", function (){
        if (validate(password, passwordVer)){
            if (passwordVer.classList.contains("redBorder")){
                passwordVer.classList.remove("redBorder")
            }
            if (!(passwordVer.classList.contains("greenBorder"))){
                passwordVer.classList.add("greenBorder");
            }
        } else {
            if (passwordVer.classList.contains("greenBorder")){
                passwordVer.classList.remove("greenBorder")
            }
            if (!(passwordVer.classList.contains("redBorder"))){
                passwordVer.classList.add("redBorder");
            }
        }
    });
}

function validate(password, passwordVer){
    return password.textContent === passwordVer.textContent;
}

function main(){
    let password = document.querySelector("#password");
    let passwordVer = document.querySelector("#passwordVer");

    validatePassword(password, passwordVer);
}

main();