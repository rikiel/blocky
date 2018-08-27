package eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.other.InvoiceImpl;

public interface MyBatisXmlInvoiceMapper {
    @Nullable
    List<InvoiceImpl> getInvoiceList();

    int remove(@Param("invoices") List<Invoice> invoices);

    int create(@Param("invoice") Invoice invoice);

    int getNextInvoiceId();
}
