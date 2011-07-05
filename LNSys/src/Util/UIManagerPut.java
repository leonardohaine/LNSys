package Util;

import javax.swing.UIManager;

public class UIManagerPut {

    public void CsUIManagerPut() {
        
        UIManager.put("FileChooser.lookInLabelMnemonic", new Integer('E'));
        UIManager.put("FileChooser.lookInLabelText", "Examinar:");
        UIManager.put("FileChooser.saveInLabelText", "Salvar em:");

        UIManager.put("FileChooser.fileNameLabelMnemonic", new Integer('N')); 
        UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo:");

        UIManager.put("FileChooser.filesOfTypeLabelMnemonic", new Integer('T')); 
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo:");

        UIManager.put("FileChooser.upFolderToolTipText", "Um nível acima");
        UIManager.put("FileChooser.upFolderAccessibleName", "Um nível acima");

        UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
        UIManager.put("FileChooser.homeFolderAccessibleName", "Desktop");

        UIManager.put("FileChooser.newFolderToolTipText", "Criar nova pasta");
        UIManager.put("FileChooser.newFolderAccessibleName", "Criar nova pasta");

        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");

        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Detalhes");

        UIManager.put("FileChooser.fileNameHeaderText", "Nome");
        UIManager.put("FileChooser.fileSizeHeaderText", "Tamanho");
        UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
        UIManager.put("FileChooser.fileDateHeaderText", "Data");
        UIManager.put("FileChooser.fileAttrHeaderText", "Atributos");

        UIManager.put("FileChooser.saveButtonText", "Salvar");
        UIManager.put("FileChooser.cancelButtonText", "Cancelar");
        UIManager.put("FileChooser.openButtonText", "Abrir");

        UIManager.put("FileChooser.acceptAllFileFilterText", "Todos Arquivos (*.*)");

        UIManager.put("FileChooser.openDialogTitleText", "Abrir");
        UIManager.put("FileChooser.saveDialogTitleText", "Salvar");

        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.okButtonText", "Ok");
        UIManager.put("OptionPane.yesButtonText", "Sim");
    }
    
}
