/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.safi.asterisk.actionstep.impl;

import org.apache.commons.lang.StringUtils;
import org.asteriskjava.fastagi.AgiChannel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.safi.asterisk.AsteriskPackage;
import com.safi.asterisk.Call;
import com.safi.asterisk.CallConsumer1;
import com.safi.asterisk.actionstep.ActionstepPackage;
import com.safi.asterisk.actionstep.MeetMeCount;
import com.safi.core.actionstep.ActionStepException;
import com.safi.core.actionstep.DynamicValue;
import com.safi.core.actionstep.impl.ActionStepImpl;
import com.safi.core.actionstep.util.VariableTranslator;
import com.safi.core.saflet.SafletContext;
import com.safi.core.saflet.SafletEnvironment;
import com.safi.db.Variable;
import com.safi.db.VariableScope;
import com.safi.db.VariableType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Meet Me Count</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.safi.asterisk.actionstep.impl.MeetMeCountImpl#getCall1 <em>Call1</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.MeetMeCountImpl#getConferenceNumber <em>Conference Number</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.MeetMeCountImpl#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MeetMeCountImpl extends ActionStepImpl implements MeetMeCount {
  
  private static final String MEETME_COUNT_VARNAME = "MEETME_COUNT";
  /**
   * The cached value of the '{@link #getCall1() <em>Call1</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCall1()
   * @generated
   * @ordered
   */
  protected Call call1;

  /**
   * The cached value of the '{@link #getConferenceNumber() <em>Conference Number</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConferenceNumber()
   * @generated
   * @ordered
   */
  protected DynamicValue conferenceNumber;

  /**
   * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariableName()
   * @generated
   * @ordered
   */
  protected DynamicValue variableName;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MeetMeCountImpl() {
    super();
  }

  @Override
  public void beginProcessing(SafletContext context) throws ActionStepException {
    super.beginProcessing(context);
    Exception exception = null;
    if (call1 == null || call1.getChannel() == null) {
      exception = new ActionStepException(call1 == null ? "No current call found"
          : "No channel found in current context");
    } else {
      AgiChannel channel = call1.getChannel();
      try {

        String conferenceNum = (String) VariableTranslator.translateValue(VariableType.TEXT,
            resolveDynamicValue(conferenceNumber, context));
        if (debugLog.isDebugEnabled())
          debug("Getting meetme count for conference: " + conferenceNum);
        if (StringUtils.isBlank(conferenceNum)) {
          exception = new ActionStepException("Conference number is required for MeetMeCount");
        } else {

          Variable v = resolveVariableFromName(variableName, context);

          StringBuffer appCmd = new StringBuffer();
          appCmd.append(conferenceNum);
          if (v != null) {
            appCmd.append('|').append(MEETME_COUNT_VARNAME);
          }

          int result = channel.exec("MeetMeCount", appCmd.toString());

          if (debugLog.isDebugEnabled())
            debug("MeetMeCount returned " + translateAppReturnValue(result) + " of int "
                + result);

          if (result == -2) {
            exception = new ActionStepException("Application MeetMeCount not found");
          } else if (result == -1) {
            exception = new ActionStepException("Channel was hung up");
            // context.addException(exception);
            // return;
          } else if (v != null) {
            String count = channel.getVariable(MEETME_COUNT_VARNAME);
            if (debugLog.isDebugEnabled()) {
              debug("MEET_ME_COUNT var was " + count);
            }
            int meetmeCount = -1;
            if (count != null) {
              try {
                meetmeCount = Integer.parseInt(count);

                if (debugLog.isDebugEnabled()) {
                  debug("MeetMe count was " + meetmeCount);
                }
                
              } catch (NumberFormatException e) {
              }
            }
            
            if (v.getScope() != VariableScope.GLOBAL)
              context.setVariableRawValue(v.getName(), VariableTranslator.translateValue(v.getType(),
                  meetmeCount));
            else {
              SafletEnvironment env = getSaflet().getSafletEnvironment();
              env.setGlobalVariableValue(v.getName(), VariableTranslator.translateValue(v.getType(),
                  meetmeCount));
            }
          }
        }
      } catch (Exception e) {
        exception = e;
      }
    }

    if (exception != null) {
      handleException(context, exception);
      return;
    }
    handleSuccess(context);

  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ActionstepPackage.Literals.MEET_ME_COUNT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Call getCall1() {
    if (call1 != null && call1.eIsProxy()) {
      InternalEObject oldCall1 = (InternalEObject)call1;
      call1 = (Call)eResolveProxy(oldCall1);
      if (call1 != oldCall1) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionstepPackage.MEET_ME_COUNT__CALL1, oldCall1, call1));
      }
    }
    return call1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Call basicGetCall1() {
    return call1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCall1(Call newCall1) {
    Call oldCall1 = call1;
    call1 = newCall1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.MEET_ME_COUNT__CALL1, oldCall1, call1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DynamicValue getConferenceNumber() {
    return conferenceNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConferenceNumber(DynamicValue newConferenceNumber, NotificationChain msgs) {
    DynamicValue oldConferenceNumber = conferenceNumber;
    conferenceNumber = newConferenceNumber;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER, oldConferenceNumber, newConferenceNumber);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConferenceNumber(DynamicValue newConferenceNumber) {
    if (newConferenceNumber != conferenceNumber) {
      NotificationChain msgs = null;
      if (conferenceNumber != null)
        msgs = ((InternalEObject)conferenceNumber).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER, null, msgs);
      if (newConferenceNumber != null)
        msgs = ((InternalEObject)newConferenceNumber).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER, null, msgs);
      msgs = basicSetConferenceNumber(newConferenceNumber, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER, newConferenceNumber, newConferenceNumber));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DynamicValue getVariableName() {
    return variableName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetVariableName(DynamicValue newVariableName, NotificationChain msgs) {
    DynamicValue oldVariableName = variableName;
    variableName = newVariableName;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME, oldVariableName, newVariableName);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVariableName(DynamicValue newVariableName) {
    if (newVariableName != variableName) {
      NotificationChain msgs = null;
      if (variableName != null)
        msgs = ((InternalEObject)variableName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME, null, msgs);
      if (newVariableName != null)
        msgs = ((InternalEObject)newVariableName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME, null, msgs);
      msgs = basicSetVariableName(newVariableName, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME, newVariableName, newVariableName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER:
        return basicSetConferenceNumber(null, msgs);
      case ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME:
        return basicSetVariableName(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ActionstepPackage.MEET_ME_COUNT__CALL1:
        if (resolve) return getCall1();
        return basicGetCall1();
      case ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER:
        return getConferenceNumber();
      case ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME:
        return getVariableName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ActionstepPackage.MEET_ME_COUNT__CALL1:
        setCall1((Call)newValue);
        return;
      case ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER:
        setConferenceNumber((DynamicValue)newValue);
        return;
      case ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME:
        setVariableName((DynamicValue)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case ActionstepPackage.MEET_ME_COUNT__CALL1:
        setCall1((Call)null);
        return;
      case ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER:
        setConferenceNumber((DynamicValue)null);
        return;
      case ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME:
        setVariableName((DynamicValue)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case ActionstepPackage.MEET_ME_COUNT__CALL1:
        return call1 != null;
      case ActionstepPackage.MEET_ME_COUNT__CONFERENCE_NUMBER:
        return conferenceNumber != null;
      case ActionstepPackage.MEET_ME_COUNT__VARIABLE_NAME:
        return variableName != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == CallConsumer1.class) {
      switch (derivedFeatureID) {
        case ActionstepPackage.MEET_ME_COUNT__CALL1: return AsteriskPackage.CALL_CONSUMER1__CALL1;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == CallConsumer1.class) {
      switch (baseFeatureID) {
        case AsteriskPackage.CALL_CONSUMER1__CALL1: return ActionstepPackage.MEET_ME_COUNT__CALL1;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //MeetMeCountImpl