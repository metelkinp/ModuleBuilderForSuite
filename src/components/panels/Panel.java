package components.panels;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "panel")
public class Panel {

    private String name;
    private List<PanelItem> items;

    @XmlElementWrapper(name = "items")
    @XmlElement(name="panelItem")
    public List<PanelItem> getItems() {
        return items;
    }

    public void setItems(List<PanelItem> panelItems) {
        this.items = panelItems;
    }

    @XmlAttribute(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
