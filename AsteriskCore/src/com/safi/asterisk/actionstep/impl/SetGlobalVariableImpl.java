/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.safi.asterisk.actionstep.impl;

import com.safi.asterisk.Call;

import com.safi.asterisk.actionstep.ActionstepPackage;
import com.safi.asterisk.actionstep.SetGlobalVariable;

import com.safi.core.CorePackage;
import com.safi.core.ProductIdentifiable;

import com.safi.core.actionstep.ActionStep;
import com.safi.core.actionstep.ActionStepException;
import com.safi.core.actionstep.ActionStepPackage;
import com.safi.core.actionstep.DynamicValue;
import com.safi.core.actionstep.Output;

import com.safi.core.saflet.Saflet;
import com.safi.core.saflet.SafletContext;
import com.safi.core.saflet.SafletPackage;

import com.safi.core.scripting.SafletScriptException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Global Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getCall1 <em>Call1</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getProductId <em>Product Id</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#isPaused <em>Paused</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#isActive <em>Active</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getSaflet <em>Saflet</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getDefaultOutput <em>Default Output</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getErrorOutput <em>Error Output</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.safi.asterisk.actionstep.impl.SetGlobalVariableImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetGlobalVariableImpl extends EObjectImpl implements SetGlobalVariable {
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
   * The default value of the '{@link #getProductId() <em>Product Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProductId()
   * @generated
   * @ordered
   */
  protected static final String PRODUCT_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getProductId() <em>Product Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProductId()
   * @generated
   * @ordered
   */
  protected String productId = PRODUCT_ID_EDEFAULT;

  /**
   * The default value of the '{@link #isPaused() <em>Paused</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPaused()
   * @generated
   * @ordered
   */
  protected static final boolean PAUSED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isPaused() <em>Paused</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPaused()
   * @generated
   * @ordered
   */
  protected boolean paused = PAUSED_EDEFAULT;

  /**
   * The default value of the '{@link #isActive() <em>Active</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isActive()
   * @generated
   * @ordered
   */
  protected static final boolean ACTIVE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isActive() <em>Active</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isActive()
   * @generated
   * @ordered
   */
  protected boolean active = ACTIVE_EDEFAULT;

  /**
   * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutputs()
   * @generated
   * @ordered
   */
  protected EList<Output> outputs;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getDefaultOutput() <em>Default Output</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultOutput()
   * @generated
   * @ordered
   */
  protected Output defaultOutput;

  /**
   * The cached value of the '{@link #getErrorOutput() <em>Error Output</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getErrorOutput()
   * @generated
   * @ordered
   */
  protected Output errorOutput;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected DynamicValue value;

  /**
   * The default value of the '{@link #getVariable() <em>Variable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected static final String VARIABLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getVariable() <em>Variable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected String variable = VARIABLE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SetGlobalVariableImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ActionstepPackage.Literals.SET_GLOBAL_VARIABLE;
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1, oldCall1, call1));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1, oldCall1, call1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getProductId() {
    return productId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProductId(String newProductId) {
    String oldProductId = productId;
    productId = newProductId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID, oldProductId, productId));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPaused(boolean newPaused) {
    boolean oldPaused = paused;
    paused = newPaused;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED, oldPaused, paused));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isActive() {
    return active;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setActive(boolean newActive) {
    boolean oldActive = active;
    active = newActive;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE, oldActive, active));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Output> getOutputs() {
    if (outputs == null) {
      outputs = new EObjectContainmentWithInverseEList<Output>(Output.class, this, ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS, ActionStepPackage.OUTPUT__PARENT);
    }
    return outputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Saflet getSaflet() {
    if (eContainerFeatureID != ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET) return null;
    return (Saflet)eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSaflet(Saflet newSaflet, NotificationChain msgs) {
    msgs = eBasicSetContainer((InternalEObject)newSaflet, ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSaflet(Saflet newSaflet) {
    if (newSaflet != eInternalContainer() || (eContainerFeatureID != ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET && newSaflet != null)) {
      if (EcoreUtil.isAncestor(this, newSaflet))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newSaflet != null)
        msgs = ((InternalEObject)newSaflet).eInverseAdd(this, SafletPackage.SAFLET__ACTIONSTEPS, Saflet.class, msgs);
      msgs = basicSetSaflet(newSaflet, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET, newSaflet, newSaflet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Output getDefaultOutput() {
    if (defaultOutput != null && defaultOutput.eIsProxy()) {
      InternalEObject oldDefaultOutput = (InternalEObject)defaultOutput;
      defaultOutput = (Output)eResolveProxy(oldDefaultOutput);
      if (defaultOutput != oldDefaultOutput) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT, oldDefaultOutput, defaultOutput));
      }
    }
    return defaultOutput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Output basicGetDefaultOutput() {
    return defaultOutput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefaultOutput(Output newDefaultOutput) {
    Output oldDefaultOutput = defaultOutput;
    defaultOutput = newDefaultOutput;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT, oldDefaultOutput, defaultOutput));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Output getErrorOutput() {
    if (errorOutput != null && errorOutput.eIsProxy()) {
      InternalEObject oldErrorOutput = (InternalEObject)errorOutput;
      errorOutput = (Output)eResolveProxy(oldErrorOutput);
      if (errorOutput != oldErrorOutput) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT, oldErrorOutput, errorOutput));
      }
    }
    return errorOutput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Output basicGetErrorOutput() {
    return errorOutput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setErrorOutput(Output newErrorOutput) {
    Output oldErrorOutput = errorOutput;
    errorOutput = newErrorOutput;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT, oldErrorOutput, errorOutput));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DynamicValue getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetValue(DynamicValue newValue, NotificationChain msgs) {
    DynamicValue oldValue = value;
    value = newValue;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE, oldValue, newValue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(DynamicValue newValue) {
    if (newValue != value) {
      NotificationChain msgs = null;
      if (value != null)
        msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE, null, msgs);
      if (newValue != null)
        msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE, null, msgs);
      msgs = basicSetValue(newValue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE, newValue, newValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getVariable() {
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVariable(String newVariable) {
    String oldVariable = variable;
    variable = newVariable;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionstepPackage.SET_GLOBAL_VARIABLE__VARIABLE, oldVariable, variable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void beginProcessing(SafletContext context) throws ActionStepException {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object executeScript(String scriptName, String scriptText) throws SafletScriptException {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void handleException(SafletContext context, Exception e) throws ActionStepException {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object resolveDynamicValue(DynamicValue dynamicValue, SafletContext context) throws ActionStepException {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createDefaultOutputs() {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputs()).basicAdd(otherEnd, msgs);
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetSaflet((Saflet)otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        return basicSetSaflet(null, msgs);
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE:
        return basicSetValue(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
    switch (eContainerFeatureID) {
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        return eInternalContainer().eInverseRemove(this, SafletPackage.SAFLET__ACTIONSTEPS, Saflet.class, msgs);
    }
    return super.eBasicRemoveFromContainerFeature(msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1:
        if (resolve) return getCall1();
        return basicGetCall1();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID:
        return getProductId();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED:
        return isPaused() ? Boolean.TRUE : Boolean.FALSE;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE:
        return isActive() ? Boolean.TRUE : Boolean.FALSE;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        return getOutputs();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__NAME:
        return getName();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        return getSaflet();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT:
        if (resolve) return getDefaultOutput();
        return basicGetDefaultOutput();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT:
        if (resolve) return getErrorOutput();
        return basicGetErrorOutput();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE:
        return getValue();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VARIABLE:
        return getVariable();
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
      case ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1:
        setCall1((Call)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID:
        setProductId((String)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED:
        setPaused(((Boolean)newValue).booleanValue());
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE:
        setActive(((Boolean)newValue).booleanValue());
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends Output>)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__NAME:
        setName((String)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        setSaflet((Saflet)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT:
        setDefaultOutput((Output)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT:
        setErrorOutput((Output)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE:
        setValue((DynamicValue)newValue);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VARIABLE:
        setVariable((String)newValue);
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
      case ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1:
        setCall1((Call)null);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID:
        setProductId(PRODUCT_ID_EDEFAULT);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED:
        setPaused(PAUSED_EDEFAULT);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE:
        setActive(ACTIVE_EDEFAULT);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        getOutputs().clear();
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        setSaflet((Saflet)null);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT:
        setDefaultOutput((Output)null);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT:
        setErrorOutput((Output)null);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE:
        setValue((DynamicValue)null);
        return;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VARIABLE:
        setVariable(VARIABLE_EDEFAULT);
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
      case ActionstepPackage.SET_GLOBAL_VARIABLE__CALL1:
        return call1 != null;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID:
        return PRODUCT_ID_EDEFAULT == null ? productId != null : !PRODUCT_ID_EDEFAULT.equals(productId);
      case ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED:
        return paused != PAUSED_EDEFAULT;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE:
        return active != ACTIVE_EDEFAULT;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
      case ActionstepPackage.SET_GLOBAL_VARIABLE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET:
        return getSaflet() != null;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT:
        return defaultOutput != null;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT:
        return errorOutput != null;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VALUE:
        return value != null;
      case ActionstepPackage.SET_GLOBAL_VARIABLE__VARIABLE:
        return VARIABLE_EDEFAULT == null ? variable != null : !VARIABLE_EDEFAULT.equals(variable);
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
    if (baseClass == ProductIdentifiable.class) {
      switch (derivedFeatureID) {
        case ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID: return CorePackage.PRODUCT_IDENTIFIABLE__PRODUCT_ID;
        default: return -1;
      }
    }
    if (baseClass == ActionStep.class) {
      switch (derivedFeatureID) {
        case ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED: return ActionStepPackage.ACTION_STEP__PAUSED;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE: return ActionStepPackage.ACTION_STEP__ACTIVE;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS: return ActionStepPackage.ACTION_STEP__OUTPUTS;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__NAME: return ActionStepPackage.ACTION_STEP__NAME;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET: return ActionStepPackage.ACTION_STEP__SAFLET;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT: return ActionStepPackage.ACTION_STEP__DEFAULT_OUTPUT;
        case ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT: return ActionStepPackage.ACTION_STEP__ERROR_OUTPUT;
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
    if (baseClass == ProductIdentifiable.class) {
      switch (baseFeatureID) {
        case CorePackage.PRODUCT_IDENTIFIABLE__PRODUCT_ID: return ActionstepPackage.SET_GLOBAL_VARIABLE__PRODUCT_ID;
        default: return -1;
      }
    }
    if (baseClass == ActionStep.class) {
      switch (baseFeatureID) {
        case ActionStepPackage.ACTION_STEP__PAUSED: return ActionstepPackage.SET_GLOBAL_VARIABLE__PAUSED;
        case ActionStepPackage.ACTION_STEP__ACTIVE: return ActionstepPackage.SET_GLOBAL_VARIABLE__ACTIVE;
        case ActionStepPackage.ACTION_STEP__OUTPUTS: return ActionstepPackage.SET_GLOBAL_VARIABLE__OUTPUTS;
        case ActionStepPackage.ACTION_STEP__NAME: return ActionstepPackage.SET_GLOBAL_VARIABLE__NAME;
        case ActionStepPackage.ACTION_STEP__SAFLET: return ActionstepPackage.SET_GLOBAL_VARIABLE__SAFLET;
        case ActionStepPackage.ACTION_STEP__DEFAULT_OUTPUT: return ActionstepPackage.SET_GLOBAL_VARIABLE__DEFAULT_OUTPUT;
        case ActionStepPackage.ACTION_STEP__ERROR_OUTPUT: return ActionstepPackage.SET_GLOBAL_VARIABLE__ERROR_OUTPUT;
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
    result.append(" (productId: ");
    result.append(productId);
    result.append(", paused: ");
    result.append(paused);
    result.append(", active: ");
    result.append(active);
    result.append(", name: ");
    result.append(name);
    result.append(", variable: ");
    result.append(variable);
    result.append(')');
    return result.toString();
  }

} //SetGlobalVariableImpl
