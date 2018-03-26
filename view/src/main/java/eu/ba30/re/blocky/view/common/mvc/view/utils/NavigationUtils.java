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

    /**
     * @param viewName view to be navigated to
     */
    public static void navigateTo(@Nonnull final ApplicationViewName viewName) {
        navigateTo(viewName, null);
    }

    /**
     * @param viewName view to be navigated to
     * @param data data for target view
     * @param <Data> type of data
     */
    public static <Data> void navigateTo(@Nonnull final ApplicationViewName viewName,
                                         @Nullable final Data data) {
        Validate.notNull(viewName);
        log.info("Navigating to {} with data {}", viewName, data);

        final UI currentUI = UI.getCurrent();
        currentUI.setData(data);
        currentUI.getNavigator().navigateTo(viewName.getViewName());
    }

    /**
     * @param <Data> type of saved data
     * @return what user saved in previous call of {@link #navigateTo(ApplicationViewName, Object)}.
     * @throws NullPointerException when data is null
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <Data> Data getDataAfterNavigation() {
        final Data data = tryGetDataAfterNavigation();
        Validate.notNull(data, "Method returns always notnull data.");
        return data;
    }

    /**
     * @param <Data> type of saved data
     * @return what user saved in previous call of {@link #navigateTo(ApplicationViewName, Object)}. When nothing is saved, returns null.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <Data> Data tryGetDataAfterNavigation() {
        final UI currentUI = UI.getCurrent();
        return (Data) currentUI.getData();
    }
}
