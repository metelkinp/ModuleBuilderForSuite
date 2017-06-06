package components.relationships;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "relationship")
public class ModuleRelationship {
    private String lhsModule;
    private String lhsTable;
    private String lhsKey;
    private String rhsModule;
    private String rhsTable;
    private String rhsKey;
    private String type;
    private String name;

    @XmlElement(name = "lhs_module", required = true)
    public String getLhsModule() {
        return lhsModule;
    }

    public void setLhsModule(String lhsModule) {
        this.lhsModule = lhsModule;
    }

    @XmlElement(name = "lhs_table", required = true)
    public String getLhsTable() {
        return lhsTable;
    }

    public void setLhsTable(String lhsTable) {
        this.lhsTable = lhsTable;
    }

    @XmlElement(name = "lhs_key", required = true)
    public String getLhsKey() {
        return lhsKey;
    }

    public void setLhsKey(String lhsKey) {
        this.lhsKey = lhsKey;
    }

    @XmlElement(name = "rhs_module", required = true)
    public String getRhsModule() {
        return rhsModule;
    }

    public void setRhsModule(String rhsModule) {
        this.rhsModule = rhsModule;
    }

    @XmlElement(name = "rhs_table", required = true)
    public String getRhsTable() {
        return rhsTable;
    }

    public void setRhsTable(String rhsTable) {
        this.rhsTable = rhsTable;
    }

    @XmlElement(name = "rhs_key", required = true)
    public String getRhsKey() {
        return rhsKey;
    }

    public void setRhsKey(String rhsKey) {
        this.rhsKey = rhsKey;
    }

    @XmlElement(name = "relationship_type", required = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
