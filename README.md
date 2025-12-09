# **Stocktrack**

Proyecto Integrador - Desarrollo de Aplicaciones Móviles
4to Cuatrimestre Grupo F
Fecha de entrega: 9 de Diciembre

## **Equipo de Desarrollo:**

1. [Cano Landa Jorge Emmanuel]	Repositorio, Backend, Retrofit, Login, Distribución de carpetas	@Emmanuel-Cano
2. [Ocampo Vargas Ricardo Naul]	UI Design, Fronted, Lógica de CRUD	@RicardoOcampo06
3. [Villalba González Carlos Giovanni]	Sensor, Lógica, Backend, CRUD, Retrofit	@carlosvillalba06


## **Descripción del Proyecto**

¿Qué hace la aplicación?
Esta aplicación es un gestor de inventario bien pasado de perro, donde los usuarios pueden registrarse, acceder y empezar a administrar sus productos sin complicaciones. Dentro de la app podrás agregar artículos con toda su información, incluyendo:

1. Nombre del producto

2. Descripción detallada

3. Cantidad disponible

4. Fecha de Registro

5. Tipo

6. Imagen del producto

Todo esto pensado para que lleves tu inventario más fácil, más rápido y bien perrón.

### **Objetivo:**
Demostrar la implementación de una arquitectura sólida en Android utilizando servicios web, manejo de datos, lógica de negocio y el uso de hardware del dispositivo.

## **Stack Tecnológico y Características**

Este proyecto fue desarrollado siguiendo los lineamientos de la materia:

Lenguaje: Kotlin 100%.

Interfaz de Usuario: Jetpack Compose.

Arquitectura: MVVM (Model-View-ViewModel).

Conectividad (API REST): Retrofit.

GET:
1. Obtener todos los productos
GET /products
La app consume este endpoint para cargar y mostrar la lista completa del inventario.

2. Obtener un producto por ID
GET /products/{id}
Se utiliza cuando el usuario selecciona un producto para ver o editar sus datos.

POST: 
1. Registrar un nuevo producto
POST /products

JSON: crear productos sin imagen

Campos enviados: name, description, amount, date, type, image

2. Registrar un usuario
POST /auth/register
La app realiza el registro enviando: nombre, email, password, passwordc

POST — Login

POST /auth/login
Valida credenciales y devuelve los datos del usuario si son correctos.

UPDATE:
PUT /products/{id}
Permite actualizar datos del producto, incluyendo su imagen si se envía nuevamente como archivo.

DELETE:
DELETE — Eliminar producto


## **Uso del sensor:**
La aplicación utiliza el sensor para tomar foto del producto y poder identificarlo sin ningun problema.


## **Instalación y Releases**

El ejecutable firmado (.apk) se encuentra disponible en la sección de **Releases** de este repositorio.

1.  Ve a la sección "Releases" (o haz clic aquí: [(link_a_tus_releases)](https://github.com/Emmanuel-Cano/StockTrackApp/releases/tag/Integradora)).
2.  Descarga el archivo .apk de la última versión.
3.  Instálalo en tu dispositivo Android (asegúrate de permitir la instalación de orígenes desconocidos).



## **Capturas**



<img width="812" height="1722" alt="image" src="https://github.com/user-attachments/assets/cbfd80ce-3fb8-4bc0-92ce-02c935c1cab7" />



Primero, la app le pedirá al usuario iniciar sesión o registrarte.


<img width="808" height="1738" alt="image" src="https://github.com/user-attachments/assets/21e94cd0-5037-4006-a128-2a61b597c321" />



Posterior, la app solicitara al usuario ingresar con las credenciales válidas




