/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa figura con forma de rectángulo con esquinas redondeadas.
 * Proporciona métodos para pintarla, desplazarla y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class RectanguloRedondeado extends Figura{
    
    private RoundRectangle2D roundRect;

    /**
     * Crea nuevo RectanguloRedondeado.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase RoundRectangle2D.Double
     * @param color color de relleno (frente)
     * @param color2 color de relleno (fondo)
     * @param colorTrazo color de trazo
     * @param grosor grosor de trazo
     * @param trazo estilo de trazo
     * @param modoRelleno modo de relleno
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x coordenada x de la esquina superior izquierda del objeto RoundRectangle2D.Double
     * @param y coordenada y de la esquina superior izquierda del objeto RoundRectangle2D.Double
     * @param w anchura del objeto RoundRectangle2D.Double
     * @param h altura del objeto RoundRectangle2D.Double
     */
    public RectanguloRedondeado(Color color, Color color2, Color colorTrazo, int grosor, String trazo, ModoRelleno modoRelleno,
            boolean alisar, float transp, double x, double y, double w, double h, double arcw, double arch) {
        super(color, color2, colorTrazo, grosor,trazo, modoRelleno, alisar, transp);
        roundRect = new RoundRectangle2D.Double(x,y,w,h,arcw,arch);
    }

    /**
     * Establece la diagonal del objeto RoundRectangle2D.Double a partir de los puntos especificados.
     * @param p1 punto inicial de la diagonal
     * @param p2 punto final de la diagonal
     */
    public void setFrameFromDiagonal(Point2D p1, Point2D p2){
        roundRect.setFrameFromDiagonal(p1,p2);
    }
    
    @Override
    public void paint(Graphics2D g2d){
        super.paintBoundingBox(g2d);
        g2d.setPaint(color);
        g2d.setComposite(comp); //Transparencia
        
        if(modoRelleno != ModoRelleno.SIN){
            Point2D p1 = new Point2D.Double(roundRect.getX(),roundRect.getY());
            if (modoRelleno == ModoRelleno.DEGRADADO_H){        
                Point2D p2 = new Point2D.Double(roundRect.getX()+roundRect.getWidth(),roundRect.getY());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if (modoRelleno == ModoRelleno.DEGRADADO_V){
                Point2D p2 = new Point2D.Double(roundRect.getX(),roundRect.getY()+roundRect.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.DEGRADADO_D){
                Point2D p2 = new Point2D.Double(roundRect.getX()+roundRect.getWidth(),roundRect.getY()+roundRect.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.RADIAL){
                Point2D center = new Point2D.Double(roundRect.getCenterX(),roundRect.getCenterY());
                float radio = 20;
                float[] dist = {0.2f, 1.0f};
                Color[] vColor = {color, color2};
                g2d.setPaint(new RadialGradientPaint(center, radio, dist, vColor,MultipleGradientPaint.CycleMethod.REPEAT));
            }
            g2d.fill(roundRect);
        }
        
        g2d.setPaint(colorTrazo); 
        g2d.setStroke(stroke);  //Trazo         
        if(alisar)
            g2d.setRenderingHints(render);  //Renderización
   
        g2d.draw(roundRect);
    }

    
    @Override
    public boolean contains(Point2D pos){
        return roundRect.contains(pos);
    }


    @Override
    public void setLocation(double dx, double dy){
        roundRect.setFrame(roundRect.getMinX()+dx, roundRect.getMinY()+dy, 
                  roundRect.getWidth(), roundRect.getHeight());
    }

    @Override
    public Rectangle2D getBoundingBox() {
        Rectangle2D bounding = roundRect.getBounds2D();
        Rectangle2D box = new Rectangle2D.Double(bounding.getMinX()-1, bounding.getMinY()-1,
                bounding.getWidth()+2, bounding.getHeight()+2);
        return box;
    }

    
}
