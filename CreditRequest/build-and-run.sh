#!/bin/bash

#docker compose down

# Paso 1: Limpiar y construir el proyecto db_Initializer
cd ../db_Initializer
if [ $? -ne 0 ]; then
    echo "Error al cambiar al directorio de la base de datos."
    exit 1
fi
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "Error al construir el proyecto de la base de datos."
    exit 1
fi

cd ../CreditRequest
if [ $? -ne 0 ]; then
    echo "Error al cambiar al directorio de CreditRequest."
    exit 1
fi

echo "Construyendo el proyecto con Maven..."
mvn clean package -DskipTests

# Verificar si la construcción fue exitosa
if [ $? -ne 0 ]; then
    echo "Error al construir el proyecto."
    exit 1
fi

# Paso 2-: Ejecutar el creador de base de datos
echo "Ejecutando db_Initializer..."
java -jar ../db_Initializer/target/db_Initializer-1.0-SNAPSHOT.jar &
PID_DATABASE=$!

# Paso 2: Ejecutar CreditRequest
echo "Ejecutando CreditRequest..."
java -jar target/CreditRequest-1.0-SNAPSHOT.jar &
PID_CREDIT_REQUEST=$!

# Paso 3: Esperar a que ambos servicios terminen
wait $PID_DATABASE
wait $PID_CREDIT_REQUEST

#docker compose build
#docker compose up -d
