    package com.stackoverflow.hsqldbrunner;

    import java.io.IOException;
    import java.io.PrintWriter;
    import java.io.StringWriter;
    import java.util.Properties;

    import org.hsqldb.Server;
    import org.hsqldb.persist.HsqlProperties;
    import org.hsqldb.server.ServerAcl.AclFormatException;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.SmartLifecycle;
    import org.springframework.core.io.Resource;
    import org.springframework.core.io.support.PropertiesLoaderUtils;
    import org.springframework.stereotype.Component;

    @Component
    public class HyperSqlDbServer implements SmartLifecycle {
        private final Logger logger = LoggerFactory.getLogger(HyperSqlDbServer.class);
        private HsqlProperties properties;
        private Server server;
        private boolean running = false;

        public HyperSqlDbServer(@Value("classpath:/hsqldb.properties") Resource serverProps) throws IOException {
            Properties props = PropertiesLoaderUtils.loadProperties(serverProps);
            if (props.elements().hasMoreElements() && logger.isDebugEnabled()) {
                StringWriter writer = new StringWriter();
                props.list(new PrintWriter(writer));
                logger.debug(writer.toString());
            }
            properties = new HsqlProperties(props);
        }

        @Override
        public boolean isRunning() {
            if (server != null)
                server.checkRunning(running);
            return running;
        }

        @Override
        public void start() {
            if (server == null) {
                logger.info("Starting HSQL server...");
                server = new Server();
                try {
                    server.setProperties(properties);
                    server.start();
                    running = true;
                } catch (AclFormatException afe) {
                    logger.error("Error starting HSQL server.", afe);
                } catch (IOException e) {
                    logger.error("Error starting HSQL server.", e);
                }
            }
        }

        @Override
        public void stop() {
            logger.info("Stopping HSQL server...");
            if (server != null) {
                server.stop();
                running = false;
            }
        }

        @Override
        public int getPhase() {
            return 0;
        }

        @Override
        public boolean isAutoStartup() {
            return true;
        }

        @Override
        public void stop(Runnable runnable) {
            stop();
            runnable.run();
        }
    }