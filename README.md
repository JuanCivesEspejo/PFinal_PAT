# PFinal_PAT

### Práctica final
#### Autores: Juan Orlando Cives, Juan de Egaña, Cristina Lao y Juan Pardo de Santayana Navarro

# **Aplicación de proveedores para restaurantes**
_Autores: Juan Orlando Cives Espejo, Juan de Egaña Marín, Cristina Lao Navarro y Juan Pardo de Santayana_

## **Descripción del proyecto**
El propósito de esta aplicación es la creación de un sistema que permita gestionar los pedidos que hacen los restaurantes a sus proveedores. De esta forma, los usuarios de la aplicación se podrán dar de alta con el rol de restaurante.
Los miembros que se registren con el rol de restaurante, podrán realizar pedidos empleando para ello el catálogo disponible en la aplicación.

La idea es que los proveedores, de manera similar que en la vida real, contactasen de manera privada o por otras vías con nosotros como gestores de la pagina web para que negociáramos con ellos las condiciones y les añadiéramos de manera manual a la base de datos de los proveedores, y les proporcionaríamos un usuario y clave especial para proveedores.

## **Interfaz de usuario: Casos de uso.**

### **Login y registro de usuarios**
La primera interfaz de usuario de la aplicación consiste en el registro o login de los usuarios, según sean nuevos o ya estén registrados. Para el registro, los usuarios deberán proporcionar un nombre, un email una contraseña y el rol correspondiente (que en este caso será siempre restaurante). El nuevo usuario se registrará en la base de datos y para acceder a su página personal, tendrá que iniciar sesión con su email y contraseña correspondientes. Toda esta lógica se manejará desde una API de usuarios. A modo de ilustración, el diagrama de flujo sería el siguiente:

![image](https://github.com/JuanCivesEspejo/PFinal_PAT/assets/123270221/73eea86b-548e-4faa-9146-318f253790d6)

### **Página principal de restaurantes y proveedores**
Las otras dos interfaces de usuario de las que dispone la aplicación son la página principal de los restaurantes desde la que se pueden hacer pedidos y la página principal de proveedores desde dónde se procesan dichos pedidos.

La página principal de los restaurantes tendrá las siguientes funcionalidades:
* Hacer un pedido: permite al restaurante realizar pedidos a sus proveedores.

La página principal de los proveedores tendrá las siguientes funcionalidades:
* Mirar pedidos pendientes: despliega la lista de los pedidos sin procesar en el orden en el que se han realizado.

![image](https://github.com/JuanCivesEspejo/PFinal_PAT/assets/123270221/b9643952-f8e4-49d7-a305-045f6bf901af)´

## **Diseño Técnico**

### **Modelo de Base de Datos**

Para el diseño de la base de datos, se tienen los siguientes atributos:

* User_ID: identificador unívoco para cada usuario.
* Email: correo electrónico del usuario.
* Password: contraseña del usuario hasheada.
* Role: indica si un usuario es Restaurante y Proveedor.
* Name: nombe del usuario.
* Token_ID: identificador unívoco para cada token.
* Product_ID: identificador unívoco de cada producto.
* Product_group: cada producto pertenece a un grupo como carne, vinos, lácteos...
* Product_name: nombre del producto.
* Price: precio unitario de cada producto.
* Total_Price: precio total de un pedido.
* Order_ID: identificador unívoco de cada pedido.
* Status: Indica el estado del pedido.
* Quantity: Indica la cantidad que se ha pedido de un mismo producto.
* Order_Date: Indica la fecha en la que se hizo el pedido.
* Detail_ID: Identifica unívocamente el detalle de un pedido.
* Detail_Price: Identifica el precio total de un detalle de un pedido.

Asimismo, las restricciones de la base de datos son las siguientes:

1. Cada usuario tiene un único email y ID, que se corresponden biunívocamente.
2. Cada usuario pertenece a un único rol.
3. Cada usuario tiene un nombre y una contraseña. Varios usuarios pueden tener el mismo nombre y contraseña.
4. Cada Token se relaciona con un único usuario biunívocamente.
5. Cada producto tiene un ID con el que se relaciona biunívocamente.
6. Cada producto pertenece a un único grupo y tiene un precio. Este precio puede ser el mismo para varios productos.
7. Cada producto tiene un nombre.
8. Cada producto tiene un proveedor que se encarga de su suministro. Varios productos pueden estar asociados a un mismo proveedor.
9. Para cada pedido, se quiere guardar el restaurante que lo realizó, la fecha en la que se hizo el pedido y el precio total.
10. Puesto que en un mismo pedido intervienen varios proveedores, será necesario crear detalles de cada pedido. El detalle de cada pedido tiene un identificador unívoco.
11. Cada detalle registra un único producto, la cantidad pedida de ese producto, el precio total de esta cantidad y el proveedor que se encarga de realizar ese pedido.
12. Además, en cada detalle, se registra el estado del pedido para la parte del pedido correspondiente. Es decir, si el producto X del pedido Y se esta realizando, se ha realizado o esta pendiente de realizarse.

Teniendo en cuenta las anteriores restricciones y atributos, la base de datos contendrá las siguientes tablas:

#### **Tabla de usuarios:**
Contiene información acerca de:
* User_ID: identificador del usuario. Será primary key.
* Email: correo electrónico del usuario.
* Password: contraseña hasheada.
* Role: rol del usuario (restaurante o proveedor).
* Name: Nombre del usuario.

#### **Tabla de tokens:**
Contiene información acerca de:
* Token_ID: identificador de cada token. Será primary key.
* AppUser: usuario asociado a un token. Será foreign key con la tabla usuarios.

#### **Tabla de productos:**
Contiene información acerca de:
* Product_ID: identificador de un producto. Será primary key.
* Product_group: grupo al que pertenece el producto.
* Product_name: nombre del producto.
* Price: precio unitario del producto.
* AppUser: proveedor que suministra el producto. Será foreign key con la tabla usuarios.

#### **Tabla de pedidos:**
Contiene información acerca de:
* Order_ID: identificador de cada pedido. Será primary key.
* AppUser: restaurante que realizó el pedido. Será foreign key con la tabla usuarios.
* Order_Date: fecha en la que se hizo el pedido.
* Total_Price: precio total del pedido.

#### **Tabla de detalles del pedidio:**
Contiene información acerca de:
* Detail_ID: identificador de detalle del pedido. Será primary key.
* Order: pedido asociado al detalle. Será foreign key con la tabla pedidos.
* Product: producto asociado al detalle. Será foreign key con la tabla productos.
* Quantity: cantidad pedida del producto.
* Detail_Price: precio del detalle de la cantidad pedida.
* AppUser: proveedor que realiza el pedido. Será foreign key con la tabla usuarios.
* Status: estado del pedido (Pendiente, En proceso, Realizado).

## Tabla con los endpoints del api rest:
| Método | Ruta | Descripción | Respuestas           |
|--------|------|-------------|----------------------|
| POST   |    "/api/orders"  | Este método crea una orden de pedido.| CREATED, NOT FOUND, BAD REQUEST|
| GET |   "/api/productos"   | Este método te proporciona una lista con todos los productos | OK |
| GET|    "/api/orders/delivery/{providerEmail}"  |   Este método te proporciona una lista de ordenes por cada proveedores en función de su email   | OK |
|POST|"/api/users"|Este método registra un nuevo usuario.|CREATED, CONFLICT|
|POST|"/api/users/me/session"|Este método inicia sesión para el usuario.|CREATED, UNAUTHORIZED|
|DELETE|"/api/users/me/session"|Este método cierra sesión para el usuario.|NO CONTENT, UNAUTHORIZED|
|GET|"/api/users/me"|Este método devuelve el perfil del usuario.|OK, UNAUTHORIZED|
|PUT|"/api/users/me"|Este método actualiza el perfil del usuario.|OK, UNAUTHORIZED|
|DELETE|"/api/users/me"|Este método elimina el perfil del usuario.|NO CONTENT, UNAUTHORIZED|
|GET|"/api/orders/restaurant"/{clientEmail}"|Este método nos devuelve la lista de pedidos realizado por el email .|OK|
