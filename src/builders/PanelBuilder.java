package builders;

import components.panels.Panel;

import java.util.List;

public class PanelBuilder {
    public synchronized static String build(List<Panel> panelDefs) {
        StringBuilder builder = new StringBuilder();

        builder.append("'panels' => array(\n");

        panelDefs.forEach((panel -> {
            builder.append("'").append(panel.getName()).append("' => array(\n");

            panel.getItems().forEach(item -> {
                builder.append("array(");
                item.getElements().forEach(element -> {
                    if (element.isEmpty()) {
                        builder.append("'',");
                    } else if (element.isNameOnly()) {
                        builder.append("'").append(element.getName()).append("',");
                    } else {
                        builder.append("\narray(\n");
                        builder.append("'name' => '").append(element.getName()).append("',\n");
                        builder.append("'customCode' => '").append(element.getCustomCode()).append("',\n");
                        builder.append("'label' => '").append(element.getLabel()).append("',\n");
                        builder.append("),");
                    }
                });
                builder.append("),\n");
            });

            builder.append("),\n");
        }));

        builder.append("),\n");
        return builder.toString();
    }
}
