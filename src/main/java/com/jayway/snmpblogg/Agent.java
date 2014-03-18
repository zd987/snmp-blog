package com.jayway.snmpblogg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.TransportDomains;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.log.Log4jLogFactory;
import org.snmp4j.log.LogAdapter;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.TransportMappings;

/**
 * This Agent contains mimimal functionality for running a version 2c snmp
 * agent.
 * 
 * 
 * @author johanrask
 * 
 */
public class Agent extends BaseAgent {

	// not needed but very useful of course
	static {
		LogFactory.setLogFactory(new Log4jLogFactory());
	}
	private static final LogAdapter logger =
		      LogFactory.getLogger(Agent.class);
	private String address;
	private String statusServletURL = "https://sso.rudimentary.us:9786/status";
	private String trapDestination = "192.168.144.128/162";
	private OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");

	public Agent(String address) throws IOException {

		// These files does not exist and are not used but has to be specified
		// Read snmp4j docs for more info
		super(new File("conf.agent"), new File("bootCounter.agent"),
				new CommandProcessor(
						new OctetString(MPv3.createLocalEngineID())));
		this.address = address;
	}

	/**
	 * We let clients of this agent register the MO they
	 * need so this method does nothing
	 */
	@Override
	protected void registerManagedObjects() {
		
	}

	/**
	 * Clients can register the MO they need
	 */
	public void registerManagedObject(ManagedObject mo) {
		try {
			server.register(mo, null);
		} catch (DuplicateRegistrationException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void registerManagedObject(MOGroup moGroup) {
		try {
			moGroup.registerMOs(server, null);
		} catch (DuplicateRegistrationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void unregisterManagedObject(MOGroup moGroup) {
		moGroup.unregisterMOs(server, getContext(moGroup));
	}

	/*
	 * Empty implementation
	 */
	@Override
	protected void addNotificationTargets(SnmpTargetMIB targetMIB,
			SnmpNotificationMIB notificationMIB) {
		targetMIB.addDefaultTDomains();

	    targetMIB.addTargetAddress(new OctetString("notificationV2c"),
	                               TransportDomains.transportDomainUdpIpv4,
	                               new OctetString(new UdpAddress(this.trapDestination).getValue()),
	                               200, 1,
	                               new OctetString("notify"),
	                               new OctetString("v2c"),
	                               StorageType.permanent);
	    targetMIB.addTargetParams(new OctetString("v2c"),
	                              MessageProcessingModel.MPv2c,
	                              SecurityModel.SECURITY_MODEL_SNMPv2c,
	                              new OctetString("cpublic"),
	                              SecurityLevel.NOAUTH_NOPRIV,
	                              StorageType.permanent);
	    notificationMIB.addNotifyEntry(new OctetString("default"),
	                                   new OctetString("notify"),
	                                   SnmpNotificationMIB.SnmpNotifyTypeEnum.inform,
	                                   StorageType.permanent);
	}

	/**
	 * Minimal View based Access Control
	 * 
	 * http://www.faqs.org/rfcs/rfc2575.html
	 */
	@Override
	protected void addViews(VacmMIB vacm) {
		vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new OctetString(
				"cpublic"), new OctetString("v1v2group"),
				StorageType.nonVolatile);

		vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
				SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
				MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
				new OctetString("fullWriteView"), new OctetString(
						"fullNotifyView"), StorageType.nonVolatile);
		
		vacm.addAccess(new OctetString("v1v2group"), new OctetString(),
                SecurityModel.SECURITY_MODEL_ANY,
                SecurityLevel.NOAUTH_NOPRIV,
                MutableVACM.VACM_MATCH_EXACT,
                new OctetString("fullReadView"),
                new OctetString("fullWriteView"),
                new OctetString("fullNotifyView"),
                StorageType.nonVolatile);

		vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
				new OctetString(), VacmMIB.vacmViewIncluded,
				StorageType.nonVolatile);

	    vacm.addViewTreeFamily(new OctetString("fullNotifyView"), new OID("1.3"),
	                           new OctetString(), VacmMIB.vacmViewIncluded,
	                           StorageType.nonVolatile);
	}

	/**
	 * User based Security Model, only applicable to
	 * SNMP v.3
	 * 
	 */
	protected void addUsmUser(USM usm) {
	}

	protected void initTransportMappings() throws IOException {
		transportMappings = new TransportMapping[1];
		Address addr = GenericAddress.parse(address);
		TransportMapping tm = TransportMappings.getInstance()
				.createTransportMapping(addr);
		transportMappings[0] = tm;
	}

	/**
	 * Start method invokes some initialization methods needed to
	 * start the agent
	 * @throws IOException
	 */
	public void start() throws IOException {

		init();
		// This method reads some old config from a file and causes
		// unexpected behavior.
		// loadConfig(ImportModes.REPLACE_CREATE); 
		addShutdownHook();
		getServer().addContext(new OctetString("public"));
		finishInit();
		run();
		sendColdStartNotification();
	}
	

	
	protected void unregisterManagedObjects() {
		// here we should unregister those objects previously registered...
	}

	/**
	 * The table of community strings configured in the SNMP
	 * engine's Local Configuration Datastore (LCD).
	 * 
	 * We only configure one, "public".
	 */
	protected void addCommunities(SnmpCommunityMIB communityMIB) {
		Variable[] com2sec = new Variable[] { 
				new OctetString("public"), // community name
				new OctetString("cpublic"), // security name
				getAgent().getContextEngineID(), // local engine ID
				new OctetString("public"), // default context name
				new OctetString(), // transport tag
				new Integer32(StorageType.nonVolatile), // storage type
				new Integer32(RowStatus.active) // row status
		};
		MOTableRow row = communityMIB.getSnmpCommunityEntry().createRow(
				new OctetString("public2public").toSubIndex(true), com2sec);
		communityMIB.getSnmpCommunityEntry().addRow(row);
	}
	
	public HashMap<String, String> updateMIB() throws Exception{
		logger.info("update MIB");
		SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	            builder.build());
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
	            sslsf).build();

	    HttpGet httpGet = new HttpGet(this.statusServletURL);
	    CloseableHttpResponse response = httpclient.execute(httpGet);
	    String html = null;
	    try {
	        //System.out.println(response.getStatusLine());
	        HttpEntity entity = response.getEntity();
	        html = EntityUtils.toString(entity);
	    }
	    finally {
	        response.close();
	    }
		HashMap<String, String> m = new HashMap<String, String>();
	    if(html == null) return null;
		Document doc = Jsoup.parse(html);
		String[] rows = doc.select("pre").first().html().split("<br />");
		int i, j, k;
		String prefix = "";
		boolean trap = false;
		ArrayList<VariableBinding> list = new ArrayList<VariableBinding>();
		for(i = 0; i <rows.length; ++i){
			String row = rows[i].trim();
			if(row.length() == 0) continue;
			String[] elems = row.split(":");
			if(elems.length != 2) continue;
			String key = elems[0].trim();
			String value = elems[1].trim();
			String nkey = "";
			if(key.equals("SINGLEPOINT IDENTITY ROUTER STATUS")){
				nkey = "singlepointIdentityRouterStatus";
				prefix = "singlepointIdentityRouter";
				trap = !value.equals("OK");
			} else if(key.equals("SERVICE STATUS")){
				nkey = "serviceStatus";
				prefix = "service";
				trap = !value.equals("OK");
			} else if(key.equals("MEMORY STATUS")){
				nkey = "memoryStatus";
				prefix = "memory";
				trap = !value.equals("OK");
			} else if(key.equals("FILE SYSTEM STATUS")){
				nkey = "filesystemStatus";
				prefix = "filesystem";
				trap = !value.equals("OK");
			} else if(key.equals("SESSION STATUS")){
				nkey = "sessionStatus";
				prefix = "session";
				trap = !value.equals("OK");
			}  else if(key.equals("SIMPLELINK STATUS")){
				nkey = "simplelinkStatus";
				prefix = "simplelink";
				value = "TEST ERROR";
				trap = !value.equals("OK");
			} else {
				nkey = prefix + key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();
			}
			m.put(nkey, value);
			if(trap){
				VariableBinding vb = new VariableBinding(StatusMIB.getOID(nkey), new OctetString(value));
				list.add(vb);
			}
		}
		VariableBinding[] vbs = new VariableBinding[1];
		if(list.size() > 0) {
			logger.info("send notification");
			this.getNotificationOriginator().notify(new OctetString("public"), this.sysDescr, list.toArray(vbs));
		}
		return m;
	}

	public static void main(String[] args) throws Exception {
		Agent agent = new Agent("0.0.0.0/161");
		if(args.length == 2){
			agent.statusServletURL = args[0];
			agent.trapDestination = args[1];
		}
		agent.start();
		//OID interfacesTable = new OID(".1.3.6.1.2.1.2.2.1");
		// Since BaseAgent registers some mibs by default we need to unregister
		// one before we register our own sysDescr. Normally you would
		// override that method and register the mibs that you need
		agent.unregisterManagedObject(agent.getSnmpv2MIB());
		agent.registerManagedObject(
				MOScalarFactory.createReadOnly(agent.sysDescr,"SinglePoint Identity Router Status"));
		HashMap<String, String> m = agent.updateMIB();
		StatusMIB statusMIB = new StatusMIB(m);
		agent.registerManagedObject(statusMIB);
		while(true) {
			try {
				m = agent.updateMIB();
				agent.unregisterManagedObject(statusMIB);
				statusMIB = new StatusMIB(m);
				agent.registerManagedObject(statusMIB);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.sleep(5000);
		}
	}
}
