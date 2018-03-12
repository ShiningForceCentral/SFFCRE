/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfc.sffc.cutscene.io;

import com.sfc.sffc.cutscene.Cutscene;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wiz
 */
public class Sf2DisasmManager {

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
        StringBuilder sb = new StringBuilder();
        byte[] data = cutscene.getData();
        int cursor = 0;
        byte b = data[cursor];
        int l = 0;
        while(b!=-1){
            
            switch(b&0xFF){
                
                case(0x0):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x2):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x4):
                    sb.append("    soundCommand $"+Integer.toHexString(data[cursor+1]&0xFF));
                    l=2;
                    break;
                
                case(0x5):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x6):
                    sb.append("    setTextCursor $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF));
                    l=3;
                    break;
                
                case(0x7):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x8):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x9):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0xA):
                    String subroutineOffset = Integer.toHexString(data[cursor+2]&0xFF)
                            + Integer.toHexString(data[cursor+1]&0xFF);
                    sb.append("    executeSubroutine 0x"+subroutineOffset);
                    l=3;
                    break;
                
                case(0xB):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0xC):
                    sb.append("    ; Undocumented command " 
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
                    break;
                
                case(0xD):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0xE):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0xF):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x10):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x11):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x12):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x13):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x14):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x15):
                    sb.append("    setFacing $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + getFacing(data[cursor+2]&0xFF));
                    l=3;
                    break;
                
                case(0x16):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x17):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x18):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x19):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x1F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x20):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x21):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x22):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x23):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x24):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x25):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x26):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x27):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x28):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x29):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x2A):
                    sb.append("    initializeEntity $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + ",$" + Integer.toHexString(data[cursor+2]&0xFF));
                    l=3;
                    break;
                
                case(0x2B):
                    sb.append("    initializeEntityPosition $"+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + ",$" + Integer.toHexString(data[cursor+4]&0xFF));
                    l=5;
                    break;
                
                case(0x2C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x2D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x2E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x2F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x30):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x31):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x32):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x33):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x34):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x35):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x36):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x37):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x38):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x39):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3D):
                    sb.append("    blockCopy "+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF)
                                            + "," + Integer.toString(data[cursor+4]&0xFF)
                                            + "," + Integer.toString(data[cursor+5]&0xFF)
                                            + "," + Integer.toString(data[cursor+6]&0xFF));
                    l=7;
                    break;
                
                case(0x3E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x3F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x40):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x41):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x42):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x43):
                    sb.append("    loadMap "+Integer.toHexString(data[cursor+1]&0xFF)
                                            + "," + Integer.toString(data[cursor+2]&0xFF)
                                            + "," + Integer.toString(data[cursor+3]&0xFF));
                    l=4;
                    break;
                
                case(0x44):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x45):
                    sb.append("    mapFadeIn");
                    l=1;
                    break;
                
                case(0x46):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x47):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x48):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x49):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x4A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x4B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x4C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x4D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
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
                    break;
                
                case(0x4F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x50):
                    sb.append("    moveSequence ");
                    l=1;
                    break;
                
                case(0x51):
                    sb.append("    ; Undocumented command  "+Integer.toHexString(data[cursor]&0xFF)
                                            + " " + Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    break;
                
                case(0x52):
                    sb.append("    setMoveSequenceEntityNumber " + Integer.toHexString(data[cursor+1]&0xFF));
                    l=2;
                    break;
                
                case(0x53):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=2;
                    break;
                
                case(0x54):
                    sb.append("    ; Undocumented command  "+Integer.toHexString(data[cursor]&0xFF)
                                            + " " + Integer.toString(data[cursor+1]&0xFF));
                    l=2;
                    break;
                
                case(0x55):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x56):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x57):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x58):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x59):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5A):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5B):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5C):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5D):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5E):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                case(0x5F):
                    sb.append("    ; Undocumented command " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
                default:
                    sb.append("    ; UNEXPECTED COMMAND VALUE " + Integer.toHexString(data[cursor]&0xFF));
                    l=1;
                    break;
                
            }
            
            sb.append("\n");
            
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
    
    
    
    
    
    
    
}
