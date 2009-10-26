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
import com.safi.asterisk.actionstep.Voicemail;
import com.safi.core.actionstep.ActionStepException;
import com.safi.core.actionstep.ActionStepFactory;
import com.safi.core.actionstep.DynamicValue;
import com.safi.core.actionstep.Output;
import com.safi.core.actionstep.OutputType;
import com.safi.core.actionstep.impl.ActionStepImpl;
import com.safi.core.actionstep.util.VariableTranslator;
import com.safi.core.saflet.SafletContext;
import com.safi.db.VariableType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Voicemail</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#getCall1 <em>Call1</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#getMailbox <em>Mailbox</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#isSkipInstructions <em>Skip Instructions</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#isPlayUnavailableMessage <em>Play Unavailable Message</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#isPlayBusyMessage <em>Play Busy Message</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.VoicemailImpl#getRecordingGain <em>Recording Gain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VoicemailImpl extends ActionStepImpl implements Voicemail {
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
   * The cached value of the '{@link #getMailbox() <em>Mailbox</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMailbox()
   * @generated
   * @ordered
   */
  protected DynamicValue mailbox;

  /**
   * The default value of the '{@link #isSkipInstructions() <em>Skip Instructions</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSkipInstructions()
   * @generated
   * @ordered
   */
  protected static final boolean SKIP_INSTRUCTIONS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isSkipInstructions() <em>Skip Instructions</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSkipInstructions()
   * @generated
   * @ordered
   */
  protected boolean skipInstructions = SKIP_INSTRUCTIONS_EDEFAULT;

  /**
   * The default value of the '{@link #isPlayUnavailableMessage() <em>Play Unavailable Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPlayUnavailableMessage()
   * @generated
   * @ordered
   */
  protected static final boolean PLAY_UNAVAILABLE_MESSAGE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isPlayUnavailableMessage() <em>Play Unavailable Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPlayUnavailableMessage()
   * @generated
   * @ordered
   */
  protected boolean playUnavailableMessage = PLAY_UNAVAILABLE_MESSAGE_EDEFAULT;

  /**
   * The default value of the '{@link #isPlayBusyMessage() <em>Play Busy Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPlayBusyMessage()
   * @generated
   * @ordered
   */
  protected static final boolean PLAY_BUSY_MESSAGE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isPlayBusyMessage() <em>Play Busy Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPlayBusyMessage()
   * @generated
   * @ordered
   */
  protected boolean playBusyMessage = PLAY_BUSY_MESSAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getRecordingGain() <em>Recording Gain</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRecordingGain()
   * @generated
   * @ordered
   */
  protected static final int RECORDING_GAIN_EDEFAULT = -1;

  /**
   * The cached value of the '{@link #getRecordingGain() <em>Recording Gain</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRecordingGain()
   * @generated
   * @ordered
   */
  protected int recordingGain = RECORDING_GAIN_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VoicemailImpl() {
    super();
  }

  @Override
  public void beginProcessing(SafletContext context) throws ActionStepException {
    super.beginProcessing(context);
    Exception exception = null;
    int idx = 0;
    if (call1 == null || call1.getChannel() == null) {
      exception = new ActionStepException(call1 == null ? "No current call found"
          : "No channel found in current context");
    } else {
      AgiChannel channel = call1.getChannel();
      try {

        String mb = (String) VariableTranslator.translateValue(VariableType.TEXT,
            resolveDynamicValue(mailbox, context));
        if (debugLog.isDebugEnabled())
          debug("Getting Voicemail for mailbox: " + mb);
        if (StringUtils.isBlank(mb)) {
          exception = new ActionStepException("mailbox is required for Voicemail");
        } else {

          
          StringBuffer appCmd = new StringBuffer();
          appCmd.append(mb);
          if (skipInstructions)
            appCmd.append('|').append('s');
          if (playUnavailableMessage){
//            if (appCmd.length()==0)
              appCmd.append('|');
            appCmd.append('u');
          }
          else
          if (playBusyMessage){
//            if (appCmd.length()==0)
              appCmd.append('|');
            appCmd.append('b');
          }
          
          if (recordingGain != 0){
//            if (appCmd.length()==0)
              appCmd.append('|');
            appCmd.append("g(").append(recordingGain).append(")");
          }
          
          if (debugLog.isDebugEnabled())
            debug("sending VoiceMail "+appCmd);
          
          int result = channel.exec("VoiceMail", appCmd.toString());

          if (debugLog.isDebugEnabled())
            debug("VoiceMail returned " + translateAppReturnValue(result) + " of int "
                + result);

          if (result == -2) {
            exception = new ActionStepException("Application VoiceMail not found");
          } else if (result == -1) {
            exception = new ActionStepException("Channel was hung up");
            // context.addException(exception);
            // return;
          }
          
          String status = channel.getVariable("VMSTATUS");
          if (StringUtils.equalsIgnoreCase("SUCCESS", status))
            idx = 1;
          else
            if (StringUtils.equalsIgnoreCase("USEREXIT", status))
              idx = 2;
            else
              exception = new ActionStepException("Voicemail() returned failure: "+status);
        }
      } catch (Exception e) {
        exception = e;
      }
    }

    if (exception != null) {
      handleException(context, exception);
      return;
    }
    handleSuccess(context, idx);

  }
  

  @Override
  public void createDefaultOutputs() {
    // TODO Auto-generated method stub
    super.createDefaultOutputs();
    Output o = ActionStepFactory.eINSTANCE.createOutput();
    o.setOutputType(OutputType.CHOICE);
    o.setName("userExit");
    setErrorOutput(o);
    getOutputs().add(o);
  }  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ActionstepPackage.Literals.VOICEMAIL;
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionstepPackage.VOICEMAIL__CALL1, oldCall1, call1));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__CALL1, oldCall1, call1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DynamicValue getMailbox() {
    return mailbox;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMailbox(DynamicValue newMailbox, NotificationChain msgs) {
    DynamicValue oldMailbox = mailbox;
    mailbox = newMailbox;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__MAILBOX, oldMailbox, newMailbox);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMailbox(DynamicValue newMailbox) {
    if (newMailbox != mailbox) {
      NotificationChain msgs = null;
      if (mailbox != null)
        msgs = ((InternalEObject)mailbox).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.VOICEMAIL__MAILBOX, null, msgs);
      if (newMailbox != null)
        msgs = ((InternalEObject)newMailbox).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.VOICEMAIL__MAILBOX, null, msgs);
      msgs = basicSetMailbox(newMailbox, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__MAILBOX, newMailbox, newMailbox));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSkipInstructions() {
    return skipInstructions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSkipInstructions(boolean newSkipInstructions) {
    boolean oldSkipInstructions = skipInstructions;
    skipInstructions = newSkipInstructions;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__SKIP_INSTRUCTIONS, oldSkipInstructions, skipInstructions));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isPlayUnavailableMessage() {
    return playUnavailableMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPlayUnavailableMessage(boolean newPlayUnavailableMessage) {
    boolean oldPlayUnavailableMessage = playUnavailableMessage;
    playUnavailableMessage = newPlayUnavailableMessage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__PLAY_UNAVAILABLE_MESSAGE, oldPlayUnavailableMessage, playUnavailableMessage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isPlayBusyMessage() {
    return playBusyMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPlayBusyMessage(boolean newPlayBusyMessage) {
    boolean oldPlayBusyMessage = playBusyMessage;
    playBusyMessage = newPlayBusyMessage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__PLAY_BUSY_MESSAGE, oldPlayBusyMessage, playBusyMessage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getRecordingGain() {
    return recordingGain;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRecordingGain(int newRecordingGain) {
    int oldRecordingGain = recordingGain;
    recordingGain = newRecordingGain;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.VOICEMAIL__RECORDING_GAIN, oldRecordingGain, recordingGain));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ActionstepPackage.VOICEMAIL__MAILBOX:
        return basicSetMailbox(null, msgs);
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
      case ActionstepPackage.VOICEMAIL__CALL1:
        if (resolve) return getCall1();
        return basicGetCall1();
      case ActionstepPackage.VOICEMAIL__MAILBOX:
        return getMailbox();
      case ActionstepPackage.VOICEMAIL__SKIP_INSTRUCTIONS:
        return isSkipInstructions() ? Boolean.TRUE : Boolean.FALSE;
      case ActionstepPackage.VOICEMAIL__PLAY_UNAVAILABLE_MESSAGE:
        return isPlayUnavailableMessage() ? Boolean.TRUE : Boolean.FALSE;
      case ActionstepPackage.VOICEMAIL__PLAY_BUSY_MESSAGE:
        return isPlayBusyMessage() ? Boolean.TRUE : Boolean.FALSE;
      case ActionstepPackage.VOICEMAIL__RECORDING_GAIN:
        return new Integer(getRecordingGain());
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
      case ActionstepPackage.VOICEMAIL__CALL1:
        setCall1((Call)newValue);
        return;
      case ActionstepPackage.VOICEMAIL__MAILBOX:
        setMailbox((DynamicValue)newValue);
        return;
      case ActionstepPackage.VOICEMAIL__SKIP_INSTRUCTIONS:
        setSkipInstructions(((Boolean)newValue).booleanValue());
        return;
      case ActionstepPackage.VOICEMAIL__PLAY_UNAVAILABLE_MESSAGE:
        setPlayUnavailableMessage(((Boolean)newValue).booleanValue());
        return;
      case ActionstepPackage.VOICEMAIL__PLAY_BUSY_MESSAGE:
        setPlayBusyMessage(((Boolean)newValue).booleanValue());
        return;
      case ActionstepPackage.VOICEMAIL__RECORDING_GAIN:
        setRecordingGain(((Integer)newValue).intValue());
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
      case ActionstepPackage.VOICEMAIL__CALL1:
        setCall1((Call)null);
        return;
      case ActionstepPackage.VOICEMAIL__MAILBOX:
        setMailbox((DynamicValue)null);
        return;
      case ActionstepPackage.VOICEMAIL__SKIP_INSTRUCTIONS:
        setSkipInstructions(SKIP_INSTRUCTIONS_EDEFAULT);
        return;
      case ActionstepPackage.VOICEMAIL__PLAY_UNAVAILABLE_MESSAGE:
        setPlayUnavailableMessage(PLAY_UNAVAILABLE_MESSAGE_EDEFAULT);
        return;
      case ActionstepPackage.VOICEMAIL__PLAY_BUSY_MESSAGE:
        setPlayBusyMessage(PLAY_BUSY_MESSAGE_EDEFAULT);
        return;
      case ActionstepPackage.VOICEMAIL__RECORDING_GAIN:
        setRecordingGain(RECORDING_GAIN_EDEFAULT);
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
      case ActionstepPackage.VOICEMAIL__CALL1:
        return call1 != null;
      case ActionstepPackage.VOICEMAIL__MAILBOX:
        return mailbox != null;
      case ActionstepPackage.VOICEMAIL__SKIP_INSTRUCTIONS:
        return skipInstructions != SKIP_INSTRUCTIONS_EDEFAULT;
      case ActionstepPackage.VOICEMAIL__PLAY_UNAVAILABLE_MESSAGE:
        return playUnavailableMessage != PLAY_UNAVAILABLE_MESSAGE_EDEFAULT;
      case ActionstepPackage.VOICEMAIL__PLAY_BUSY_MESSAGE:
        return playBusyMessage != PLAY_BUSY_MESSAGE_EDEFAULT;
      case ActionstepPackage.VOICEMAIL__RECORDING_GAIN:
        return recordingGain != RECORDING_GAIN_EDEFAULT;
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
        case ActionstepPackage.VOICEMAIL__CALL1: return AsteriskPackage.CALL_CONSUMER1__CALL1;
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
        case AsteriskPackage.CALL_CONSUMER1__CALL1: return ActionstepPackage.VOICEMAIL__CALL1;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (skipInstructions: ");
    result.append(skipInstructions);
    result.append(", playUnavailableMessage: ");
    result.append(playUnavailableMessage);
    result.append(", playBusyMessage: ");
    result.append(playBusyMessage);
    result.append(", recordingGain: ");
    result.append(recordingGain);
    result.append(')');
    return result.toString();
  }

} //VoicemailImpl