package cars24.DocumentCreator.utility;

import java.util.*;

public interface Constants {
    interface TABLE_CONSTANTS{
        String INVOICE_ITEMS = "InvoiceItems";
        String BODY_ELEMENT_ID = "items-table-body";
        String HEADER_ELEMENT_ID = "items-table-header";
    }
    interface INVOICE_FIELDS{
        String ITEM_NAME = "itemName";
        String ITEM_DESCRIPTION = "itemDescription";
        String ITEM_QUANTITY = "itemQuantity";
        String ITEM_PRICE = "itemPrice";
        String SERVICE_TAX = "serviceTax";
        String SGST = "sgst";
        String SGST_AMOUNT = "sgstAmount";
        String CGST_AMOUNT = "cgstAmount";
        String CGST = "cgst";
    }
    public final LinkedHashMap<String,String> FIELD_TO_COLUMN_MAPPING = new LinkedHashMap<>(){{
        put("itemName","Name");
        put("itemDescription", "Description");
        put("itemQuantity", "Quantity");
        put("itemPrice" , "Amount");
        put("serviceTax", "Service Charge (%)");
        put("sgst", "SGST");
        put("sgstAmount", "SGST AMOUNT");
        put("cgst", "CGST");
        put("cgstAmount", "CGST AMOUNT");
        put("itemAmount","Total Amount");
    }};
    public final Set<String> DYNAMIC_FIEDLS = new HashSet<>(
            List.of(INVOICE_FIELDS.ITEM_DESCRIPTION,
                    INVOICE_FIELDS.SERVICE_TAX,
                    INVOICE_FIELDS.SGST,
                    INVOICE_FIELDS.CGST,
                    INVOICE_FIELDS.SGST_AMOUNT,
                    INVOICE_FIELDS.CGST_AMOUNT));


    interface INPUT_FIELDS{
        String REQUEST_TYPE = "requestType";
        String TEMPLATE_ID = "templateId";
        String DOC_TYPE = "docType";
    }

    interface REQUEST_TYPE{
        String CREATE_DOCUMENT = "CreateDocument";
        String CREATE_TEMPLATE = "CreateTemplate";
        String UPDATE_TEMPLATE = "UpdateTemplate";
        String GET_TEMPLATE_PAYLOAD = "GetTemplatePayload";
    }
    interface TEMPLATE_FIELDS{
        String HTML_TEMPLATE = "htmlTemplate";
        String JSON_VALIDATOR = "jsonValidator";
        String TEMPLATE_NAME = "templateName";
    }
    interface DOCUMENT_TYPE{
        List<String> IMAGE = List.of("image","png");
        List<String> PDF = List.of("pdf");
    }

}
