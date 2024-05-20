function inicializar() {
  if (location.search === '?registrado') {
    mostrarAviso('✓ ¡Registrado! Prueba a entrar', 'success');
  }
}

function entrar(datosJsonFormulario) {
  fetch('/api/users/me/session', {
    method: 'POST',
    body: datosJsonFormulario,
    headers: {'content-type': 'application/json'}
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Credenciales incorrectas');
      }
      return fetch('/api/users/me');
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Error al obtener el perfil del usuario');
      }
      return response.json();
    })
    .then(profile => {
      if (profile.role === 'RESTAURANTE') {
        location.href = 'restaurante.html';
      } else if (profile.role === 'PROVEEDOR') {
        location.href = 'proveedor.html';
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