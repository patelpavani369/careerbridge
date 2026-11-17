# CareerBridge - container image (frontend + Java backend in one service)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY backend/src/main ./backend/src/main
COPY docker/Download.java ./
RUN mkdir out lib \
 && javac -encoding UTF-8 -d out $(find backend/src/main/java -name '*.java') \
 && java Download.java https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.4/postgresql-42.7.4.jar lib/postgresql.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --create-home app && mkdir /data && chown app /data
COPY --from=build /app/out ./classes
COPY --from=build /app/lib/postgresql.jar ./lib/postgresql.jar
COPY lib/h2.jar ./lib/h2.jar
COPY *.html *.css *.js ./
COPY assets ./assets
ENV PORT=8080 DATA_DIR=/data STATIC_DIR=/app
VOLUME /data
EXPOSE 8080
USER app
# Set DATABASE_URL (postgres://user:pass@host/db) to use a managed PostgreSQL database; otherwise H2 inside /data is used.
CMD ["java", "-cp", "classes:lib/h2.jar:lib/postgresql.jar", "com.careerbridge.Main"]
