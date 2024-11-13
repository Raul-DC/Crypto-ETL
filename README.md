---

# <p align="center">📊 Crypto-ETL ♻️</p>

. CryptoETL is a Java-based ETL (Extract, Transform, Load) project that pulls cryptocurrency data from the CoinGecko API 🦎 and processes it then it stores the results in a CSV file. After that the data is further analyzed using Azure Databricks for insights and the process concludes by using the Databricks CLI to download the CSV file locally for further use or reporting. This project showcases data engineering skills, Java language usage, API interaction, Azure storage usage, and data analysis with Databricks (using Jupyter notebooks) and the Databricks CLI for efficient data management and file handling.

![image](https://github.com/user-attachments/assets/1e58745a-d8c7-4051-84de-350559c42cad)

---

## <p align="center">📃 Table of Contents 📃</p>
- [Installation](#installation)
- [Project Setup](#project-setup)
- [API Interaction](#api-interaction)
- [Azure Setup](#azure-setup)
- [Databricks Cluster Setup](#databricks-cluster-setup)
- [Running the ETL Process](#running-the-etl-process)
- [Jupyter Notebook](#jupyter-notebook)
- [Downloading CSV File](#downloading-csv-file)
- [Troubleshooting](#troubleshooting)

---

## <p align="center">Installation</p>

1. **Java Installation**
   
   _Ensure that Java is installed on your machine. You can download it_ [here](https://www.oracle.com/java/technologies/javase-downloads.html). ⬅️

	 ![image](https://github.com/user-attachments/assets/390f9270-4eac-44b2-970e-d1e04faf2529)


2. **IntelliJ IDEA**
    
   _Download and install IntelliJ IDEA (Community Edition) from_ [this link](https://www.jetbrains.com/idea/). ⬅️

  ![image](https://github.com/user-attachments/assets/7071220b-9569-4ffc-877f-4b8a24f94797)
	![image](https://github.com/user-attachments/assets/a36732f7-232e-41b6-817e-aa8e3e98a99f)

3. **Maven Installation**
   
   _Download Maven from this_ [page](https://maven.apache.org/download.cgi) _and extract it to a folder such as_ `C:\Program Files\Apache\Maven\apache-maven-3.9.9`. _Configure your environment variables accordingly to ensure Maven is added to your system's PATH._

   ![image](https://github.com/user-attachments/assets/f90123ba-98d4-4e99-ab68-f23ee47a7a2b)

<p align="center">(You can click on the image to enlarge it)</p>

4. **Verify Maven Installation**
   
   _Open a terminal (or command prompt) and run:_
   ```bash
   mvn -v
   ```
   <p align="center">This should display the Maven version and Java environment details.</p>
   
---

## <p align="center">Project Setup</p>

1. **Create Maven Project**
   
   _Open IntelliJ IDEA and create a new Maven project using the_ `maven-archetype-quickstart`. _Set the_ `GroupId` _to_ `com.user.cryptoetl` ⚠️ _(change 'user' for your name) and_ `ArtifactId` _to_ `crypto-etl`.

   ![image](https://github.com/user-attachments/assets/ca2f0863-9f32-4ef0-b1da-2f6a0d9a3573)


2. **Dependencies**
   
   _Add the following dependencies to your_ `pom.xml`:

   - **Testing dependency**
   ```xml
   <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
   ```
   
   - **Dependency for handling HTTP requests**
   ```xml
   <dependency>
      <groupId>com.konghq</groupId>
      <artifactId>unirest-java</artifactId>
      <version>3.14.5</version>
    </dependency>
   ```

   - **Dependency for handling JSON**
   ```xml
   <dependency>
      <groupId>com.googlecode.json-simple</groupId>
      <artifactId>json-simple</artifactId>
      <version>1.1.1</version>
    </dependency>
   ```

   - **SLF4J's logging dependency**
   ```xml
   <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>2.0.0</version> <!-- Change the version if necessary -->
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
      <version>2.0.0</version> <!-- Change the version if necessary -->
    </dependency>
   ```
   
   - **Azure's SDK for Blob Storage**:
   ```xml
   <dependency>
     <groupId>com.azure</groupId>
     <artifactId>azure-storage-blob</artifactId>
     <version>12.14.0</version>
   </dependency>
   ```

   - **dotenv for environment variables**:
   ```xml
   <dependency>
     <groupId>io.github.cdimascio</groupId>
     <artifactId>dotenv-java</artifactId>
     <version>5.2.2</version>
   </dependency>
   ```

---

## <p align="center">API Interaction</p>

1. **CoinGecko API**
   
   _Register on_ [CoinGecko](https://www.coingecko.com/en/api) _to get your API key for cryptocurrency data. You can access your API dashboard_ [here](https://www.coingecko.com/en/developers/dashboard). ⬅️

   ![image](https://github.com/user-attachments/assets/04d41c20-516a-4f11-a2ef-57fc460dfedf)


2. **Endpoints**  
   _Use the_ `/coins/markets` _endpoint to pull cryptocurrency data. For documentation, visit_ [CoinGecko API Docs](https://docs.coingecko.com/v3.0.1/reference/coins-markets).

   <p align="center">The documentation has an integrated AI that is surprisingly helpful.</p>

---

## <p align="center">Azure Setup</p>

1. **Azure Account**
    
   _Sign up for an Azure account at_ [azure.microsoft.com](https://azure.microsoft.com). ⬅️

2. **Blob Storage Setup**
   
   _Create a Blob Storage container on Azure with minimal security and cost-efficient settings. Modify your_ `CryptoETL.java` _file to include methods for uploading files to Azure Blob._

   ```java
   AZURE_CONNECTION_STRING=your_connection_string
   AZURE_CONTAINER_NAME=your_container_name
   ```

   ![image](https://github.com/user-attachments/assets/d0fb627f-c106-4dd7-b02b-ae1de573ea65)


---

## <p align="center">Databricks Cluster Setup</p>

1. **Create Databricks Cluster**
   
   _Create a cluster with the following configuration:_
   - Runtime: `15.4.x-scala2.12`
   - Node: `Standard_DC4as_v5`
   - Driver: `16 GB Memory, 4 Cores`

   ![image](https://github.com/user-attachments/assets/0637907a-dfb2-4aa4-8d76-4059f82b1b65)

2. **Check Node Availability**
   
   _Use the Azure CLI to check available nodes in your region:_
   ```bash
   az vm list-skus --location centralus --size Standard_D --output table
   ```
   _If you want to check the documentation click_ [here](https://learn.microsoft.com/en-us/azure/azure-resource-manager/troubleshooting/error-sku-not-available?tabs=azure-cli#code-try-4) ⬅️

---

## <p align="center">Running the ETL Process</p>

1. **Run CryptoETL.java** ☕
   
   _Run the_ `CryptoETL.java` _file, which will:_

   	- Pull data from the CoinGecko API ↙️ (extract)
    - Save the data as a CSV file ♻️ (transform)
    - Upload the CSV to Azure Blob Storage ☁️ (load)

2. **Databricks Notebook** 📙
    
   _Open the Databricks workspace, setup the account credentials, load the csv files and run the notebook for further analysis. More info in the section below_ ⬇️

   ![image](https://github.com/user-attachments/assets/5ff1a523-c929-44e5-9beb-16940c8f38c5)

---

## <p align="center">Jupyter Notebook</p>

1. **Spark Enviroment Settings**

   _This will let you set a Spark enviroment which will let Spark access the Blob Storage on Azure using an account key:_
   
   ```python
   spark.conf.set(
    "fs.azure.account.key.<storage_account_name>.blob.core.windows.net",
    "<your_account_key>"
	 )
   ```
   <p align="center">To fill it you will need to know your *Container's name* and your *Account Key*.</p>
	 
2. **Load Data into Databricks**
   
   _Load the CSV file into Databricks for further processing:_
   
   ```python
   df = spark.read.csv("wasbs://<your_container_name>@<your_storage_account_name>.blob.core.windows.net/<your_csv_file_path>", header=True, inferSchema=True)
   print(f"Number of rows in the DataFrame: {df.count()}")
   print(f"Number of partitions: {df.rdd.getNumPartitions()}")
   df.display()
   ```
   <p align="center">To fill it you will need to know your *Container's name* and the *Storage Account's name* and for the file path you could use *.csv and it will select all files in the storage.</p>

3. **Filter and Analyze Data**
   
   Perform various filtering and analysis on the cryptocurrency data, such as:
   
   - *Filter by Current Price greater than 2000 USD.*
   - *Filter by Current Price less than 50 USD.*
   - *Filter by positive 24h Change.*
   - *Filter by negative 24h Change.*
   - *Filter by Name containing "Bitcoin".*
   - *Filter by Name specifically "Ethereum".*
   - *Filter by Name specifically "Litecoin".*

   ```python
   # Filter 1: Filter by prices greater than 2000 USD
   filtered_df_1 = df.filter(df["`Current Price`"] > 2000)
   print("Filtered by Current Price > 2000:")
   display(filtered_df_1)

   # Filter 2: Filter by prices less than 101 USD
   filtered_df_2 = df.filter(df["`Current Price`"] < 101)
   print("Filtered by Current Price < 101:")
   display(filtered_df_2)

   # Filter 3: Filter by positive 24h Change (if the column exists)
   filtered_df_3 = df.filter(df["`24h Change`"] > 0)
   print("Filtered by 24h Change > 0:")
   display(filtered_df_3)

   # Filter 4: Filter by negative 24h Change (if the column exists)
   filtered_df_4 = df.filter(df["`24h Change`"] < 0)
   print("Filtered by 24h Change < 0:")
   display(filtered_df_4)

   # Filter 5: Filter by coins containing "Bitcoin" in the Name
   filtered_df_5 = df.filter(df["Name"].like("%Bitcoin%"))
   print("Filtered by Name containing 'Bitcoin':")
   display(filtered_df_5)

   # Filter 6: Filter by coins specifically named 'Ethereum'
   filtered_df_6 = df.filter(df["Name"] == "Ethereum")
   print("Filtered by Name 'Ethereum':")
   display(filtered_df_6)

   # Filter 7: Filter by coins specifically named 'Litecoin'
   filtered_df_7 = df.filter(df["Name"] == "Litecoin")
   print("Filtered by Name 'Litecoin':")
   display(filtered_df_7)
   ```
   
4. **Save File and Download**

_Processes cryptocurrency data to compute average price changes and the most recent prices, then writes the results to a CSV file:_

  ```python
  from pyspark.sql.functions import col, max, avg
from pyspark.sql.window import Window

# Get the most recent 'Creation Date' for each 'Name'
latest_creation_date_df = df.withColumn("Creation Date", col("`Creation Date`").cast("timestamp"))
latest_creation_date_df = latest_creation_date_df.withColumn(
    "max_creation_date", max("Creation Date").over(Window.partitionBy("Name"))
)

# Filter rows where 'Creation Date' is the most recent
filtered_df = latest_creation_date_df.filter(col("Creation Date") == col("max_creation_date")).drop("max_creation_date")

# Group by 'Name' and calculate the average of '24h Change', and keep the most recent 'Current Price'
agg_df = filtered_df.groupBy("Name").agg(
    avg("`24h Change`").alias("AVG price change"),  # Temporary alias
    max("`Current Price`")  # Keep the most recent 'Current Price'
)

# Rename the 'AVG price change' column permanently
result_df = agg_df.withColumnRenamed("AVG price change", "Average Price Change")

# Show the resulting DataFrame
display(result_df)

# Write the result to a CSV file in /dbfs/tmp/result_df with semicolon as the delimiter
output_path = "/dbfs/tmp/result_df"
result_df.coalesce(1).write.mode("overwrite").option("header", "true").option("delimiter", ";").csv(output_path)

# Find the generated CSV file in the directory
csv_files = dbutils.fs.ls(output_path)
csv_file_path = ""
for file_info in csv_files:
    if file_info.name.endswith(".csv"):
        csv_file_path = file_info.path
        break

if csv_file_path:
    # Move the file to the /FileStore directory
    dbutils.fs.mv(csv_file_path, "dbfs:/FileStore/result_df.csv")
    # Read the contents of the CSV file and print it
    file_content = dbutils.fs.head("dbfs:/FileStore/result_df.csv")
    result_df.show()
    print("File content:\n")
    print(file_content)
    print(f"\nNumber of rows in result_df: {result_df.count()}")
    print("The prices are in USD.")
    print("The 'Average Price Change' is the result of averaging all the daily price changes.")
else:
    print("No CSV file was generated.")
  ```

5. **Check Things**

_This one is really helpful and will serve you to delete the 'result_df.csv' file each time you need a new one:_

```python
# '/dbfs/' section:

#dbutils.fs.ls("dbfs/tmp/") # Checks the 'tmp' directory to see if the 'result_df' directory was successfully created

#dbutils.fs.ls("/dbfs/tmp/result_df/") # Checks if the files on 'result_df' were successfully created (The one you want is the 'part-00000-tid-<id>.csv')

#dbutils.fs.head("/dbfs/tmp/result_df/part-00000-tid-???.csv") # Check the content of the file, you need to fill the id

#dbutils.fs.rm("/dbfs/tmp/result_df/", recurse=True) # Deletes the 'result_df' directory (don't worry because the code above creates it again)


# 'dbfs:/' section:   <-- This is the one you need to use

#dbutils.fs.rm("dbfs:/FileStore/result_df.csv", recurse=True) # Deletes the 'result_df.csv' file

#dbutils.fs.ls("dbfs:/FileStore/") # Checks the 'FileStore' directory to see if the 'result_df.csv' file was successfully created
```

<p align="center">Just uncomment what you want to use</p>

---

## <p align="center">Downloading CSV File</p>

To download the resulting CSV file from Databricks, follow these steps for using the **Databricks CLI**:

1. _Open_ **IntelliJ** _in the root folder of your project and create a new Python virtual environment by running the following command:_
    ```bash
    python -m venv databricks-env
    ```

2. _Activate the virtual environment:_
    ```bash
    databricks-env\Scripts\activate
    ```

3. _Install the Databricks CLI package using `pip`:_
    ```bash
    pip install databricks-cli
    ```

4. _Configure the CLI by setting your Databricks token:_
    ```bash
    databricks configure --token
    ```
    - Enter your domain when prompted: `https://adb-3022457162513861.1.azuredatabricks.net/` (Example)
    - Enter the token generated from your Databricks account.
    
      * _To generate a token, go to your Databricks profile:  
      Navigate to **User Settings > Developer > Access Tokens** and generate a new token._

      * _To get your domain simply see it on your web browser's current URL_

5. _Check if the output file was saved in the temporary folder on DBFS:_
    ```bash
    databricks fs ls dbfs:/FileStore/
    ```

6. _Download the file to your local_ `data` _directory (where all the files generated by_ `CryptoETL.java` _are stored):_
    ```bash
    databricks fs cp dbfs:/FileStore/result_df.csv ./data/result_df.csv
    ```

7. _To deactivate the virtual environment, use:_
    ```bash
    deactivate
    ```

---

## <p align="center">Troubleshooting</p>

1. **Downloading the CSV file from Databricks ⚠️**
    
   _I've tried many solutions to download directly from the jupyter notebook but at the end not even one worked. I've had to search on the Databricks Forum and found people with the same issues and solved using Databricks CLI._


2. **Cluster Node Availability Issues ⛔** 
    
   _If you encounter node availability issues when setting up your Databricks cluster, refer to the Azure documentation and run the necessary commands to list available nodes._

	 _If you want to see the documentation, see this_ [link](https://learn.microsoft.com/en-us/azure/azure-resource-manager/troubleshooting/error-sku-not-available?tabs=azure-cli#code-try-4) 

3. **Issues with Excel Separators ❌**

   _It can happen that when you download the_ `result_df.csv file` _it comes with commas or periods in places where they didn't appear on the execution of the CSV file, that happens when the separators setting on Excel is different than the one that was configured on the code enviroment_

   	_To fix it you can follow this instructions: In Excel, for example, you can adjust the decimal and thousands separators in:_
`File > Options > Advanced > Editing options > Use system separators` _(uncheck this and set the period as the decimal separator)._ 

---

## <p align="center">Conclusion</p>

_CryptoETL successfully extracts cryptocurrency data, processes it, and stores it using Azure Blob and Databricks. Future work may involve setting up automated pipelines for continuous data processing and analysis._

---
