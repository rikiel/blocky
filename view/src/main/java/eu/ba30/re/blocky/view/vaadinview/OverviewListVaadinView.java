package eu.ba30.re.blocky.view.vaadinview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.ViewChangeListener;

import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;

@Component
@Scope("prototype")
public class OverviewListVaadinView extends AbstractVaadinView {
    @Autowired
    private OverviewListView.OverviewListHandler handler;

    @SuppressWarnings("unchecked")
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final CommonView view = handler.getView();
        view.setHandler(handler);
        handler.onViewEnter();
        addComponent(view);
        setWidth("100%");
    }
}
