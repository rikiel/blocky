package eu.ba30.re.blocky.service.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;

@Service
public class CstManagerImpl implements CstManager {
    private static final List<Category> CATEGORIES;

    static {
        final Category c1 = new Category();
        c1.setId(1);
        c1.setDescription("Descr1");
        c1.setName("Potraviny");

        final Category c2 = new Category();
        c2.setId(2);
        c2.setDescription("Descr2");
        c2.setName("Drogéria");

        CATEGORIES = Lists.newArrayList(c1, c2);
    }

    @Nonnull
    @Override
    public List<Category> getCategories() {
        return CATEGORIES;
    }

    @Nonnull
    @Override
    public Category getCategory(int categoryId) {
        return CATEGORIES.get(0);
    }
}
