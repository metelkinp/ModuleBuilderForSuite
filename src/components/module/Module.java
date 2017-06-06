package components.module;

import components.fields.ModuleField;
import components.indices.Index;
import components.panels.Panel;
import components.relationships.ModuleRelationship;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="module")
public class Module {
    private String moduleName;
    private String beanName;
    private String inherit;
    private String tableName;
    private String pseudonymMultiple;
    private String pseudonymSingle;
    private List<ModuleField> fields;
    private List<Index> indices;
    private List<ModuleRelationship> relationships;
    private List<Panel> editview;
    private List<Panel> detailview;

    @XmlAttribute(required = true)
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @XmlAttribute(required = true)
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @XmlAttribute(required = true)
    public String getInherit() {
        return inherit;
    }

    public void setInherit(String inherit) {
        this.inherit = inherit;
    }

    @XmlAttribute(required = true)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlAttribute(name = "pseudoMult")
    public String getPseudonymMultiple() {
        return pseudonymMultiple;
    }

    public void setPseudonymMultiple(String pseudonymMultiple) {
        this.pseudonymMultiple = pseudonymMultiple;
    }

    @XmlAttribute(name = "pseudoSingle")
    public String getPseudonymSingle() {
        return pseudonymSingle;
    }

    public void setPseudonymSingle(String pseudonymSingle) {
        this.pseudonymSingle = pseudonymSingle;
    }

    @XmlElementWrapper(name = "editview")
    @XmlElement(name = "panel")
    public List<Panel> getEditview() {
        return editview;
    }

    public void setEditview(List<Panel> editview) {
        this.editview = editview;
    }

    @XmlElementWrapper(name = "detailview")
    @XmlElement(name = "panel")
    public List<Panel> getDetailview() {
        return detailview;
    }

    public void setDetailview(List<Panel> detailview) {
        this.detailview = detailview;
    }

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    public List<ModuleField> getFields() {
        return fields;
    }

    public void setFields(List<ModuleField> fields) {
        this.fields = fields;
    }

    @XmlElementWrapper(name = "indices")
    @XmlElement(name = "index")
    public List<Index> getIndices() {
        return indices;
    }

    public void setIndices(List<Index> indices) {
        this.indices = indices;
    }

    @XmlElementWrapper(name = "relationships")
    @XmlElement(name = "relationship")
    public List<ModuleRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<ModuleRelationship> relationships) {
        this.relationships = relationships;
    }
}
