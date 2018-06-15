/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Representa operación pixel a pixel que maximiza el color predominante en cada uno
 * 
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class OperacionPixel extends BufferedImageOpAdapter{
    
    public OperacionPixel(){}
    /**
     * Aplica la operación pixel a pixel.
     * Recorremos cada pixel, calculamos la banda que tiene un valor más alto y 
     * le sumamos 10 a dicha banda. Las demás se mantienen constantes.
     * 
     * @param src imagen fuente
     * @param dest imagen destino
     * @return imagen destino
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        if(src==null)
            throw new NullPointerException("src image is null");
        if(dest==null)
            dest = createCompatibleDestImage(src,null);
        
        WritableRaster srcRaster=src.getRaster();
        WritableRaster destRaster=dest.getRaster();
        
        //Recorremos cada pixel
        for(int x=0; x < srcRaster.getWidth(); x++){
            for(int y=0; y < srcRaster.getHeight(); y++){
                int[] pixel = null;   
                int max = 0;
                int maxindex = -1;
                pixel = srcRaster.getPixel(x,y,pixel);
                
                if(pixel[0]!=0 || pixel[1]!=0 || pixel[2]!=0){ //Si no es negro
                    if(pixel[0]> pixel[1]){
                        if(pixel[0]> pixel[2]){
                            max = pixel[0];
                            maxindex = 0;                     
                        }else{
                            max = pixel[2];
                            maxindex = 2;
                        }
                    }else if(pixel[1] > pixel[2]){
                        max = pixel[1];
                        maxindex=1;
                    }else if(pixel[2]>max){
                        max=pixel[2];
                        maxindex=2;
                    }
                    
                    if(maxindex>=0){
                        max+=10;
                        if(max>255) max=255;
                        pixel[maxindex] = max;
                    }                    
                }
                

                destRaster.setPixel(x,y,pixel);

            }
        }
        int pixel[] = null;
        pixel = src.getRaster().getPixel(300, 200, pixel);
        System.out.println(pixel[0]);
        System.out.println(pixel[1]);
        System.out.println(pixel[2]);
        return dest;
        
    }
    
}
