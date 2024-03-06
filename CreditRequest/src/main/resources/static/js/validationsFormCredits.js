document.addEventListener("DOMContentLoaded", function () {
    // Obtén el formulario por su ID o cualquier otro selector
    const form = document.getElementById('personForm'); // Reemplaza 'personForm' con el ID de tu formulario

// Obtén todos los elementos input dentro del formulario
    const inputElements = form.querySelectorAll('input, select');

// Itera a través de los elementos input
    inputElements.forEach((inputElement) => {
        // Verifica si el input no está deshabilitado
        if (!inputElement.disabled) {
            const valorInput = inputElement.value;

            // Verifica si el input tiene un valor y agrega la clase 'is-valid' si es necesario
            if (valorInput.trim() !== '') {
                inputElement.classList.add('is-valid');
            }
        }
    });

    const disabledInputsWithValue = document.querySelectorAll('input[disabled][value]');

    disabledInputsWithValue.forEach(function (input) {
        input.classList.add('is-valid');
    });
});

// Función para validar el DNI
function validateDNI(inputElement) {

    if (/^\d{1,16}$/.test(inputElement.value)) {
        // La expresión regular /\d{1,16}/ verifica que haya de 1 a 16 dígitos.
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
    } else {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
    }
}

function validateFullName(inputElement) {

    // Utiliza una expresión regular para verificar si el valor no contiene números
    if (/^[^0-9]+$/.test(inputElement.value.trim())) {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
    } else {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
    }
}

function validateEmail(inputElement) {
    // Expresión regular para validar el formato del correo electrónico
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    // Dominios permitidos
    const allowedDomains = ["hotmail.com", "gmail.com"];

    // Obtén el valor del campo de correo electrónico
    const email = inputElement.value.trim();

    // Verifica si el correo electrónico tiene el formato correcto
    if (!emailPattern.test(email)) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");

        // Elimina el atributo 'required' si no se cumple la validación
        inputElement.removeAttribute("required");

        return;
    }

    // Verifica si el correo electrónico tiene un dominio permitido
    const domain = email.split("@")[1]; // Obtiene el dominio del correo electrónico
    if (domain.endsWith(".edu.co") || allowedDomains.includes(domain)) {
        // Si pasa todas las validaciones, se marca como válido
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
    } else {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");

        // Elimina el atributo 'required' si no se cumple la validación
        inputElement.removeAttribute("required");
    }
}



function validateGender(selectElement) {
    // Obtén el valor seleccionado
    const selectedValue = selectElement.value;

    // Verifica si se seleccionó una opción válida
    if (selectedValue === "") {
        selectElement.classList.remove("is-valid");
        selectElement.classList.add("is-invalid");
    } else {
        selectElement.classList.remove("is-invalid");
        selectElement.classList.add("is-valid");
    }
}

function validateBirthDate(inputElement) {
    const inputDate = new Date(inputElement.value);
    const currentDate = new Date();

    // Define la edad mínima permitida (por ejemplo, 18 años)
    const minAge = 18;

    // Calcula la fecha hace 18 años a partir de la fecha actual
    const minAgeDate = new Date(currentDate.getFullYear() - minAge, currentDate.getMonth(), currentDate.getDate());

    if (inputDate < minAgeDate) {
        inputElement.setCustomValidity("");
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
    } else {
        inputElement.setCustomValidity("You must be at least 18 years old.");
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
    }
}

function validatePhoneNumber(inputElement) {
    // Obtén el valor del campo de teléfono
    const phoneNumber = inputElement.value.trim();

    // Define una expresión regular para un formato de número de teléfono deseado
    const phonePattern = /^[0-9]{10}$/;

    // Realiza la validación
    if (!phonePattern.test(phoneNumber)) {
        inputElement.setCustomValidity("Please enter a valid 10-digit phone number.");
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
    } else {
        inputElement.setCustomValidity("");
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
    }
}

function validateMarriageYears(inputElement) {
    const Years = inputElement.value;
    if (!inputElement.value.trim()) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
        return false;
    } else {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
        return true;
    }
}

function validatebothEmployees(selectElement) {
    // Obtén el valor seleccionado
    const selectedValue = selectElement.value;

    // Verifica si se seleccionó una opción válida
    if (selectedValue === "") {
        selectElement.classList.remove("is-valid");
        selectElement.classList.add("is-invalid");
    } else {
        selectElement.classList.remove("is-invalid");
        selectElement.classList.add("is-valid");
    }
}

function validateHousePrices(inputElement) {
    const Price = inputElement.value;
    if (!inputElement.value.trim()) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
        return false;
    } else {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
        return true;
    }
}

function updateCoupleSavingsMax() {
    const housePricesInput = document.getElementById('housePrices');
    const coupleSavingsInput = document.getElementById('coupleSavings');

    // Obtener el valor actual de housePrices
    const housePricesValue = parseFloat(housePricesInput.value);

    // Establecer el valor máximo de coupleSavings
    coupleSavingsInput.max = housePricesValue * 0.10;
}


function validateQuotaValue(inputElement) {
    const value = inputElement.value;
    if (!inputElement.value.trim()) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
        return false;
    } else {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
        return true;
    }
}

function validateCoupleSavings(inputElement) {
    const Savings = inputElement.value;
    if (!inputElement.value.trim()) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
        return false;
    } else {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
        return true;
    }
}

function validatePdfFile(inputElement) {
    if (!inputElement.value.trim()) {
        inputElement.classList.remove("is-valid");
        inputElement.classList.add("is-invalid");
        return false;
    } else {
        inputElement.classList.remove("is-invalid");
        inputElement.classList.add("is-valid");
        return true;
    }
}

function validateFormField(form) {
    const validElementsCouple = form.querySelectorAll('.is-valid');
    const formElements = form.elements;
    // Verifica si todos los campos tienen la clase 'is-valid'
    if (validElementsCouple.length === formElements.length - 1) {
        // Mostrar la alerta con un indicador de carga al inicio del envío del formulario
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
                    text: 'the couple has been successfully registered',
                    showConfirmButton: false,
                })
            }
        })
        return true; // Envía el formulario si todos los campos son válidos
    } else {
        // Muestra una alerta si algún campo no es válido
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Error!',
            text: 'please enter correct information',
            showConfirmButton: false,
            timer: 1000
        })
        return false; // Evita el envío del formulario
    }
}
