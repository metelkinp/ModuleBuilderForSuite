package builders;

import components.fields.ModuleField;
import components.module.Module;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ModuleClassBuilder implements Callable<Boolean>, ModuleWriter {
    private Module moduleDescription;
    private String dirPath;

    public ModuleClassBuilder(Module moduleDescription, String dirPath) {
        this.moduleDescription = moduleDescription;
        this.dirPath = dirPath;
    }

    @Override
    public Boolean call() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirPath + String.format("\\%s.php", moduleDescription.getBeanName())))) {
            HashSet<ModuleField> rfields = new HashSet<>(analiseModule());

            writeRow(writer, "<?php");
            writeRow(writer, String.format("require_once('include/SugarObjects/templates/%s/%s.php');", moduleDescription.getInherit().toLowerCase(), moduleDescription.getInherit()));

            writeRow(writer, String.format("class %s extends %s", moduleDescription.getBeanName(), moduleDescription.getInherit()));
            writeRow(writer, "{");

            buildMainComponents(writer);
            buildNoRelatedFields(writer, rfields);
            buildRelatedFields(writer, rfields);
            buildMethods(writer);

            writeRow(writer, "}");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void buildMethods(BufferedWriter writer) throws IOException {
        writeRow(writer, "public function bean_implements($interface)");
        writeRow(writer, "{");
        writeRow(writer, "switch ($interface) {");
        writeRow(writer, "case 'ACL':");
        writeRow(writer, "return true;");
        writeRow(writer, "}");
        writeRow(writer, "return false;");
        writeRow(writer, "}");

        writer.newLine();

        writeRow(writer, "public function create_new_list_query($order_by, $where, $filter = array(),");
        writeRow(writer, "$params = array(), $show_deleted = 0,");
        writeRow(writer, "$join_type = '', $return_array = false,");
        writeRow(writer, "$parentbean = null, $singleSelect = false, $ifListForExport = false)");
        writeRow(writer, "{");
        writeRow(writer, "return parent::create_new_list_query($order_by, $where, $filter,");
        writeRow(writer, "$params, $show_deleted, $join_type, $return_array,");
        writeRow(writer, "$parentbean, $singleSelect, $ifListForExport);");
        writeRow(writer, "}");

        writer.newLine();

        writeRow(writer, "public function save($check_notify = false)");
        writeRow(writer, "{");
        writeRow(writer, "return parent::save($check_notify);");
        writeRow(writer, "}");

        writer.newLine();
    }

    private void buildRelatedFields(BufferedWriter writer, HashSet<ModuleField> rfields) throws IOException {

        writeRow(writer, "//related");

        rfields.forEach(f -> {
            writeClassField(writer, f.getName());
        });

        writer.newLine();

        writeRow(writer, "public $additional_column_fields = Array(");
        writeRow(writer, "'assigned_user_name',");
        rfields.forEach(f -> {
            try {
                writeRow(writer, String.format("'%s',", f.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writeRow(writer, ");");

        writer.newLine();

        writeRow(writer, "public $relationship_fields = Array(");
        rfields.stream().filter(f -> (f.getType().equals("id"))).forEach(f -> {
            try {
                writeRow(writer, String.format("'%s' => '%s',", f.getName(), f.getTable()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writeRow(writer, ");");

        writer.newLine();
    }

    private void buildNoRelatedFields(BufferedWriter writer, HashSet<ModuleField> rfields) throws IOException {

        moduleDescription.getFields().stream().filter(f -> !rfields.contains(f)).forEach(f -> {
            writeClassField(writer, f.getName());
        });

        writer.newLine();
    }

    private void buildMainComponents(BufferedWriter writer) throws IOException {
        writeRow(writer, "public $new_schema = true;");
        writeRow(writer, String.format("public $module_dir = '%s';", moduleDescription.getModuleName()));
        writeRow(writer, String.format("public $object_name = '%s';", moduleDescription.getBeanName()));
        writeRow(writer, String.format("public $table_name = '%s';", moduleDescription.getTableName()));
        writeRow(writer, "public $importable = true;");
        writeRow(writer, "public $acltype = 'module';");
        writeRow(writer, String.format("public $acl_category = '%s';", moduleDescription.getModuleName()));

        String[] standartFields = {
                "id",
                "name",
                "date_entered",
                "date_modified",
                "modified_by_name",
                "modified_user_id",
                "created_by",
                "created_by_name",
                "deleted",
                "description",
                "created_by_link",
                "modified_user_link",
                "assigned_user_id",
                "assigned_user_name",
                "assigned_user_link",
                "SecurityGroups"
        };

        Arrays.stream(standartFields).forEach(s -> {
            writeClassField(writer, s);
        });

        writer.newLine();
    }

    private void writeClassField(BufferedWriter writer, String fieldName) {
        try {
            writeRow(writer, String.format("public $%s;", fieldName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ModuleField> analiseModule() {
        List<ModuleField> tmp = new LinkedList<>();
        tmp.addAll(moduleDescription.getFields().stream().filter(f -> (f.getType().equals("id") || f.getType().equals("relate"))).collect(Collectors.toList()));
        return tmp;
    }
}
