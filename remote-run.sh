#!/bin/bash

# Configuratie
PI_USER="pi"
PI_HOST="192.168.178.88"
PI_TARGET_DIR="/home/pi/git/schaakrob/schaakrob-server/target"
LOCAL_JAR="schaakrob-server/target/schaakrob-server-1.0-shaded.jar"
REMOTE_PROJECT_DIR="/home/pi/git/schaakrob"

# 1. Stop 'ui' processen op de Pi
echo "🛑 Stoppen van 'ui' processen op de Pi..."
ssh ${PI_USER}@${PI_HOST} "pkill -f ui || true"

# 2. Stop 'java' processen op de Pi
echo "🛑 Stoppen van 'java' processen op de Pi..."
ssh ${PI_USER}@${PI_HOST} "pkill -f java || true"

# 3. Start de jar op de Pi
echo "🚀 Starten van de jar op de Pi..."
ssh ${PI_USER}@${PI_HOST} "cd ${REMOTE_PROJECT_DIR} && nohup java -jar schaakrob-server/target/schaakrob-server-1.0-shaded.jar"

