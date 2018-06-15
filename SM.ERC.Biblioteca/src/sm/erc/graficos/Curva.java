/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa curva con un punto de control.
 * Proporciona métodos para pintarla, desplazarla y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class Curva extends Figura{

    private QuadCurve2D curva;

    /**
     * Crea una nueva Curva.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase QuadCurve2D.Double a partir de los puntos especificados.
     * @param colorTrazo color del trazo
     * @param grosor grosor del trazo
     * @param trazo estilo del trazo
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x1 Coordenada x del punto inicial
     * @param y1 Coordenada y del punto inicial
     * @param ctrlx Coordenada x del punto de control
     * @param ctrly Coordenada y del punto de control
     * @param x2 Coordenada x del punto final
     * @param y2 Coordenada y del punto final
     */
    public Curva(Color colorTrazo, int grosor, String trazo, boolean alisar, float transp,
            double x1, double y1, double ctrlx, double ctrly, double x2, double y2){   
        
        super(Color.BLACK, Color.BLACK, colorTrazo, grosor, trazo, ModoRelleno.SIN, alisar, transp);
        curva = new QuadCurve2D.Double(x1,y1,ctrlx,ctrly,x2,y2);    
    }
    /**
     * Devuelve coordenada x del punto inicial de esta Curva.
     * @return coordenada x del punto inicial
     */
    public double getX1(){
        return curva.getX1();
    }
    /**
     * Devuelve coordenada y del punto inicial de esta Curva.
     * @return coordenada y del punto inicial
     */
    public double getY1(){
        return curva.getY1();
    }
    /**
     * Devuelve coordenada x del punto final de esta Curva.
     * @return coordenada x del punto final
     */
    public double getX2(){
        return curva.getX2();
    }
    /**
     * Devuelve coordenada y del punto final de esta Curva.
     * @return coordenada y del punto final
     */
    public double getY2(){
        return curva.getY2();
    }
    /**
     * Cambia la localización de los puntos de inicio, control y final de esta Curva
     * a la de los puntos especificados.
     * Llama al método setCurve de QuadCurve2D.
     * @param x1 Coordenada x del punto inicial
     * @param y1 Coordenada y del punto inicial
     * @param ctrlx Coordenada x del punto de control
     * @param ctrly Coordenada y del punto de control
     * @param x2 Coordenada x del punto final
     * @param y2 Coordenada y del punto final
     */
    public void setCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2){
        curva.setCurve(x1,y1,ctrlx,ctrly,x2,y2);
    }
    
    @Override
    public void paint(Graphics2D g2d){
        super.paintBoundingBox(g2d);
        g2d.setPaint(colorTrazo);
        g2d.setStroke(stroke);
        if(alisar)
            g2d.setRenderingHints(render);
        g2d.setComposite(comp);
                
        g2d.draw(curva);
    }

    
    @Override
    public boolean contains(Point2D pos){
        return curva.contains(pos);
    }

    @Override
    public void setLocation(double dx, double dy){
        curva.setCurve(curva.getX1()+dx, curva.getY1()+dy, curva.getCtrlX()+dx, curva.getCtrlY()+dy,
                curva.getX2()+dx, curva.getY2()+dy);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return curva.getBounds2D();
    }

    
}
