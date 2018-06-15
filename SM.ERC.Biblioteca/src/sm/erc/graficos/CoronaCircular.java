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
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa figura con forma de corona circular o elíptica.
 * Proporciona métodos para pintarla, desplazarla y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class CoronaCircular extends Figura{

    private Area area1;
    private Area area2;
    private Ellipse2D.Double elipse1;
    private Ellipse2D.Double elipse2;
    
    /**
     * Crea nueva CoronaCircular.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase Area a partir de los parámetros x,y,w y h especificados
     * @param color color de relleno (frente)
     * @param color2 color de relleno (fondo)
     * @param colorTrazo color de trazo
     * @param grosor grosor de trazo
     * @param trazo estilo de trazo
     * @param modoRelleno modo de relleno
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x coordenada x de la esquina del área
     * @param y coordenada y de la esquina del área
     * @param w anchura del área
     * @param h altura del área
     */
    public CoronaCircular(Color color, Color color2, Color colorTrazo, int grosor, String trazo, ModoRelleno modoRelleno, boolean alisar,
            float transp, double x, double y, double w, double h){
        super(color, color2, colorTrazo, grosor, trazo, ModoRelleno.LISO, alisar, transp);
        
        this.elipse1 = new Ellipse2D.Double(x,y,w,h);
        this.elipse2 = new Ellipse2D.Double(x,y,w,h);
        this.area1 = new Area(elipse1);
        this.area2 = new Area(elipse2);
        this.area1.subtract(area2);
    }
    
    /**
     * Establece la diagonal del marco rectangular del área usando los dos puntos 
     * especificados. Establece la diagonal de la elipse exterior a partir de los puntos dado, 
     * establece la elipse interior a partir de la exterior y crea el área sustrayendo al área de
     * la elipse mayor la menor.
     * @param p1 punto inicial de la diagonal
     * @param p2 punto final de la diagonal
     */
    public void setFrameFromDiagonal(Point2D p1, Point2D p2){
        
        elipse1.setFrameFromDiagonal(p1,p2);
        
        double radioX = elipse1.getWidth()/4;
        double radioY = elipse1.getHeight()/4;
        double coordX, coordY;
        if(p1.getX()<p2.getX())
            coordX = p1.getX()+radioX;
        else
            coordX = p1.getX()-radioX;
        if(p1.getY()<p2.getY())
            coordY = p1.getY()+radioY;
        else
            coordY = p1.getY()-radioY;
        
        Point2D center = new Point2D.Double(elipse1.getCenterX(), elipse1.getCenterY());    
        Point2D corner = new Point2D.Double(coordX, coordY);
 
        elipse2.setFrameFromCenter(center,corner);
        
        area1 = new Area(elipse1);
        area2 = new Area(elipse2);
        this.area1.subtract(area2);
        
    }


    @Override
    public void paint(Graphics2D g2d){
        super.paintBoundingBox(g2d);
        
        g2d.setPaint(color);
        g2d.setComposite(comp); //Transparencia
        
        if(modoRelleno != ModoRelleno.SIN){
            Point2D p1 = new Point2D.Double(elipse1.getX(),elipse1.getY());
            if (modoRelleno == ModoRelleno.DEGRADADO_H){        
                Point2D p2 = new Point2D.Double(elipse1.getX()+elipse1.getWidth(),elipse1.getY());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if (modoRelleno == ModoRelleno.DEGRADADO_V){
                Point2D p2 = new Point2D.Double(elipse1.getX(),elipse1.getY()+elipse1.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.DEGRADADO_D){
                Point2D p2 = new Point2D.Double(elipse1.getX()+elipse1.getWidth(),elipse1.getY()+elipse1.getHeight());
                g2d.setPaint(new GradientPaint(p1, color, p2, color2));
            }else if(modoRelleno == ModoRelleno.RADIAL){
                Point2D center = new Point2D.Double(elipse1.getCenterX(),elipse1.getCenterY());
                float radio = 20;
                float[] dist = {0.2f, 1.0f};
                Color[] vColor = {color, color2};
                g2d.setPaint(new RadialGradientPaint(center, radio, dist, vColor,MultipleGradientPaint.CycleMethod.REPEAT));
            }
            g2d.fill(area1);
        }
        
        
        g2d.setPaint(colorTrazo); 
        g2d.setStroke(stroke);  //Trazo         
        if(alisar)
            g2d.setRenderingHints(render);  //Renderización
   
        g2d.draw(area1);
    }

    @Override
    public boolean contains(Point2D pos) {
        return area1.contains(pos);
    }

    @Override
    public void setLocation(double difX, double difY) {
        AffineTransform at = new AffineTransform();
        at.translate(difX, difY);
        area1.transform(at);
    }
    
    @Override
    public Rectangle2D getBoundingBox(){
        return area1.getBounds2D();
    }
}
