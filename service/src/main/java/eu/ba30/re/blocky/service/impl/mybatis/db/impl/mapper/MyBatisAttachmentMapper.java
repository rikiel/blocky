package eu.ba30.re.blocky.service.impl.mybatis.db.impl.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import eu.ba30.re.blocky.model.Attachment;

public interface MyBatisAttachmentMapper {
    @Nullable
    @Results(id = "getAttachmentList", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "fileName", column = "FILE_NAME"),
            @Result(property = "mimeType", column = "MIME_TYPE"),
            @Result(property = "attachmentType", column = "TYPE", typeHandler = MyBatisAttachmentTypeHandler.class),
            @Result(property = "content", column = "FILE_CONTENT")
    })
    @Select({
            "SELECT *",
            "FROM T_ATTACHMENTS",
    })
    List<Attachment> getAttachmentList();

    @Nullable
    @Results(id = "getAttachmentsByInvoiceId", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "fileName", column = "FILE_NAME"),
            @Result(property = "mimeType", column = "MIME_TYPE"),
            @Result(property = "attachmentType", column = "TYPE", typeHandler = MyBatisAttachmentTypeHandler.class),
            @Result(property = "content", column = "FILE_CONTENT")
    })
    @Select({
            "SELECT *",
            "FROM T_ATTACHMENTS",
            "WHERE INVOICE_ID = #{invoiceId}",
    })
    List<Attachment> getAttachmentsByInvoiceId(@Param("invoiceId") int invoiceId);

    @Insert({
            "<script>",
            "INSERT INTO T_ATTACHMENTS",
            "(ID, INVOICE_ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT)",
            "VALUES",
            "<foreach collection='attachments' item='a' separator=','>",
            "(#{a.id}, #{invoiceId}, #{a.name}, #{a.fileName}, #{a.mimeType}, #{a.attachmentType.id}, #{a.content})",
            "</foreach>",
            "</script>",
    })
    int createAttachmentsForInvoice(@Param("invoiceId") int invoiceId, @Param("attachments") List<Attachment> attachments);

    @Delete({
            "<script>",
            "DELETE FROM T_ATTACHMENTS",
            "WHERE ID IN",
            "(",
            "<foreach collection='attachments' item='a' separator=','>",
            "#{a.id}",
            "</foreach>",
            ")",
            "</script>",
    })
    int removeAttachments(@Param("attachments") List<Attachment> attachments);

    @Select({
            "SELECT NEXT VALUE FOR S_ATTACHMENT_ID",
            "FROM DUAL_ATTACHMENT_ID",
    })
    int getNextId();
}
