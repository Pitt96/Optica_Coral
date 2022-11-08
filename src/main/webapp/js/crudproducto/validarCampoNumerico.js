const contenedor = document.getElementById('contenedor-campos');

contenedor.addEventListener('keydown', function (evento) {
    const elemento = evento.target;
    if (elemento.className === 'campo-numerico form-control') {
        const teclaPresionada = evento.key;
        const teclaPresionadaEsUnNumero =
                Number.isInteger(parseInt(teclaPresionada));

        const sePresionoUnaTeclaNoAdmitida =
                teclaPresionada != 'ArrowDown' &&
                teclaPresionada != 'ArrowUp' &&
                teclaPresionada != 'ArrowLeft' &&
                teclaPresionada != 'ArrowRight' &&
                teclaPresionada != 'Backspace' &&
                teclaPresionada != 'Delete' &&
                teclaPresionada != 'Enter' &&
                !teclaPresionadaEsUnNumero;
        const comienzaPorCero =
                elemento.value.length === 0 &&
                teclaPresionada == 0;

        if (sePresionoUnaTeclaNoAdmitida || comienzaPorCero) {
            evento.preventDefault();
        }
    }
});

/*
 <div id = "contenedor-campos">
 <input class="campo-numerico" type="number" min="1" pattern="^[0-9]+" onpaste="return false;" onDrop="return false;" autocomplete=off>
 <input class="campo-numerico" type="number" min="1" pattern="^[0-9]+" onpaste="return false;" onDrop="return false;" autocomplete=off>
 <input class="campo-numerico" type="number" min="1" pattern="^[0-9]+" onpaste="return false;" onDrop="return false;" autocomplete=off>
 </div>
 */