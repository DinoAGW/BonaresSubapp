package de.zbmed.api;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.w3c.dom.Node;

import com.exlibris.dps.DepositWebServices_Service;
import com.exlibris.dps.SipStatusInfo;
import com.exlibris.dps.SipWebServices_Service;
import com.exlibris.dps.sdk.pds.HeaderHandlerResolver;

import de.zbmed.utilities.Custom;
import de.zbmed.utilities.XmlHelper;

public class Rosetta {
	public static void main(String[] args) throws Exception {
//		System.out.println(getMD("IE28266070", "prod"));
//		System.out.println(getMD("IE9712123", "prod"));
		String depositXml = submitDepositActivity("2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0", "82407524",
				"14903169", "dev");
//		System.out.println(depositXml);
		String sipId = extractSipId(depositXml);
//		String sipId = "136077";
//		SipStatusInfo ssi = getSIPStatusInfo(sipId, "dev");
//		System.out.println(ssi.getModule());
//		System.out.println(ssi.getStatus());
//		System.out.println(ssi.getStage());
		waitTillProcessed(sipId, "dev");
		System.out.println("WebServices Ende");
	}

	public static boolean isProcessed(SipStatusInfo ssi) {
		return ssi.getModule().contentEquals("REP") || ssi.getModule().contentEquals("PER");
	}

	public static String extractSipId(String depositXml) throws Exception {
		Node node = XmlHelper.parse(depositXml);
		XmlHelper.removeEmptyNodes(node);
		node = XmlHelper.getFirstChildByName(node, "ser:deposit_result");
		node = XmlHelper.getFirstChildByName(node, "ser:sip_id");
		return node.getTextContent();
	}

	public static String extractDepositId(String depositXml) throws Exception {
		Node node = XmlHelper.parse(depositXml);
		XmlHelper.removeEmptyNodes(node);
		node = XmlHelper.getFirstChildByName(node, "ser:deposit_result");
		node = XmlHelper.getFirstChildByName(node, "ser:deposit_activity_id");
		return node.getTextContent();
	}

	public static void waitTillProcessed(String sipId, String rosettaInstance) throws Exception {
		System.out.println("Warte bis " + sipId + " abgeschlossen ist (" + rosettaInstance + ")");
		int versuche = 600;
		while (true) {
			SipStatusInfo ssi = getSIPStatusInfo(sipId, rosettaInstance);
			if (isProcessed(ssi)) {
				return;
			} else {
				--versuche;
				if (versuche == 0) {
					throw new Exception("Wird wohl nichts?");
				} else {
//					System.out.println(ssi.getModule());
//					System.out.println(ssi.getStatus());
//					System.out.println(ssi.getStage());
					System.out.println(ssi.getModule() + " -> " + ssi.getStatus() + " -> " + ssi.getStage());
					Thread.sleep(10);
				}
			}
		}
	}

	public static SipStatusInfo getSIPStatusInfo(String sipId, String rosettaInstance) throws Exception {
		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
		final String institution = Custom.getInstitution(rosettaInstance);
		final String userName = Custom.getUsername(rosettaInstance);
		final String password = Custom.getPassword(rosettaInstance);
		final String sip_WSDL_URL = Custom.getSip_WSDL_URL(rosettaURL);

		SipWebServices_Service sWs = new SipWebServices_Service(new URL(sip_WSDL_URL),
				new QName("http://dps.exlibris.com/", "SipWebServices"));
		sWs.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));

		return sWs.getSipWebServicesPort().getSIPStatusInfo(sipId, true);
	}

	@Deprecated
	public static String getDepositActivityBySubmitDate(String depositId, String date, String rosettaInstance)
			throws Exception {
		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
		final String institution = Custom.getInstitution(rosettaInstance);
		final String userName = Custom.getUsername(rosettaInstance);
		final String password = Custom.getPassword(rosettaInstance);
		final String deposit_WSDL_URL = Custom.getDeposit_WSDL_URL(rosettaURL);

		DepositWebServices_Service dWs = new DepositWebServices_Service(new URL(deposit_WSDL_URL),
				new QName("http://dps.exlibris.com/", "DepositWebServices"));
		dWs.setHandlerResolver(new HeaderHandlerResolver(userName, password, institution));

		String retXml = dWs.getDepositWebServicesPort().getDepositActivityBySubmitDate(null, "All", null, null, date,
				date, "1", "1000");
		Node retDoc = XmlHelper.parse(retXml);
		XmlHelper.removeEmptyNodes(retDoc);
		Node deposit_activity_list = retDoc.getFirstChild();
		String is_error = XmlHelper.getFirstChildByName(deposit_activity_list, "is_error").getTextContent();
		if (is_error.contentEquals("true")) {
			throw new Exception("Es gab einen Fehler:\n" + retXml);
		}
		int total_records = Integer
				.parseInt(XmlHelper.getFirstChildByName(deposit_activity_list, "total_records").getTextContent());
		if (total_records < 1 || total_records > 999) {
			throw new Exception("Unerwartete Anzahl an Rückgabewerte:\n" + retXml);
		}
		List<Node> records = XmlHelper
				.asList(XmlHelper.getFirstChildByName(deposit_activity_list, "records").getChildNodes());
		for (Node record : records) {
			String deposit_activity_id = XmlHelper.getFirstChildByName(record, "deposit_activity_id").getTextContent();
			if (deposit_activity_id.contentEquals(depositId)) {
				return XmlHelper.getFirstChildByName(record, "status").getTextContent();
			}
		}
		throw new Exception("depositId nicht gefunden:\n" + retXml);
	}

	public static String submitDepositActivity(String subDirectoryName, String materialFlowId, String producerId,
			String rosettaInstance) throws Exception {
		final String rosettaURL = Custom.getRosettaURL(rosettaInstance);
		final String institution = Custom.getInstitution(rosettaInstance);
		final String userName = Custom.getUsername(rosettaInstance);
		final String password = Custom.getPassword(rosettaInstance);
		final String deposit_WSDL_URL = Custom.getDeposit_WSDL_URL(rosettaURL);

		DepositWebServices_Service dWs = new DepositWebServices_Service(new URL(deposit_WSDL_URL),
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
