/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfc.sffc.cutscene.io;

import com.sfc.sffc.cutscene.Cutscene;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wiz
 */
public class DisassemblyManager {

    public static Cutscene importDisassembly(String filePath, String offsetString, String lengthString){
        System.out.println("com.sfc.sffc.cutscene.io.DisassemblyManager.importDisassembly() - Importing disassembly ...");
        Cutscene cutscene = new Cutscene();
        try {
            Path filepath = Paths.get(filePath);
            byte[] data = Files.readAllBytes(filepath);
            int offset = Integer.parseInt(offsetString,16);
            int length = Integer.parseInt(lengthString,10);
            byte[] cutsceneBytes = new byte[length];
            System.arraycopy(data, offset, cutsceneBytes, 0, length);
            cutscene.setData(cutsceneBytes);
        } catch (IOException ex) {
            Logger.getLogger(DisassemblyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("com.sfc.sffc.cutscene.io.DisassemblyManager.importDisassembly() - Disassembly imported.");  
        return cutscene;
    }
    
}
