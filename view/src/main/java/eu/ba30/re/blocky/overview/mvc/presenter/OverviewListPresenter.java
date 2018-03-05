package eu.ba30.re.blocky.overview.mvc.presenter;

import eu.ba30.re.blocky.overview.mvc.view.OverviewListView;
import eu.ba30.re.blocky.overview.mvc.view.impl.OverviewListViewImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OverviewListPresenter implements OverviewListView.OverviewListHandler {
    @Autowired
    private OverviewListViewImpl view;

    @Override
    public OverviewListView getView() {
        return view;
    }
}
