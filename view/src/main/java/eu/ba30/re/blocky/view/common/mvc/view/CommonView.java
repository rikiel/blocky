package eu.ba30.re.blocky.view.common.mvc.view;


import com.vaadin.navigator.View;

import javax.annotation.Nonnull;

public interface CommonView<Model, Handler extends CommonView.CommonHandler> extends View {
    void setHandler(@Nonnull Handler handler);

    void setModel(@Nonnull Model model);

    interface CommonHandler {
        void onViewEnter();

        @Nonnull
        CommonView<?, ?> getView();
    }
}
