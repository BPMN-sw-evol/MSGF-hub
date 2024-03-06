function fillFormFields(button) {
    // Obtener la fila correspondiente
    var row = button.closest("tr");

    // Obtener los datos de la fila
    var processId = row.querySelector("td:nth-child(1)").textContent;

    var form = document.createElement('form');
    form.method = 'post';
    form.action = '/view-credit-committee'; // Endpoint donde se enviará el formulario

    // Crear un input oculto para enviar el processId
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'processId'; // Nombre del parámetro
    input.value = processId; // Valor del processId

    // Agregar el input al formulario
    form.appendChild(input);

    // Agregar el formulario al body para que se envíe
    document.body.appendChild(form);

    // Enviar el formulario
    form.submit();
}
