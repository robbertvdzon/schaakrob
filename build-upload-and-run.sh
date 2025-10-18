#!/bin/bash

# Configuratie
PI_USER="pi"
PI_HOST="192.168.178.88"
PI_TARGET_DIR="/home/pi/git/schaakrob/schaakrob-server/target"
LOCAL_JAR="schaakrob-server/target/schaakrob-server-1.0-shaded.jar"
REMOTE_PROJECT_DIR="/home/pi/git/schaakrob"

# 1. Build de jar zonder tests
echo "🔨 Bouwen van de jar..."
./mvnw package -DskipTests=true || { echo "Build failed"; exit 1; }

# 2. Stop 'ui' processen op de Pi
echo "🛑 Stoppen van 'ui' processen op de Pi..."
ssh ${PI_USER}@${PI_HOST} "pkill -f ui || true"

# 3. Stop 'java' processen op de Pi
echo "🛑 Stoppen van 'java' processen op de Pi..."
ssh ${PI_USER}@${PI_HOST} "pkill -f java || true"

# 4. Kopieer de jar naar de Pi
echo "📦 Kopiëren van jar naar de Pi..."
scp ${LOCAL_JAR} ${PI_USER}@${PI_HOST}:${PI_TARGET_DIR}/ || { echo "Copy failed"; exit 1; }

# 5. Start de jar op de Pi
echo "🚀 Starten van de jar op de Pi..."
ssh ${PI_USER}@${PI_HOST} "cd ${REMOTE_PROJECT_DIR} && nohup java -jar schaakrob-server/target/schaakrob-server-1.0-shaded.jar"

