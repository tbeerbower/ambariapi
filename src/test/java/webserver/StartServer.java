package webserver;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Created with IntelliJ IDEA.
 * User: john
 * Date: 6/20/12
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartServer {

    public static void main(String[] args) throws IOException {
        Map<String, String> mapArgs = parseArgs(args);
        System.out.println("Starting Ambari API server using the following properties: " + mapArgs);
        System.setProperty("ambariapi.dbfile", mapArgs.get("db"));

        ResourceConfig config = new PackagesResourceConfig("org.apache.ambari.api.services");
        System.out.println("Starting server: http://localhost:" + mapArgs.get("port") + '/');
        HttpServer server = HttpServerFactory.create("http://localhost:" + mapArgs.get("port") + '/', config);
        server.start();

        System.out.println("SERVER RUNNING: http://localhost:" + mapArgs.get("port") + '/');
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> mapProps = new HashMap<String, String>();
        mapProps.put("port", "9998");
        mapProps.put("db", "/var/db/hmc/data/data.db");

        for (int i = 0; i < args.length; i += 2) {
            String arg = args[i];
            if (arg.equals("-p")) {
                mapProps.put("port", args[i + 1]);
            } else if (arg.equals("-d")) {
                mapProps.put("db", args[i + 1]);
            } else {
                printUsage();
                throw new RuntimeException("Unexpected argument, See usage message.");
            }
        }
        return mapProps;
    }

    public static void printUsage() {
        System.err.println("Usage: java StartServer [-p portNum] [-d abs path to ambari db file]");
        System.err.println("Default Values: portNum=9998, ambariDb=/var/db/hmc/data/data.db");
    }
}