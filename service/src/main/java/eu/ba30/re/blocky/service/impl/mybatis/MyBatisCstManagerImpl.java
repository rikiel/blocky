package eu.ba30.re.blocky.service.impl.mybatis;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class MyBatisCstManagerImpl implements CstManager {
    @Autowired
    private CstCategoryRepository cstCategoryRepository;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategoryList() {
        return cstCategoryRepository.getCategoryList();
    }

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(int categoryId) {
        return cstCategoryRepository.getCategoryById(categoryId);
    }
}
