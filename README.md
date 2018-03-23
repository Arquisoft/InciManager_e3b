
[![Build Status](https://travis-ci.org/Arquisoft/InciManager_e3b.svg?branch=master)](https://travis-ci.org/Arquisoft/InciManager_e3b)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6b9e962e78224811933f6fc1025a2b3f)](https://www.codacy.com/app/TonyMarin/InciManager_e3b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciManager_e3b&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/InciManager_e3b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciManager_e3b)
[![Gitter](https://badges.gitter.im/Arquisoft/InciManager_e3b.svg)](https://gitter.im/Arquisoft/InciManager_e3b?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

# InciManager_e3b 

Módulo de gestión y carga de incidencias (equipo de prácticas *Course1718_e3b2*).

Este proyecto ha sido desarrollado como práctica de la asignatura _[Arquitectura del Software](http://sies.uniovi.es/ofe-pod-jsf/ofertaFormativaServlet?asignatura=3011)_ perteneciente al tercer curso del _[Grado en Ingeniería Informática del Software](https://ingenieriainformatica.uniovi.es/web/ingenieriainformatica/infoacademica/grado)_ impartido en la _[Escuela de Ingenieria Informática (EII)](https://ingenieriainformatica.uniovi.es)_ de la _[Universidad de Oviedo](http://www.uniovi.es)_ durante el **curso 2017/2018**. La práctica consiste en la implementación de un sistema informático de análisis de incidencias ([Incidence System_e3b](https://github.com/Arquisoft/Inci_e3b)) cuya estructura ha sido dividida en los siguientes módulos:

- Módulo de carga de usuarios: [Loader_e3a](https://github.com/Arquisoft/Loader_e3a).
- Módulo de consulta y gestión de agentes: [Agents_e3b](https://github.com/Arquisoft/Agents_e3b).
- Módulo de gestión y carga de incidencias: [InciManager_e3b](https://github.com/Arquisoft/InciManager_e3b).
- Módulo de análisis y cuadro de mandos: [InciDashboard_e3a](https://github.com/Arquisoft/InciDashboard_e3a).

## Tabla de Contenidos

1. [Descripción del proyecto](#incimanager_e3b)
2. [Como instalar el proyecto](#como-instalar-el-proyecto)
3. [Como ejecutar el proyecto](#como-ejecutar-el-proyecto)
4. [Como contribuir al proyecto](#como-contribuir-al-proyecto)
5. [Creditos del proyecto](#creditos-del-proyecto)
    1. [Contribuidores](#contribuidores)
    2. [Reconocimientos](#reconocimientos)
6. [Licencia del proyecto](#licencia-del-proyecto)

## Como instalar el proyecto

## Como ejecutar el proyecto

### Para ejecutar
Lo primero que se necesita es descargar [Apache Kafka](https://kafka.apache.org/downloads). Ejecutar los siguientes comandos desde el menú raiz de la carpeta descomprimida de kafka. En caso de utilizar Linux, omitir la carpeta `windows` de la ruta del comando.

Primero -> Zookeeper:
	`bin\windows\zookeeper-server-start.bat config\zookeeper.properties`

Segundo -> Apache Kafka:
	`bin\windows\kafka-server-start.sh config\server.properties`

Una vez corriendo Zookeeper y Kafka, ya se puede ejecutar la aplicación, que se sirve en el puerto http://localhost:8091

## Como contribuir al proyecto

## Creditos del proyecto

### Contribuidores

- Equipo de prácticas **2017/2018 E3B2** [[@Arquisoft/course1718_e3b2](https://github.com/orgs/Arquisoft/teams/course1718_e3b2)]:
  - Miguel Martínez Serrano \<uo237030@uniovi.es\> [[@miguelms95](https://github.com/miguelms95)]
  - José Antonio Marín Álvarez \<uo212006@uniovi.es\> [[@TonyMarin](https://github.com/TonyMarin)]
  - Daniel Martínez Valerinao \<uo252438@uniovi.es\> [[@Gemeto](https://github.com/Gemeto)]
  - Andrés Ángel González Granda \<uo68216@uniovi.es\> [[@AndresAngelGG](https://github.com/AndresAngelGG)]
  - Kilian Pérez González \<uo21504@uniovi.es\> [[@kilianpg](https://github.com/kilianpg)]

### Reconocimientos

- Equipo docente de la asignatura **Arquitectura del Software**:
  - Jose Emilio Labra Gayo \<labra@uniovi.es\> [[@labra](https://github.com/labra)].
  - Aquilino Adolfo Juan Fuente \<aajuan@uniovi.es\>
  - Juan Luis Mateo Cerdan \<mateojuan@uniovi.es\>
  - Herminio García González [[@herminiogg](https://github.com/herminiogg)].

## Licencia del proyecto

El contenido completo de este proyecto esta licenciado bajo los terminos de la licencia: _[Unlicense](http://unlicense.org)_. Los detalles completos de la licencia pueden consultarse en el fichero: [LICENSE](LICENSE).
