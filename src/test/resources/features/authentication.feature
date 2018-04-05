# language: es
Característica: Autenticación de agentes
  
  Como agente dado de alta en el sistema, quiero poder acceder al sistema y 
  procesar incidencias

  Esquema del escenario: Autenticación interactiva de agentes dados de alta previamente
    Dado un agente registrado en el sistema con el nombre de usuario "<username>" y la contraseña "<password>"
    Y una vez situado en la página de inicio de sesión "/login"
    Cuando introduzco "<username>" y "<password>" en los campos "username" y "password"
    Y presiono el botón de confirmación "loginButton"
    Entonces puedo procesar incidencias desde la página principal "/home"

    Ejemplos: 
      | username                | password | kind   |
      | paco@gmail.com          |   123456 | Person |
      | pepe@gmail.com          |   123456 | Person |
      | admin@sensores.com      |   123456 | Sensor |
      | ambiente@ministerio.com |   123456 | Entity |
      | musk@spacex.com         |   123456 | Sensor |

  Esquema del escenario: Autenticación interactiva de agentes no dados de alta previamente
    Dado un agente no registrado en el sistema con el nombre de usuario "<username>" y la contraseña "<password>"
    Y una vez situado en la página de inicio de sesión "/login"
    Cuando introduzco "<username>" y "<password>" en los campos "username" y "password"
    Y presiono el botón de confirmación "loginButton"
    Entonces recibo una notificación de error de acceso "/login?error"
    Y no puedo procesar incidencias desde la página principal "/home"

    Ejemplos: 
      | username           | password         | kind   |
      | paco@gmail.com     | INVALID_PASSWORD | Person |
      | unregistered@agent | ANY_PASSWORD     | Person |
