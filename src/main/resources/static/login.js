function inicializar() {
  if (location.search === '?registrado') {
    mostrarAviso('✓ ¡Registrado! Prueba a entrar', 'success');
  }
}

function entrar(datosJsonFormulario) {
  fetch('http://localhost:8080/api/users', {
    method: 'post',
    body: datosJsonFormulario,
    headers: {'content-type': 'application/json'}
  })
    .then(response => {
      if (response.ok) {
        const sessionCookie = document.cookie
          .split('; ')
          .find(row => row.startsWith('session='))
          .split('=')[1];

        return fetch(' http://localhost:8080/api/users', {
          method: 'get',
          headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${sessionCookie}`
          }
        });
      } else {
        throw new Error('Credenciales incorrectas');
      }
    })
    .then(response => response.json())
    .then(data => {
      if (data && data.role) {
        if (data.role === 'restaurante') {
          location.href = 'restaurante.html';
        } else if (data.role === 'proveedor') {
          location.href = 'proveedor.html';
        }
      }
    })
    .catch(error => {
      mostrarAviso(`✖︎ ${error.message}`, 'error');
    });
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}