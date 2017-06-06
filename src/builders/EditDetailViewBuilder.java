package builders;

import components.module.Module;
import components.panels.Panel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class EditDetailViewBuilder implements Callable<Boolean>, ModuleWriter {
    private boolean isDetail = false;
    private String[] buttons;
    private String dirPath;
    private Module moduleDescription;

    public EditDetailViewBuilder(String[] buttons, String dirPath, Module moduleDescription) {
        this.buttons = buttons;
        this.dirPath = dirPath;
        this.moduleDescription = moduleDescription;
    }

    public EditDetailViewBuilder(String[] buttons, String dirPath, Module moduleDescription, boolean isDetail) {
        this.isDetail = isDetail;
        this.buttons = buttons;
        this.dirPath = dirPath;
        this.moduleDescription = moduleDescription;
    }

    @Override
    public Boolean call() throws Exception {
        List<Panel> panels = isDetail ? moduleDescription.getDetailview() : moduleDescription.getEditview();
        String view = isDetail ? "DetailView" : "EditView";
        String fileName = isDetail ? "\\detailviewdefs.php" : "\\editviewdefs.php";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirPath + fileName))) {
            writeRow(writer, "<?php");
            writeRow(writer, String.format("$module_name = '%s';", moduleDescription.getModuleName()));
            writeRow(writer, String.format("$viewdefs['%s']['%s'] = array(", moduleDescription.getModuleName(), view));
            writeRow(writer, "'templateMeta' => array(");
            writeRow(writer, "'includes' => array(),");
            writeRow(writer, "'form' => array(");
            writeRow(writer, "'buttons' => array(");

            for(String s : buttons)
                writeRow(writer, String.format("'%s',", s));

            writeRow(writer, "),");
            writeRow(writer, "),");
            writeRow(writer, "'maxColumns' => '2',");
            writeRow(writer, "'widths' => array(array('label' => 10, 'field' => 30), array('label' => 10, 'field' => 30)),");
            writeRow(writer, "'useTabs' => true,");
            writeRow(writer, "'tabDefs' => array(");

            for (Panel p : panels) {
                writeRow(writer, String.format("'%s' => array('newTab' => true, 'panelDefault' => 'expanded'),", p.getName().toUpperCase()));
            }

            writeRow(writer, "),");
            writeRow(writer, "),");

            writeRow(writer, PanelBuilder.build(panels));

            writeRow(writer, ");");

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
