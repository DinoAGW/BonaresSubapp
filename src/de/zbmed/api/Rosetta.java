package de.zbmed.api;

import java.net.URL;

import javax.xml.namespace.QName;

import com.exlibris.dps.DepositWebServices_Service;
import com.exlibris.dps.sdk.pds.HeaderHandlerResolver;

import de.zbmed.utilities.Custom;

public class Rosetta {
	public static void main(String[] args) throws Exception {
//		System.out.println(getMD("IE28266070", "prod"));
//		System.out.println(getMD("IE9712123", "prod"));
		System.out.println(submitDepositActivity("2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0", "82407524",
				"14903169", "dev"));
		System.out.println("WebServices Ende");
	}

	public static String submitDepositActivity(String subDirectoryName, String materialFlowId, String producerId,
			String rosettaInstance) throws Exception {
		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
		final String institution = Custom.getInstitution(rosettaInstance);
		final String userName = Custom.getUsername(rosettaInstance);
		final String password = Custom.getPassword(rosettaInstance);
		final String Deposit_WSDL_URL = Custom.getDeposit_WSDL_URL(rosettaURL);

		DepositWebServices_Service dWs = new DepositWebServices_Service(new URL(Deposit_WSDL_URL),
				new QName("http://dps.exlibris.com/", "DepositWebServices"));
		dWs.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));

		return dWs.getDepositWebServicesPort().submitDepositActivity(null, materialFlowId, subDirectoryName, producerId,
				null);
	}

//	private static IEWebServices getIEWebServicesPort(String rosettaInstance) throws Exception {
//			final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//			final String institution = Custom.getInstitution(rosettaInstance);
//			final String userName = Custom.getUsername(rosettaInstance);
//			final String password = Custom.getPassword(rosettaInstance);
//			final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//			IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//					new QName("http://dps.exlibris.com/", "IEWebServices"));
//			ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//			ieWs = ieWS.getIEWebServicesPort();
//		}
//		return ieWs;
//	}

//	public static String getMD(String rosettaInstance, String iePid) throws Exception {
//		String retval = null;
//		int tried = 0;
//		while (retval == null) {
//			try {
//				retval = getIEWebServicesPort(rosettaInstance).getMD(null, iePid, null, null, null);
//			} catch (Exception e) {
//				System.err.println("Fehler");
//				++tried;
//				if (tried == 10)
//					throw e;
//			}
//		}
//		return retval;
//	}

//	public static String getIE(String rosettaInstance, String iePid) throws Exception {
//		String retval = null;
//		int tried = 0;
//		while (retval == null) {
//			try {
//				retval = getIEWebServicesPort(rosettaInstance).getIE(null, iePid, null);
//			} catch (Exception e) {
//				System.err.println("Fehler");
//				++tried;
//				if (tried == 10)
//					throw e;
//			}
//		}
//		return retval;
//	}

//	public static String lockIE(String iePid, String rosettaInstance) throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		Action action = Action.valueOf("LOCK");
//		IeStatusInfo iesi = ieWS.getIEWebServicesPort().manageIE(action, iePid, null);
//		return iesi.getLockedBy();
//	}

//	public static String rollbackIE(String iePid, String rosettaInstance) throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		Action action = Action.valueOf("ROLLBACK");
//		IeStatusInfo iesi = ieWS.getIEWebServicesPort().manageIE(action, iePid, null);
//		return iesi.getLockedBy();
//	}

//	public static String commitIE(String iePid, String rosettaInstance) throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		Action action = Action.valueOf("COMMIT");
//		IeStatusInfo iesi = ieWS.getIEWebServicesPort().manageIE(action, iePid, null);
//		return iesi.getLockedBy();
//	}

//	public static void updateMD(String iePid, String rosettaInstance, Document doc, Boolean commit) throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		List<MetaData> metadata = new Stack<>();
//		MetaData metadatum = new MetaData();
//		metadatum.setType("descriptive");
//		metadatum.setSubType("dc");
//		metadatum.setContent(XmlHelper.getStringFromDocumentWithIndention(doc));
//		metadata.add(metadatum);
//		ieWS.getIEWebServicesPort().updateMD(commit, metadata, iePid, null);
//	}

//	public static long updateRepresentation(List<RepresentationContent> representationContent, List<MetaData> metadata,
//			String repPid, String iePid, String rosettaInstance, boolean commit, String submissionReason)
//			throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		System.out.println("Update Repräsentation: " + iePid + " " + repPid + " '" + submissionReason + "'");
//		return ieWS.getIEWebServicesPort().updateRepresentation(commit, iePid, metadata, null, repPid,
//				representationContent, submissionReason);
//	}

//	public static String getRipStatus(long ripID, String rosettaInstance) throws Exception {
//		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
//		final String institution = Custom.getInstitution(rosettaInstance);
//		final String userName = Custom.getUsername(rosettaInstance);
//		final String password = Custom.getPassword(rosettaInstance);
//		final String IE_WSDL_URL = Custom.getIE_WSDL_URL(rosettaURL);
//
//		IEWebServices_Service ieWS = new IEWebServices_Service(new URL(IE_WSDL_URL),
//				new QName("http://dps.exlibris.com/", "IEWebServices"));
//		ieWS.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));
//
//		return ieWS.getIEWebServicesPort().getRipStatus(null, ripID);
//	}
}
