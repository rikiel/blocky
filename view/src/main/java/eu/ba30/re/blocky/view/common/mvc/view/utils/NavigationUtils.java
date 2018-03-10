package eu.ba30.re.blocky.view.common.mvc.view.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.UI;

import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.ApplicationViewName;

public class NavigationUtils {
    private static final Logger log = LoggerFactory.getLogger(NavigationUtils.class);

    private NavigationUtils() {
        throw new UnsupportedOperationException();
    }

    public static void navigateTo(@Nonnull final ApplicationViewName viewName) {
        navigateTo(viewName, null);
    }

    public static <Data> void navigateTo(@Nonnull final ApplicationViewName viewName,
                                         @Nullable final Data data) {
        Validate.notNull(viewName);
        log.info("Navigating to {} with data {}", viewName, data);

        final UI currentUI = UI.getCurrent();
        currentUI.setData(data);
        currentUI.getNavigator().navigateTo(viewName.getViewName());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public static <Data> Data getDataAfterNavigation() {
        final UI currentUI = UI.getCurrent();
        final Data data = (Data) currentUI.getData();
        Validate.notNull(data, "Method returns always notnull data.");
        return data;
    }
}
