package Util;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


public class MockServerStandaloneRun {
    public static WireMockServer wireMockServerS;

    public static void startServer() {
        WireMockConfiguration wireMockConfig = WireMockConfiguration.options().port(8080);
        wireMockServerS = new WireMockServer(wireMockConfig);
        wireMockServerS.start();
        showServerStartedDialog();
        loadStubsFromJsonDirectory("src/test/resources/mappings");
        waitForStopCommand();
    }

    public static void stopServer() {
        wireMockServerS.stop();
        JOptionPane.showMessageDialog(null, "Server stopped.");
    }

    private static void showServerStartedDialog() {
        JOptionPane.showMessageDialog(null, "WireMock server started successfully.");
    }

    private static void waitForStopCommand() {
        JOptionPane.showMessageDialog(null, "Click OK to stop the server.");
        stopServer();
    }

    private static void loadStubsFromJsonDirectory(String directoryPath) {
        File stubsDir = new File(directoryPath);
        if (stubsDir.exists() && stubsDir.isDirectory()) {
            File[] stubFiles = stubsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
            if (stubFiles != null) {
                for (File file : stubFiles) {
                    try {
                        String jsonContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                        StubMapping mapping = StubMapping.buildFrom(jsonContent);
                        wireMockServerS.addStubMapping(mapping);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
