# <p align="center">📊 Crypto-ETL ♻️</p>

CryptoETL es un proyecto ETL (Extraer, Transformar, Cargar) basado en Java que obtiene datos de criptomonedas de la API de CoinGecko 🦎, los procesa y almacena los resultados en un archivo CSV. Posteriormente, los datos se analizan utilizando Azure Databricks para obtener insights, y el proceso concluye utilizando la CLI de Databricks para descargar el archivo CSV localmente para su uso o reportes adicionales. Este proyecto demuestra habilidades de ingeniería de datos, uso del lenguaje Java, interacción con APIs, uso de almacenamiento en Azure, análisis de datos con Databricks (usando notebooks de Jupyter) y la CLI de Databricks para una gestión eficiente de datos y manejo de archivos.

![image](https://github.com/user-attachments/assets/1e58745a-d8c7-4051-84de-350559c42cad)

<p align="center">(Puedes hacer clic en la imagen para ampliarla)</p>

---

## <p align="center">📃 Tabla de Contenidos 📃</p>
- [Instalación](#instalación)
- [Configuración del Proyecto](#configuración-del-proyecto)
- [Interacción con la API](#interacción-con-la-api)
- [Configuración de Azure](#configuración-de-azure)
- [Configuración del Clúster de Databricks](#configuración-del-clúster-de-databricks)
- [Ejecución del Proceso ETL](#ejecución-del-proceso-etl)
- [Notebook de Jupyter](#notebook-de-jupyter)
- [Descarga del Archivo CSV](#descarga-del-archivo-csv)
- [Solución de Problemas](#solución-de-problemas)

---

## <p align="center">Instalación</p>

1. **Instalación de Java**
   
   _Asegúrate de tener Java instalado en tu máquina. Puedes descargarlo_ [aquí](https://www.oracle.com/java/technologies/javase-downloads.html). ⬅️

	 ![image](https://github.com/user-attachments/assets/390f9270-4eac-44b2-970e-d1e04faf2529)

2. **IntelliJ IDEA**
    
   _Descarga e instala IntelliJ IDEA (Edición Comunitaria) desde_ [este enlace](https://www.jetbrains.com/idea/). ⬅️

  ![image](https://github.com/user-attachments/assets/7071220b-9569-4ffc-877f-4b8a24f94797)
	![image](https://github.com/user-attachments/assets/a36732f7-232e-41b6-817e-aa8e3e98a99f)

3. **Instalación de Maven**
   
   _Descarga Maven desde esta_ [página](https://maven.apache.org/download.cgi) _y extráelo en una carpeta como_ `C:\Program Files\Apache\Maven\apache-maven-3.9.9`. _Configura las variables de entorno para asegurarte de que Maven esté en el PATH del sistema._

   ![image](https://github.com/user-attachments/assets/f90123ba-98d4-4e99-ab68-f23ee47a7a2b)

<p align="center">(Puedes hacer clic en la imagen para ampliarla)</p>

4. **Verificar la Instalación de Maven**
   
   _Abre una terminal (o símbolo del sistema) y ejecuta:_
   ```bash
   mvn -v
   ```
   <p ="center">Esto mostrará la versión de Maven y los detalles del entorno de Java.</p>
   
---

## <p align="center">Configuración del Proyecto</p>

1. **Crear un Proyecto Maven**
   
   _Abre IntelliJ IDEA y crea un nuevo proyecto Maven usando el arquetipo_ `maven-archetype-quickstart`. _Establece el_ `GroupId` _como_ `com.user.cryptoetl` ⚠️ _(cambia 'user' por tu nombre) y el_ `ArtifactId` _como_ `crypto-etl`.

   ![image](https://github.com/user-attachments/assets/ca2f0863-9f32-4ef0-b1da-2f6a0d9a3573)

2. **Dependencias**
   
   _Agrega las siguientes dependencias a tu_ `pom.xml`:

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

   - **Dependencia de registro de SLF4J**
   ```xml
   <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>2.0.0</version> <!-- Cambia la versión si es necesario -->
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
      <version>2.0.0</version> <!-- Cambia la versión si es necesario -->
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
   
   _Regístrate en_ [CoinGecko](https://www.coingecko.com/en/api) _para obtener tu clave API para datos de criptomonedas. Puedes acceder a tu panel de API_ [aquí](https://www.coingecko.com/en/developers/dashboard). ⬅️

   ![image](https://github.com/user-attachments/assets/04d41c20-516a-4f11-a2ef-57fc460dfedf)

2. **Endpoints**  
   _Usa el endpoint_ `/coins/markets` _para obtener datos de criptomonedas. Para la documentación, visita_ [Documentación de la API de CoinGecko](https://docs.coingecko.com/v3.0.1/reference/coins-markets).

   <p ="center">La documentación incluye una IA integrada que es sorprendentemente útil.</p>

---

## <p align="center">Configuración de Azure</p>

1. **Cuenta de Azure**
    
   _Regístrate en Azure en_ [azure.microsoft.com](https://azure.microsoft.com). ⬅️

2. **Configuración de Blob Storage**
   
   _Crea un contenedor de Blob Storage en Azure con configuraciones de seguridad mínimas y costo-eficientes. Modifica tu archivo_ `CryptoETL.java` _para incluir métodos que suban archivos a Azure Blob._

   ```java
   AZURE_CONNECTION_STRING=tu_cadena_de_conexión
   AZURE_CONTAINER_NAME=tu_nombre_de_contenedor
   ```

   ![image](https://github.com/user-attachments/assets/d0fb627f-c106-4dd7-b02b-ae1de573ea65)

---

## <p align="center">Configuración del Clúster de Databricks</p>

1. **Crear un Clúster en Databricks**
   
   _Crea un clúster con la siguiente configuración:_
   - Runtime: `15.4.x-scala2.12`
   - Nodo: `Standard_DC4as_v5`
   - Driver: `16 GB Memory, 4 Cores`

   ![image](https://github.com/user-attachments/assets/0637907a-dfb2-4aa4-8d76-4059f82b1b65)

2. **Verificar Disponibilidad de Nodos**
   
   _Usa la CLI de Azure para verificar los nodos disponibles en tu región:_
   ```bash
   az vm list-skus --location centralus --size Standard_D --output table
   ```
   _Si deseas consultar la documentación, haz clic_ [aquí](https://learn.microsoft.com/en-us/azure/azure-resource-manager/troubleshooting/error-sku-not-available?tabs=azure-cli#code-try-4) ⬅️

---

## <p align="center">Ejecución del Proceso ETL</p>

1. **Ejecutar CryptoETL.java** ☕
   
   _Ejecuta el archivo_ `CryptoETL.java`, _que realizará lo siguiente:_

   	- Extraer datos de la API de CoinGecko ↙️ (extraer)
    - Guardar los datos como un archivo CSV ♻️ (transformar)
    - Subir el CSV a Azure Blob Storage ☁️ (cargar)

2. **Notebook de Databricks** 📙
    
   _Abre el espacio de trabajo de Databricks, configura las credenciales de la cuenta, carga los archivos CSV y ejecuta el notebook para análisis adicionales. Más información en la sección siguiente_ ⬇️

   ![image](https://github.com/user-attachments/assets/5ff1a523-c929-44e5-9beb-16940c8f38c5)

---

## <p align="center">Notebook de Jupyter</p>

1. **Configuración del Entorno Spark**

   _Esto te permitirá configurar un entorno Spark que permitirá a Spark acceder al Blob Storage en Azure usando una clave de cuenta:_
   
   ```python
   spark.conf.set(
    "fs.azure.account.key.<nombre_de_la_cuenta_de_almacenamiento>.blob.core.windows.net",
    "<tu_clave_de_cuenta>"
	 )
   ```
   <p align="center">Para completarlo, necesitarás conocer el *Nombre del contenedor* y tu *Clave de cuenta*.</p>
	 
2. **Cargar Datos en Databricks**
   
   _Carga el archivo CSV en Databricks para su procesamiento:_
   
   ```python
   df = spark.read.csv("wasbs://<nombre_de_tu_contenedor>@<nombre_de_tu_cuenta_de_almacenamiento>.blob.core.windows.net/<ruta_de_tu_archivo_csv>", header=True, inferSchema=True)
   print(f"Número de filas en el DataFrame: {df.count()}")
   print(f"Número de particiones: {df.rdd.getNumPartitions()}")
   df.display()
   ```
   <p align="center">Para completarlo, necesitarás conocer el *Nombre del contenedor* y el *Nombre de la cuenta de almacenamiento*. Para la ruta del archivo, puedes usar *.csv y seleccioná todos los archivos en el almacenamiento.</p>

3. **Filtrar y Analizar Datos**
   
   Realiza varios filtrados y análisis en los datos de criptomonedas, como:

   - *Filtrar por Precio Actual mayor a 2000 USD.*
   - *Filtrar por Precio Actual menor a 50 USD.*
   - *Filtrar por Cambio positivo en 24h.*
   - *Filtrar por Cambio negativo en 24h.*
   - *Filtrar por Nombre que contenga "Bitcoin".*
   - *Filtrar por Nombre específicamente "Ethereum".*
   - *Filtrar por Nombre específicamente "Litecoin".*

   ```python
   # Filtro 1: Filtrar por precios mayores a 2000 USD
   filtered_df_1 = df.filter(df["`Current Price`"] > 2000)
   print("Filtrado por Precio Actual > 2000:")
   display(filtered_df_1)

   # Filtro 2: Filtrar por precios menores a 101 USD
   filtered_df_2 = df.filter(df["`Current Price`"] < 101)
   print("Filtrado por Precio Actual < 101:")
   display(filtered_df_2)

   # Filtro 3: Filtrar por Cambio positivo en 24h (si la columna existe)
   filtered_df_3 = df.filter(df["`24h Change`"] > 0)
   print("Filtrado por Cambio en 24h > 0:")
   display(filtered_df_3)

   # Filtro 4: Filtrar por Cambio negativo en 24h (si la columna existe)
   filtered_df_4 = df.filter(df["`24h Change`"] < 0)
   print("Filtrado por Cambio en 24h < 0:")
   display(filtered_df_4)

   # Filtro 5: Filtrar por monedas que contengan "Bitcoin" en el Nombre
   filtered_df_5 = df.filter(df["Name"].like("%Bitcoin%"))
   print("Filtrado por Nombre que contiene 'Bitcoin':")
   display(filtered_df_5)

   # Filtro 6: Filtrar por monedas específicamente llamadas 'Ethereum'
   filtered_df_6 = df.filter(df["Name"] == "Ethereum")
   print("Filtrado por Nombre 'Ethereum':")
   display(filtered_df_6)

   # Filtro 7: Filtrar por monedas específicamente llamadas 'Litecoin'
   filtered_df_7 = df.filter(df["Name"] == "Litecoin")
   print("Filtrado por Nombre 'Litecoin':")
   display(filtered_df_7)
   ```
   
4. **Guardar Archivo y Descargar**

_Procesa los datos de criptomonedas para calcular cambios de precio promedio y los precios más recientes, luego escribe los resultados en un archivo CSV:_

  ```python
  from pyspark.sql.functions import col, max, avg
from pyspark.sql.window import Window

# Obtener la 'Fecha de Creación' más reciente para cada 'Nombre'
latest_creation_date_df = df.withColumn("Creation Date", col("`Creation Date`").cast("timestamp"))
latest_creation_date_df = latest_creation_date_df.withColumn(
    "max_creation_date", max("Creation Date").over(Window.partitionBy("Name"))
)

# Filtrar filas donde 'Fecha de Creación' es la más reciente
filtered_df = latest_creation_date_df.filter(col("Creation Date") == col("max_creation_date")).drop("max_creation_date")

# Agrupar por 'Nombre' y calcular el promedio de 'Cambio en 24h', y mantener el 'Precio Actual' más reciente
agg_df = filtered_df.groupBy("Name").agg(
    avg("`24h Change`").alias("Cambio de Precio Promedio"),  # Alias temporal
    max("`Current Price`")  # Mantener el 'Precio Actual' más reciente
)

# Renombrar la columna 'Cambio de Precio Promedio' permanentemente
result_df = agg_df.withColumnRenamed("Cambio de Precio Promedio", "Cambio de Precio Promedio")

# Mostrar el DataFrame resultante
display(result_df)

# Escribir el resultado en un archivo CSV en /dbfs/tmp/result_df con punto y coma como delimitador
output_path = "/dbfs/tmp/result_df"
result_df.coalesce(1).write.mode("overwrite").option("header", "true").option("delimiter", ";").csv(output_path)

# Encontrar el archivo CSV generado en el directorio
csv_files = dbutils.fs.ls(output_path)
csv_file_path = ""
for file_info in csv_files:
    if file_info.name.endswith(".csv"):
        csv_file_path = file_info.path
        break

if csv_file_path:
    # Mover el archivo al directorio /FileStore
    dbutils.fs.mv(csv_file_path, "dbfs:/FileStore/result_df.csv")
    # Leer el contenido del archivo CSV y mostrarlo
    file_content = dbutils.fs.head("dbfs:/FileStore/result_df.csv")
    result_df.show()
    print("Contenido del archivo:\n")
    print(file_content)
    print(f"\nNúmero de filas en result_df: {result_df.count()}")
    print("Los precios están en USD.")
    print("El 'Cambio de Precio Promedio' es el resultado de promediar todos los cambios diarios de precio.")
else:
    print("No se generó ningún archivo CSV.")
  ```

5. **Verificar Cosas**

_Esta sección es muy útil y te servirá para eliminar el archivo 'result_df.csv' cada vez que necesites uno nuevo:_

```python
# Sección '/dbfs/':

#dbutils.fs.ls("dbfs/tmp/") # Verifica el directorio 'tmp' para ver si el directorio 'result_df' se creó correctamente

#dbutils.fs.ls("/dbfs/tmp/result_df/") # Verifica si los archivos en 'result_df' se crearon correctamente (El que necesitas es el 'part-00000-tid-<id>.csv')

#dbutils.fs.head("/dbfs/tmp/result_df/part-00000-tid-???.csv") # Verifica el contenido del archivo, necesitas completar el id

#dbutils.fs.rm("/dbfs/tmp/result_df/", recurse=True) # Elimina el directorio 'result_df' (no te preocupes, el código anterior lo crea nuevamente)


# Sección 'dbfs:/':   <-- Esta es la que necesitas usar

#dbutils.fs.rm("dbfs:/FileStore/result_df.csv", recurse=True) # Elimina el archivo 'result_df.csv'

#dbutils.fs.ls("dbfs:/FileStore/") # Verifica el directorio 'FileStore' para ver si el archivo 'result_df.csv' se creó correctamente
```

<p align="center">Solo descomenta lo que quieras usar</p>

---

## <p align="center">Descarga del Archivo CSV</p>

Para descargar el archivo CSV resultante de Databricks, sigue estos pasos usando la **CLI de Databricks**:

1. _Abre_ **IntelliJ** _en la carpeta raíz de tu proyecto y crea un nuevo entorno virtual Python ejecutando el siguiente comando:_
    ```bash
    python -m venv databricks-env
    ```

2. _Activa el entorno virtual:_
    ```bash
    databricks-env\Scripts\activate
    ```

3. _Instala el paquete de la CLI de Databricks usando `pip`:_
    ```bash
    pip install databricks-cli
    ```

4. _Configura la CLI estableciendo tu token de Databricks:_
    ```bash
    databricks configure --token
    ```
    - Ingresa tu dominio cuando se te solicite: `https://adb-3022457162513861.1.azuredatabricks.net/` (Ejemplo)
    - Ingresa el token generado desde tu cuenta de Databricks.
    
      * _Para generar un token, ve a tu perfil de Databricks:  
      Navega a **Configuración de Usuario > Desarrollador > Tokens de Acceso** y genera un nuevo token._

      * _Para obtener tu dominio, simplemente míralo en la URL actual de tu navegador web._

5. _Verifica si el archivo de salida se guardó en la carpeta temporal en DBFS:_
    ```bash
    databricks fs ls dbfs:/FileStore/
    ```

6. _Descarga el archivo a tu directorio local_ `data` _(donde se almacenan todos los archivos generados por_ `CryptoETL.java`_):_
    ```bash
    databricks fs cp dbfs:/FileStore/result_df.csv ./data/result_df.csv
    ```

7. _Para desactivar el entorno virtual, usa:_
    ```bash
    deactivate
    ```

---

## <p align="center">Solución de Problemas</p>

1. **Descargar el archivo CSV desde Databricks ⚠️**
    
   _He probado muchas soluciones para descargar directamente desde el notebook de Jupyter, pero al final ninguna funcionó. Tuve que buscar en el Foro de Databricks y encontré personas con los mismos problemas, quienes lo resolvieron usando la CLI de Databricks._

2. **Problemas de Disponibilidad de Nodos en el Clúster ⛔** 
    
   _Si encuentras problemas de disponibilidad de nodos al configurar tu clúster de Databricks, consulta la documentación de Azure y ejecuta los comandos necesarios para listar los nodos disponibles._

	 _Si deseas ver la documentación, consulta este_ [enlace](https://learn.microsoft.com/en-us/azure/azure-resource-manager/troubleshooting/error-sku-not-available?tabs=azure-cli#code-try-4) 

3. **Problemas con Separadores en Excel ❌**

   _Puede ocurrir que cuando descargues el archivo_ `result_df.csv` _venga con comas o puntos en lugares donde no aparecían durante la ejecución del archivo CSV. Esto sucede cuando la configuración de separadores en Excel es diferente a la configurada en el entorno del código._

   	_Para solucionarlo, sigue estas instrucciones: En Excel, por ejemplo, puedes ajustar los separadores decimales y de miles en:_
`Archivo > Opciones > Avanzado > Opciones de edición > Usar separadores del sistema` _(desmarca esta opción y establece el punto como separador decimal)._ 

---

## <p align="center">Conclusión</p>

_CryptoETL extrae, procesa y almacena exitosamente datos de criptomonedas en Azure Blob Storage y Databricks. Además, el proyecto utiliza la CLI de Databricks para descargar el archivo CSV procesado localmente para su análisis o reportes adicionales. Trabajos futuros pueden incluir la automatización de pipelines para el procesamiento y análisis continuo de datos._

---
