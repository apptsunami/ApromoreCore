
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.apromore.portal.service_oryx;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apromore.portal.common.Constants;
import org.apromore.portal.common.Utils;
import org.apromore.portal.exception.ExceptionImport;
import org.apromore.portal.exception.ExceptionReadEditSession;
import org.apromore.portal.exception.ExceptionUpdateProcess;
import org.apromore.portal.exception.ExceptionVersion;
import org.apromore.portal.manager.RequestToManager;
import org.apromore.portal.model_manager.EditSessionType;
import org.apromore.portal.model_manager.ProcessSummaryType;
import org.apromore.portal.model_oryx.CloseSessionInputMsgType;
import org.apromore.portal.model_oryx.CloseSessionOutputMsgType;
import org.apromore.portal.model_oryx.ReadNativeInputMsgType;
import org.apromore.portal.model_oryx.ReadNativeOutputMsgType;
import org.apromore.portal.model_oryx.ResultType;
import org.apromore.portal.model_oryx.WriteAnnotationOutputMsgType;
import org.apromore.portal.model_oryx.WriteNewAnnotationOutputMsgType;
import org.apromore.portal.model_oryx.WriteNewProcessInputMsgType;
import org.apromore.portal.model_oryx.WriteNewProcessOutputMsgType;
import org.apromore.portal.model_oryx.WriteProcessInputMsgType;
import org.apromore.portal.model_oryx.WriteProcessOutputMsgType;
import org.wfmc._2008.xpdl2.PackageType;

/**
 * This class was generated by Apache CXF 2.2.9
 * Tue Feb 01 09:34:33 CET 2011
 * Generated source version: 2.2.9
 * 
 */

@javax.jws.WebService(
                      serviceName = "PortalOryxService",
                      portName = "PortalOryx",
                      targetNamespace = "http://www.apromore.org/portal/service_oryx",
                      wsdlLocation = "http://localhost:8080/Apromore-portal/services?wsdl",
                      endpointInterface = "org.apromore.portal.service_oryx.PortalOryxPortType")

		public class PortalOryxPortTypeImpl implements PortalOryxPortType {

	private static final Logger LOG = Logger.getLogger(PortalOryxPortTypeImpl.class.getName());



	public org.apromore.portal.model_oryx.WriteNewAnnotationOutputMsgType writeNewAnnotation
	(org.apromore.portal.model_oryx.WriteNewAnnotationInputMsgType payload) { 
		LOG.info("Executing operation writeNewAnnotation");
		System.out.println(payload);
		WriteNewAnnotationOutputMsgType res = new WriteNewAnnotationOutputMsgType();
		ResultType result = new ResultType();
		res.setResult(result);

		try {
			Integer code = payload.getEditSessionCode();
			String newAnnotationName = payload.getAnnotationName();
			DataHandler handler = payload.getNative();
			InputStream native_is = handler.getInputStream();
			RequestToManager request = new RequestToManager();
			// request details associated with edit session
			EditSessionType editSession = request.ReadEditSession(code);
			String nat_type = editSession.getNativeType();
			Integer processId = editSession.getProcessId();
			String version = editSession.getVersionName();
			request.WriteAnnotation (code, newAnnotationName, true, processId, version, nat_type, native_is);
			// delete previous edit session
			request.DeleteEditionSession(code);
			// request a new session code for the new process and return it to Oryx
			EditSessionType newEditSession = new EditSessionType();
			newEditSession.setDomain(editSession.getDomain());
			newEditSession.setNativeType(editSession.getNativeType());
			newEditSession.setProcessId(editSession.getProcessId());
			newEditSession.setProcessName(editSession.getProcessName());
			newEditSession.setUsername(editSession.getUsername());
			newEditSession.setVersionName(editSession.getVersionName());
			newEditSession.setWithAnnotation(editSession.isWithAnnotation());
			newEditSession.setAnnotation(newAnnotationName);
			int newEditSessionCode = request.WriteEditSession(newEditSession);
			res.setEditSessionCode(newEditSessionCode);
			result.setCode(0);
			result.setMessage("");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}


	public org.apromore.portal.model_oryx.WriteAnnotationOutputMsgType writeAnnotation(org.apromore.portal.model_oryx.WriteAnnotationInputMsgType payload) { 
		LOG.info("Executing operation writeAnnotation");
		System.out.println(payload);
		WriteAnnotationOutputMsgType res = new WriteAnnotationOutputMsgType();
		ResultType result = new ResultType();
		res.setResult(result);
		try {
			Integer code = payload.getEditSessionCode();
			DataHandler handler = payload.getNative();
			InputStream native_is = handler.getInputStream();
			RequestToManager request = new RequestToManager();
			// request details associated with edit session
			EditSessionType editSession = request.ReadEditSession(code);
			String nat_type = editSession.getNativeType();
			Integer processId = editSession.getProcessId();
			String version = editSession.getVersionName();
			String annotationName = editSession.getAnnotation();
			request.WriteAnnotation (code, annotationName, false, processId, version, nat_type, native_is);
			result.setCode(0);
			result.setMessage("");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}

	public CloseSessionOutputMsgType closeSession(CloseSessionInputMsgType payload) { 
		LOG.info("Executing operation closeSession");
		System.out.println(payload);
		CloseSessionOutputMsgType res = new CloseSessionOutputMsgType();

		ResultType result = new ResultType();
		res.setResult(result);
		int code = payload.getCode();
		try {
			// delete edit session
			RequestToManager request = new RequestToManager();
			request.DeleteEditionSession(code);
			result.setCode(0);
			result.setMessage("");

		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apromore.portal.service_oryx.PortalOryxPortType#writeProcess(org.apromore.portal.model_oryx.WriteProcessInputMsgType  payload )*
	 */
	public WriteProcessOutputMsgType writeProcess(WriteProcessInputMsgType payload) {
		LOG.info("Executing operation writeProcess");
		System.out.println(payload);
		WriteProcessOutputMsgType res = new WriteProcessOutputMsgType();
		ResultType result = new ResultType();
		res.setResult(result);
		try {
			DataHandler handler = payload.getNative();
			InputStream native_is = handler.getInputStream();
			int code = payload.getEditSessionCode();
			RequestToManager request = new RequestToManager();
			// request details associated with edit session
			EditSessionType editSession = request.ReadEditSession(code);
			String username = editSession.getUsername();
			int processId = editSession.getProcessId();
			String format = editSession.getNativeType();
			String preVersion = editSession.getVersionName(); 
			// update process: create new version whose name is in native_is, derived from head version
			request.UpdateProcess (code, username, format, processId, preVersion, native_is);
			result.setCode(0);
			result.setMessage("");
		} catch (ExceptionVersion ex) {
			result.setCode(-3);
			result.setMessage(ex.getMessage());
		} catch (IOException ex) {
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		} catch (ExceptionUpdateProcess ex) {
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		} catch (ExceptionReadEditSession ex) {
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apromore.portal.service_oryx.PortalOryxPortType#writeNewProcess(org.apromore.portal.model_oryx.WriteNewProcessInputMsgType  payload )*
	 */
	public WriteNewProcessOutputMsgType writeNewProcess(WriteNewProcessInputMsgType payload) { 
		LOG.info("Executing operation writeNewProcess");
		System.out.println(payload);
		WriteNewProcessOutputMsgType res = new WriteNewProcessOutputMsgType();
		ResultType result = new ResultType();
		res.setResult(result);
		int code = payload.getEditSessionCode();

		try {
			DataHandler handler = payload.getNative();
			InputStream native_is = handler.getInputStream();
			RequestToManager request = new RequestToManager();
			/* request details associated with edit session: username and native type only
			 * are accurate.
			 */
			EditSessionType editSession = request.ReadEditSession(code);
			String username = editSession.getUsername();
			String nativeType = editSession.getNativeType();
			String domain = editSession.getDomain();
			/* process name, version name, creation date, last update and documentation
			 * must be read read native_is
			 */
			String new_processName = null;
			String new_versionName = null;
			String documentation = null;
			String created = null;
			String lastupdate = null;
			if ("XPDL 2.1".compareTo(nativeType)==0) {
				JAXBContext jc = JAXBContext.newInstance("org.wfmc._2008.xpdl2");
				Unmarshaller u = jc.createUnmarshaller();
				JAXBElement<PackageType> rootElement = (JAXBElement<PackageType>) u.unmarshal(native_is);
				PackageType pkg = rootElement.getValue();
				try {
					new_processName = pkg.getName().trim();
					new_versionName = pkg.getRedefinableHeader().getVersion().getValue().trim();
					if (pkg.getPackageHeader().getDocumentation()!=null) {
						documentation = pkg.getPackageHeader().getDocumentation().getValue().trim();
					} else {
						documentation = "";
					}
					if (pkg.getPackageHeader()!=null && pkg.getPackageHeader().getCreated()!=null) {
						created = pkg.getPackageHeader().getCreated().getValue().trim();
					} else {
						created = "";
					}
					if (pkg.getPackageHeader()!=null && pkg.getPackageHeader().getModificationDate()!=null) {
						pkg.getPackageHeader().getModificationDate().getValue().trim();
					} else {
						lastupdate = "";
					}
				} catch (NullPointerException e) {
					throw new ExceptionImport("Missing information in NPF.");
				}
				Marshaller m = jc.createMarshaller();
				m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				JAXBElement<PackageType> rootxpdl = new org.wfmc._2008.xpdl2.ObjectFactory().createPackage(pkg);
				ByteArrayOutputStream xpdl_xml = new ByteArrayOutputStream();
				m.marshal(rootxpdl, xpdl_xml);
				native_is = new ByteArrayInputStream(xpdl_xml.toByteArray());
			} else if ("EPML 2.0".compareTo(nativeType)==0) {
				// as epml doesn't support version, process names, etc.. 
				// there is nothing to do!
			} else {
				throw new ExceptionImport("WriteNewProcess: native format not supported");
			}
			// import native for corresponding process version
			ProcessSummaryType newProcess = request.importProcess(username, nativeType, new_processName, new_versionName, native_is, 
					domain, documentation, created, lastupdate);
			// delete edit session
			request.DeleteEditionSession(code);
			// request a new session code for the new process and return it to Oryx
			EditSessionType newEditSession = new EditSessionType();
			newEditSession.setDomain(newProcess.getDomain());
			newEditSession.setNativeType(newProcess.getOriginalNativeType());
			newEditSession.setProcessId(newProcess.getId());
			newEditSession.setProcessName(newProcess.getName());
			newEditSession.setUsername(newProcess.getOwner());
			newEditSession.setVersionName(newProcess.getLastVersion());
			newEditSession.setWithAnnotation(true);
			newEditSession.setAnnotation(Constants.INITIAL_ANNOTATION);
			int newEditSessionCode = request.WriteEditSession(newEditSession);
			res.setEditSessionCode(newEditSessionCode);

			result.setCode(0);
			result.setMessage("");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apromore.portal.service_oryx.PortalOryxPortType#readNative(org.apromore.portal.model_oryx.ReadNativeInputMsgType  payload )*
	 */
	public ReadNativeOutputMsgType readNative(ReadNativeInputMsgType payload) { 
		LOG.info("Executing operation readNative");
		System.out.println(payload);
		ReadNativeOutputMsgType res = new ReadNativeOutputMsgType();
		ResultType result = new ResultType();
		res.setResult(result);
		int code = payload.getEditSessionCode();

		try {
			// request the edit session identified by code
			RequestToManager request = new RequestToManager();
			EditSessionType editSession = request.ReadEditSession(code);
			int processId = editSession.getProcessId();
			String version = editSession.getVersionName();
			String nativeType = editSession.getNativeType();
			String processName = editSession.getProcessName();
			Boolean withAnnotation = editSession.isWithAnnotation();
			String annotation = editSession.getAnnotation();
			String owner = editSession.getUsername();
			// request native to be exported in format nativeType
			InputStream native_is = 
				request.ExportFormat(processId, processName, version, nativeType, annotation, withAnnotation, owner);
			DataSource sourceNative = new ByteArrayDataSource(native_is, "text/xml"); 
			res.setNative(new DataHandler(sourceNative));
			res.setEditionType(nativeType);
			res.setNativeType(nativeType);
			result.setCode(0);
			result.setMessage("");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		}
		return res;
	}

}
