function datosPerfil() {
  return fetch('/api/users/me').then(res => res.json());
}

function pedidosPendientes(providerEmail) {
  return fetch(`/api/orders/delivery?email=${encodeURIComponent(providerEmail)}`).then(res => res.json());
}

function cargarPerfil() {
  return datosPerfil().then(perfil => {
    document.getElementById('nombre-inicio').textContent = perfil.name;
    document.getElementById('tel-inicio').textContent = perfil.role;
    document.getElementById('email-inicio').textContent = perfil.email;

    return perfil.email;
  });
}

function cargarPedidos(providerEmail) {
  return pedidosPendientes(providerEmail).then(pedidos => {
    const tablaPedidos = document.getElementById('tabla-pedidos');
    tablaPedidos.innerHTML = '';

    pedidos.forEach(pedido => {
      const fila = document.createElement('tr');
      fila.innerHTML = `
        <td>${pedido.id}</td>
        <td>${pedido.cliente}</td>
        <td>${pedido.direccion}</td>
        <td>${pedido.estado}</td>
      `;
      tablaPedidos.appendChild(fila);
    });
  });
}

function articuloInicio() {
  cargarPerfil().then(providerEmail => {
    if (providerEmail) {
      return cargarPedidos(providerEmail);
    } else {
      console.error('No se encontró el supplierId en el perfil del usuario');
    }
  }).catch(error => {
    console.error('Error al cargar el perfil o los pedidos:', error);
  });
}

function salir() {
  fetch('/api/users/me/session', {method: 'delete'})
    .then(() => location.href = 'login.html');
}

function baja() {
  if (confirm("Esto borrará tu usuario, ¿estás seguro?")) {
    fetch('/api/users/me', {method: 'delete'})
      .then(() => location.href = 'login.html');
  }
}

addEventListener('hashchange', inicializar);

function inicializar() {
  Array.from(document.querySelectorAll('article')).forEach(a => a.hidden = true);
  Array.from(document.querySelectorAll('nav a')).forEach(a => a.classList.remove('active'));
  const articulo = location.hash || "#inicio";
  cargarArticulo(articulo).then(() => mostrarArticulo(articulo));
}

function cargarArticulo(articulo) {
  switch(articulo) {
    case '#inicio': return articuloInicio();
    default: return articuloInicio();
    return Promise.resolve();
  }
}

function mostrarArticulo(articulo) {
  document.querySelector(articulo).hidden = false;
  document.querySelector(`a[href="${articulo}"]`).classList.add('active');
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}