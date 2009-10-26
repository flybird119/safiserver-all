/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.safi.asterisk.saflet.impl;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;

import com.safi.asterisk.Call;
import com.safi.asterisk.CallSource1;
import com.safi.asterisk.CallSource2;
import com.safi.asterisk.saflet.AsteriskSaflet;
import com.safi.asterisk.saflet.SafletPackage;
import com.safi.core.actionstep.ActionStep;
import com.safi.core.saflet.impl.SafletImpl;
import com.safi.db.manager.DBManagerException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Asterisk Saflet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AsteriskSafletImpl extends SafletImpl implements AsteriskSaflet {
  
  private final static Logger log = Logger.getLogger(AsteriskSafletImpl.class);
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AsteriskSafletImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return SafletPackage.Literals.ASTERISK_SAFLET;
  }

  @Override
  public String getUniqueCallName(String prefix) {
    int max = 1;
    com.safi.core.initiator.Initiator init = getInitiator();
    if (init instanceof CallSource1) {
      Call call = ((CallSource1) init).getNewCall1();
      if (call != null) {
        String sn = call.getName();
        max = Math.max(max, getNextNameSeq(sn));
      }

      if (init instanceof CallSource2) {
        Call call2 = ((CallSource2) init).getNewCall2();
        if (call2 != null) {
          String sn = call2.getName();
          max = Math.max(max, getNextNameSeq(sn));
        }
      }
    }

    for (ActionStep ts : getActionsteps()) {
      if (ts instanceof CallSource1) {
        Call call = ((CallSource1) ts).getNewCall1();
        if (call != null) {
          String sn = call.getName();
          max = Math.max(max, getNextNameSeq(sn));
        }

        if (ts instanceof CallSource2) {
          Call call2 = ((CallSource2) ts).getNewCall2();
          if (call2 != null) {
            String sn = call2.getName();
            max = Math.max(max, getNextNameSeq(sn));
          }
        }

      }
    }
    return prefix + max;
  }

  public int getNextNameSeq(String callName) {
    int idx = callName.length() - 1;
    while (idx >= 0 && Character.isDigit(callName.charAt(idx))) {
      --idx;
    }
    if ((idx + 1) <= (callName.length())) {
      try {
        String seq = callName.substring(idx + 1, callName.length());
        return Integer.parseInt(seq) + 1;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 1;
  }

  @Override
  public String getPromptPathByName(String name) throws DBManagerException {
    // TODO Auto-generated method stub
    return null;
  }
} //AsteriskSafletImpl
