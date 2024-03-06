$(document).ready(function() {
    // Obtener la última fila de la tabla
    var lastRow = $("#creditTable tbody tr:last");

    // Verificar el valor de status en la última fila
    var status = lastRow.find("td:last span").text();

    if (status === "DRAFT") {
        // Mostrar el botón si el estado es DRAFT
        $("#editCredit").show();
        $("#confirmRequest").show();

    } else {
        // Ocultar el botón si el estado no es DRAFT
        $("#editCredit").hide();
        $("#confirmRequest").hide();
    }
});

function submitFormExternally() {
    // Obtener referencia al formulario
    var form = document.getElementById("personForm");

    // Verificar si el formulario existe
    if (form) {
        // Disparar el evento de envío del formulario
        form.dispatchEvent(new Event("submit"));
    }
}

