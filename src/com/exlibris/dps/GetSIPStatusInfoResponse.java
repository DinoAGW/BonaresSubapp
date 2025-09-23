
package com.exlibris.dps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für getSIPStatusInfoResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="getSIPStatusInfoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sipStatusInfo" type="{http://dps.exlibris.com/}sipStatusInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSIPStatusInfoResponse", propOrder = {
    "sipStatusInfo"
})
public class GetSIPStatusInfoResponse {

    protected SipStatusInfo sipStatusInfo;

    /**
     * Ruft den Wert der sipStatusInfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SipStatusInfo }
     *     
     */
    public SipStatusInfo getSipStatusInfo() {
        return sipStatusInfo;
    }

    /**
     * Legt den Wert der sipStatusInfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SipStatusInfo }
     *     
     */
    public void setSipStatusInfo(SipStatusInfo value) {
        this.sipStatusInfo = value;
    }

}
