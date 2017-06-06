package components.panels;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "panelItemElement")
public class PanelItemElement {

    private boolean empty = true;
    private boolean nameOnly = true;
    private String name;
    private String customCode;
    private String label;

    @XmlAttribute(required = true)
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @XmlAttribute
    public boolean isNameOnly() {
        return nameOnly;
    }

    public void setNameOnly(boolean nameOnly) {
        this.nameOnly = nameOnly;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
