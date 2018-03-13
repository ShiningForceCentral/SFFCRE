/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfc.sffc.cutscene.io;

import com.sfc.sffc.cutscene.Cutscene;
import com.sfc.sffc.cutscene.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wiz
 */
public class Sf2DisasmManager {
    
    private static StringBuilder sb;
    private static int moveSequenceEntityNumber = 0;
    private static Map<Integer,Entity> entities;
    private static Map<Integer,Entity> initialEntities;
    private static int portraitIndex;
    private static int portraitParams;

    public static void exportCutscene(Cutscene cutscene, String filePath){
        System.out.println("com.sfc.sffc.cutscene.io.DisassemblyManager.exportCutscene() - Exporting SF2DISASM cutscene ...");
        try {

            System.out.println("data = " + bytesToHex(cutscene.getData()));
            
            String sf2DisasmCutscene = convert(cutscene);
            
            System.out.println(sf2DisasmCutscene);
            
        } catch (Exception ex) {
            Logger.getLogger(Sf2DisasmManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("com.sfc.sffc.cutscene.io.DisassemblyManager.exportCutscene() - SF2DISASM cutscene exported.");  
        return;
    }
    
    final protected static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }     
    
    private static String convert(Cutscene cutscene){
        sb = new StringBuilder();
        entities = new HashMap<Integer,Entity>();
        byte[] data = cutscene.getData();
        int cursor = 0;
        byte b = data[cursor];
        int l = 0;
        int entityIndex;
        Entity entity;
        int facing;
        String comment;
        while(b!=-1){
            
            switch(b&0xFF){
                
                case(0x0):
                    
                    sb.append("    nextSingleText $"+Integer.toHexString(portraitParams)
                                                + "," + Integer.toString(portraitIndex));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x2):
                    sb.append("    nextText $"+Integer.toHexString(portraitParams)
                                                + "," + Integer.toString(portraitIndex));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x3):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4):
                    sb.append("    playSound $"+Integer.toHexString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x5):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x6):
                    int lineIndex = (data[cursor+1]&0xFF) + (256 * ((data[cursor+2]&0xFF) -1));
                    sb.append("    textCursor $"+Integer.toHexString(lineIndex));
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x7):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x8):
                    sb.append("    command " 
                                + Integer.toHexString(data[cursor]&0xFF) + " "
                                + Integer.toHexString(data[cursor+1]&0xFF) + " "
                                + Integer.toHexString(data[cursor+2]&0xFF) + " "
                                + Integer.toHexString(data[cursor+3]&0xFF)
                                );
                    l=4;
                    sb.append("\n");
                    break;
                
                case(0x9):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0xA):
                    String subroutineOffset = Integer.toHexString(data[cursor+2]&0xFF)
                            + Integer.toHexString(data[cursor+1]&0xFF);
                    sb.append("    executeSubroutine 0x"+subroutineOffset);
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0xB):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0xC):
                    sb.append("    command " 
                                + Integer.toHexString(data[cursor]&0xFF) + " "
                                + Integer.toHexString(data[cursor+1]&0xFF) + " "
                                + Integer.toHexString(data[cursor+2]&0xFF) + " "
                                + Integer.toHexString(data[cursor+3]&0xFF) + " "
                                + Integer.toHexString(data[cursor+4]&0xFF) + " "
                                + Integer.toHexString(data[cursor+5]&0xFF) + " "
                                + Integer.toHexString(data[cursor+6]&0xFF) + " "
                                + Integer.toHexString(data[cursor+7]&0xFF)
                                );
                    l=8;
                    sb.append("\n");
                    break;
                
                case(0xD):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0xE):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0xF):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x10):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x11):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x12):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x13):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x14):
                    sb.append("    csWait "+Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x15):
                    entityIndex = data[cursor+1]&0xFF;
                    facing = data[cursor+2]&0xFF;
                    entity = entities.get(entityIndex);
                    if(entity!=null){
                        entity.setFacing(facing);
                    }else{
                        Entity newEntity = new Entity();
                        newEntity.setFacing(facing);
                        entities.put(entityIndex, newEntity);
                    }
                    sb.append("    setFacing $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + getFacing(data[cursor+2]&0xFF));
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x16):
                    sb.append("    setCamDest "+Integer.toString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF));
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x17):
                    String action = null;
                    sb.append("    entityActionsWait $"+Integer.toHexString(data[cursor+1]&0xFF));
                    sb.append("\n");
                    switch(data[cursor+2]&0xFF){
                        case 0:
                            action = " moveRight";
                            break;
                        case 1:
                            action = " moveUp";
                            break;
                        case 2:
                            action = " moveLeft";
                            break;
                        case 3:
                            action = " moveDown";
                            break;
                        default:
                            action = " ; UNKNOWN MOVE";
                            break;
                    }
                    sb.append("    "+action+" "+Integer.toString(data[cursor+3]&0xFF));
                    sb.append("\n");
                    sb.append("    endActions");
                    l=4;
                    sb.append("\n");
                    break;
                
                case(0x18):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x19):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x1E):
                    entityIndex = data[cursor+1]&0xFF;
                    entity = entities.get(entityIndex);
                    if(entity!=null){
                        facing = entity.getFacing();
                        comment = "";
                    } else{
                        facing = 3;
                        comment = " ; conversion could not get entity's current facing, set DOWN by default";
                    }
                    sb.append("    setPos $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + "," + getFacing(facing)
                                            + comment);
                    l=4;
                    sb.append("\n");
                    break;
                
                case(0x1F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x20):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x21):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x22):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x23):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x24):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x25):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x26):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x27):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x28):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x29):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x2A):
                    sb.append("    newEntity $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + ",63"
                                            + ",63"
                                            + ",DOWN"
                                            + "," + Integer.toString(data[cursor+2]&0xFF));
                    sb.append("\n    setActscript $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + ",eas_Init");
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x2B):
                    /*sb.append("    initializeEntityPosition $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + ",$" + Integer.toHexString(data[cursor+4]&0xFF));*/
                    entityIndex = data[cursor+1]&0xFF;
                    entity = entities.get(entityIndex);
                    if(entity!=null){
                        facing = entity.getFacing();
                        comment = "";
                    } else{
                        facing = 3;
                        comment = " ; conversion could not get entity's current facing, set DOWN by default";
                    }
                    sb.append("    setPos $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + "," + getFacing(facing)
                                            + comment);                    
                    l=5;
                    sb.append("\n");
                    break;
                
                case(0x2C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x2D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x2E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x2F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x30):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x31):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x32):
                    /* sb.append("    displayPortrait "+Integer.toString(data[cursor+1]&0xFF)); */
                    portraitIndex = data[cursor+1]&0x3F;
                    if((data[cursor+1]&0x40)!=0){
                        portraitParams = 0x80;
                    }else{
                        portraitParams = 0;
                    }
                    l=2;
                    break;
                
                case(0x33):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x34):
                    /* sb.append("    entityRotate $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + " " + Integer.toString(data[cursor+2]&0xFF)); */
                    sb.append("    ; rotate to implement here");
                    
                    /*
                    customActscriptWait $8C
                        ac_setAnimCounter $0   ;   
                        ac_setFlip $2          ;   
                        ac_updateSprite        ;   
                        ac_jump eas_Idle       ;   
                    ac_end
                    */
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x35):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x36):
                    /* sb.append("    entityRotateShrink $" + Integer.toHexString(data[cursor+1]&0xFF) + " "
                                + Integer.toString(data[cursor+2]&0xFF) + " "
                                + Integer.toString(data[cursor+3]&0xFF)
                                ); */
                    sb.append("    ; rotate + shrink to implement here");
                    l=4;
                    sb.append("\n");
                    break;
                
                case(0x37):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x38):
                    sb.append("    ; command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x39):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x3A):
                    /* sb.append("    entityJump $"+Integer.toHexString(data[cursor+1]&0xFF)); */
                    sb.append("    setActscript $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            +",eas_Jump");
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x3B):
                    /* sb.append("    entityPulse $"+Integer.toHexString(data[cursor+1]&0xFF)); */
                    sb.append("    shiver $"+Integer.toHexString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x3C):
                    sb.append("    ; command " 
                                + Integer.toHexString(data[cursor]&0xFF) + " "
                                + Integer.toHexString(data[cursor+1]&0xFF) + " "
                                + Integer.toHexString(data[cursor+2]&0xFF)
                                );
                    l=3;
                    sb.append("\n");
                    break;
                
                case(0x3D):
                    sb.append("    setBlocks "+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + "," + Integer.toString(data[cursor+4]&0xFF)
                                            + "," + Integer.toString(data[cursor+5]&0xFF)
                                            + "," + Integer.toString(data[cursor+6]&0xFF));
                    l=7;
                    sb.append("\n");
                    break;
                
                case(0x3E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x3F):
                    int flashNumber = data[cursor+1]&0xFF;
                    for(int i=0;i<flashNumber;i++){
                        sb.append("    flashWhite 10\n");
                        sb.append("    csWait 2\n");
                    }
                    l=2;
                    break;
                
                case(0x40):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x41):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x42):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x43):
                    sb.append("    loadMapFadeIn "+Integer.toString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF));
                    l=4;
                    sb.append("\n");
                    break;
                
                case(0x44):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x45):
                    sb.append("    fadeInB");
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x46):
                    sb.append("    fadeOutB");
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x47):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x48):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x49):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x4E):
                    sb.append("    ; Undocumented command " 
                                + Integer.toHexString(data[cursor]&0xFF) + " "
                                + Integer.toHexString(data[cursor+1]&0xFF) + " "
                                + Integer.toHexString(data[cursor+2]&0xFF) + " "
                                + Integer.toHexString(data[cursor+3]&0xFF) + " "
                                + Integer.toHexString(data[cursor+4]&0xFF) + " "
                                + Integer.toHexString(data[cursor+5]&0xFF) + " "
                                + Integer.toHexString(data[cursor+6]&0xFF)
                                );
                    l=7;
                    sb.append("\n");
                    break;
                
                case(0x4F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x50):
                    sb.append("    moveSequence ");
                    l = parseMoveSequence(data,cursor);
                    sb.append("\n");
                    break;
                
                case(0x51):
                    sb.append("    ; command  "+Integer.toHexString(data[cursor]&0xFF)
                                            + " " + Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x52):
                    /* sb.append("    setMoveSequenceEntityNumber " + Integer.toHexString(data[cursor+1]&0xFF)); */
                    moveSequenceEntityNumber = data[cursor+1]&0xFF;
                    l=2;
                    break;
                
                case(0x53):
                    sb.append("    ; command " + Integer.toHexString(data[cursor]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x54):
                    sb.append("    ; command  "+Integer.toHexString(data[cursor]&0xFF)
                                            + " " + Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x55):
                    sb.append("    setQuake "+Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    sb.append("\n");
                    break;
                
                case(0x56):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x57):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x58):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x59):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                case(0x5F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
                default:
                    sb.append("    ; UNEXPECTED COMMAND VALUE " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    sb.append("\n");
                    break;
                
            }
            
            
            cursor+=l;
            b = data[cursor];
        }
        
        
        sb.append("    csEnd");
        
        
        
        return sb.toString();
    }
    
    
    private static String getFacing(int facing){
        switch(facing){
            case 0:
                return "RIGHT";
            case 1:
                return "UP";
            case 2:
                return "LEFT";
            case 3:
                return "DOWN";
            default:
                return "ERROR";
        }
    }
    
    
    private static int parseMoveSequence(byte[] data, int start){
        int length = 0;
        int cursor = start + 1;
        cursor+=moveSequenceEntityNumber;
        for(int i=0;i<moveSequenceEntityNumber;i++){
            sb.append("\n");
            sb.append("     entity "+Integer.toHexString(data[start+1+i]&0xFF));
            int moveCommand = data[cursor];
            while((moveCommand & 0xC0)!=0xC0){
                sb.append("\n");
                switch(data[cursor]&0xFF){
                    case 0x0:
                        sb.append("      Go RIGHT "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0x1:
                        sb.append("      Go UP "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0x2:
                        sb.append("      Go LEFT "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0x3:
                        sb.append("      Go DOWN "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0x4:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8:
                        sb.append("      Quick move "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0x9:
                        sb.append("      Quick move "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0xA:
                        sb.append("      Quick move "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0xB:
                        sb.append("      Quick move "+Integer.toString(data[cursor+1]&0xFF)+" blocks");
                        break;
                        
                    case 0xC:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0xD:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0xE:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0xF:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x10:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x11:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x12:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x13:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x14:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x15:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x16:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x17:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x18:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x19:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x1F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x20:
                        sb.append("      Jump RIGHT "+Integer.toString(data[cursor+1]&0xFF)+" times");
                        break;
                        
                    case 0x21:
                        sb.append("      Jump UP "+Integer.toString(data[cursor+1]&0xFF)+" times");
                        break;
                        
                    case 0x22:
                        sb.append("      Jump LEFT "+Integer.toString(data[cursor+1]&0xFF)+" times");
                        break;
                        
                    case 0x23:
                        sb.append("      Jump DOWN "+Integer.toString(data[cursor+1]&0xFF)+" times");
                        break;
                        
                    case 0x24:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x25:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x26:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x27:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x28:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x29:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x2F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x30:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x31:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x32:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x33:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x34:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x35:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x36:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x37:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x38:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x39:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x3F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x40:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x41:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x42:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x43:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x44:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x45:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x46:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x47:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x48:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x49:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x4F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x50:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x51:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x52:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x53:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x54:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x55:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x56:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x57:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x58:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x59:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x5F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x60:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x61:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x62:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x63:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x64:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x65:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x66:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x67:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x68:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x69:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x6F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x70:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x71:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x72:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x73:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x74:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x75:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x76:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x77:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x78:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x79:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x7F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x80:
                        sb.append("      Face RIGHT and wait " + Integer.toHexString(data[cursor+1]&0xFF)+" block moves");
                        break;
                        
                    case 0x81:
                        sb.append("      Face UP and wait " + Integer.toHexString(data[cursor+1]&0xFF)+" block moves");
                        break;
                        
                    case 0x82:
                        sb.append("      Face LEFT and wait " + Integer.toHexString(data[cursor+1]&0xFF)+" block moves");
                        break;
                        
                    case 0x83:
                        sb.append("      Face DOWN and wait " + Integer.toHexString(data[cursor+1]&0xFF)+" block moves");
                        break;
                        
                    case 0x84:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x85:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x86:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x87:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x88:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x89:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8A:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8B:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8C:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8D:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8E:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    case 0x8F:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                        
                    default:
                        sb.append("      unknownMoveCommand "+Integer.toHexString(data[cursor]&0xFF)
                                            + " " + Integer.toHexString(data[cursor+1]&0xFF));
                        break;
                }
                cursor+=2;
                moveCommand = data[cursor];
            }
            cursor++;
        }
        length = cursor-start;
        return length;
    }
    
    
    
    
}
