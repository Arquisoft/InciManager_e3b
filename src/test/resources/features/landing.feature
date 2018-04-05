# language: es
Característica: Acceso página de bienvenida
Como usuario (usuario no validado)
Quiero poder acceder a la página de bienvenida del servicio

Escenario: usuario hace una llamada GET a /
Cuando el usuario invoca /
Entonces el usuario recibe el código de estado 200
Y el usuario recibe una página con la cadena "InciManager"
