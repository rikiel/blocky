package eu.ba30.re.blocky.service.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;

@Service
public class CstManagerImpl implements CstManager {
    @Autowired
    private CstCategoryRepository cstCategoryRepository;

    @Nonnull
    @Override
    public List<Category> getCategories() {
        return cstCategoryRepository.getAllCategories();
    }

    @Nonnull
    @Override
    public Category getCategory(int categoryId) {
        return cstCategoryRepository.getById(categoryId);
    }
}
