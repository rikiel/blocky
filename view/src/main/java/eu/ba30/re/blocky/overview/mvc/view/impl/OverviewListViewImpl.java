package eu.ba30.re.blocky.overview.mvc.view.impl;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import eu.ba30.re.blocky.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.overview.mvc.view.OverviewListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OverviewListViewImpl extends CssLayout implements OverviewListView {
    private static final Logger log = LoggerFactory.getLogger(OverviewListViewImpl.class);

    private OverviewListHandler handler;
    private OverviewListModel model;

    @Override
    public void setHandler(OverviewListHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(OverviewListModel model) {
        this.model = model;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        log.debug("enter()");
        addComponent(new Label("OverviewList"));
    }
}
