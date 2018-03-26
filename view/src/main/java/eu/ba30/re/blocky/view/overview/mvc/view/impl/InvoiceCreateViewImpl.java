package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import java.util.Arrays;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.AttachmentUploadFragment;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.common.mvc.view.utils.FormatterUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;

@org.springframework.stereotype.Component
@Scope("prototype")
public class InvoiceCreateViewImpl extends AbstractViewImpl implements InvoiceCreateView {
    private static final Logger log = LoggerFactory.getLogger(InvoiceCreateViewImpl.class);
    public static final int FORM_WIDTH = 600;

    private InvoiceCreateHandler handler;
    private InvoiceCreateModel model;

    @Autowired
    private CstManager cstManager;

    private TextField name;
    private ComboBox<Category> category;
    private TextArea details;
    private AttachmentUploadFragment attachmentUploadFragment;

    private Binder<Invoice> binder;

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

        addInvoiceForm();
        bindFormFields();
    }

    @Override
    public boolean validateView() {
        binder.validate();
        try {
            binder.writeBean(model.getInvoice());
            return true;
        } catch (ValidationException e) {
            Notification.show("Opravte položky");
            log.trace("Validation error", e);
            return false;
        }
    }

    @Override
    public void stopUpload() {
        attachmentUploadFragment.stopUpload();
    }

    @Override
    public void showAttachment(@Nonnull final Attachment attachment) {
        attachmentUploadFragment.showAttachment(attachment);
    }

    private void addHeader() {
        final Header header;
        if (Objects.equals(InvoiceCreateModel.UseCase.CREATE, model.getUseCase())) {
            header = new Header("Vytvorenie novej položky");
        } else {
            header = new Header("Úprava položky");
        }
        addComponent(new CssLayout(header));
    }

    private void addActions() {
        final CssLayout layout = new CssLayout();
        layout.addStyleName(Style.BUTTONS.getCssClass());

        final Button backButton = new Button("Späť");
        backButton.addClickListener(event -> handler.onBack());

        final Button actionButton = new Button("Potvrdiť");
        if (Objects.equals(InvoiceCreateModel.UseCase.CREATE, model.getUseCase())) {
            actionButton.addClickListener(event -> handler.onCreate());
        } else {
            actionButton.addClickListener(event -> handler.onUpdate());
        }

        layout.addComponents(backButton, actionButton);
        addComponent(layout);
    }

    private void addInvoiceForm() {
        final VerticalLayout layout = new VerticalLayout();
        layout.setWidth(FORM_WIDTH, Unit.PIXELS);

        name = new TextField("Názov");

        category = new ComboBox<>("Kategória");
        category.setDataProvider(new CategoryDataProvider());
        category.setItemCaptionGenerator(FormatterUtils::formatCategoryByName);

        details = new TextArea("Detaily položky");

        attachmentUploadFragment = new AttachmentUploadFragment(handler);

        for (Component c : Arrays.asList(name, category, details, attachmentUploadFragment)) {
            c.setWidth(FORM_WIDTH, Unit.PIXELS);
            layout.addComponent(c);
        }
        addComponent(layout);
    }

    private void bindFormFields() {
        binder = new Binder<>();

        binder.forField(name)
                .asRequired("Neplatny nazov")
                .bind(Invoice::getName, Invoice::setName);
        binder.forField(category)
                .asRequired("Neplatna kategoria")
                .bind(Invoice::getCategory, Invoice::setCategory);
        binder.forField(details)
                .bind(Invoice::getDetails, Invoice::setDetails);

        for (Attachment attachment : model.getInvoice().getAttachments()) {
            attachmentUploadFragment.showAttachment(attachment);
        }

        binder.readBean(model.getInvoice());
    }

    private class CategoryDataProvider extends ListDataProvider<Category> {
        public CategoryDataProvider() {
            super(cstManager.getCategories());
        }
    }
}
