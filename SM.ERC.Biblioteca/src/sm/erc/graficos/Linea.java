/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa línea recta.
 * Proporciona métodos para pintarla, desplazarla y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class Linea extends Figura{
    
    private Line2D.Double linea;

    /**
     * Crea una nueva Linea.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase Line2D.Double a partir de los puntos especificados.
     * @param colorTrazo color del trazo
     * @param grosor grosor del trazo
     * @param trazo estilo del trazo
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x1 coordenada x del punto de inicio
     * @param y1 coordenada y del punto de inicio
     * @param x2 coordenada x del punto final
     * @param y2 coordenada y del punto final
     */
    public Linea(Color colorTrazo, int grosor, String trazo, boolean alisar, float transp,
            double x1, double y1, double x2, double y2) {
        super(Color.BLACK, Color.BLACK, colorTrazo, grosor, trazo, ModoRelleno.SIN, alisar, transp);
        linea = new Line2D.Double(x1,y1,x2,y2);
    }
    
    /**
     * Cambia la localización de los puntos de la línea a la de los puntos especificados.
     * Llama al método setLine de la clase Line2D.Double
     * @param p1 punto inicial de la línea
     * @param p2 punto final de la línea
     */
    public void setLine(Point2D p1, Point2D p2){
        linea.setLine(p1, p2);        
    }


    @Override
    public void paint(Graphics2D g2d){
        super.paintBoundingBox(g2d);
        
        g2d.setPaint(colorTrazo);
        g2d.setStroke(stroke);
        g2d.setComposite(comp);
        if(alisar)
            g2d.setRenderingHints(render);
          
        g2d.draw(linea);
    }


    @Override
    public boolean contains(Point2D p){
        return linea.ptLineDist(p)<=2.0;
    }
    
    @Override
    public void setLocation(double dx, double dy){
        Point2D pIni = new Point2D.Double(linea.getP1().getX()+dx, linea.getP1().getY()+dy);
        Point2D pFin= new Point2D.Double(linea.getP2().getX()+dx, linea.getP2().getY()+dy);

        linea.setLine(pIni, pFin);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return linea.getBounds2D();
    }
    
}
