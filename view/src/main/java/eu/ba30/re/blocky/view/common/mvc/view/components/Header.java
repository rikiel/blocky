package eu.ba30.re.blocky.view.common.mvc.view.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * H1 header
 */
public class Header extends Label {
    public Header(String text) {
        super(text);
        addStyleName(ValoTheme.LABEL_H1);
    }
}
