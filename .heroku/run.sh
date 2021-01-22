#!/usr/bin/env sh

cd ../
# Frontend first
mkdir -p backend/src/main/resources/static
cd frontend
npm install
npm run build

cp -R dist/* ../backend/src/main/resources/static/

# Backend then
cd ../backend
./gradlew clean assemble