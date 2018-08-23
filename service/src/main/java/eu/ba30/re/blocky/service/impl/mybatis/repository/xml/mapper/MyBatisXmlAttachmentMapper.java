package eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;

import eu.ba30.re.blocky.model.Attachment;

public interface MyBatisXmlAttachmentMapper {
    @Nullable
    List<Attachment> getAttachmentList();

    @Nullable
    List<Attachment> getAttachmentsByInvoiceId(@Param("invoiceId") int invoiceId);

    int createAttachmentsForInvoice(@Param("invoiceId") int invoiceId, @Param("attachments") List<Attachment> attachments);

    int removeAttachments(@Param("attachments") List<Attachment> attachments);

    int getNextAttachmentId();
}
