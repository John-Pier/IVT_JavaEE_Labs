'use strict';

const formName = "calculationForm";

const calculationFormFieldNames = {
    FIRST: "first",
    SECOND: "second",
    OPERATION: "operation",
};

window.onload = () => makeSubmitHandler();

function makeSubmitHandler() {
    const form = document.forms.namedItem(formName);
    if(!form) {
        return;
    }

    form.addEventListener("submit", e => {
        const operation = form.elements[calculationFormFieldNames.OPERATION].value;
        const secondOperand =  form.elements[calculationFormFieldNames.SECOND].value;
        if(operation === "/" && secondOperand === 0) {
            e.preventDefault();
            alert("The second operand must not be 0 if division is selected.!");
        }
    })
}