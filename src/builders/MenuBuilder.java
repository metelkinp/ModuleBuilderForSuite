package builders;

import components.module.Module;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.Callable;

public class MenuBuilder implements Callable<Boolean>, ModuleWriter {
    private Module moduleDescription;
    private String dirPath;

    public MenuBuilder(Module moduleDescription, String dirPath) {
        this.moduleDescription = moduleDescription;
        this.dirPath = dirPath;
    }

    @Override
    public Boolean call() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirPath + "\\Menu.php"))) {
            writeRow(writer, "<?php");

            writeRow(writer, "if (!defined('sugarEntry') || !sugarEntry) {");
            writeRow(writer, "die('Not A Valid Entry Point');");
            writeRow(writer, "}");

            writer.newLine();

            writeRow(writer, "global $mod_strings, $app_strings, $sugar_config;");

            writeRow(writer, String.format("if(ACLController::checkAccess('%s', 'edit', true)){", moduleDescription.getModuleName()));
            writeRow(writer, String.format("$module_menu[]=array('index.php?module=%s&action=EditView&return_module=%s&return_action=DetailView', $mod_strings['LNK_NEW_RECORD'], 'Add');", moduleDescription.getModuleName(), moduleDescription.getModuleName()));
            writeRow(writer, "}");
            writeRow(writer, String.format("if(ACLController::checkAccess('%s', 'list', true)){", moduleDescription.getModuleName()));
            writeRow(writer, String.format("$module_menu[]=array('index.php?module=%s&action=index&return_module=%s&return_action=DetailView', $mod_strings['LNK_LIST'],'List');", moduleDescription.getModuleName(), moduleDescription.getModuleName()));
            writeRow(writer, "}");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
