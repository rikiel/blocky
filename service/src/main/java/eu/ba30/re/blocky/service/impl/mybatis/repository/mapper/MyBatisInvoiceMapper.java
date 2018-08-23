package eu.ba30.re.blocky.service.impl.mybatis.repository.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import eu.ba30.re.blocky.model.Invoice;

public interface MyBatisInvoiceMapper {
    @Nullable
    @Results(id = "getInvoiceList", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "category", column = "CATEGORY_ID", one = @One(select = "eu.ba30.re.blocky.service.impl.mybatis.repository.mapper.MyBatisCategoryMapper.getCategoryById")),
            @Result(property = "details", column = "DETAILS"),
            @Result(property = "creationDate", column = "CREATION"),
            @Result(property = "modificationDate", column = "LAST_MODIFICATION"),
    })
    @Select({
            "SELECT *",
            "FROM T_INVOICES",
    })
    List<Invoice> getInvoiceList();

    @Delete({
            "<script>",
            "DELETE FROM T_INVOICES",
            "WHERE ID IN",
            "(",
            "<foreach collection='invoices' item='i' separator=','>",
            "#{i.id}",
            "</foreach>",
            ")",
            "</script>",
    })
    int remove(@Param("invoices") List<Invoice> invoices);

    @Update({
            "INSERT INTO T_INVOICES",
            "(ID, NAME, CATEGORY_ID, DETAILS, CREATION, LAST_MODIFICATION)",
            "VALUES (#{invoice.id}, #{invoice.name}, #{invoice.category.id}, #{invoice.details}, #{invoice.creationDate}, #{invoice.modificationDate})"
    })
    int create(@Param("invoice") Invoice invoice);

    @Select({
            "SELECT NEXT VALUE FOR S_INVOICE_ID",
            "FROM DUAL_INVOICE_ID",
    })
    int getNextId();
}
