package eu.ba30.re.blocky.view.common.mvc.view;

import com.vaadin.ui.Component;

import javax.annotation.Nonnull;

public interface CommonView<Model, Handler extends CommonView.CommonHandler> extends Component {
    void setHandler(@Nonnull Handler handler);

    void setModel(@Nonnull Model model);

    interface CommonHandler {
        void onViewEnter();

        @Nonnull
        CommonView<?, ?> getView();
    }
}
