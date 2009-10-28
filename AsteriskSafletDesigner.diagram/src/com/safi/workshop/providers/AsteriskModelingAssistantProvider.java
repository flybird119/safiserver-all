package com.safi.workshop.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.safi.core.actionstep.ActionStep;
import com.safi.core.initiator.Initiator;
import com.safi.workshop.edit.parts.ActionstepSubItem;
import com.safi.workshop.edit.parts.CaseItemTargetToolstepEditPart;
import com.safi.workshop.edit.parts.ChoiceEditPart;
import com.safi.workshop.edit.parts.GetColValuesEditPart;
import com.safi.workshop.edit.parts.MultiStreamAudioEditPart;
import com.safi.workshop.edit.parts.OutputEditPart;
import com.safi.workshop.edit.parts.OutputTargetEditPart;
import com.safi.workshop.edit.parts.SetColValuesEditPart;
import com.safi.workshop.edit.parts.ToolstepEditPart;
import com.safi.workshop.part.AsteriskDiagramEditorPlugin;
import com.safi.workshop.part.Messages;

/**
 * @generated
 */
public class AsteriskModelingAssistantProvider extends ModelingAssistantProvider {

  private static List toolstepTypes = addAllToolstepTypes(new ArrayList());

  private static Map<EClass, List<IElementType>> popupbarTypeMap = new HashMap<EClass, List<IElementType>>();

  public static void registerPopupbarType(EClass clz, IElementType type) {
    List<IElementType> lst = popupbarTypeMap.get(clz);
    if (lst == null) {
      lst = new ArrayList<IElementType>();
      popupbarTypeMap.put(clz, lst);
    }
    lst.add(type);

  }

  //  
  // @Override
  // public boolean provides(IOperation operation) {
  // try {
  // System.err.println("Zeee operation is "+operation);
  // return super.provides(operation);
  // } catch (RuntimeException e) {
  // e.printStackTrace();
  // throw e;
  // }
  //   
  // }

  @Override
  public boolean provides(IOperation operation) {
    // TODO Auto-generated method stub
    return super.provides(operation);
  }
  /**
   * @generated NOT
   */
  @Override
  public List getTypesForPopupBar(IAdaptable host) {
    IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);

    if (editPart instanceof ChoiceEditPart) {
      return Collections.singletonList(AsteriskElementTypes.CaseItem_2002);
    }
    if (editPart instanceof MultiStreamAudioEditPart) {
      return Collections.singletonList(AsteriskElementTypes.AudioFileItem_2003);
    }

    if (editPart instanceof SetColValuesEditPart) {
      return Collections.singletonList(AsteriskElementTypes.SetColMapping_2005);
    }

    if (editPart instanceof GetColValuesEditPart) {
      return Collections.singletonList(AsteriskElementTypes.GetColMapping_2004);
    }

    if (editPart instanceof ToolstepEditPart) {
      ToolstepEditPart ts = (ToolstepEditPart) editPart;
      ActionStep tsm = ts.getActionStep();
      return popupbarTypeMap.get(tsm.eClass());
    }
    // if (editPart instanceof RunQueryEditPart) {
    // return Collections.singletonList(AsteriskElementTypes.QueryParamMapping_2006);
    // }

    // if (editPart instanceof GetFullVariableEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof MultiStreamAudioEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof HangupEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof IfThenEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof RecordFileEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayAlphaEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayDateTimeEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayDigitsEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayNumberEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayPhoneticEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SayTimeEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetAutoHangupEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetCallerIdEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetChannelVariableEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetContextEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetExtensionEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof StopMusicOnHoldEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetMusicOnEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SetPriorityEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof StreamAudioEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof WaitForDigitEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof GetDigitsEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof OriginateCallEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof PlayDTMFEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof RecordCallEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof RedirectCallEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof ExecuteApplicationEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof PlayMusicOnHoldEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof PromptGetDigitsEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof SleepEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }
    // if (editPart instanceof StreamAudioExtendedEditPart) {
    // List types = new ArrayList();
    // types.add(AsteriskElementTypes.Output_2001);
    // return types;
    // }

    // uncomment the following to add popup bar
    // if (editPart instanceof HandlerEditPart) {
    // return toolstepTypes;
    // }
    // if (editPart instanceof IncomingCallEditPart)
    // return Collections.singletonList(AsteriskElementTypes.InitiatorNextToolstep_3002);
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated NOT
   */
  @Override
  public List getRelTypesOnSource(IAdaptable source) {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    if (sourceEditPart instanceof OutputEditPart) {
      return Collections.singletonList(AsteriskElementTypes.OutputTarget_3001);
    }
    // if (isInitiator(sourceEditPart)) {
    // return Collections.singletonList(AsteriskElementTypes.InitiatorNextToolstep_3002);
    // }
    if (sourceEditPart instanceof ActionstepSubItem) {
      return Collections.singletonList(AsteriskElementTypes.CaseItemTargetToolstep_3003);
    }
    // else if ((sourceEditPart.getModel() instanceof View)
    // && (((View) sourceEditPart.getModel()).getElement() instanceof CaseItem)) {
    // return Collections.singletonList(AsteriskElementTypes.CaseItemTargetToolstep_3003);
    //
    // }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated NOT
   */
  @Override
  public List getRelTypesOnTarget(IAdaptable target) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated NOT
   */
  @Override
  public List getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
        .getAdapter(IGraphicalEditPart.class);

    boolean targetIsToolstep = isToolstep(targetEditPart);
    boolean sourceIsToolstep = isToolstep(sourceEditPart);
    if ((sourceEditPart instanceof OutputEditPart && targetIsToolstep)
        || (sourceEditPart instanceof OutputTargetEditPart && targetIsToolstep)
        || (targetEditPart instanceof OutputEditPart && sourceIsToolstep)) {

      // if (sourceEditPart instanceof OutputEditPart && targetEditPart instanceof
      // AbstractBorderedShapeEditPart) {
      return Collections.singletonList(AsteriskElementTypes.OutputTarget_3001);
    } else if ((sourceEditPart instanceof ActionstepSubItem && targetIsToolstep)
        || (sourceEditPart instanceof CaseItemTargetToolstepEditPart && targetIsToolstep)
        || (targetEditPart instanceof ActionstepSubItem && sourceIsToolstep)) {
      return Collections.singletonList(AsteriskElementTypes.CaseItemTargetToolstep_3003);
    }
    // else if ((sourceEditPart.getModel() instanceof View)
    // && (((View) sourceEditPart.getModel()).getElement() instanceof CaseItem)) {
    // return Collections.singletonList(AsteriskElementTypes.CaseItemTargetToolstep_3003);
    //
    // }

    return Collections.EMPTY_LIST;
  }

  /**
   * @generated NOT
   */
  @Override
  public List getTypesForSource(IAdaptable target, IElementType relationshipType) {
    IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
        .getAdapter(IGraphicalEditPart.class);
    boolean targetIsToolstep = isToolstep(targetEditPart);
    if (targetIsToolstep) {
      if (relationshipType == AsteriskElementTypes.OutputTarget_3001) {
        return Collections.singletonList(AsteriskElementTypes.Output_2001);
      }
      if ((targetEditPart instanceof ChoiceEditPart)
          && relationshipType == AsteriskElementTypes.CaseItemTargetToolstep_3003) {
        return Collections.singletonList(AsteriskElementTypes.CaseItem_2002);
      }
    }
    return Collections.EMPTY_LIST;
  }

  public boolean isToolstep(IGraphicalEditPart editPart) {
    return editPart.getModel() instanceof Node
        && ((Node) editPart.getModel()).getElement() instanceof ActionStep;
  }

  public boolean isInitiator(IGraphicalEditPart editPart) {
    return editPart.getModel() instanceof Node
        && ((Node) editPart.getModel()).getElement() instanceof Initiator;
  }

  /**
   * @generated NOT
   */
  @Override
  public List getTypesForTarget(IAdaptable source, IElementType relationshipType) {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    if (sourceEditPart instanceof OutputEditPart || isInitiator(sourceEditPart)
        || (sourceEditPart instanceof ActionstepSubItem)) {
      return toolstepTypes;
    }
    return Collections.EMPTY_LIST;
  }

  private static List addAllToolstepTypes(List types) {
    types.add(AsteriskElementTypes.Answer_1001);
    types.add(AsteriskElementTypes.Assignment_1002);
    types.add(AsteriskElementTypes.Choice_1003);
    types.add(AsteriskElementTypes.GetFullVariable_1004);
    types.add(AsteriskElementTypes.MultiStreamAudio_1005);
    types.add(AsteriskElementTypes.Hangup_1006);
    types.add(AsteriskElementTypes.IfThen_1007);
    types.add(AsteriskElementTypes.RecordFile_1008);
    types.add(AsteriskElementTypes.SayAlpha_1009);
    types.add(AsteriskElementTypes.SayDateTime_1010);
    types.add(AsteriskElementTypes.SayDigits_1011);
    types.add(AsteriskElementTypes.SayNumber_1012);
    types.add(AsteriskElementTypes.SayPhonetic_1013);
    types.add(AsteriskElementTypes.SayTime_1014);
    types.add(AsteriskElementTypes.SetAutoHangup_1015);
    types.add(AsteriskElementTypes.SetCallerId_1016);
    types.add(AsteriskElementTypes.SetChannelVariable_1017);
    types.add(AsteriskElementTypes.SetContext_1018);
    // types.add(AsteriskElementTypes.SetExtension_1019);
    types.add(AsteriskElementTypes.StopMusicOnHold_1020);
    types.add(AsteriskElementTypes.SetMusicOn_1021);
    types.add(AsteriskElementTypes.SetPriority_1022);
    types.add(AsteriskElementTypes.StreamAudio_1023);
    types.add(AsteriskElementTypes.WaitForDigit_1024);
    types.add(AsteriskElementTypes.DIDMatcher_1025);
    types.add(AsteriskElementTypes.GetDigits_1026);
    types.add(AsteriskElementTypes.OriginateCall_1027);
    types.add(AsteriskElementTypes.PlayDTMF_1028);
    types.add(AsteriskElementTypes.RecordCall_1029);
    types.add(AsteriskElementTypes.Transfer_1076);
    types.add(AsteriskElementTypes.ExecuteApplication_1030);
    types.add(AsteriskElementTypes.PlayMusicOnHold_1031);
    types.add(AsteriskElementTypes.PromptGetDigits_1032);
    types.add(AsteriskElementTypes.Sleep_1033);
    types.add(AsteriskElementTypes.StreamAudioExtended_1034);
    types.add(AsteriskElementTypes.GetCallInfo_1036);
    types.add(AsteriskElementTypes.ExecuteScript_1037);
    types.add(AsteriskElementTypes.InvokeSaflet_1038);

    // types.add(AsteriskElementTypes.Dial_1039);
    // types.add(AsteriskElementTypes.Pickup_1040);
    // types.add(AsteriskElementTypes.Background_1041);
    // types.add(AsteriskElementTypes.BackgroundDetect_1042);
    // types.add(AsteriskElementTypes.WaitExten_1043);
    // types.add(AsteriskElementTypes.SoftHangup_1044);
    // types.add(AsteriskElementTypes.PickupChan_1045);
    // types.add(AsteriskElementTypes.Pickdown_1046);
    types.add(AsteriskElementTypes.Progress_1047);
    // types.add(AsteriskElementTypes.Bridge_1048);
    types.add(AsteriskElementTypes.PlaceCall_1049);
    types.add(AsteriskElementTypes.Voicemail_1050);
    types.add(AsteriskElementTypes.VoicemailMain_1051);
    // types.add(AsteriskElementTypes.VMAuthenticate_1052);
    types.add(AsteriskElementTypes.MeetMe_1053);
    types.add(AsteriskElementTypes.MeetMeAdmin_1054);
    types.add(AsteriskElementTypes.MeetMeCount_1055);
    types.add(AsteriskElementTypes.DebugLog_1056);
    types.add(AsteriskElementTypes.WaitForRing_1057);
    types.add(AsteriskElementTypes.WaitMusicOnHold_1058);
    // types.add(AsteriskElementTypes.GetAvailableChannel_1059);
    types.add(AsteriskElementTypes.Congestion_1060);
    types.add(AsteriskElementTypes.Ringing_1061);
    types.add(AsteriskElementTypes.SetCallerPresentation_1062);
    types.add(AsteriskElementTypes.SetGlobalVariable_1063);
    types.add(AsteriskElementTypes.Echo_1064);
    types.add(AsteriskElementTypes.Festival_1065);
    // types.add(AsteriskElementTypes.Playtones_1066);
    // types.add(AsteriskElementTypes.StopPlaytones_1067);
    types.add(AsteriskElementTypes.ChanSpy_1068);
    types.add(AsteriskElementTypes.Dictate_1069);
    types.add(AsteriskElementTypes.ExtensionSpy_1070);
    // types.add(AsteriskElementTypes.MixMonitor_1071);
    // types.add(AsteriskElementTypes.StopMixmonitor_1072);
    types.add(AsteriskElementTypes.StopMonitor_1073);
    types.add(AsteriskElementTypes.Monitor_1074);
    types.add(AsteriskElementTypes.Directory_1075);

    types.add(AsteriskElementTypes.OpenDBConnection_1077);

    types.add(AsteriskElementTypes.CloseDBConnection_1078);
    types.add(AsteriskElementTypes.RunQuery_1097);
    types.add(AsteriskElementTypes.ExecuteUpdate_1081);
    // types.add(AsteriskElementTypes.OpenQuery_1079);
    // types.add(AsteriskElementTypes.SetQueryParam_1080);
    // types.add(AsteriskElementTypes.ExecuteUpdate_1081);
    // types.add(AsteriskElementTypes.ExecuteQuery_1082);
    types.add(AsteriskElementTypes.NextRow_1083);
    types.add(AsteriskElementTypes.PreviousRow_1093);
    types.add(AsteriskElementTypes.GetColValue_1084);
    types.add(AsteriskElementTypes.GetColValues_1094);
    types.add(AsteriskElementTypes.SetColValue_1085);
    types.add(AsteriskElementTypes.SetColValues_1095);
    types.add(AsteriskElementTypes.UpdatetRow_1086);
    types.add(AsteriskElementTypes.InsertRow_1091);
    types.add(AsteriskElementTypes.DeleteRow_1089);
    types.add(AsteriskElementTypes.MoveToRow_1087);
    types.add(AsteriskElementTypes.MoveToLastRow_1088);

    types.add(AsteriskElementTypes.MoveToInsertRow_1090);
    types.add(AsteriskElementTypes.MoveToFirstRow_1092);
    types.add(AsteriskElementTypes.ExtensionTransfer_1096);

    return types;
  }

  /**
   * @generated
   */
  @Override
  public EObject selectExistingElementForSource(IAdaptable target, IElementType relationshipType) {
    return selectExistingElement(target, getTypesForSource(target, relationshipType));
  }

  /**
   * @generated
   */
  @Override
  public EObject selectExistingElementForTarget(IAdaptable source, IElementType relationshipType) {
    return selectExistingElement(source, getTypesForTarget(source, relationshipType));
  }

  /**
   * @generated
   */
  protected EObject selectExistingElement(IAdaptable host, Collection types) {
    if (types.isEmpty()) {
      return null;
    }
    IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
    if (editPart == null) {
      return null;
    }
    Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
    Collection elements = new HashSet();
    for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
      EObject element = (EObject) it.next();
      if (isApplicableElement(element, types)) {
        elements.add(element);
      }
    }
    if (elements.isEmpty()) {
      return null;
    }
    return selectElement((EObject[]) elements.toArray(new EObject[elements.size()]));
  }

  /**
   * @generated
   */
  protected boolean isApplicableElement(EObject element, Collection types) {
    IElementType type = ElementTypeRegistry.getInstance().getElementType(element);
    return types.contains(type);
  }

  /**
   * @generated
   */
  protected EObject selectElement(EObject[] elements) {
    Shell shell = Display.getCurrent().getActiveShell();
    ILabelProvider labelProvider = new AdapterFactoryLabelProvider(AsteriskDiagramEditorPlugin
        .getInstance().getItemProvidersAdapterFactory());
    ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
    dialog.setMessage(Messages.AsteriskModelingAssistantProviderMessage);
    dialog.setTitle(Messages.AsteriskModelingAssistantProviderTitle);
    dialog.setMultipleSelection(false);
    dialog.setElements(elements);
    EObject selected = null;
    if (dialog.open() == Window.OK) {
      selected = (EObject) dialog.getFirstResult();
    }
    return selected;
  }

  public static List getToolstepTypes() {
    return toolstepTypes;
  }
}