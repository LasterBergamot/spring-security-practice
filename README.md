# Spring Security Practice
Several Spring Boot apps with different approaches on security, such as mTLS, OAuth2 and JWT.

## JWT

Before starting the application, run `docker compose up -d` from the root of the project to start up the **PostgreSQL** database and **Adminer**.

To tear down the services mentioned above, run `docker compose down`.

By default, a **default user**, with username and password **admin**, is created when the application starts, which is stored in the `jwt_user` table. This table is always dropped and created with application shutdown and startup, so any data persisted will be lost.

## mTLS
To be able to demonstrate how mTLS works, we have to create some certificates and keystores. ([Source](https://medium.com/@niral22/2-way-ssl-with-spring-boot-microservices-2c97c974e83))
Creating self signed certificates:
Inside the mtls folder: (ssp = spring security practice)
Client:
- type in the following command to generate the JKS keystore:
  `keytool -genkeypair -alias {your-client-keystore-alias} -keyalg RSA -keysize 2048 -storetype JKS -keystore {your-client-keystore-name}.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1`
- example: 
  `keytool -genkeypair -alias ssp-mtls-client -keyalg RSA -keysize 2048 -storetype JKS -keystore ssp-mtls-client.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1`
- the keystore password can be anything you want, and for the prompted questions the default value, Unknown, can be used
- the password for the alias can be the same as the one for the keystore

That last part in key tool command is very critical as self signed cert created without SAN entries won’t work with Chrome and Safari.

Server:
- you can proceed similarly for the server:
  `keytool -genkeypair -alias {your-server-keystore-alias} -keyalg RSA -keysize 2048 -storetype JKS -keystore {your-server-keystore-name}.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1`
- example:
  `keytool -genkeypair -alias ssp-mtls-server -keyalg RSA -keysize 2048 -storetype JKS -keystore ssp-mtls-server.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1`

Create public certificate files:
Client:
  `keytool -export -alias {your-client-cert-alias} -file {your-client-cert-name}.crt -keystore {your-client-keystore-name}.jks`
- example:
  `keytool -export -alias ssp-mtls-client -file ssp-mtls-client.crt -keystore ssp-mtls-client.jks`
- enter the password you gave for the keystore above

Server:
  `keytool -export -alias {your-server-cert-alias} -file {your-server-cert-name}.crt -keystore {your-server-keystore-name}.jks`
- example:
  `keytool -export -alias ssp-mtls-server -file ssp-mtls-server.crt -keystore ssp-mtls-server.jks`

Import certificates to JKS files:
Import client certificate to the server's JKS file:
  `keytool -import -alias {your-client-import-alias} -file {your-client-cert-name}.crt -keystore {your-server-keystore-name}.jks`
- example:
  `keytool -import -alias ssp-mtls-client -file ssp-mtls-client.crt -keystore ssp-mtls-server.jks`
- enter the password of the keystore
- and of course, you can trust this certificate

Import server certificate to the client's JKS file:
  `keytool -import -alias {your-server-import-alias} -file {your-server-cert-name}.crt -keystore {your-client-keystore-name}.jks`
- example:
  `keytool -import -alias ssp-mtls-server -file ssp-mtls-server.crt -keystore ssp-mtls-client.jks`

When we would like to reach the localhost after starting up the client and server app, the browser will complain about the client certificate being needed! (In this case the browser will become the client to our client app.)
- the client app will ask the browser to present a certificate for authentication
- to overcome this, we'll have to import a certificate to our browser
- but our browser cannot understand a **.jks** file, so we need a **PKCS12** formatted file instead
- the following command will convert our **.jks** file to a **PKCS12** one:
  `keytool -importkeystore -srckeystore {your-server-keystore-name}.jks -destkeystore {your-server-pkcs12-keystore-name}.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass {your-server-keystore-password} -deststorepass {your-server-pkcs12-keystore-password} -srcalias {your-server-keystore-alias} -destalias {your-server-pkcs12-keystore-alias} -srckeypass {your-server-key-password} -destkeypass {your-server-pkcs12-key-password} -noprompt`
- example:
  `keytool -importkeystore -srckeystore ssp-mtls-server.jks -destkeystore ssp-mtls-server.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass password -deststorepass password -srcalias ssp-mtls-server -destalias ssp-mtls-server -srckeypass password -destkeypass password -noprompt`
- then you can import the newly created certificate to your browser