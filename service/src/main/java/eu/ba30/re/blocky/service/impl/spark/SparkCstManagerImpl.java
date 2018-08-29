package eu.ba30.re.blocky.service.impl.spark;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class SparkCstManagerImpl implements CstManager {
    @Autowired
    private CstCategoryRepository categoryRepository;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.getCategoryList();
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }
}