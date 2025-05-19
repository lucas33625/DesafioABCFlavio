# Etapa de build
FROM node:12 as build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

# Etapa de produção
FROM nginx:alpine
COPY --from=build /app/dist/desafio-web /usr/share/nginx/html
EXPOSE 80
