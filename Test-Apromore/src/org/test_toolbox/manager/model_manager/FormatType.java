
package org.test_toolbox.manager.model_manager;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormatType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormatType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormatType")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
public class FormatType {

    @XmlAttribute(name = "format")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    protected String format;
    @XmlAttribute(name = "extension")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    protected String extension;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-07-21T09:03:57+10:00", comments = "JAXB RI vhudson-jaxb-ri-2.2-27")
    public void setExtension(String value) {
        this.extension = value;
    }

}