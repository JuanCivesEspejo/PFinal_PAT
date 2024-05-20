document.addEventListener('DOMContentLoaded', () => {
  cargarProductos();
  configurarBotonPedido();
});

function datosPerfil() {
  return fetch('/api/users/me').then(res => res.json());
}

function pedidosPendientes(email) {
  const url = `/api/orders/delivery/${encodeURIComponent(email)}`;
  console.log("Request URL:", url);
  return fetch(url).then(res => {
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    return res.json();
  });
}

function cargarPerfil() {
  return datosPerfil().then(perfil => {
    document.getElementById('nombre-inicio').textContent = perfil.name;
    document.getElementById('tel-inicio').textContent = perfil.role;
    document.getElementById('email-inicio').textContent = perfil.email;
    return perfil.email;
  });
}

function cargarPedidos(email) {
  return pedidosPendientes(email).then(pedidos => {
    const tablaPedidos = document.getElementById('tabla-pedidos');
    tablaPedidos.innerHTML = '';

    pedidos.forEach(pedido => {
    console.log(pedido);
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

function configurarBotonPedido() {
  const botonCargarPedidos = document.getElementById('boton-cargar-pedidos');
  botonCargarPedidos.addEventListener('click', () => {
    datosPerfil().then(perfil => {
      const email = perfil.email;
      if (email) {
        return cargarPedidos(email);
      } else {
        console.error('No se encontró el correo electrónico en el perfil del usuario');
      }
    }).catch(error => {
      console.error('Error al cargar los pedidos pendientes:', error);
    });
  });
}

function articuloInicio() {
  return cargarPerfil().then(email => {
    if (email) {
      return cargarPedidos(email);
    } else {
      console.error('No se encontró el correo electrónico en el perfil del usuario');
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
  cargarArticulo(articulo).then(() => mostrarArticulo(articulo)).catch(error => {
    console.error('Error al cargar el artículo:', error);
  });
}

function cargarArticulo(articulo) {
  switch (articulo) {
    case '#inicio':
      return articuloInicio();
    default:
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
