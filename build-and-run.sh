#!/bin/bash

#docker compose down

# Paso 1: Limpiar y construir el proyecto
echo "Construyendo el proyecto con Maven..."
mvn clean package -DskipTests

# Verificar si la construcción fue exitosa
if [ $? -ne 0 ]; then
    echo "Error al construir el proyecto."
    exit 1
fi

# Paso 2: Ejecutar BPM-Engine
echo "Ejecutando BPM-Engine..."
java -jar BPM-Engine/target/BPM-Engine-1.0-SNAPSHOT.jar &
PID_BPM_ENGINE=$!

# Paso 3: Ejecutar CentralSys
echo "Ejecutando CentralSys..."
java -jar CentralSys/target/CentralSys-1.0-SNAPSHOT.jar &
PID_CENTRAL_SYS=$!

# Paso 4: Ejecutar CreditRequest
echo "Ejecutando CreditRequest..."
java -jar CreditRequest/target/CreditRequest-1.0-SNAPSHOT.jar &
PID_CREDIT_REQUEST=$!

# Paso 5: Ejecutar Treasury
echo "Ejecutando Treasury..."
java -jar Treasury/target/Treasury-1.0-SNAPSHOT.jar &
PID_TREASURY=$!

# Paso 6: Esperar a que ambos servicios terminen
wait $PID_BPM_ENGINE
wait $PID_CENTRAL_SYS
wait $PID_CREDIT_REQUEST
wait $PID_TREASURY

#docker compose build
#docker compose up -d
