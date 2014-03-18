package com.jayway.snmpblogg; 
//--AgentGen BEGIN=_BEGIN
//--AgentGen END

import java.util.HashMap;

import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.log.LogAdapter;
import org.snmp4j.log.LogFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;


//--AgentGen BEGIN=_IMPORT
//--AgentGen END

public class StatusMIB 
//--AgentGen BEGIN=_EXTENDS
//--AgentGen END
implements MOGroup 
//--AgentGen BEGIN=_IMPLEMENTS
//--AgentGen END
{

  private static final LogAdapter LOGGER = 
      LogFactory.getLogger(StatusMIB.class);

//--AgentGen BEGIN=_STATIC
//--AgentGen END

  // Factory
  private MOFactory moFactory = 
    DefaultMOFactory.getInstance();

  // Constants 

  /**
   * OID of this MIB module for usage which can be 
   * used for its identification.
   */
  public static final OID oidStatusMib =
    new OID(new int[] { 1,3,6,1,2,1,118,1 });

  // Identities
  // Scalars
  public static final OID oidSinglepointIdentityRouterStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,1,0 });
  public static final OID oidServiceStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,2,0 });
  public static final OID oidServiceRunning = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,3,0 });
  public static final OID oidServicePaused = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,4,0 });
  public static final OID oidServiceStopped = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,5,0 });
  public static final OID oidMemoryStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,6,0 });
  public static final OID oidMemoryMaximum = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,7,0 });
  public static final OID oidMemoryUsed = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,8,0 });
  public static final OID oidMemoryFree = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,9,0 });
  public static final OID oidFilesystemStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,10,0 });
  public static final OID oidFilesystemTotal = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,11,0 });
  public static final OID oidFilesystemUsable = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,12,0 });
  public static final OID oidSessionStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,13,0 });
  public static final OID oidSessionTotal = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,14,0 });
  public static final OID oidSessionRejected = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,15,0 });
  public static final OID oidSimplelinkStatus = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,16,0 });
  public static final OID oidSimplelinkVpn = 
    new OID(new int[] { 1,3,6,1,2,1,118,1,17,0 });
  
  private static HashMap<String, OID> oidMap = new HashMap<String, OID>();
  static{
	  oidMap.put("singlepointIdentityRouterStatus", oidSinglepointIdentityRouterStatus);
	  oidMap.put("serviceStatus", oidServiceStatus);
	  oidMap.put("serviceRunning", oidServiceRunning);
	  oidMap.put("servicePaused", oidServicePaused);
	  oidMap.put("serviceStopped", oidServiceStopped);
	  oidMap.put("memoryStatus", oidMemoryStatus);
	  oidMap.put("memoryMaximum", oidMemoryMaximum);
	  oidMap.put("memoryUsed", oidMemoryUsed);
	  oidMap.put("memoryFree", oidMemoryFree);
	  oidMap.put("filesystemStatus", oidFilesystemStatus);
	  oidMap.put("filesystemTotal", oidFilesystemTotal);
	  oidMap.put("filesystemUsable", oidFilesystemUsable);
	  oidMap.put("sessionStatus", oidSessionStatus);
	  oidMap.put("sessionTotal", oidSessionTotal);
	  oidMap.put("sessionRejected", oidSessionRejected);
	  oidMap.put("simplelinkStatus", oidSimplelinkStatus);
	  oidMap.put("simplelinkVpn", oidSimplelinkVpn);
  }
  public static OID getOID(String key){
	  return oidMap.get(key);
  }
  // Tables

  // Notifications

  // Enumerations




  // TextualConventions

  // Scalars
  private MOScalar<OctetString> singlepointIdentityRouterStatus;
  private MOScalar<OctetString> serviceStatus;
  private MOScalar<OctetString> serviceRunning;
  private MOScalar<OctetString> servicePaused;
  private MOScalar<OctetString> serviceStopped;
  private MOScalar<OctetString> memoryStatus;
  private MOScalar<OctetString> memoryMaximum;
  private MOScalar<OctetString> memoryUsed;
  private MOScalar<OctetString> memoryFree;
  private MOScalar<OctetString> filesystemStatus;
  private MOScalar<OctetString> filesystemTotal;
  private MOScalar<OctetString> filesystemUsable;
  private MOScalar<OctetString> sessionStatus;
  private MOScalar<OctetString> sessionTotal;
  private MOScalar<OctetString> sessionRejected;
  private MOScalar<OctetString> simplelinkStatus;
  private MOScalar<OctetString> simplelinkVpn;

  // Tables


//--AgentGen BEGIN=_MEMBERS
//--AgentGen END

  /**
   * Constructs a StatusMIB instance without actually creating its
   * <code>ManagedObject</code> instances. This has to be done in a
   * sub-class constructor or after construction by calling 
   * {@link #createMO(MOFactory moFactory)}. 
   */
  protected StatusMIB() {
//--AgentGen BEGIN=_DEFAULTCONSTRUCTOR
//--AgentGen END
  }

  /**
   * Constructs a StatusMIB instance and actually creates its
   * <code>ManagedObject</code> instances using the supplied 
   * <code>MOFactory</code> (by calling
   * {@link #createMO(MOFactory moFactory)}).
   * @param moFactory
   *    the <code>MOFactory</code> to be used to create the
   *    managed objects for this module.
   */
  public StatusMIB(HashMap<String, String> m) {
  	this();
    createMO( m);
//--AgentGen BEGIN=_FACTORYCONSTRUCTOR
//--AgentGen END
  }

//--AgentGen BEGIN=_CONSTRUCTORS
//--AgentGen END

  /**
   * Create the ManagedObjects defined for this MIB module
   * using the specified {@link MOFactory}.
   * @param moFactory
   *    the <code>MOFactory</code> instance to use for object 
   *    creation.
   */
  @SuppressWarnings("unchecked")
protected void createMO(HashMap<String, String> m) {
    addTCsToFactory(moFactory);
    singlepointIdentityRouterStatus = 
      moFactory.createScalar(oidSinglepointIdentityRouterStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("singlepointIdentityRouterStatus")));
    serviceStatus = 
      moFactory.createScalar(oidServiceStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("serviceStatus")));
    serviceRunning = 
      moFactory.createScalar(oidServiceRunning,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("serviceRunning")));
    servicePaused = 
      moFactory.createScalar(oidServicePaused,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("servicePaused")));
    serviceStopped = 
      moFactory.createScalar(oidServiceStopped,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("serviceStopped")));
    memoryStatus = 
      moFactory.createScalar(oidMemoryStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("memoryStatus")));
    memoryMaximum = 
      moFactory.createScalar(oidMemoryMaximum,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("memoryMaximum")));
    memoryUsed = 
      moFactory.createScalar(oidMemoryUsed,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("memoryUsed")));
    memoryFree = 
      moFactory.createScalar(oidMemoryFree,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("memoryFree")));
    filesystemStatus = 
      moFactory.createScalar(oidFilesystemStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("filesystemStatus")));
    filesystemTotal = 
      moFactory.createScalar(oidFilesystemTotal,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("filesystemTotal")));
    filesystemUsable = 
      moFactory.createScalar(oidFilesystemUsable,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("filesystemUsable")));
    sessionStatus = 
      moFactory.createScalar(oidSessionStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("sessionStatus")));
    sessionTotal = 
      moFactory.createScalar(oidSessionTotal,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("sessionTotal")));
    sessionRejected = 
      moFactory.createScalar(oidSessionRejected,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("sessionRejected")));
    simplelinkStatus = 
      moFactory.createScalar(oidSimplelinkStatus,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("simplelinkStatus")));
    simplelinkVpn = 
      moFactory.createScalar(oidSimplelinkVpn,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new OctetString(m.get("simplelinkVpn")));
  }

  public MOScalar<OctetString> getSinglepointIdentityRouterStatus() {
    return singlepointIdentityRouterStatus;
  }
  public MOScalar<OctetString> getServiceStatus() {
    return serviceStatus;
  }
  public MOScalar<OctetString> getServiceRunning() {
    return serviceRunning;
  }
  public MOScalar<OctetString> getServicePaused() {
    return servicePaused;
  }
  public MOScalar<OctetString> getServiceStopped() {
    return serviceStopped;
  }
  public MOScalar<OctetString> getMemoryStatus() {
    return memoryStatus;
  }
  public MOScalar<OctetString> getMemoryMaximum() {
    return memoryMaximum;
  }
  public MOScalar<OctetString> getMemoryUsed() {
    return memoryUsed;
  }
  public MOScalar<OctetString> getMemoryFree() {
    return memoryFree;
  }
  public MOScalar<OctetString> getFilesystemStatus() {
    return filesystemStatus;
  }
  public MOScalar<OctetString> getFilesystemTotal() {
    return filesystemTotal;
  }
  public MOScalar<OctetString> getFilesystemUsable() {
    return filesystemUsable;
  }
  public MOScalar<OctetString> getSessionStatus() {
    return sessionStatus;
  }
  public MOScalar<OctetString> getSessionTotal() {
    return sessionTotal;
  }
  public MOScalar<OctetString> getSessionRejected() {
    return sessionRejected;
  }
  public MOScalar<OctetString> getSimplelinkStatus() {
    return simplelinkStatus;
  }
  public MOScalar<OctetString> getSimplelinkVpn() {
    return simplelinkVpn;
  }




  public void registerMOs(MOServer server, OctetString context) 
    throws DuplicateRegistrationException 
  {
    // Scalar Objects
    server.register(this.singlepointIdentityRouterStatus, context);
    server.register(this.serviceStatus, context);
    server.register(this.serviceRunning, context);
    server.register(this.servicePaused, context);
    server.register(this.serviceStopped, context);
    server.register(this.memoryStatus, context);
    server.register(this.memoryMaximum, context);
    server.register(this.memoryUsed, context);
    server.register(this.memoryFree, context);
    server.register(this.filesystemStatus, context);
    server.register(this.filesystemTotal, context);
    server.register(this.filesystemUsable, context);
    server.register(this.sessionStatus, context);
    server.register(this.sessionTotal, context);
    server.register(this.sessionRejected, context);
    server.register(this.simplelinkStatus, context);
    server.register(this.simplelinkVpn, context);
//--AgentGen BEGIN=_registerMOs
//--AgentGen END
  }

  public void unregisterMOs(MOServer server, OctetString context) {
    // Scalar Objects
    server.unregister(this.singlepointIdentityRouterStatus, context);
    server.unregister(this.serviceStatus, context);
    server.unregister(this.serviceRunning, context);
    server.unregister(this.servicePaused, context);
    server.unregister(this.serviceStopped, context);
    server.unregister(this.memoryStatus, context);
    server.unregister(this.memoryMaximum, context);
    server.unregister(this.memoryUsed, context);
    server.unregister(this.memoryFree, context);
    server.unregister(this.filesystemStatus, context);
    server.unregister(this.filesystemTotal, context);
    server.unregister(this.filesystemUsable, context);
    server.unregister(this.sessionStatus, context);
    server.unregister(this.sessionTotal, context);
    server.unregister(this.sessionRejected, context);
    server.unregister(this.simplelinkStatus, context);
    server.unregister(this.simplelinkVpn, context);
//--AgentGen BEGIN=_unregisterMOs
//--AgentGen END
  }

  // Notifications

  // Scalars

  // Value Validators


  // Rows and Factories


//--AgentGen BEGIN=_METHODS
//--AgentGen END

  // Textual Definitions of MIB module StatusMIB
  protected void addTCsToFactory(MOFactory moFactory) {
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_BEGIN
//--AgentGen END

  // Textual Definitions of other MIB modules
  public void addImportedTCsToFactory(MOFactory moFactory) {
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_END
//--AgentGen END

//--AgentGen BEGIN=_CLASSES
//--AgentGen END

//--AgentGen BEGIN=_END
//--AgentGen END
}


