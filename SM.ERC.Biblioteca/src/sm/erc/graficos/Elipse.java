/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa figura con forma elíptica.
 * Proporciona métodos para pintarla, desplazarla y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class Elipse extends Figura{
    
    private Ellipse2D.Double elipse;
    
    /**
     * Crea nueva Elipse.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase Ellipse2D.Double
     * @param color color de relleno (frente)
     * @param color2 color de relleno (fondo)
     * @param colorTrazo color de trazo
     * @param grosor grosor de trazo
     * @param trazo estilo de trazo
     * @param modoRelleno modo de relleno
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x coordenada x de la esquina superior izquierda del rectángulo que enmarca la Ellipse2D.Double
     * @param y coordenada y de la esquina superior izquierda del rectángulo que enmarca la Ellipse2D.Double
     * @param w anchura del rectángulo que enmarca la Ellipse2D.Double
     * @param h altura del rectángulo que enmarca la Ellipse2D.Double
     */
    public Elipse(Color color, Color color2, Color colorTrazo, int grosor, String trazo, ModoRelleno modoRelleno, boolean alisar, float transp,
            double x, double y, double w, double h){
        super(color, color2, colorTrazo, grosor, trazo, modoRelleno, alisar, transp);
        elipse = new Ellipse2D.Double(x,y,w,h);
    }
    
    
    /**
     * Establece la diagonal rectángulo que enmarca esta Elipse a partir de los puntos especificados.
     * @param p1 punto inicial de la diagonal
     * @param p2 punto final de la diagonal
     */
    public void setFrameFromDiagonal(Point2D p1, Point2D p2){
        elipse.setFrameFromDiagonal(p1,p2);
    }
    
    
    @Override
    public void setLocation(double dx, double dy){
        elipse.setFrame(elipse.getMinX()+dx, elipse.getMinY()+dy, elipse.getWidth(), elipse.getHeight());
    }
    

    @Override
    public void paint(Graphics2D g2d){
        super.paintBoundingBox(g2d);
        g2d.setPaint(color);
        g2d.setComposite(comp); //Transparencia
        
        if(modoRelleno != ModoRelleno.SIN){
            Point2D p1 = new Point2D.Double(elipse.getX(),elipse.getY());
            if (modoRelleno == ModoRelleno.DEGRADADO_H){        
                Point2D p2 = new Point2D.Double(elipse.getX()+elipse.getWidth(),elipse.getY());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if (modoRelleno == ModoRelleno.DEGRADADO_V){
                Point2D p2 = new Point2D.Double(elipse.getX(),elipse.getY()+elipse.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.DEGRADADO_D){
                Point2D p2 = new Point2D.Double(elipse.getX()+elipse.getWidth(),elipse.getY()+elipse.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.RADIAL){
                Point2D center = new Point2D.Double(elipse.getCenterX(),elipse.getCenterY());
                float radio = 20;
                float[] dist = {0.2f, 1.0f};
                Color[] vColor = {color, color2};
                g2d.setPaint(new RadialGradientPaint(center, radio, dist, vColor,CycleMethod.REPEAT));
            }
            g2d.fill(elipse);
        }
        
        g2d.setPaint(colorTrazo); 
        g2d.setStroke(stroke);  //Trazo         
        if(alisar)
            g2d.setRenderingHints(render);  //Renderización
   
        g2d.draw(elipse);
    }
    
    @Override
    public boolean contains(Point2D pos){
        return elipse.contains(pos);
    }

    @Override
    public Rectangle2D getBoundingBox(){
        return elipse.getBounds2D();
    }
    
}
