package eu.ba30.re.blocky.view.common.mvc.view.utils;

import com.vaadin.ui.UI;
import eu.ba30.re.blocky.utils.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NavigationUtils {
    private NavigationUtils() {
        throw new UnsupportedOperationException();
    }

    public static void navigateTo(@Nonnull final String view) {
        navigateTo(view, null);
    }

    public static <Data> void navigateTo(@Nonnull final String viewName, @Nullable final Data data) {
        Validate.notNull(viewName);

        final UI currentUI = UI.getCurrent();
        currentUI.setData(data);
        currentUI.getNavigator().navigateTo(viewName);
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
