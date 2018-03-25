
# InciManager_e3b 

[![Build Status](https://travis-ci.org/Arquisoft/InciManager_e3b.svg?branch=master)](https://travis-ci.org/Arquisoft/InciManager_e3b)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6b9e962e78224811933f6fc1025a2b3f)](https://www.codacy.com/app/TonyMarin/InciManager_e3b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciManager_e3b&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/InciManager_e3b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciManager_e3b)
[![Gitter](https://badges.gitter.im/Arquisoft/InciManager_e3b.svg)](https://gitter.im/Arquisoft/InciManager_e3b?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Módulo de gestión y carga de incidencias (equipo de prácticas *Course1718_e3b2*).

Este proyecto ha sido desarrollado como práctica de la asignatura *[Arquitectura del Software](http://sies.uniovi.es/ofe-pod-jsf/ofertaFormativaServlet?asignatura=3011)* perteneciente al tercer curso del *[Grado en Ingeniería Informática del Software](https://ingenieriainformatica.uniovi.es/web/ingenieriainformatica/infoacademica/grado)* impartido en la *[Escuela de Ingenieria Informática (EII)](https://ingenieriainformatica.uniovi.es)* de la *[Universidad de Oviedo](http://www.uniovi.es)* durante el **curso 2017/2018**. La práctica consiste en la implementación de un sistema informático de análisis de incidencias ([Incidence System_e3b](https://github.com/Arquisoft/Inci_e3b)) cuya estructura ha sido dividida en los siguientes módulos:

- Módulo de carga de usuarios: [Loader_e3a](https://github.com/Arquisoft/Loader_e3a).
- Módulo de consulta y gestión de agentes: [Agents_e3b](https://github.com/Arquisoft/Agents_e3b).
- Módulo de gestión y carga de incidencias: [InciManager_e3b](https://github.com/Arquisoft/InciManager_e3b).
- Módulo de análisis y cuadro de mandos: [InciDashboard_e3a](https://github.com/Arquisoft/InciDashboard_e3a).

## Tabla de Contenidos

- [Descripción del proyecto](#incimanager_e3b)
- [Como instalar el proyecto](#como-instalar-el-proyecto)
    - [Requisitos de instalación](#requisitos-de-instalaci-n)	     
    - [Obtención del código fuente](#obtenci-n-del-c-digo-fuente)	  
    - [Instalación de las dependencias](#instalaci-n-de-las-dependencias)	  
    - [Reinstalación de las dependencias](#reinstalaci-n-de-las-dependencias)	
- [Como ejecutar el proyecto](#como-ejecutar-el-proyecto)
    - [Requisitos para ejecutar el proyecto](#requisitos-para-ejecutar-el-proyecto)
    - [Configuración del servicio InciManager](#configuraci-n-del-servicio-incimanager)
    - [Inicio del servicio Apache Kafka](#inicio-del-servicio-apache-kafka)
         - [Inicio de Apache Kafka en MS-Windows](#inicio-de-apache-kafka-en-ms-windows)
         - [Inicio de Apache Kafka en GNU/LiNUX](#inicio-de-apache-kafka-en-gnu-linux)
    - [Inicio del servicio InciManager](#inicio-del-servicio-incimanager)
- [Como contribuir al proyecto](#como-contribuir-al-proyecto)
- [Creditos del proyecto](#creditos-del-proyecto)
     - [Contribuidores](#contribuidores)
     - [Reconocimientos](#reconocimientos)
- [Licencia del proyecto](#licencia-del-proyecto)

## Como instalar el proyecto

### Requisitos de instalación

- Máquina virtual de Java: [OpenJDK](http://openjdk.java.net) (versión: >= 1.6).
- Sistema de control de versiones: [GIT](https://git-scm.com) (versión: >= 2.16).
- Herramienta de construcción de proyectos: [Apache Maven](https://maven.apache.org) (versión: >= 3.5).

### Obtención del código fuente

Descarga la última versión del codigo fuente desde el repositorio oficial del proyecto:

~~~
git clone https://github.com/Arquisoft/InciManager_e3b.git
~~~

### Instalación de las dependencias

Situarse en el directorio de descarga del código fuente y ejecutar:

~~~
mvn clean install
~~~

### Reinstalación de las dependencias

En caso de ser necesario es posible purgar el repositorio local con las dependencias del proyecto ejecutando:

~~~
mvn dependency:purge-local-repository clean install -U
~~~

## Como ejecutar el proyecto

### Requisitos para ejecutar el proyecto

- [Apache Maven](https://maven.apache.org) (versión: >= 3.5).
- [Apache Kafka](https://kafka.apache.org) (versión: >= 1.0).
- [Módulo Agents](https://github.com/Arquisoft/Agents_e3b) (versión: = e3b). 

### Configuración del servicio InciManager

Configurar el puerto del servicio en el fichero `[resources/application.properties](src/main/resources/application.properties)`

~~~properties
server.port = 8091
~~~

### Inicio del servicio Apache Kafka

Si no se dispone de una instancia de Apache Kafka en ejecución, es posible descargar una versión ya compilada para Java desde su [página oficial](https://kafka.apache.org/quickstart) y lanzar el servicio manualmente (la distribución binaria de Apache Kafka requiere a su vez iniciar su propia instancia de Apache Zookeeper).

#### Inicio de Apache Kafka en MS-Windows

~~~batchfile
REM Start Apache Zookeeper server:
start "ZooKeeper" /D ".\bin\windows\" "zookeeper-server-start.bat" "..\..\config\zookeeper.properties"
REM Wait 10 seconds:
timeout 10
REM Start Apache Kafka server:
start "Kafka" /D ".\bin\windows\" "kafka-server-start.bat" "..\..\config\server.properties"
~~~

#### Inicio de Apache Kafka en GNU/LiNUX

~~~bash
# Start Apache Zookeeper server:
nohup bash -c "bin/zookeeper-server-start.sh config/zookeeper.properties &"
# Wait 10 seconds:
sleep 10
# Start Apache Kafka server:
nohup bash -c "bin/kafka-server-start.sh config/server.properties &"
~~~

### Inicio del servicio InciManager

Situarse en el directorio de instalación y ejecutar:

~~~
mvn spring-boot:run
~~~

Una vez iniciado el servicio ya es posible acceder a través del navegador en la dirección: `http://localhost:8091`

## Como contribuir al proyecto

La información completa sobre como contribuir al proyecto: código de conducta, flujo de trabajo, etc, puede consultarse en el fichero [CONTRIBUTING.md](CONTRIBUTING.md). 

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
