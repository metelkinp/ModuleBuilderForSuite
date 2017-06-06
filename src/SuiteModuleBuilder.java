import builders.ModuleBuilder;
import builders.PanelBuilder;
import components.module.Module;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SuiteModuleBuilder {
    public static void main(String[] args) throws Exception {
        String path = args.length > 1 ? args[1] : System.getProperty("user.home") + "\\Documents";
        String fileName;
        if (args.length > 0) fileName = args[0];
        else throw new FileNotFoundException();

        JAXBContext context = JAXBContext.newInstance(Module.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        Module module = (Module) unmarshaller.unmarshal(new File(fileName));

        String fullPath = path + module.getModuleName();

        deployModuleStructure(fullPath);

        new ModuleBuilder(module, fullPath).build();
    }

    private static void deployModuleStructure(String fullPath) throws IOException {
        Path p1 = Paths.get(fullPath + "\\metadata\\subpanels");
        Path p2 = Paths.get(fullPath + "\\language");
        Path p3 = Paths.get(fullPath + "\\tpls");
        Path p4 = Paths.get(fullPath + "\\javascript");

        Files.createDirectories(p1);
        Files.createDirectories(p2);
        Files.createDirectories(p3);
        Files.createDirectories(p4);
    }
}
