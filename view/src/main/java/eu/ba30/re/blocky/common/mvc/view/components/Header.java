package eu.ba30.re.blocky.common.mvc.view.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class Header extends Label {
    public Header(String text) {
        super(text);
        addStyleName(ValoTheme.LABEL_H1);
    }
}
