package components.panels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="panelItem")
public class PanelItem {

    private List<PanelItemElement> elements;

    @XmlElementWrapper(name = "elements")
    @XmlElement(name = "panelItemElement")
    public List<PanelItemElement> getElements() {
        return elements;
    }

    public void setElements(List<PanelItemElement> elements) {
        this.elements = elements;
    }
}
