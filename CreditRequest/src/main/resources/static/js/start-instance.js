function enviarFilaConEstadoDraft() {
    // Obtén todas las filas de la tabla
    var filas = document.querySelectorAll('#creditTable tbody tr');

    // Itera a través de las filas y busca la fila con estado "DRAFT"
    for (var i = 0; i < filas.length; i++) {
        var estado = filas[i].querySelector('td:last-child span').textContent.trim();
        if (estado === "DRAFT") {
            // Encuentra la fila con estado "DRAFT", obtén los datos y envíalos al controlador
            var taskId = filas[i].querySelector('td:first-child span').textContent.trim();
            // Verificar el valor de la penúltima columna (penúltimo td) en la última fila
            var lastColumnValue = filas[i].querySelector("td:nth-last-child(2) span").textContent;
            console.log(lastColumnValue);
            console.log("procesoId: " + taskId);

            // Crear un formulario oculto y agregar los datos a enviar
            var form = document.getElementById('routing');

            if(lastColumnValue == 0){
                form.action = "/complete"
            }else{
                form.action = "/message-event"
            }

            // Después de obtener los valores
            document.getElementById('taskId').value = taskId;

            document.body.appendChild(form);

            // Enviar el formulario
            form.submit();

            // Marcar que se envió el formulario
            formularioEnviado = true;


            break; // Detener la iteración después de enviar la fila
        }
    }

    // Mostrar la alerta de éxito o error después de enviar el formulario
    if (formularioEnviado) {
        Swal.fire({
            position: 'center',
            icon: 'info',
            title: 'sending response',
            timerProgressBar: true,
            showConfirmButton: false,
            timer: 2500
        }).then((result) => {
            /* Read more about handling dismissals below */
            if (result.dismiss === Swal.DismissReason.timer) {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'successful sending!',
                    text: 'The application has been sent successfully',
                    showConfirmButton: false,
                })
            }
        })
    } else {
        // Muestra la alerta de error
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Error!',
            text: 'Please try to send the application again',
            showConfirmButton: false,
            timer: 1000
        });
    }

}