package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Objects;

@Component
@Scope("prototype")
public class InvoiceCreateViewImpl extends VerticalLayout implements InvoiceCreateView {
    private InvoiceCreateHandler handler;
    private InvoiceCreateModel model;

    @Autowired
    private CstManager cstManager;

    private TextField name;
    private ComboBox<Category> category;
    private TextArea details;

    @Override
    public void setHandler(@Nonnull final InvoiceCreateHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(@Nonnull final InvoiceCreateModel model) {
        this.model = model;
    }

    @Override
    public void buildView() {
        removeAllComponents();

        addHeader();
        addActions();

        addInvoiceFormular();
    }

    @Override
    public boolean validateView() {
        return false;
    }

    private void addHeader() {
        final Header header;
        if (Objects.equals(InvoiceCreateModel.UseCase.CREATE, model.getUseCase())) {
            header = new Header("Vytvorenie novej položky");
        }else {
            header = new Header("Úprava položky");
        }
        addComponent(header);
    }

    private void addActions() {
        final HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName(Style.BUTTONS.getCssClass());

        final Button backButton = new Button("Späť");
        backButton.addClickListener(event -> handler.onBack());

        final Button createButton = new Button("Vytvoriť");
        createButton.addClickListener(event -> handler.onCreate());

        layout.addComponentsAndExpand(backButton, createButton);
        addComponent(layout);
    }

    private void addInvoiceFormular() {
        final FormLayout layout = new FormLayout();

        name = new TextField("Názov");

        category = new ComboBox<>("Kategória");
        category.setDataProvider(new CategoryDataProvider());

        details = new TextArea("Detaily položky");

        layout.addComponents(name, category, details);
        addComponent(layout);
    }

    private class CategoryDataProvider extends ListDataProvider<Category> {
        public CategoryDataProvider() {
            super(cstManager.getCategories());
        }
    }
}
