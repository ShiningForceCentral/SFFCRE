/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfc.sffc.cutscene;

import com.sfc.sffc.cutscene.io.DisassemblyManager;
import com.sfc.sffc.cutscene.io.Sf2DisasmManager;


/**
 *
 * @author wiz
 */
public class CutsceneManager {
       
    private Cutscene cutscene;
    
    public void importDisassembly(String filePath, String offsetString, String lengthString){
        System.out.println("com.sfc.sffc.cutscene.CutsceneManager.importDisassembly() - Importing disassembly ...");
        cutscene = DisassemblyManager.importDisassembly(filePath, offsetString, lengthString);
        System.out.println("com.sfc.sffc.cutscene.CutsceneManager.importDisassembly() - Disassembly imported.");
    }
    
    public void exportSf2Disasm(String filePath, int xShift, int yShift){
        System.out.println("com.sfc.sffc.cutscene.CutsceneManager.exportSf2Disasm() - Exporting SF2DISASM cutscene ...");
        Sf2DisasmManager.exportCutscene(cutscene, filePath, xShift, yShift);
        System.out.println("com.sfc.sffc.cutscene.CutsceneManager.exportSf2Disasm() - SF2DISASM cutscene exported.");
    }
    
    
}
