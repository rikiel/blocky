package eu.ba30.re.blocky.view.common.mvc.view;


import com.vaadin.navigator.View;

public interface CommonView<Model, Handler extends CommonView.CommonHandler> extends View {
    void setHandler(Handler handler);

    void setModel(Model model);

    interface CommonHandler {
        void onViewEnter();

        CommonView<?, ?> getView();
    }
}
