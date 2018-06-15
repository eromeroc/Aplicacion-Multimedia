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
 * Representa operación componente a componente que da el valor RGB: (0,0,0) a píxeles aleatorios
 * 
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class OperacionComp extends BufferedImageOpAdapter{
    
    public OperacionComp(){}
    
    /**
     * Aplica la operación componente a componente.
     * Recorremos cada pixel, obtenemos valor aleatorio entre 0 y1. Si es el valor >0.75
     * los valores de cada componente del pixel pasan a ser 0. Si no, se mantienen constantes
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
        double num;
        
        //Recorremos cada pixel
        for(int x=0; x < srcRaster.getWidth(); x++){
            for(int y=0; y < srcRaster.getHeight(); y++){
                num = Math.random();
                if(num>0.75){
                    for(int band=0; band<srcRaster.getNumBands();band++){  
                        int sample = srcRaster.getSample(x,y,band);
                        sample=0;                        
                        destRaster.setSample(x,y,band,sample);
                    }
                }
            }
        }
        return dest;
        
    }
    
}
