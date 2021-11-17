# soq70006118
Solution code for https://stackoverflow.com/q/70006118/592355

A simple spring-boot application, which:

>- Start HSQLDB in server mode, with file persistance (to get it back when I restart my app)
>- Expose a public API that communicates with this hsqldb instance
>- Let me the possibility to connect to this hsqldb server remotely, for instance using the embedded Swing app contained into hsqldb.jar.

Execute with:

    mvn spring-boot:run

More description [in my answer](https://stackoverflow.com/a/70009370/592355).
