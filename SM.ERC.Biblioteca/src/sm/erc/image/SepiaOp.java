/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.image;

import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * Representa el operador de efecto sepia
 * 
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class SepiaOp extends BufferedImageOpAdapter{

    public SepiaOp(){}
    
    /**
     * Aplica la operación sepia pixel a pixel.
     * Comprobamos que el nuevo valor de cada componente del pixel no sea mayor de 255,
     * en caso de serlo se le asigna el  valor 255.
     * @param src imagen fuente
     * @param dest imagen destino
     * @return imagen destino
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        if(src==null){
            throw new NullPointerException("src image is null");
        }
        if(dest==null){
            dest = createCompatibleDestImage(src,null);
        }
        for(int x=0; x<src.getWidth(); x++){
            for(int y=0; y<src.getHeight(); y++){
                int pixel[] = null;
                int nuevoPixel[] = {0,0,0};
                pixel = src.getRaster().getPixel(x, y, pixel);
                
                nuevoPixel[0] = (int) (0.393*pixel[0] + 0.769*pixel[1] + 0.189*pixel[2]); 
                nuevoPixel[1] = (int) (0.349*pixel[0] + 0.686*pixel[1] + 0.168*pixel[2]);
                nuevoPixel[2] = (int) (0.272*pixel[0] + 0.534*pixel[1] + 0.131*pixel[2]);
                
                if ( nuevoPixel[0] > 255)
                     nuevoPixel[0] = 255;
                if ( nuevoPixel[1] > 255 )
                    nuevoPixel[1] = 255;
                if (nuevoPixel[2] > 255)
                    nuevoPixel[2] = 255;
                
                dest.getRaster().setPixel(x, y, nuevoPixel); 
            }
        }
        return dest;
    }
    
}
