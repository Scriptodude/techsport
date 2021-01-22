#!/usr/bin/env sh

# Frontend first
echo 'Building the frontend'
cd frontend
echo 'Making folders in the backend'
mkdir -p ../backend/src/main/resources/static
echo 'NPM Install'
npm install

echo 'npm build'
npm run build

echo 'Moving resources to backend'
mv ./dist/* ../backend/src/main/resources/static/

# Backend then
echo 'Building the backend'
cd ../backend

echo 'Gradle'
./gradlew clean assemble