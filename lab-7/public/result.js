'use strict';

const operationsMap = {
    '-': (a,b) => a - b,
    '+': (a,b) => a + b,
    '/': (a,b) => a / b,
    '*': (a,b) => a * b,
};

const queriesParamsNames = {
    FIRST: "first",
    SECOND: "second",
    OPERATION: "operation",
};

const tableBodyId = 'tbody-result';

window.onload = () => setCalculationResult();

function setCalculationResult() {
    const tableBody = document.getElementById(tableBodyId);
    const queriesParams =  new URL(window.location.href).searchParams;
    const first = Number.parseFloat(queriesParams.get(queriessParamsNames.FIRST));
    const second = Number.parseFloat(queriesParams.get(queriesParamsNames.SECOND));
    const operation = queriesParams.get(queriesParamsNames.OPERATION);

    if(!([first, second, operation].filter(v => Boolean(v.toString())).length && operationsMap[operation])) {
        alert("Calculation failed!");
        return;
    }

    const row = document.createElement("tr");
    row.innerHTML = `<td>${operation}</td><td>${first}</td><td>${second}</td><td>${operationsMap[operation](first, second)}</td>`;
    tableBody.append(row);
}