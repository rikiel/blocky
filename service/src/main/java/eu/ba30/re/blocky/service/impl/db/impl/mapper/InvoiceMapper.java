package eu.ba30.re.blocky.service.impl.db.impl.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import eu.ba30.re.blocky.model.Invoice;

public interface InvoiceMapper {
    @Nullable
    @Results(id = "getAllInvoices", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "category", column = "CATEGORY_ID", typeHandler = InvoiceCategoryHandler.class),
            @Result(property = "details", column = "DETAILS"),
            @Result(property = "creationDate", column = "CREATION"),
            @Result(property = "modificationDate", column = "LAST_MODIFICATION"),
    })
    @Select({
            "SELECT *",
            "FROM T_INVOICES",
    })
    List<Invoice> getAllInvoices();

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
            "VALUES (#{invoice.id}, #{invoice.name}, #{invoice.category}, #{invoice.details}, #{invoice.creationDate}, #{invoice.modificationDate})"
    })
    int create(@Param("invoice") Invoice invoice);

    @Select({
            "SELECT NEXT VALUE FOR S_INVOICE_ID",
            "FROM DUAL_INVOICE_ID",
    })
    int getNextId();
}
