package components.fields;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "field")
public class ModuleField {

    private String name;
    private String label;
    private String type;
    @XmlAttribute
    private String linkType;
    @XmlAttribute
    private String length;
    private String table;
    @XmlAttribute
    private String nullable;
    private String dbType;
    @XmlAttribute
    private String reportable;
    @XmlAttribute
    private String required;
    @XmlAttribute
    private String defaultValue;
    @XmlAttribute
    private String displayDefault;
    @XmlAttribute
    private String massupdate;
    @XmlAttribute
    private String rname;
    @XmlAttribute
    private String idName;
    @XmlAttribute
    private String source;
    @XmlAttribute
    private String importable;
    @XmlAttribute
    private String options;
    @XmlAttribute
    private String relationship;
    @XmlAttribute
    private String module;
    @XmlAttribute
    private String beanName;
    @XmlAttribute
    private String audited;
    @XmlAttribute
    private String inlineEdit;
    @XmlAttribute
    private String duplicateMerge;
    @XmlAttribute
    private String quicksearch;
    @XmlAttribute
    private String studio;

    private boolean search = false;

    @XmlAttribute(name = "search")
    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @XmlAttribute
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
