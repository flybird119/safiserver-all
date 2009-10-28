/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.safi.core.actionstep.impl;

import java.sql.ResultSet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.safi.core.actionstep.ActionStepException;
import com.safi.core.actionstep.ActionStepFactory;
import com.safi.core.actionstep.ActionStepPackage;
import com.safi.core.actionstep.DBResultSetId;
import com.safi.core.actionstep.NextRow;
import com.safi.core.actionstep.Output;
import com.safi.core.actionstep.OutputType;
import com.safi.core.saflet.SafletContext;
import com.safi.db.SafiDriverManager;
import com.safi.db.manager.DBManager;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Next Row</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.safi.core.actionstep.impl.NextRowImpl#getResultSet <em>Result Set</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NextRowImpl extends ActionStepImpl implements NextRow {
  /**
   * The cached value of the '{@link #getResultSet() <em>Result Set</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResultSet()
   * @generated
   * @ordered
   */
  protected DBResultSetId resultSet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  protected NextRowImpl() {
    super();
    productId = "APDBTOOLS";
  }

  @Override
  public void beginProcessing(SafletContext context) throws ActionStepException {

    super.beginProcessing(context);
    Exception exception = null;
    int idx = 1;
    try {
      SafiDriverManager dm = DBManager.getInstance().getDriverManager();
      if (dm == null)
        exception = new ActionStepException(
            "Safi DB Driver Manager could not be found.  Check your DB configuration");
      else if (resultSet == null)
        exception = new ActionStepException(
        "ResultSet name is required.");
      else if (resultSet.getJDBCResultSet() == null) {
        exception = new ActionStepException("ResultSet "+resultSet.getName()+" has not been initialized.");
      } else {
        ResultSet rs = resultSet.getJDBCResultSet();
        boolean b = rs.next();
        if (!b)
          idx = 2;
          
      }

    } catch (Exception e) {
      // e.printStackTrace();
      exception = e;
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
    o.setName("last row");
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
    return ActionStepPackage.eINSTANCE.getNextRow();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DBResultSetId getResultSet() {
    return resultSet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setResultSet(DBResultSetId newResultSet) {
    DBResultSetId oldResultSet = resultSet;
    resultSet = newResultSet;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ActionStepPackage.NEXT_ROW__RESULT_SET, oldResultSet, resultSet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ActionStepPackage.NEXT_ROW__RESULT_SET:
        return getResultSet();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ActionStepPackage.NEXT_ROW__RESULT_SET:
        setResultSet((DBResultSetId)newValue);
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
      case ActionStepPackage.NEXT_ROW__RESULT_SET:
        setResultSet((DBResultSetId)null);
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
      case ActionStepPackage.NEXT_ROW__RESULT_SET:
        return resultSet != null;
    }
    return super.eIsSet(featureID);
  }

} //NextRowImpl