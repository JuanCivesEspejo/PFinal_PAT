function datosPerfil() {
  return fetch('/api/users/me').then(res => res.json());
}

function pedidosPendientes(providerEmail) {
  const url = `/api/orders/delivery/${providerEmail}`;
  console.log("Request URL:", url);
  return fetch(url).then(res => {
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    return res.json();
  });
}

function articuloInicio() {
  return datosPerfil().then(perfil => {
    document.getElementById('nombre-inicio').textContent = perfil.name;
    document.getElementById('tel-inicio').textContent = perfil.role;
    document.getElementById('email-inicio').textContent = perfil.email;
    return cargarPedidos(perfil.email);
  }).catch(error => {
    console.error('Error al cargar el perfil:', error);
  });
}

function cargarPedidos(email) {
  return pedidosPendientes(email).then(pedidos => {
    console.log(pedidos);
    const tablaPedidos = document.getElementById('tabla-pedidos');
    tablaPedidos.innerHTML = '';

    pedidos.forEach(pedido => {
      console.log(pedido);
      const fila = document.createElement('tr');
      fila.innerHTML = `
        <td>${pedido.order.id}</td>
        <td>${pedido.order.appUser.email}</td>
        <td>${pedido.product.productName}</td>
        <td>${pedido.detailPrice}</td>
        <td>${pedido.status}</td>
      `;
      tablaPedidos.appendChild(fila);
    });
  }).catch(error => {
    console.error('Error al cargar los pedidos pendientes:', error);
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
  cargarArticulo(articulo).then(() => mostrarArticulo(articulo)).catch(error => {
    console.error('Error al cargar el artículo:', error);
  });
}

function cargarArticulo(articulo) {
  switch(articulo) {
    case '#inicio': return articuloInicio();
    default: return articuloInicio();
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
