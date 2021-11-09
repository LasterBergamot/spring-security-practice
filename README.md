# Spring Security Practice
Several Spring Boot apps with different approaches on security, such as mTLS, OAuth2 and JWT.

# mTLS
To be able to demonstrate how mTLS works, we have to create some certificates and keystores.
Creating self signed certificates:
Inside the mtls-client folder: (ssp = spring security practice)
- type in `keytool -genkeypair -alias ssp-mtls-client -keyalg RSA -keysize 2048 -storetype JKS -keystore ssp-mtls-client.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1` to generate the JKS keystore
- the keystore password can be anything you want, and for the prompted questions the default value, Unknown, can be used
- the password for the alias can be the same as the one for the keystore

That last part in key tool command is very critical as self signed cert created without SAN entries won’t work with Chrome and Safari.

Inside the mtls-server folder:
- type in `keytool -genkeypair -alias ssp-mtls-server -keyalg RSA -keysize 2048 -storetype JKS -keystore ssp-mtls-server.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1` to generate the JKS keystore
- everything else is the same as above

Create public certificate files:
Client:
- `keytool -export -alias ssp-mtls-client -file ssp-mtls-client.crt -keystore ssp-mtls-client.jks`
- enter the password you gave for the keystore above

Server:
- `keytool -export -alias ssp-mtls-server -file ssp-mtls-server.crt -keystore ssp-mtls-server.jks`
- enter the password you gave for the keystore above

Import certificates to JKS files:
Import client certificate to the server's JKS file:
- `keytool -import -alias ssp-mtls-client -file ssp-mtls-client.crt -keystore ..\mtls-server\ssp-mtls-server.jks`
- enter the password of the keystore
- and of course, you can trust this certificate

Import server certificate to the client's JKS file:
- `keytool -import -alias ssp-mtls-server -file ssp-mtls-server.crt -keystore ..\mtls-client\ssp-mtls-client.jks`