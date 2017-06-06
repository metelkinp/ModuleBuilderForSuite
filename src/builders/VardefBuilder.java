package builders;

import components.module.Module;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


public class VardefBuilder implements Callable<Boolean>, ModuleWriter {
    private Module moduleDescription;
    private String dirPath;

    public VardefBuilder(Module moduleDescription, String dirPath) {
        this.moduleDescription = moduleDescription;
        this.dirPath = dirPath;
    }

    @Override
    public Boolean call() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirPath + "\\vardefs.php"))) {
            writeRow(writer, "<?php");

            writeRow(writer, String.format("$dictionary['%s'] = array(", moduleDescription.getBeanName()));
            writeRow(writer, String.format("'table' => '%s',", moduleDescription.getTableName()));
            writeRow(writer, "'audited' => false,");
            writeRow(writer, "'inline_edit' => true,");
            writeRow(writer, "'duplicate_merge' => 'disabled',");

            writeRow(writer, "'fields' => array(");
            buildFields(writer);
            writeRow(writer, "),");

            writeRow(writer, "'indices' => array(");
            buildIndices(writer);
            writeRow(writer, "),");

            writeRow(writer, "'relationships' => array(");
            buildRelationships(writer);
            writeRow(writer, "),");

            writeRow(writer, "'optimistic_locking' => true,");
            writeRow(writer, "'unified_search' => true,");
            writeRow(writer, ");");

            writeRow(writer, "if (!class_exists('VardefManager')) {");
            writeRow(writer, "require_once('include/SugarObjects/VardefManager.php');}");

            writeRow(writer, String.format("VardefManager::createVardef('%s', '%s', array('acl', 'assignable', 'security_groups', 'basic'));", moduleDescription.getModuleName(), moduleDescription.getBeanName()));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void buildRelationships(BufferedWriter writer) {
        moduleDescription.getRelationships().forEach(r -> {
            try {

                writeRow(writer, String.format("'%s' => array(", r.getName()));
                writeRow(writer, String.format("'lhs_module' => '%s',", r.getLhsModule()));
                writeRow(writer, String.format("'lhs_table' => '%s',", r.getLhsTable()));
                writeRow(writer, String.format("'lhs_key' => '%s',", r.getLhsKey()));
                writeRow(writer, String.format("'rhs_module' => '%s',", r.getRhsModule()));
                writeRow(writer, String.format("'rhs_table' => '%s',", r.getRhsTable()));
                writeRow(writer, String.format("'rhs_key' => '%s',", r.getRhsKey()));
                writeRow(writer, String.format("'relationship_type' => '%s',", r.getType()));
                writeRow(writer, "),");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void buildIndices(BufferedWriter writer) {
        moduleDescription.getIndices().forEach(index -> {
            try {

                writeRow(writer, "array(");
                writeRow(writer, String.format("'name' => '%s',", index.getName()));
                writeRow(writer, String.format("'type' => '%s',", index.getType()));

                StringBuilder fieldsBuilder = new StringBuilder();

                index.getFields().forEach(fname -> {
                    fieldsBuilder.append("'").append(fname).append("'").append(",");
                });

                writeRow(writer, String.format("'fields' => array(%s),", fieldsBuilder.toString()));

                writeRow(writer, "),");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void buildFields(BufferedWriter writer) {

        moduleDescription.getFields().forEach(f -> {
            try {

                writeRow(writer, String.format("'%s' => array(", f.getName()));
                writeRow(writer, String.format("'vname' => 'LBL_%s',", f.getName().toUpperCase()));

                List<Field> notNullObjFields = Arrays.stream(f.getClass().getDeclaredFields()).filter(e -> {
                    e.setAccessible(true);
                    try {
                        return (e.get(f) != null);
                    } catch (IllegalAccessException e1) {
                        return false;
                    }
                }).filter(e -> !e.getName().equals("label") && !e.getName().equals("search"))
                        .collect(Collectors.toList());

                //array inner text
                notNullObjFields.forEach(field -> {

                    try {

                        String name = field.getName();

                        switch (name) {
                            case ("linkType"):
                                name = "link_type";
                                break;
                            case ("length"):
                                name = "len";
                                break;
                            case ("nullable"):
                                name = "isnull";
                                break;
                            case ("defaultValue"):
                                name = "default";
                                break;
                            case ("idName"):
                                name = "id_name";
                                break;
                            case ("beanName"):
                                name = "bean_name";
                                break;
                            case ("inlineEdit"):
                                name = "inline_edit";
                                break;
                            case ("duplicateMerge"):
                                name = "duplicate_edit";
                                break;
                            case ("displayDefault") : name = "display_default"; break;
                        }

                        field.setAccessible(true);
                        String  value = (String) field.get(f);

                        writeRow(writer, String.format("'%s' => '%s',", name, value));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                writeRow(writer, "),");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
