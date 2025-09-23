
package com.exlibris.dps;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exlibris.dps package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSIPStatusInfoResponse_QNAME = new QName("http://dps.exlibris.com/", "getSIPStatusInfoResponse");
    private final static QName _GetSipStatus_QNAME = new QName("http://dps.exlibris.com/", "getSipStatus");
    private final static QName _GetSIPsStatusInfo_QNAME = new QName("http://dps.exlibris.com/", "getSIPsStatusInfo");
    private final static QName _GetSipIEs_QNAME = new QName("http://dps.exlibris.com/", "getSipIEs");
    private final static QName _GetSIPStatusInfoByExternalIdResponse_QNAME = new QName("http://dps.exlibris.com/", "getSIPStatusInfoByExternalIdResponse");
    private final static QName _Exception_QNAME = new QName("http://dps.exlibris.com/", "Exception");
    private final static QName _GetHeartBitResponse_QNAME = new QName("http://dps.exlibris.com/", "getHeartBitResponse");
    private final static QName _GetSIPStatusInfoByExternalId_QNAME = new QName("http://dps.exlibris.com/", "getSIPStatusInfoByExternalId");
    private final static QName _GetSIPsStatusInfoResponse_QNAME = new QName("http://dps.exlibris.com/", "getSIPsStatusInfoResponse");
    private final static QName _GetSipIEsResponse_QNAME = new QName("http://dps.exlibris.com/", "getSipIEsResponse");
    private final static QName _GetSIPStatusInfo_QNAME = new QName("http://dps.exlibris.com/", "getSIPStatusInfo");
    private final static QName _GetHeartBit_QNAME = new QName("http://dps.exlibris.com/", "getHeartBit");
    private final static QName _GetSipStatusResponse_QNAME = new QName("http://dps.exlibris.com/", "getSipStatusResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exlibris.dps
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSIPStatusInfoByExternalIdResponse }
     * 
     */
    public GetSIPStatusInfoByExternalIdResponse createGetSIPStatusInfoByExternalIdResponse() {
        return new GetSIPStatusInfoByExternalIdResponse();
    }

    /**
     * Create an instance of {@link GetSipIEs }
     * 
     */
    public GetSipIEs createGetSipIEs() {
        return new GetSipIEs();
    }

    /**
     * Create an instance of {@link GetSIPsStatusInfo }
     * 
     */
    public GetSIPsStatusInfo createGetSIPsStatusInfo() {
        return new GetSIPsStatusInfo();
    }

    /**
     * Create an instance of {@link GetSipStatus }
     * 
     */
    public GetSipStatus createGetSipStatus() {
        return new GetSipStatus();
    }

    /**
     * Create an instance of {@link GetSIPStatusInfoResponse }
     * 
     */
    public GetSIPStatusInfoResponse createGetSIPStatusInfoResponse() {
        return new GetSIPStatusInfoResponse();
    }

    /**
     * Create an instance of {@link GetSIPsStatusInfoResponse }
     * 
     */
    public GetSIPsStatusInfoResponse createGetSIPsStatusInfoResponse() {
        return new GetSIPsStatusInfoResponse();
    }

    /**
     * Create an instance of {@link GetHeartBitResponse }
     * 
     */
    public GetHeartBitResponse createGetHeartBitResponse() {
        return new GetHeartBitResponse();
    }

    /**
     * Create an instance of {@link GetSIPStatusInfoByExternalId }
     * 
     */
    public GetSIPStatusInfoByExternalId createGetSIPStatusInfoByExternalId() {
        return new GetSIPStatusInfoByExternalId();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GetHeartBit }
     * 
     */
    public GetHeartBit createGetHeartBit() {
        return new GetHeartBit();
    }

    /**
     * Create an instance of {@link GetSIPStatusInfo }
     * 
     */
    public GetSIPStatusInfo createGetSIPStatusInfo() {
        return new GetSIPStatusInfo();
    }

    /**
     * Create an instance of {@link GetSipIEsResponse }
     * 
     */
    public GetSipIEsResponse createGetSipIEsResponse() {
        return new GetSipIEsResponse();
    }

    /**
     * Create an instance of {@link GetSipStatusResponse }
     * 
     */
    public GetSipStatusResponse createGetSipStatusResponse() {
        return new GetSipStatusResponse();
    }

    /**
     * Create an instance of {@link SipStatusInfo }
     * 
     */
    public SipStatusInfo createSipStatusInfo() {
        return new SipStatusInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPStatusInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPStatusInfoResponse")
    public JAXBElement<GetSIPStatusInfoResponse> createGetSIPStatusInfoResponse(GetSIPStatusInfoResponse value) {
        return new JAXBElement<GetSIPStatusInfoResponse>(_GetSIPStatusInfoResponse_QNAME, GetSIPStatusInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSipStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSipStatus")
    public JAXBElement<GetSipStatus> createGetSipStatus(GetSipStatus value) {
        return new JAXBElement<GetSipStatus>(_GetSipStatus_QNAME, GetSipStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPsStatusInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPsStatusInfo")
    public JAXBElement<GetSIPsStatusInfo> createGetSIPsStatusInfo(GetSIPsStatusInfo value) {
        return new JAXBElement<GetSIPsStatusInfo>(_GetSIPsStatusInfo_QNAME, GetSIPsStatusInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSipIEs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSipIEs")
    public JAXBElement<GetSipIEs> createGetSipIEs(GetSipIEs value) {
        return new JAXBElement<GetSipIEs>(_GetSipIEs_QNAME, GetSipIEs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPStatusInfoByExternalIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPStatusInfoByExternalIdResponse")
    public JAXBElement<GetSIPStatusInfoByExternalIdResponse> createGetSIPStatusInfoByExternalIdResponse(GetSIPStatusInfoByExternalIdResponse value) {
        return new JAXBElement<GetSIPStatusInfoByExternalIdResponse>(_GetSIPStatusInfoByExternalIdResponse_QNAME, GetSIPStatusInfoByExternalIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHeartBitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getHeartBitResponse")
    public JAXBElement<GetHeartBitResponse> createGetHeartBitResponse(GetHeartBitResponse value) {
        return new JAXBElement<GetHeartBitResponse>(_GetHeartBitResponse_QNAME, GetHeartBitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPStatusInfoByExternalId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPStatusInfoByExternalId")
    public JAXBElement<GetSIPStatusInfoByExternalId> createGetSIPStatusInfoByExternalId(GetSIPStatusInfoByExternalId value) {
        return new JAXBElement<GetSIPStatusInfoByExternalId>(_GetSIPStatusInfoByExternalId_QNAME, GetSIPStatusInfoByExternalId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPsStatusInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPsStatusInfoResponse")
    public JAXBElement<GetSIPsStatusInfoResponse> createGetSIPsStatusInfoResponse(GetSIPsStatusInfoResponse value) {
        return new JAXBElement<GetSIPsStatusInfoResponse>(_GetSIPsStatusInfoResponse_QNAME, GetSIPsStatusInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSipIEsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSipIEsResponse")
    public JAXBElement<GetSipIEsResponse> createGetSipIEsResponse(GetSipIEsResponse value) {
        return new JAXBElement<GetSipIEsResponse>(_GetSipIEsResponse_QNAME, GetSipIEsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSIPStatusInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSIPStatusInfo")
    public JAXBElement<GetSIPStatusInfo> createGetSIPStatusInfo(GetSIPStatusInfo value) {
        return new JAXBElement<GetSIPStatusInfo>(_GetSIPStatusInfo_QNAME, GetSIPStatusInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHeartBit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getHeartBit")
    public JAXBElement<GetHeartBit> createGetHeartBit(GetHeartBit value) {
        return new JAXBElement<GetHeartBit>(_GetHeartBit_QNAME, GetHeartBit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSipStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dps.exlibris.com/", name = "getSipStatusResponse")
    public JAXBElement<GetSipStatusResponse> createGetSipStatusResponse(GetSipStatusResponse value) {
        return new JAXBElement<GetSipStatusResponse>(_GetSipStatusResponse_QNAME, GetSipStatusResponse.class, null, value);
    }

}
