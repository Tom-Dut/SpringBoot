#!/bin/bash

LOG_FILE="pipeline.log"
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a "$LOG_FILE"
}

fail() {
    log "ERREUR : $1"
    exit 1
}

# Étape 0 : Vérification des nouveaux commits
cd "$PROJECT_DIR" || fail "Répertoire projet introuvable"

git fetch origin main || fail "Échec du git fetch"

LOCAL=$(git rev-parse HEAD)
REMOTE=$(git rev-parse origin/main)

if [ "$LOCAL" = "$REMOTE" ]; then
    log "Aucun nouveau commit -- pipeline non déclenché."
    exit 0
fi

git pull origin main || fail "Échec du git pull"
log "Nouveau commit détecté : $(git rev-parse --short HEAD)"

log "Début du pipeline"

# Étape 1 : Compilation
log "Étape compilation"
mvn compile || fail "Échec de la compilation"
log "Compilation réussie"

# Étape 2 : Tests unitaires
log "Étape tests unitaires"
mvn test || fail "Échec des tests unitaires"
log "Tests unitaires réussis"

# Étape 3 : Build
log "Étape build"

mvn package -DskipTests || fail "Échec de la création du JAR"
log "JAR créé avec succès"

podman build -t mtu-api:latest . || fail "Échec de la construction de l'image Podman"
log "Image Podman créée avec succès"

# Étape 4 : Déploiement
log "Étape déploiement"

if podman container exists mtu-api-prod; then
    log "Arrêt de l'ancien conteneur mtu-api-prod"
    podman stop mtu-api-prod || fail "Échec de l'arrêt de l'ancien conteneur"

    log "Suppression de l'ancien conteneur mtu-api-prod"
    podman rm mtu-api-prod || fail "Échec de la suppression de l'ancien conteneur"
fi

podman run -d --name mtu-api-prod -p 8080:8080 mtu-api:latest || fail "Échec du déploiement"
log "Déploiement réussi"

# Étape 5 : Test de fumée
log "Étape test de fumée"

sleep 3

curl -fsS http://localhost:8080/api/vehicules > /dev/null || fail "Échec du test de fumée"

log "Test de fumée réussi"