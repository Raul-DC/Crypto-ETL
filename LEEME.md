---

# <p align="center">📊 Crypto-ETL ♻️</p>

CryptoETL es un proyecto ETL (Extract, Transform, Load) basado en Java que obtiene datos de criptomonedas de la API de CoinGecko 🦎, los procesa y luego almacena los resultados en un archivo CSV. Posteriormente, los datos se analizan en Azure Databricks para obtener información, y el proceso concluye utilizando la CLI de Databricks para descargar el archivo CSV localmente para su uso posterior o generación de informes. Este proyecto demuestra habilidades en ingeniería de datos, uso del lenguaje Java, interacción con APIs, almacenamiento en Azure y análisis de datos con Databricks (utilizando Jupyter Notebooks) y la CLI de Databricks para una gestión eficiente de archivos.

![image](https://github.com/user-attachments/assets/1e58745a-d8c7-4051-84de-350559c42cad)

<p align="center">(Puedes hacer clic en la imagen para ampliarla)</p>

---

## <p align="center">📃 Tabla de Contenidos 📃</p>
- [Instalación](#instalacion)
- [Configuración del Proyecto](#configuracion-del-proyecto)
- [Interacción con la API](#interaccion-con-la-api)
- [Configuración de Azure](#configuracion-de-azure)
- [Configuración del Cluster de Databricks](#configuracion-del-cluster-de-databricks)
- [Ejecución del Proceso ETL](#ejecucion-del-proceso-etl)
- [Uso de Jupyter Notebook](#uso-de-jupyter-notebook)
- [Descarga del Archivo CSV](#descarga-del-archivo-csv)
- [Solución de Problemas](#solucion-de-problemas)

---

## <p align="center">Instalación</p>

1. **Instalación de Java**
   
   _Asegúrate de que Java esté instalado en tu máquina. Puedes descargarlo_ [aquí](https://www.oracle.com/java/technologies/javase-downloads.html). ⬅️

2. **IntelliJ IDEA**
    
   _Descarga e instala IntelliJ IDEA (Community Edition) desde_ [este enlace](https://www.jetbrains.com/idea/). ⬅️

3. **Instalación de Maven**
   
   _Descarga Maven desde esta_ [página](https://maven.apache.org/download.cgi) _y extrae el contenido en una carpeta como_ `C:\Program Files\Apache\Maven\apache-maven-3.9.9`. _Configura las variables de entorno para asegurarte de que Maven esté agregado al PATH del sistema._

4. **Verificar la instalación de Maven**
   
   _Abre una terminal o símbolo del sistema y ejecuta:_
   ```bash
   mvn -v
   ```
   <p align="center">Esto debería mostrar la versión de Maven y los detalles del entorno de Java.</p>

---

## <p align="center">Configuración del Proyecto</p>

1. **Crear un Proyecto Maven**
   
   _Abre IntelliJ IDEA y crea un nuevo proyecto Maven utilizando_ `maven-archetype-quickstart`. _Establece el_ `GroupId` _como_ `com.user.cryptoetl` ⚠️ _(sustituye 'user' por tu nombre) y el_ `ArtifactId` _como_ `crypto-etl`.

2. **Añadir Dependencias**
   
   _Agrega las siguientes dependencias en tu archivo_ `pom.xml`:

   - **Dependencia para pruebas**
   ```xml
   <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
   </dependency>
   ```
   
   - **Dependencia para manejar solicitudes HTTP**
   ```xml
   <dependency>
      <groupId>com.konghq</groupId>
      <artifactId>unirest-java</artifactId>
      <version>3.14.5</version>
   </dependency>
   ```
   
   - **Dependencia para manejar JSON**
   ```xml
   <dependency>
      <groupId>com.googlecode.json-simple</groupId>
      <artifactId>json-simple</artifactId>
      <version>1.1.1</version>
   </dependency>
   ```

   - **Dependencia para el registro con SLF4J**
   ```xml
   <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>2.0.0</version>
   </dependency>
   <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
      <version>2.0.0</version>
   </dependency>
   ```
   
   - **SDK de Azure para Blob Storage**:
   ```xml
   <dependency>
     <groupId>com.azure</groupId>
     <artifactId>azure-storage-blob</artifactId>
     <version>12.14.0</version>
   </dependency>
   ```

   - **dotenv para variables de entorno**:
   ```xml
   <dependency>
     <groupId>io.github.cdimascio</groupId>
     <artifactId>dotenv-java</artifactId>
     <version>5.2.2</version>
   </dependency>
   ```

---

## <p align="center">Interacción con la API</p>

1. **API de CoinGecko**
   
   _Regístrate en_ [CoinGecko](https://www.coingecko.com/en/api) _para obtener tu clave de API. Puedes acceder a tu panel de API_ [aquí](https://www.coingecko.com/en/developers/dashboard). ⬅️

2. **Endpoints**  
   _Usa el endpoint_ `/coins/markets` _para obtener datos de criptomonedas. Para documentación, visita_ [CoinGecko API Docs](https://docs.coingecko.com/v3.0.1/reference/coins-markets).

---

## <p align="center">Configuración de Azure</p>

1. **Cuenta de Azure**
    
   _Regístrate en Azure en_ [azure.microsoft.com](https://azure.microsoft.com). ⬅️

2. **Configurar Blob Storage**
   
   _Crea un contenedor de almacenamiento en Azure con configuraciones de seguridad mínimas y costos eficientes._
   
   ```java
   AZURE_CONNECTION_STRING=tu_cadena_de_conexion
   AZURE_CONTAINER_NAME=tu_nombre_de_contenedor
   ```

---

## <p align="center">Configuración del Cluster de Databricks</p>

1. **Crear un Cluster en Databricks**
   
   _Configura el cluster con:_
   - Runtime: `15.4.x-scala2.12`
   - Nodo: `Standard_DC4as_v5`
   - Driver: `16 GB de Memoria, 4 Núcleos`

---

## <p align="center">Ejecución del Proceso ETL</p>

1. **Ejecutar CryptoETL.java** ☕
   
   _El código extraerá datos de la API de CoinGecko, los transformará en un archivo CSV y lo cargará en Azure Blob Storage._

---

## <p align="center">Descarga del Archivo CSV</p>

1. **Instalar la CLI de Databricks**
   ```bash
   pip install databricks-cli
   ```

2. **Descargar el archivo CSV localmente**
   ```bash
   databricks fs cp dbfs:/FileStore/result_df.csv ./result_df.csv
   ```

---
