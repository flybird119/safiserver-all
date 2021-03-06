package com.safi.asterisk.handler;

import java.util.LinkedHashSet;

import org.hsqldb.ServerConstants;
import org.hsqldb.persist.HsqlProperties;

public class SafiResourceManager {

  private static final String DEFAULT_DB_PROPS_STRING = "sql.tx_no_multi_rewrite=true;sql.enforce_strict_size=true";
  private org.hsqldb.Server dbServer;
  private String bindAddress = ServerConstants.SC_DEFAULT_ADDRESS;
  private int port = ServerConstants.SC_DEFAULT_HSQL_SERVER_PORT;
  private LinkedHashSet<String> databases = new LinkedHashSet<String>();

  public void startServer() throws ResourceManagerException {
    if (dbServer == null)
     throw new ResourceManagerException("Resource Manager has not been initialized!");
    if (isRunning())
      return;
    dbServer.start();
  }
  
  public void initServer(){
    dbServer = createDBServer();
  }

  public boolean isRunning() {
    return dbServer != null && (dbServer.getState() == ServerConstants.SERVER_STATE_ONLINE
        || (dbServer.getState() == ServerConstants.SERVER_STATE_OPENING));
  }

  private org.hsqldb.Server createDBServer() {
    org.hsqldb.Server dbServer = new org.hsqldb.Server();
    dbServer.setPort(port);
    dbServer.setAddress(bindAddress);
    HsqlProperties props = new HsqlProperties();
    props.setProperty("sql.tx_no_multi_rewrite", false);
    dbServer.setProperties(props);
    return dbServer;

  }

  public boolean addDatabase(String name, String path) {
    if (!isRunning() && dbServer != null) {
      synchronized (databases) {
        if (databases.add(name)) {
          dbServer.setDatabaseName(databases.size() - 1, name);
          dbServer.setDatabasePath(databases.size() - 1, path+';'+DEFAULT_DB_PROPS_STRING);
         
          return true;
        }
      }
    }
    return false;
  }

//  public boolean removeDatabase(String path) {
//    if (!isRunning() && dbServer != null) {
//      synchronized (databases) {
//        int index = indexOf(path);
//        if (index >= 0) {
//          dbServer.setDatabasePath(index, null);
//          return true;
//        }
//      }
//    }
//    return false;
//  }
  
  private int indexOf(String path){
    int i = 0, index = -1;

    for (String db : databases) {
      if (path.equals(db)) {
        index = i;
        break;
      }
      ++i;
    }
    return index;
  }

  public String getBindAddress() {
    return bindAddress;
  }

  public void setBindAddress(String bindAddress) {
    this.bindAddress = bindAddress;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    SafiResourceManager manager = new SafiResourceManager();
    manager.initServer();
    manager.addDatabase("test","file:./test");
    try {
      manager.startServer();
    } catch (ResourceManagerException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    try {
      Class.forName("org.hsqldb.jdbcDriver");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
}
