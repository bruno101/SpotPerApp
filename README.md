# SpotPerApp
Trabalho de FBD

Para reproduzir o código corretamente, é possível que seja necessário alterar os parâmetros da conexão no início do arquivo SpotPer.java:
String url = "jdbc:sqlserver://localhost;databaseName=BDSpotPer;integratedSecurity=true";
Se a conexão no SQL Server Management Studio não for por autenticação do Windows, provavelmente será necessário passar os parâmetros da seguinte forma:
String url = "jdbc:sqlserver://localhost;databaseName=BDSpotPer;user=[nome do usuario];password=[senha]";

Para executar o código, também foi necessário instalar o driver para JDBC, baixado em "https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15".
Isso envolveu dois passos:
1. Adicionar o arquivo 'mssql-jdbc-9.2.1.jre15' (esse arquivo deve ser o compatível com a versão do Java sendo usada, que neste caso era Java 15) ao classpath do projeto.
2. Copiar o arquivo '.dll' encontrado em enu>auth>x64 (seria x86 dependendo do computador) para a past 'bin' da instalação do Java no computador.
