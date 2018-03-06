package eu.ba30.re.blocky.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import eu.ba30.re.blocky.view.overview.mvc.presenter.OverviewListPresenter;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class ApplicationUI extends UI {
    @Autowired
    private OverviewListVaadinView overviewListVaadinView;
    @Autowired
    private InvoiceBulkDeleteVaadinView invoiceBulkDeleteVaadinView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final Navigator navigator = new Navigator(this, this);
        setNavigator(navigator);

        navigator.addView(ApplicationViewName.OVERVIEW.getViewName(), overviewListVaadinView);
        navigator.addView(ApplicationViewName.BULK_DELETE.getViewName(), invoiceBulkDeleteVaadinView);
    }
}
