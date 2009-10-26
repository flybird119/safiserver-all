/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.safi.asterisk.actionstep;

import com.safi.asterisk.CallConsumer1;
import com.safi.core.actionstep.ActionStep;
import com.safi.core.actionstep.DynamicValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Play DTMF</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.safi.asterisk.actionstep.PlayDTMF#getDigits <em>Digits</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.safi.asterisk.actionstep.ActionstepPackage#getPlayDTMF()
 * @model
 * @generated
 */
public interface PlayDTMF extends ActionStep, CallConsumer1 {
  /**
   * Returns the value of the '<em><b>Digits</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Digits</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Digits</em>' containment reference.
   * @see #setDigits(DynamicValue)
   * @see com.safi.asterisk.actionstep.ActionstepPackage#getPlayDTMF_Digits()
   * @model containment="true" ordered="false"
   *        annotation="Required criteria='non-null'"
   * @generated
   */
  DynamicValue getDigits();

  /**
   * Sets the value of the '{@link com.safi.asterisk.actionstep.PlayDTMF#getDigits <em>Digits</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Digits</em>' containment reference.
   * @see #getDigits()
   * @generated
   */
  void setDigits(DynamicValue value);

} // PlayDTMF