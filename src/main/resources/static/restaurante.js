document.addEventListener('DOMContentLoaded', () => {
  cargarProductos();
  configurarBotonPedido();

});

function cargarProductos() {
  fetch('/api/products')
    .then(response => response.json())
    .then(products => {
      const productList = document.getElementById('product-list');
      products.forEach(product => {
        const productItem = crearElementoProducto(product);
        productList.appendChild(productItem);
      });
    })
    .catch(error => {
      console.error('Error al cargar productos:', error);
    });
}

function crearElementoProducto(product) {
  const productItem = document.createElement('div');
  productItem.className = 'product-item';
  productItem.innerHTML = `
    <h3>${product.productName}</h3>
    <p>Precio: $${product.productPrice}</p>
    <input type="number" min="0" placeholder="Cantidad" id="${product.productName}">
  `;
  return productItem;
}

function configurarBotonPedido() {
  document.getElementById('submit-order').addEventListener('click', () => {
    const order = recogerPedido();
    if (Object.keys(order).length > 0) {
      enviarPedido(order);
    } else {
      alert('Por favor, seleccione al menos un producto.');
    }
  });
}

function recogerPedido() {
  const order = {};
  document.querySelectorAll('.product-item input').forEach(input => {
    const productName = input.id;
    const quantity = parseInt(input.value);
    if (quantity > 0) {
      order[productName] = quantity;
    }
  });
  return order;
}

function enviarPedido(order) {
  const date = new Date();
  const formattedDate = `${String(date.getDate()).padStart(2, '0')}/${String(date.getMonth() + 1).padStart(2, '0')}/${date.getFullYear()}`;

  const emailInicioElement = document.getElementById('email-inicio').textContent;
  const orderData = {
    restaurant: emailInicioElement,
    date: formattedDate,
    orders: order
  };

  fetch('/api/orders', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(orderData)
  })
    .then(response => response.json())
    .then(data => {
      alert('Pedido realizado con éxito');
    })
    .catch(error => {
      console.error('Error al realizar el pedido:', error);
      alert('Hubo un error al realizar el pedido');
    });
}

function datosPerfil() {
  return fetch('/api/users/me').then(res => res.json());
}

function articuloInicio() {
  return datosPerfil().then(perfil => {
    document.getElementById('nombre-inicio').textContent = perfil.name;
    document.getElementById('tel-inicio').textContent = perfil.role;
    document.getElementById('email-inicio').textContent = perfil.email;
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
  }
}

function mostrarArticulo(articulo) {
  document.querySelector(articulo).hidden = false;
  document.querySelector(`a[href="${articulo}"]`).classList.add('active');
}

function pedidosRestaurante(clientEmail) {
  const url = `/api/orders/restaurant/${clientEmail}`;
  console.log("Request URL:", url);
  return fetch(url).then(res => {
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    return res.json();
  });
}

function cargarPedidos(email) {
  return pedidosRestaurante(email).then(pedidos => {
    console.log(pedidos);
    const tablaPedidos = document.getElementById('tabla-pedidos');
    tablaPedidos.innerHTML = '';

    pedidos.forEach(pedido => {
      console.log(pedido);
      const fila = document.createElement('tr');
      fila.innerHTML = `
        <td>${pedido.id}</td>
        <td>${pedido.orderDate}</td>
        <td>${pedido.totalPrice}</td>
      `;
      tablaPedidos.appendChild(fila);
    });
  }).catch(error => {
    console.error('Error al cargar los pedidos pendientes:', error);
  });}

function actualizarPedidos(){
    const email = document.getElementById('email-inicio').textContent;
    cargarPedidos(email);
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

