package eu.ba30.re.blocky.view.vaadinview;

import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractVaadinView extends VerticalLayout implements View{
    public AbstractVaadinView() {
        // undefined -> scrollbars are visible (horizontal/vertical)
        setSizeUndefined();
        setSpacing(false);
    }
}
