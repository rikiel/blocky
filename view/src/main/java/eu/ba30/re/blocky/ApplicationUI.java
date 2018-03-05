package eu.ba30.re.blocky;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import eu.ba30.re.blocky.overview.mvc.presenter.OverviewListPresenter;
import eu.ba30.re.blocky.overview.mvc.view.OverviewListView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class ApplicationUI extends UI {
    @Autowired
    private OverviewListPresenter overviewListPresenter;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final Navigator navigator = new Navigator(this, this);
        setNavigator(navigator);

        final OverviewListView overviewListView = overviewListPresenter.getView();
        overviewListView.setHandler(overviewListPresenter);

        navigator.addView("", overviewListView);
    }
}
