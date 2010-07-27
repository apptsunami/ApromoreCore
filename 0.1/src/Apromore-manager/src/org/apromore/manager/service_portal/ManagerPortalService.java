
/*
 * 
 */

package org.apromore.manager.service_portal;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.7
 * Tue Jul 27 12:08:12 EST 2010
 * Generated source version: 2.2.7
 * 
 */


@WebServiceClient(name = "ManagerPortalService", 
                  wsdlLocation = "http://localhost:8080/Apromore-manager/services/ManagerPortal?wsdl",
                  targetNamespace = "http://www.apromore.org/manager/service_portal") 
public class ManagerPortalService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.apromore.org/manager/service_portal", "ManagerPortalService");
    public final static QName ManagerPortal = new QName("http://www.apromore.org/manager/service_portal", "ManagerPortal");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/Apromore-manager/services/ManagerPortal?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8080/Apromore-manager/services/ManagerPortal?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ManagerPortalService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ManagerPortalService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ManagerPortalService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns ManagerPortalPortType
     */
    @WebEndpoint(name = "ManagerPortal")
    public ManagerPortalPortType getManagerPortal() {
        return super.getPort(ManagerPortal, ManagerPortalPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ManagerPortalPortType
     */
    @WebEndpoint(name = "ManagerPortal")
    public ManagerPortalPortType getManagerPortal(WebServiceFeature... features) {
        return super.getPort(ManagerPortal, ManagerPortalPortType.class, features);
    }

}
