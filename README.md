#  Spark Delta Lake

A Java-based data engineering project that integrates **Apache Spark 3.5** with **Delta Lake 3.2** for reliable, scalable, and ACID-compliant data processing on a Hadoop cluster.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [HDFS Setup (Docker)](#hdfs-setup-docker)
- [Build & Run](#build--run)
- [Features](#features)
- [License](#license)

---

## Overview

This project demonstrates how to build a **data lakehouse** architecture using Apache Spark and Delta Lake. It enables:

- Batch and streaming data ingestion into Delta tables
- ACID transactions on large datasets
- Time travel and versioning of data
- Scalable processing on a multi-node Hadoop cluster

---

## Tech Stack

| Technology        | Version  |
|-------------------|----------|
| Java              | 21       |
| Apache Spark      | 3.5.1    |
| Delta Lake        | 3.2.0    |
| Scala (binary)    | 2.12     |
| Jackson           | 2.15.2   |
| Log4j             | 2.20.0   |
| Maven             | 3.x      |

---

## Project Structure

```
spark-delta-lake/
├── cluster_hadoop_configuration/   # Hadoop & Spark cluster config files
├── src/
│   └── main/
│       └── java/                   # Java source code
├── .gitignore
└── pom.xml                         # Maven build file
```

---

## Prerequisites

Before running this project, make sure you have:

- **Java 21** installed
- **Apache Maven 3.x** installed
- A running **Hadoop cluster** (or local pseudo-distributed mode)
- **Apache Spark 3.5.1** available on your cluster or local machine

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/MohamedSAIFI0/spark-delta-lake.git
cd spark-delta-lake
```

### 2. Configure Hadoop

Copy or review the configuration files in `cluster_hadoop_configuration/` and place them in your Spark/Hadoop `conf/` directory:

```bash
cp cluster_hadoop_configuration/* $HADOOP_HOME/etc/hadoop/
```

### 3. Build the project

```bash
mvn clean package
```

---

## Configuration

Hadoop and Spark cluster configuration files are located in:

```
cluster_hadoop_configuration/
```

These include settings for HDFS, YARN, and Spark connectivity to the cluster.

---

## HDFS Setup (Docker)

### 1. Accéder au conteneur Hadoop

```bash
docker exec -it <container_name> bash
```

> Remplace `<container_name>` par le nom de ton conteneur, par exemple `namenode` ou `hadoop-master`.

### 2. Créer le répertoire Data Lakehouse sur HDFS

```bash
hdfs dfs -mkdir -p /datalakehouse
```

### 3. Donner les permissions nécessaires

```bash
hdfs dfs -chmod 777 /datalakehouse
```

### 4. Vérifier que le dossier est bien créé

```bash
hdfs dfs -ls /
```

### Arborescence HDFS recommandée

```
/datalakehouse/
├── raw/          # Données brutes ingérées
├── silver/       # Données nettoyées / transformées
└── gold/         # Données agrégées / prêtes à l'analyse
```

> 💡 **Astuce** : utilise `-mkdir -p` pour créer les sous-dossiers en une seule commande :
> ```bash
> hdfs dfs -mkdir -p /datalakehouse/raw /datalakehouse/silver /datalakehouse/gold
> ```

---

## Build & Run

### Build

```bash
mvn clean package -DskipTests
```

### Submit to Spark cluster

```bash
spark-submit \
  --class org.example.MainClass \
  --master yarn \
  --deploy-mode cluster \
  target/delta_lake-1.0-SNAPSHOT.jar
```

> Replace `org.example.MainClass` with your actual entry point class.

---

## Features

- ✅ **Delta Lake integration** — ACID transactions, schema enforcement
- ✅ **Apache Spark 3.5** — distributed data processing
- ✅ **Hadoop cluster support** — configuration ready for multi-node deployment
- ✅ **Java 21** — modern Java with latest performance improvements
- ✅ **Unified Jackson** — version-managed to avoid dependency conflicts

---

## Author

**Mohamed SAIFI** — [@MohamedSAIFI0](https://github.com/MohamedSAIFI0)

---

## License

This project is open source. Feel free to use, modify, and distribute.
